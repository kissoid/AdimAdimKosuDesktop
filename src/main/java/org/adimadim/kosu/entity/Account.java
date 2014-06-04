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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Ergo
 */
@Entity
@Table(catalog = "adimadim", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Account.findAll", query = "SELECT a FROM Account a"),
    @NamedQuery(name = "Account.findByAccountId", query = "SELECT a FROM Account a WHERE a.accountId = :accountId"),
    @NamedQuery(name = "Account.findByName", query = "SELECT a FROM Account a WHERE a.name = :name"),
    @NamedQuery(name = "Account.findBySurname", query = "SELECT a FROM Account a WHERE a.surname = :surname"),
    @NamedQuery(name = "Account.findByEmail", query = "SELECT a FROM Account a WHERE a.email = :email"),
    @NamedQuery(name = "Account.findByPassword", query = "SELECT a FROM Account a WHERE a.password = :password"),
    @NamedQuery(name = "Account.findByGender", query = "SELECT a FROM Account a WHERE a.gender = :gender"),
    @NamedQuery(name = "Account.findByBirthDate", query = "SELECT a FROM Account a WHERE a.birthDate = :birthDate"),
    @NamedQuery(name = "Account.findByActive", query = "SELECT a FROM Account a WHERE a.active = :active"),
    @NamedQuery(name = "Account.findByCreateDate", query = "SELECT a FROM Account a WHERE a.createDate = :createDate"),
    @NamedQuery(name = "Account.findByManager", query = "SELECT a FROM Account a WHERE a.manager = :manager"),
    @NamedQuery(name = "Account.findByAdimadim", query = "SELECT a FROM Account a WHERE a.adimadim = :adimadim"),
    @NamedQuery(name = "Account.findByAdimadimRun", query = "SELECT a FROM Account a WHERE a.adimadimRun = :adimadimRun"),
    @NamedQuery(name = "Account.findByPhoneNumber", query = "SELECT a FROM Account a WHERE a.phoneNumber = :phoneNumber"),
    @NamedQuery(name = "Account.findByPicture", query = "SELECT a FROM Account a WHERE a.picture = :picture"),
    @NamedQuery(name = "Account.findByChestNumber1", query = "SELECT a FROM Account a WHERE a.chestNumber1 = :chestNumber1"),
    @NamedQuery(name = "Account.findBySecretKey", query = "SELECT a FROM Account a WHERE a.secretKey = :secretKey"),
    @NamedQuery(name = "Account.findByUserName", query = "SELECT a FROM Account a WHERE a.userName = :userName")})
public class Account implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "account_id", nullable = false)
    private Integer accountId;
    @Basic(optional = false)
    @Column(nullable = false, length = 25)
    private String name;
    @Basic(optional = false)
    @Column(nullable = false, length = 25)
    private String surname;
    @Column(length = 50)
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
    @Column(name = "phone_number", length = 15)
    private String phoneNumber;
    @Column(length = 30)
    private String picture;
    @Column(name = "chest_number")
    private Integer chestNumber1;
    @Column(name = "secret_key", length = 50)
    private String secretKey;
    @Column(name = "user_name", length = 50)
    private String userName;

    public Account() {
    }

    public Account(Integer accountId) {
        this.accountId = accountId;
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

    public Integer getChestNumber1() {
        return chestNumber1;
    }

    public void setChestNumber1(Integer chestNumber1) {
        this.chestNumber1 = chestNumber1;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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
        if (!(object instanceof Account)) {
            return false;
        }
        Account other = (Account) object;
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
