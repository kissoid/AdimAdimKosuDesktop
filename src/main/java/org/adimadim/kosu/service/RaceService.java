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
import org.adimadim.kosu.controller.AccountClientJpaController;
import org.adimadim.kosu.controller.AccountServerJpaController;
import org.adimadim.kosu.controller.RaceJpaController;
import org.adimadim.kosu.controller.ScoreJpaController;
import org.adimadim.kosu.entity.AccountClient;
import org.adimadim.kosu.entity.AccountServer;
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
    
    private AccountClientJpaController getAccountClientJpaController(){
        return new AccountClientJpaController(getEmfClient());
    }

    private AccountServerJpaController getAccountServerJpaController(){
        return new AccountServerJpaController(getEmfServer());
    }
    
    public List<AccountServer> retrieveAllAccountsFromServer(){
        return getAccountServerJpaController().findAccountServerEntities();
    }

    public List<AccountClient> retrieveAllAccountsFromClient(){
        return getAccountClientJpaController().findAccountClientEntities();
    }

    public List<Race> retrieveAllRaces(){
        return getRaceJpaController().findRaceEntities();
    }
    
    public AccountClient retrieveAccountFromClientByChestNumber(Integer chestNumber) throws Exception{
        Map param = new HashMap();
        param.put("chestNumber", chestNumber);
        return getAccountClientJpaController().findByNamedQuery("Account.findByChestNumber", param, null);
    }
    
    public void saveAccountClientlist(List<AccountClient> accountClientList) throws Exception{
        AccountClientJpaController controller = getAccountClientJpaController();
        for(AccountClient accountClient: accountClientList){
            controller.edit(accountClient);
        }
    }

    public void saveRace(Race race) throws Exception{
        getRaceJpaController().create(race);
    }
    
}
