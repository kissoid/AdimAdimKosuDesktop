/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.adimadim.kosu.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Ergo
 */
@Embeddable
public class RaceScorePK implements Serializable {
    
    @Basic(optional = false)
    @Column(name = "race_id", nullable = false, updatable = false, insertable = false)
    private Integer raceId;
    
    @Basic(optional = false)
    @Column(name = "account_id", nullable = false, updatable = false, insertable = false)
    private Integer accountId;
    
    public RaceScorePK(){
        
    }

    public RaceScorePK(Integer raceId, Integer accountId){
        this.raceId = raceId;
        this.accountId = accountId;
    }
    
    public Integer getRaceId() {
        return raceId;
    }

    public void setRaceId(Integer raceId) {
        this.raceId = raceId;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }
    
}
