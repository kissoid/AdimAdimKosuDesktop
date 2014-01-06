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
import org.adimadim.kosu.controller.exceptions.PreexistingEntityException;
import org.adimadim.kosu.entity.AccountServer;

/**
 *
 * @author Adem
 */
public class AccountServerJpaController implements Serializable {

    public AccountServerJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(AccountServer accountServer) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(accountServer);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findAccountServer(accountServer.getAccountId()) != null) {
                throw new PreexistingEntityException("AccountServer " + accountServer + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(AccountServer accountServer) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            accountServer = em.merge(accountServer);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = accountServer.getAccountId();
                if (findAccountServer(id) == null) {
                    throw new NonexistentEntityException("The accountServer with id " + id + " no longer exists.");
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
            AccountServer accountServer;
            try {
                accountServer = em.getReference(AccountServer.class, id);
                accountServer.getAccountId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The accountServer with id " + id + " no longer exists.", enfe);
            }
            em.remove(accountServer);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<AccountServer> findAccountServerEntities() {
        return findAccountServerEntities(true, -1, -1);
    }

    public List<AccountServer> findAccountServerEntities(int maxResults, int firstResult) {
        return findAccountServerEntities(false, maxResults, firstResult);
    }

    private List<AccountServer> findAccountServerEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(AccountServer.class));
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

    public AccountServer findAccountServer(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(AccountServer.class, id);
        } finally {
            em.close();
        }
    }

    public int getAccountServerCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<AccountServer> rt = cq.from(AccountServer.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public AccountServer findByNamedQuery(String namedQuery, Map parameters, LockModeType lockModeType) throws Exception{
        Query q = getEntityManager().createNamedQuery(namedQuery);
        Iterator iterator = parameters.entrySet().iterator();
        while(iterator.hasNext()){
            Map.Entry entry = (Map.Entry)iterator.next();
            q.setParameter(entry.getKey().toString(), entry.getValue());
        }
        if(lockModeType != null){
            q.setLockMode(lockModeType);
        }
        return (AccountServer)q.getSingleResult();
    }
    
}
