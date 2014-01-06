/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.adimadim.kosu.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Adem
 */
@Entity
@Table(name="RACE", catalog = "", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Race.findAll", query = "SELECT r FROM Race r"),
    @NamedQuery(name = "Race.findByRaceId", query = "SELECT r FROM Race r WHERE r.raceId = :raceId"),
    @NamedQuery(name = "Race.findByComment", query = "SELECT r FROM Race r WHERE r.comment = :comment"),
    @NamedQuery(name = "Race.findByRaceDate", query = "SELECT r FROM Race r WHERE r.raceDate = :raceDate")})
public class Race implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "RACE_ID", nullable = false)
    private Integer raceId;
    @Basic(optional = false)
    @Column(nullable = false, length = 30)
    private String comment;
    @Basic(optional = false)
    @Column(name = "RACE_DATE", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date raceDate;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "raceId")
    private List<Score> scoreList;

    public Race() {
    }

    public Race(Integer raceId) {
        this.raceId = raceId;
    }

    public Race(Integer raceId, String comment, Date raceDate) {
        this.raceId = raceId;
        this.comment = comment;
        this.raceDate = raceDate;
    }

    public Integer getRaceId() {
        return raceId;
    }

    public void setRaceId(Integer raceId) {
        this.raceId = raceId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getRaceDate() {
        return raceDate;
    }

    public void setRaceDate(Date raceDate) {
        this.raceDate = raceDate;
    }

    @XmlTransient
    public List<Score> getScoreList() {
        return scoreList;
    }

    public void setScoreList(List<Score> scoreList) {
        this.scoreList = scoreList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (raceId != null ? raceId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Race)) {
            return false;
        }
        Race other = (Race) object;
        if ((this.raceId == null && other.raceId != null) || (this.raceId != null && !this.raceId.equals(other.raceId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.adimadim.kosu.entity.Race[ raceId=" + raceId + " ]";
    }
    
}
