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
import org.adimadim.kosu.entity.Score;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.adimadim.kosu.controller.exceptions.IllegalOrphanException;
import org.adimadim.kosu.controller.exceptions.NonexistentEntityException;
import org.adimadim.kosu.entity.Race;

/**
 *
 * @author Adem
 */
public class RaceJpaController implements Serializable {

    public RaceJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Race race) {
        if (race.getScoreList() == null) {
            race.setScoreList(new ArrayList<Score>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Score> attachedScoreList = new ArrayList<Score>();
            for (Score scoreListScoreToAttach : race.getScoreList()) {
                scoreListScoreToAttach = em.getReference(scoreListScoreToAttach.getClass(), scoreListScoreToAttach.getScoreId());
                attachedScoreList.add(scoreListScoreToAttach);
            }
            race.setScoreList(attachedScoreList);
            em.persist(race);
            for (Score scoreListScore : race.getScoreList()) {
                Race oldRaceIdOfScoreListScore = scoreListScore.getRaceId();
                scoreListScore.setRaceId(race);
                scoreListScore = em.merge(scoreListScore);
                if (oldRaceIdOfScoreListScore != null) {
                    oldRaceIdOfScoreListScore.getScoreList().remove(scoreListScore);
                    oldRaceIdOfScoreListScore = em.merge(oldRaceIdOfScoreListScore);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Race race) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Race persistentRace = em.find(Race.class, race.getRaceId());
            List<Score> scoreListOld = persistentRace.getScoreList();
            List<Score> scoreListNew = race.getScoreList();
            List<String> illegalOrphanMessages = null;
            for (Score scoreListOldScore : scoreListOld) {
                if (!scoreListNew.contains(scoreListOldScore)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Score " + scoreListOldScore + " since its raceId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Score> attachedScoreListNew = new ArrayList<Score>();
            for (Score scoreListNewScoreToAttach : scoreListNew) {
                scoreListNewScoreToAttach = em.getReference(scoreListNewScoreToAttach.getClass(), scoreListNewScoreToAttach.getScoreId());
                attachedScoreListNew.add(scoreListNewScoreToAttach);
            }
            scoreListNew = attachedScoreListNew;
            race.setScoreList(scoreListNew);
            race = em.merge(race);
            for (Score scoreListNewScore : scoreListNew) {
                if (!scoreListOld.contains(scoreListNewScore)) {
                    Race oldRaceIdOfScoreListNewScore = scoreListNewScore.getRaceId();
                    scoreListNewScore.setRaceId(race);
                    scoreListNewScore = em.merge(scoreListNewScore);
                    if (oldRaceIdOfScoreListNewScore != null && !oldRaceIdOfScoreListNewScore.equals(race)) {
                        oldRaceIdOfScoreListNewScore.getScoreList().remove(scoreListNewScore);
                        oldRaceIdOfScoreListNewScore = em.merge(oldRaceIdOfScoreListNewScore);
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

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
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
            List<Score> scoreListOrphanCheck = race.getScoreList();
            for (Score scoreListOrphanCheckScore : scoreListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Race (" + race + ") cannot be destroyed since the Score " + scoreListOrphanCheckScore + " in its scoreList field has a non-nullable raceId field.");
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
