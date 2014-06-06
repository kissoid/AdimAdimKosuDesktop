/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.adimadim.kosu.entity;



import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Ergo
 */
@Entity
@Table(name = "race_score", catalog = "adimadim", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RaceScore.findAll", query = "SELECT r FROM RaceScore r"),
    @NamedQuery(name = "RaceScore.findByRaceScoreId", query = "SELECT r FROM RaceScore r WHERE r.raceScorePK.raceId = :raceId")})
public class RaceScore implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected RaceScorePK raceScorePK;
    @Column(name = "order_no")
    private Integer orderNo;
    @JoinColumn(name = "race_id", referencedColumnName = "race_id")
    @ManyToOne(optional = false)
    private Race race;
    @JoinColumn(name = "account_id", referencedColumnName = "account_id", nullable = false)
    @ManyToOne
    private Account account;

    public RaceScore() {
    }

    public Race getRace() {
        return race;
    }

    public void setRace(Race race) {
        this.race = race;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Integer getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Integer orderNo) {
        this.orderNo = orderNo;
    }

    public RaceScorePK getRaceScorePK() {
        return raceScorePK;
    }

    public void setRaceScorePK(RaceScorePK raceScorePK) {
        this.raceScorePK = raceScorePK;
    }
    
}
