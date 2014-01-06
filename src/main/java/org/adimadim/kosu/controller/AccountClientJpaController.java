/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.adimadim.kosu.controller;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.LockModeType;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.adimadim.kosu.controller.exceptions.NonexistentEntityException;
import org.adimadim.kosu.entity.AccountClient;

/**
 *
 * @author Adem
 */
public class AccountClientJpaController implements Serializable {

    public AccountClientJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(AccountClient accountClient) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(accountClient);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(AccountClient accountClient) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            accountClient = em.merge(accountClient);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = accountClient.getAccountId();
                if (findAccountClient(id) == null) {
                    throw new NonexistentEntityException("The accountClient with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            AccountClient accountClient;
            try {
                accountClient = em.getReference(AccountClient.class, id);
                accountClient.getAccountId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The accountClient with id " + id + " no longer exists.", enfe);
            }
            em.remove(accountClient);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<AccountClient> findAccountClientEntities() {
        return findAccountClientEntities(true, -1, -1);
    }

    public List<AccountClient> findAccountClientEntities(int maxResults, int firstResult) {
        return findAccountClientEntities(false, maxResults, firstResult);
    }

    private List<AccountClient> findAccountClientEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(AccountClient.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public AccountClient findAccountClient(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(AccountClient.class, id);
        } finally {
            em.close();
        }
    }

    public int getAccountClientCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<AccountClient> rt = cq.from(AccountClient.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public AccountClient findByNamedQuery(String namedQuery, Map parameters, LockModeType lockModeType) throws Exception{
        Query q = getEntityManager().createNamedQuery(namedQuery);
        Iterator iterator = parameters.entrySet().iterator();
        while(iterator.hasNext()){
            Map.Entry entry = (Map.Entry)iterator.next();
            q.setParameter(entry.getKey().toString(), entry.getValue());
        }
        if(lockModeType != null){
            q.setLockMode(lockModeType);
        }
        return (AccountClient)q.getSingleResult();
    }
}
