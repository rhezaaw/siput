/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

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
 * @author fachrul
 */
@Entity
@Table(name = "iuran_user")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "IuranUser.findAll", query = "SELECT i FROM IuranUser i")
    , @NamedQuery(name = "IuranUser.findByIuranUserId", query = "SELECT i FROM IuranUser i WHERE i.iuranUserId = :iuranUserId")
    , @NamedQuery(name = "IuranUser.findByIuranUserStatus", query = "SELECT i FROM IuranUser i WHERE i.iuranUserStatus = :iuranUserStatus")})
public class IuranUser implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "iuran_user_id")
    private Integer iuranUserId;
    @Basic(optional = false)
    @Column(name = "iuran_user_status")
    private boolean iuranUserStatus;
    @JoinColumn(name = "iuran_id", referencedColumnName = "iuran_id")
    @ManyToOne(optional = false)
    private Iuran iuranId;
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    @ManyToOne(optional = false)
    private User userId;
    @JoinColumn(name = "transaksi_id", referencedColumnName = "transaksi_id")
    @ManyToOne(optional = false)
    private Transaksi transaksiId;

    public IuranUser() {
    }

    public IuranUser(Integer iuranUserId) {
        this.iuranUserId = iuranUserId;
    }

    public IuranUser(Integer iuranUserId, boolean iuranUserStatus) {
        this.iuranUserId = iuranUserId;
        this.iuranUserStatus = iuranUserStatus;
    }

    public Integer getIuranUserId() {
        return iuranUserId;
    }

    public void setIuranUserId(Integer iuranUserId) {
        this.iuranUserId = iuranUserId;
    }

    public boolean getIuranUserStatus() {
        return iuranUserStatus;
    }

    public void setIuranUserStatus(boolean iuranUserStatus) {
        this.iuranUserStatus = iuranUserStatus;
    }

    public Iuran getIuranId() {
        return iuranId;
    }

    public void setIuranId(Iuran iuranId) {
        this.iuranId = iuranId;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public Transaksi getTransaksiId() {
        return transaksiId;
    }

    public void setTransaksiId(Transaksi transaksiId) {
        this.transaksiId = transaksiId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iuranUserId != null ? iuranUserId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof IuranUser)) {
            return false;
        }
        IuranUser other = (IuranUser) object;
        if ((this.iuranUserId == null && other.iuranUserId != null) || (this.iuranUserId != null && !this.iuranUserId.equals(other.iuranUserId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.IuranUser[ iuranUserId=" + iuranUserId + " ]";
    }
    
}
