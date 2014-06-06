/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.adimadim.kosu.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.adimadim.kosu.controller.AccountJpaController;
import org.adimadim.kosu.controller.RaceJpaController;
import org.adimadim.kosu.controller.RaceScoreJpaController;
import org.adimadim.kosu.entity.Account;
import org.adimadim.kosu.entity.Race;
import org.adimadim.kosu.entity.RaceScore;

/**
 *
 * @author Adem
 */
public class RaceService {

    private EntityManagerFactory emfClient;
    private EntityManagerFactory emfServer;

    private EntityManagerFactory getEmfClient() {
        if (emfClient == null) {
            emfClient = Persistence.createEntityManagerFactory("adimadimClientPU");
        }
        return emfClient;
    }

    private RaceScoreJpaController getRaceScoreJpaController() {
        return new RaceScoreJpaController(getEmfClient());
    }

    private RaceJpaController getRaceJpaController() {
        return new RaceJpaController(getEmfClient());
    }

    private AccountJpaController getAccountJpaController() {
        return new AccountJpaController(getEmfClient());
    }

    public List<Account> retrieveAllAccounts() throws Exception {
        return getAccountJpaController().findAccountEntities();
    }

    public List<Race> retrieveAllRaces() throws Exception {
        return getRaceJpaController().findRaceEntities();
    }

    public List<RaceScore> retrieveRaceScoresByRaceId(Integer raceId) throws Exception {
        String jpql = "select r from RaceScore r where r.race.raceId=:raceId";
        Map<String, Integer> params = new HashMap<>();
        params.put("raceId", raceId);
        return getRaceScoreJpaController().findListByQuery(jpql, params);
    }

    public Account retrieveAccountByChestNumber(Integer chestNumber) throws Exception {
        Map param = new HashMap();
        param.put("chestNumber", chestNumber);
        return getAccountJpaController().findByNamedQuery("Account.findByChestNumber", param, null);
    }

    public void saveAccountList(List<Account> accountList) throws Exception {
        AccountJpaController controller = getAccountJpaController();
        for (Account account : accountList) {
            if (controller.findAccount(account.getAccountId()) == null) {
                controller.create(account);
            } else {
                controller.update(account);
            }
        }
    }

    public void saveRaceList(List<Race> raceList) throws Exception {
        RaceJpaController controller = getRaceJpaController();
        for (Race race : raceList) {
            if (controller.findRace(race.getRaceId()) == null) {
                controller.create(race);
            } else {
                controller.update(race);
            }
        }
    }

    public void saveRaceScore(RaceScore raceScore) throws Exception {
        RaceScoreJpaController controller = getRaceScoreJpaController();
        Map map = new HashMap();
        map.put("raceId", raceScore.getRace().getRaceId());
        map.put("accountId", raceScore.getAccount().getAccountId());
        if (controller.findRaceScore(raceScore.getRaceScorePK()) == null) {
            controller.create(raceScore);
        } else {
            controller.update(raceScore);
        }
    }

    public void saveRace(Race race) throws Exception {
        getRaceJpaController().create(race);
    }

    public Race retrieveRace(Integer raceId) throws Exception {
        return getRaceJpaController().findRace(raceId);
    }

}
