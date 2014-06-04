/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.adimadim.kosu.controller;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.adimadim.kosu.controller.exceptions.NonexistentEntityException;
import org.adimadim.kosu.entity.Race;
import org.adimadim.kosu.entity.RaceScore;

/**
 *
 * @author Adem
 */
public class ScoreJpaController implements Serializable {

    public ScoreJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(RaceScore raceScore) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Race raceId = raceScore.getRace();
            if (raceId != null) {
                raceId = em.getReference(raceId.getClass(), raceId.getRaceId());
                raceScore.setRace(raceId);
            }
            em.persist(raceScore);
            if (raceId != null) {
                raceId.getRaceScoreList().add(raceScore);
                raceId = em.merge(raceId);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(RaceScore raceScore) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            RaceScore persistentScore = em.find(RaceScore.class, raceScore.getRaceScoreId());
            Race raceIdOld = persistentScore.getRace();
            Race raceIdNew = raceScore.getRace();
            if (raceIdNew != null) {
                raceIdNew = em.getReference(raceIdNew.getClass(), raceIdNew.getRaceId());
                raceScore.setRace(raceIdNew);
            }
            raceScore = em.merge(raceScore);
            if (raceIdOld != null && !raceIdOld.equals(raceIdNew)) {
                raceIdOld.getRaceScoreList().remove(raceScore);
                raceIdOld = em.merge(raceIdOld);
            }
            if (raceIdNew != null && !raceIdNew.equals(raceIdOld)) {
                raceIdNew.getRaceScoreList().add(raceScore);
                raceIdNew = em.merge(raceIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = raceScore.getRaceScoreId();
                if (findScore(id) == null) {
                    throw new NonexistentEntityException("The score with id " + id + " no longer exists.");
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
            RaceScore raceScore;
            try {
                raceScore = em.getReference(RaceScore.class, id);
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The score with id " + id + " no longer exists.", enfe);
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

    public List<RaceScore> findScoreEntities() {
        return findScoreEntities(true, -1, -1);
    }

    public List<RaceScore> findScoreEntities(int maxResults, int firstResult) {
        return findScoreEntities(false, maxResults, firstResult);
    }

    private List<RaceScore> findScoreEntities(boolean all, int maxResults, int firstResult) {
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

    public RaceScore findScore(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(RaceScore.class, id);
        } finally {
            em.close();
        }
    }

    public int getScoreCount() {
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
    
}
