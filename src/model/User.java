/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author fachrul
 */
@Entity
@Table(name = "user")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u")
    , @NamedQuery(name = "User.findByUserId", query = "SELECT u FROM User u WHERE u.userId = :userId")
    , @NamedQuery(name = "User.findByUserUsername", query = "SELECT u FROM User u WHERE u.userUsername = :userUsername")
    , @NamedQuery(name = "User.findByUserDisplayname", query = "SELECT u FROM User u WHERE u.userDisplayname = :userDisplayname")
    , @NamedQuery(name = "User.findByUserPassword", query = "SELECT u FROM User u WHERE u.userPassword = :userPassword")
    , @NamedQuery(name = "User.findByUserTipe", query = "SELECT u FROM User u WHERE u.userTipe = :userTipe")})
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "user_id")
    private Integer userId;
    @Basic(optional = false)
    @Column(name = "user_username")
    private String userUsername;
    @Basic(optional = false)
    @Column(name = "user_displayname")
    private String userDisplayname;
    @Basic(optional = false)
    @Column(name = "user_password")
    private String userPassword;
    @Basic(optional = false)
    @Column(name = "user_tipe")
    private String userTipe;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userId")
    private List<Session> sessionList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userId")
    private List<PengeluaranUser> pengeluaranUserList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userId")
    private List<IuranUser> iuranUserList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userId")
    private List<Deposit> depositList;

    public User() {
    }

    public User(Integer userId) {
        this.userId = userId;
    }

    public User(Integer userId, String userUsername, String userDisplayname, String userPassword, String userTipe) {
        this.userId = userId;
        this.userUsername = userUsername;
        this.userDisplayname = userDisplayname;
        this.userPassword = userPassword;
        this.userTipe = userTipe;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserUsername() {
        return userUsername;
    }

    public void setUserUsername(String userUsername) {
        this.userUsername = userUsername;
    }

    public String getUserDisplayname() {
        return userDisplayname;
    }

    public void setUserDisplayname(String userDisplayname) {
        this.userDisplayname = userDisplayname;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserTipe() {
        return userTipe;
    }

    public void setUserTipe(String userTipe) {
        this.userTipe = userTipe;
    }

    @XmlTransient
    public List<Session> getSessionList() {
        return sessionList;
    }

    public void setSessionList(List<Session> sessionList) {
        this.sessionList = sessionList;
    }

    @XmlTransient
    public List<PengeluaranUser> getPengeluaranUserList() {
        return pengeluaranUserList;
    }

    public void setPengeluaranUserList(List<PengeluaranUser> pengeluaranUserList) {
        this.pengeluaranUserList = pengeluaranUserList;
    }

    @XmlTransient
    public List<IuranUser> getIuranUserList() {
        return iuranUserList;
    }

    public void setIuranUserList(List<IuranUser> iuranUserList) {
        this.iuranUserList = iuranUserList;
    }

    @XmlTransient
    public List<Deposit> getDepositList() {
        return depositList;
    }

    public void setDepositList(List<Deposit> depositList) {
        this.depositList = depositList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userId != null ? userId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if ((this.userId == null && other.userId != null) || (this.userId != null && !this.userId.equals(other.userId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.User[ userId=" + userId + " ]";
    }
    
}
