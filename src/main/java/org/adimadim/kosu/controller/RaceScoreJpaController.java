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
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.adimadim.kosu.controller.exceptions.NonexistentEntityException;
import org.adimadim.kosu.controller.exceptions.PreexistingEntityException;
import org.adimadim.kosu.entity.Race;
import org.adimadim.kosu.entity.RaceScore;
import org.adimadim.kosu.entity.RaceScorePK;

/**
 *
 * @author Ergo
 */
public class RaceScoreJpaController implements Serializable {

    public RaceScoreJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(RaceScore raceScore) throws PreexistingEntityException, Exception {
        if (raceScore.getRaceScorePK() == null) {
            raceScore.setRaceScorePK(new RaceScorePK());
        }
        raceScore.getRaceScorePK().setRaceId(raceScore.getRace().getRaceId());
        raceScore.getRaceScorePK().setAccountId(raceScore.getAccount().getAccountId());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Race race = raceScore.getRace();
            if (race != null) {
                race = em.getReference(race.getClass(), race.getRaceId());
                raceScore.setRace(race);
            }
            em.persist(raceScore);
            if (race != null) {
                race.getRaceScoreList().add(raceScore);
                race = em.merge(race);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findRaceScore(raceScore.getRaceScorePK()) != null) {
                throw new PreexistingEntityException("RaceScore " + raceScore + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void update(RaceScore raceScore) throws NonexistentEntityException, Exception {
        raceScore.getRaceScorePK().setRaceId(raceScore.getRace().getRaceId());
        raceScore.getRaceScorePK().setAccountId(raceScore.getAccount().getAccountId());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            RaceScore persistentRaceScore = em.find(RaceScore.class, raceScore.getRaceScorePK());
            Race raceOld = persistentRaceScore.getRace();
            Race raceNew = raceScore.getRace();
            if (raceNew != null) {
                raceNew = em.getReference(raceNew.getClass(), raceNew.getRaceId());
                raceScore.setRace(raceNew);
            }
            raceScore = em.merge(raceScore);
            if (raceOld != null && !raceOld.equals(raceNew)) {
                raceOld.getRaceScoreList().remove(raceScore);
                raceOld = em.merge(raceOld);
            }
            if (raceNew != null && !raceNew.equals(raceOld)) {
                raceNew.getRaceScoreList().add(raceScore);
                raceNew = em.merge(raceNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                RaceScorePK id = raceScore.getRaceScorePK();
                if (findRaceScore(id) == null) {
                    throw new NonexistentEntityException("The raceScore with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void delete(RaceScorePK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            RaceScore raceScore;
            try {
                raceScore = em.getReference(RaceScore.class, id);
                raceScore.getRaceScorePK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The raceScore with id " + id + " no longer exists.", enfe);
            }
            Race race = raceScore.getRace();
            if (race != null) {
                race.getRaceScoreList().remove(raceScore);
                race = em.merge(race);
            }
            em.remove(raceScore);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<RaceScore> findRaceScoreEntities() {
        return findRaceScoreEntities(true, -1, -1);
    }

    public List<RaceScore> findRaceScoreEntities(int maxResults, int firstResult) {
        return findRaceScoreEntities(false, maxResults, firstResult);
    }

    private List<RaceScore> findRaceScoreEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(RaceScore.class));
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

    public RaceScore findRaceScore(RaceScorePK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(RaceScore.class, id);
        } finally {
            em.close();
        }
    }

    public int getRaceScoreCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<RaceScore> rt = cq.from(RaceScore.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
      public List<RaceScore> findListByQuery(String query, Map parameters) throws Exception {
        EntityManager em = getEntityManager();
        Query q = em.createQuery(query);
        Iterator iterator = parameters.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            q.setParameter(entry.getKey().toString(), entry.getValue());
        }

        return (List<RaceScore>) q.getResultList();
    }
    
    public RaceScore findByQuery(String query, Map parameters) throws Exception {
        EntityManager em = getEntityManager();
        Query q = em.createQuery(query);
        Iterator iterator = parameters.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            q.setParameter(entry.getKey().toString(), entry.getValue());
        }

        return (RaceScore) q.getSingleResult();
    }
    
     
}
