/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.adimadim.kosu.controller;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.adimadim.kosu.entity.RaceScore;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.adimadim.kosu.controller.exceptions.IllegalOrphanException;
import org.adimadim.kosu.controller.exceptions.NonexistentEntityException;
import org.adimadim.kosu.controller.exceptions.PreexistingEntityException;
import org.adimadim.kosu.entity.Race;

/**
 *
 * @author Ergo
 */

public class RaceJpaController implements Serializable {

    public RaceJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Race race) throws PreexistingEntityException, Exception {
        if (race.getRaceScoreList() == null) {
            race.setRaceScoreList(new ArrayList<RaceScore>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<RaceScore> attachedRaceScoreList = new ArrayList<RaceScore>();
            for (RaceScore raceScoreListRaceScoreToAttach : race.getRaceScoreList()) {
                raceScoreListRaceScoreToAttach = em.getReference(raceScoreListRaceScoreToAttach.getClass(), raceScoreListRaceScoreToAttach.getRaceScorePK());
                attachedRaceScoreList.add(raceScoreListRaceScoreToAttach);
            }
            race.setRaceScoreList(attachedRaceScoreList);
            em.persist(race);
            for (RaceScore raceScoreListRaceScore : race.getRaceScoreList()) {
                Race oldRaceOfRaceScoreListRaceScore = raceScoreListRaceScore.getRace();
                raceScoreListRaceScore.setRace(race);
                raceScoreListRaceScore = em.merge(raceScoreListRaceScore);
                if (oldRaceOfRaceScoreListRaceScore != null) {
                    oldRaceOfRaceScoreListRaceScore.getRaceScoreList().remove(raceScoreListRaceScore);
                    oldRaceOfRaceScoreListRaceScore = em.merge(oldRaceOfRaceScoreListRaceScore);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findRace(race.getRaceId()) != null) {
                throw new PreexistingEntityException("Race " + race + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void update(Race race) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Race persistentRace = em.find(Race.class, race.getRaceId());
            List<RaceScore> raceScoreListOld = persistentRace.getRaceScoreList();
            List<RaceScore> raceScoreListNew = race.getRaceScoreList();
            List<String> illegalOrphanMessages = null;
            /*for (RaceScore raceScoreListOldRaceScore : raceScoreListOld) {
                if (!raceScoreListNew.contains(raceScoreListOldRaceScore)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain RaceScore " + raceScoreListOldRaceScore + " since its race field is not nullable.");
                }
            }*/
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<RaceScore> attachedRaceScoreListNew = new ArrayList<RaceScore>();
            for (RaceScore raceScoreListNewRaceScoreToAttach : raceScoreListNew) {
                raceScoreListNewRaceScoreToAttach = em.getReference(raceScoreListNewRaceScoreToAttach.getClass(), raceScoreListNewRaceScoreToAttach.getRaceScorePK());
                attachedRaceScoreListNew.add(raceScoreListNewRaceScoreToAttach);
            }
            raceScoreListNew = attachedRaceScoreListNew;
            race.setRaceScoreList(raceScoreListNew);
            race = em.merge(race);
            for (RaceScore raceScoreListNewRaceScore : raceScoreListNew) {
                if (!raceScoreListOld.contains(raceScoreListNewRaceScore)) {
                    Race oldRaceOfRaceScoreListNewRaceScore = raceScoreListNewRaceScore.getRace();
                    raceScoreListNewRaceScore.setRace(race);
                    raceScoreListNewRaceScore = em.merge(raceScoreListNewRaceScore);
                    if (oldRaceOfRaceScoreListNewRaceScore != null && !oldRaceOfRaceScoreListNewRaceScore.equals(race)) {
                        oldRaceOfRaceScoreListNewRaceScore.getRaceScoreList().remove(raceScoreListNewRaceScore);
                        oldRaceOfRaceScoreListNewRaceScore = em.merge(oldRaceOfRaceScoreListNewRaceScore);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = race.getRaceId();
                if (findRace(id) == null) {
                    throw new NonexistentEntityException("The race with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void delete(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Race race;
            try {
                race = em.getReference(Race.class, id);
                race.getRaceId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The race with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<RaceScore> raceScoreListOrphanCheck = race.getRaceScoreList();
            for (RaceScore raceScoreListOrphanCheckRaceScore : raceScoreListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Race (" + race + ") cannot be destroyed since the RaceScore " + raceScoreListOrphanCheckRaceScore + " in its raceScoreList field has a non-nullable race field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(race);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Race> findRaceEntities() {
        return findRaceEntities(true, -1, -1);
    }

    public List<Race> findRaceEntities(int maxResults, int firstResult) {
        return findRaceEntities(false, maxResults, firstResult);
    }

    private List<Race> findRaceEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Race.class));
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

    public Race findRace(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Race.class, id);
        } finally {
            em.close();
        }
    }

    public int getRaceCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Race> rt = cq.from(Race.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
