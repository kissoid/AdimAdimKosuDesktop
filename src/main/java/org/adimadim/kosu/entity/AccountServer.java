/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.adimadim.kosu.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Adem
 */
@Entity
@Table(name = "account", catalog = "adimadim", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Account.findAll", query = "SELECT a FROM AccountServer a"),
    @NamedQuery(name = "Account.findByAccountId", query = "SELECT a FROM AccountServer a WHERE a.accountId = :accountId"),
    @NamedQuery(name = "Account.findByName", query = "SELECT a FROM AccountServer a WHERE a.name = :name"),
    @NamedQuery(name = "Account.findBySurname", query = "SELECT a FROM AccountServer a WHERE a.surname = :surname"),
    @NamedQuery(name = "Account.findByEmail", query = "SELECT a FROM AccountServer a WHERE a.email = :email"),
    @NamedQuery(name = "Account.findByPassword", query = "SELECT a FROM AccountServer a WHERE a.password = :password"),
    @NamedQuery(name = "Account.findByGender", query = "SELECT a FROM AccountServer a WHERE a.gender = :gender"),
    @NamedQuery(name = "Account.findByBirthDate", query = "SELECT a FROM AccountServer a WHERE a.birthDate = :birthDate"),
    @NamedQuery(name = "Account.findByActive", query = "SELECT a FROM AccountServer a WHERE a.active = :active"),
    @NamedQuery(name = "Account.findByCreateDate", query = "SELECT a FROM AccountServer a WHERE a.createDate = :createDate"),
    @NamedQuery(name = "Account.findByManager", query = "SELECT a FROM AccountServer a WHERE a.manager = :manager"),
    @NamedQuery(name = "Account.findByAdimadim", query = "SELECT a FROM AccountServer a WHERE a.adimadim = :adimadim"),
    @NamedQuery(name = "Account.findByAdimadimRun", query = "SELECT a FROM AccountServer a WHERE a.adimadimRun = :adimadimRun"),
    @NamedQuery(name = "Account.findByPhoneCode", query = "SELECT a FROM AccountServer a WHERE a.phoneCode = :phoneCode"),
    @NamedQuery(name = "Account.findByPhoneNumber", query = "SELECT a FROM AccountServer a WHERE a.phoneNumber = :phoneNumber"),
    @NamedQuery(name = "Account.findByPicture", query = "SELECT a FROM AccountServer a WHERE a.picture = :picture"),
    @NamedQuery(name = "Account.findByChestNumber", query = "SELECT a FROM AccountServer a WHERE a.chestNumber = :chestNumber")})
public class AccountServer implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "account_id", nullable = false)
    private Integer accountId;
    @Basic(optional = false)
    @Column(nullable = false, length = 25)
    private String name;
    @Basic(optional = false)
    @Column(nullable = false, length = 25)
    private String surname;
    @Column(length = 30)
    private String email;
    @Column(length = 25)
    private String password;
    @Basic(optional = false)
    @Column(nullable = false, length = 1)
    private String gender;
    @Basic(optional = false)
    @Column(name = "birth_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date birthDate;
    @Basic(optional = false)
    @Column(nullable = false, length = 1)
    private String active;
    @Basic(optional = false)
    @Column(name = "create_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;
    @Basic(optional = false)
    @Column(nullable = false, length = 1)
    private String manager;
    @Basic(optional = false)
    @Column(nullable = false, length = 1)
    private String adimadim;
    @Basic(optional = false)
    @Column(name = "adimadim_run", nullable = false, length = 1)
    private String adimadimRun;
    @Column(name = "phone_code", length = 10)
    private String phoneCode;
    @Column(name = "phone_number", length = 10)
    private String phoneNumber;
    @Column(length = 30)
    private String picture;
    @Column(name = "chest_number")
    private Integer chestNumber;

    public AccountServer() {
    }

    public AccountServer(Integer accountId) {
        this.accountId = accountId;
    }

    public AccountServer(Integer accountId, String name, String surname, String gender, Date birthDate, String active, Date createDate, String manager, String adimadim, String adimadimRun) {
        this.accountId = accountId;
        this.name = name;
        this.surname = surname;
        this.gender = gender;
        this.birthDate = birthDate;
        this.active = active;
        this.createDate = createDate;
        this.manager = manager;
        this.adimadim = adimadim;
        this.adimadimRun = adimadimRun;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getAdimadim() {
        return adimadim;
    }

    public void setAdimadim(String adimadim) {
        this.adimadim = adimadim;
    }

    public String getAdimadimRun() {
        return adimadimRun;
    }

    public void setAdimadimRun(String adimadimRun) {
        this.adimadimRun = adimadimRun;
    }

    public String getPhoneCode() {
        return phoneCode;
    }

    public void setPhoneCode(String phoneCode) {
        this.phoneCode = phoneCode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Integer getChestNumber() {
        return chestNumber;
    }

    public void setChestNumber(Integer chestNumber) {
        this.chestNumber = chestNumber;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (accountId != null ? accountId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AccountServer)) {
            return false;
        }
        AccountServer other = (AccountServer) object;
        if ((this.accountId == null && other.accountId != null) || (this.accountId != null && !this.accountId.equals(other.accountId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.adimadim.kosu.entity.Account[ accountId=" + accountId + " ]";
    }
    
}
