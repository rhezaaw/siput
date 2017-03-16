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
@Table(name = "pengeluaran_user")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PengeluaranUser.findAll", query = "SELECT p FROM PengeluaranUser p")
    , @NamedQuery(name = "PengeluaranUser.findByPengeluaranUserId", query = "SELECT p FROM PengeluaranUser p WHERE p.pengeluaranUserId = :pengeluaranUserId")
    , @NamedQuery(name = "PengeluaranUser.findByPengeluaranUserStatus", query = "SELECT p FROM PengeluaranUser p WHERE p.pengeluaranUserStatus = :pengeluaranUserStatus")})
public class PengeluaranUser implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pengeluaran_user_id")
    private Integer pengeluaranUserId;
    @Basic(optional = false)
    @Column(name = "pengeluaran_user_status")
    private boolean pengeluaranUserStatus;
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    @ManyToOne(optional = false)
    private User userId;
    @JoinColumn(name = "iuran_id", referencedColumnName = "iuran_id")
    @ManyToOne(optional = false)
    private Iuran iuranId;
    @JoinColumn(name = "transaksi_id", referencedColumnName = "transaksi_id")
    @ManyToOne(optional = false)
    private Transaksi transaksiId;

    public PengeluaranUser() {
    }

    public PengeluaranUser(Integer pengeluaranUserId) {
        this.pengeluaranUserId = pengeluaranUserId;
    }

    public PengeluaranUser(Integer pengeluaranUserId, boolean pengeluaranUserStatus) {
        this.pengeluaranUserId = pengeluaranUserId;
        this.pengeluaranUserStatus = pengeluaranUserStatus;
    }

    public Integer getPengeluaranUserId() {
        return pengeluaranUserId;
    }

    public void setPengeluaranUserId(Integer pengeluaranUserId) {
        this.pengeluaranUserId = pengeluaranUserId;
    }

    public boolean getPengeluaranUserStatus() {
        return pengeluaranUserStatus;
    }

    public void setPengeluaranUserStatus(boolean pengeluaranUserStatus) {
        this.pengeluaranUserStatus = pengeluaranUserStatus;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public Iuran getIuranId() {
        return iuranId;
    }

    public void setIuranId(Iuran iuranId) {
        this.iuranId = iuranId;
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
        hash += (pengeluaranUserId != null ? pengeluaranUserId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PengeluaranUser)) {
            return false;
        }
        PengeluaranUser other = (PengeluaranUser) object;
        if ((this.pengeluaranUserId == null && other.pengeluaranUserId != null) || (this.pengeluaranUserId != null && !this.pengeluaranUserId.equals(other.pengeluaranUserId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.PengeluaranUser[ pengeluaranUserId=" + pengeluaranUserId + " ]";
    }
    
}
