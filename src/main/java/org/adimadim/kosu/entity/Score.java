/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.adimadim.kosu.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Adem
 */
@Entity
@Table(name="SCORE", catalog = "", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Score.findAll", query = "SELECT s FROM Score s"),
    @NamedQuery(name = "Score.findByScoreId", query = "SELECT s FROM Score s WHERE s.scoreId = :scoreId"),
    @NamedQuery(name = "Score.findByChestNumber", query = "SELECT s FROM Score s WHERE s.chestNumber = :chestNumber"),
    @NamedQuery(name = "Score.findByRaceFinishOrder", query = "SELECT s FROM Score s WHERE s.raceFinishOrder = :raceFinishOrder")})
public class Score implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "SCORE_ID", nullable = false)
    private Integer scoreId;
    @Basic(optional = false)
    @Column(name = "CHEST_NUMBER", nullable = false)
    private int chestNumber;
    @Basic(optional = false)
    @Column(name = "RACE_FINISH_ORDER", nullable = false)
    private int raceFinishOrder;
    @JoinColumn(name = "RACE_ID", referencedColumnName = "RACE_ID", nullable = false)
    @ManyToOne(optional = false)
    private Race raceId;

    public Score() {
    }

    public Score(Integer scoreId) {
        this.scoreId = scoreId;
    }

    public Score(Integer scoreId, int chestNumber, int raceFinishOrder) {
        this.scoreId = scoreId;
        this.chestNumber = chestNumber;
        this.raceFinishOrder = raceFinishOrder;
    }

    public Integer getScoreId() {
        return scoreId;
    }

    public void setScoreId(Integer scoreId) {
        this.scoreId = scoreId;
    }

    public int getChestNumber() {
        return chestNumber;
    }

    public void setChestNumber(int chestNumber) {
        this.chestNumber = chestNumber;
    }

    public int getRaceFinishOrder() {
        return raceFinishOrder;
    }

    public void setRaceFinishOrder(int raceFinishOrder) {
        this.raceFinishOrder = raceFinishOrder;
    }

    public Race getRaceId() {
        return raceId;
    }

    public void setRaceId(Race raceId) {
        this.raceId = raceId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (scoreId != null ? scoreId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Score)) {
            return false;
        }
        Score other = (Score) object;
        if ((this.scoreId == null && other.scoreId != null) || (this.scoreId != null && !this.scoreId.equals(other.scoreId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.adimadim.kosu.entity.Score[ scoreId=" + scoreId + " ]";
    }
    
}
