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
import org.adimadim.kosu.controller.ScoreJpaController;
import org.adimadim.kosu.entity.Account;
import org.adimadim.kosu.entity.Race;

/**
 *
 * @author Adem
 */
public class RaceService {
    
    private EntityManagerFactory emfClient;
    private EntityManagerFactory emfServer;
    
    private EntityManagerFactory getEmfClient(){
        if(emfClient == null){
            emfClient = Persistence.createEntityManagerFactory("adimadimClientPU");
        }
        return emfClient;
    }
    
    private EntityManagerFactory getEmfServer(){
        if(emfServer == null){
            emfServer = Persistence.createEntityManagerFactory("adimadimServerPU");
        }
        return emfServer;
    }

    private ScoreJpaController getScoreJpaController(){
        return new ScoreJpaController(getEmfClient());
    }
    
    private RaceJpaController getRaceJpaController(){
        return new RaceJpaController(getEmfClient());
    }
    
    private AccountJpaController getAccountJpaController(){
        return new AccountJpaController(getEmfClient());
    }

    public List<Account> retrieveAllAccounts(){
        return getAccountJpaController().findAccountEntities();
    }

    public List<Race> retrieveAllRaces(){
        return getRaceJpaController().findRaceEntities();
    }
    
    public Account retrieveAccountFromClientByChestNumber(Integer chestNumber) throws Exception{
        Map param = new HashMap();
        param.put("chestNumber", chestNumber);
        return getAccountJpaController().findByNamedQuery("Account.findByChestNumber", param, null);
    }
    
    public void saveAccountList(List<Account> accountList) throws Exception{
        AccountJpaController controller = getAccountJpaController();
        for(Account account: accountList){
            controller.edit(account);
        }
    }

    public void saveRace(Race race) throws Exception{
        getRaceJpaController().create(race);
    }
    
}
