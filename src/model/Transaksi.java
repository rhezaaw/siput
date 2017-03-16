/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

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
 * @author fachrul
 */
@Entity
@Table(name = "transaksi")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Transaksi.findAll", query = "SELECT t FROM Transaksi t")
    , @NamedQuery(name = "Transaksi.findByTransaksiId", query = "SELECT t FROM Transaksi t WHERE t.transaksiId = :transaksiId")
    , @NamedQuery(name = "Transaksi.findByTransaksiDate", query = "SELECT t FROM Transaksi t WHERE t.transaksiDate = :transaksiDate")
    , @NamedQuery(name = "Transaksi.findByTransaksiNama", query = "SELECT t FROM Transaksi t WHERE t.transaksiNama = :transaksiNama")
    , @NamedQuery(name = "Transaksi.findByUserId", query = "SELECT t FROM Transaksi t WHERE t.userId = :userId")
    , @NamedQuery(name = "Transaksi.findByTransaksiTipeId", query = "SELECT t FROM Transaksi t WHERE t.transaksiTipeId = :transaksiTipeId")})
public class Transaksi implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "transaksi_id")
    private Integer transaksiId;
    @Basic(optional = false)
    @Column(name = "transaksi_date")
    @Temporal(TemporalType.DATE)
    private Date transaksiDate;
    @Basic(optional = false)
    @Column(name = "transaksi_nama")
    private String transaksiNama;
    @Basic(optional = false)
    @Column(name = "user_id")
    private int userId;
    @Basic(optional = false)
    @Column(name = "transaksi_tipe_id")
    private int transaksiTipeId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "transaksiId")
    private List<PengeluaranUser> pengeluaranUserList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "transaksiId")
    private List<IuranUser> iuranUserList;

    public Transaksi() {
    }

    public Transaksi(Integer transaksiId) {
        this.transaksiId = transaksiId;
    }

    public Transaksi(Integer transaksiId, Date transaksiDate, String transaksiNama, int userId, int transaksiTipeId) {
        this.transaksiId = transaksiId;
        this.transaksiDate = transaksiDate;
        this.transaksiNama = transaksiNama;
        this.userId = userId;
        this.transaksiTipeId = transaksiTipeId;
    }

    public Integer getTransaksiId() {
        return transaksiId;
    }

    public void setTransaksiId(Integer transaksiId) {
        this.transaksiId = transaksiId;
    }

    public Date getTransaksiDate() {
        return transaksiDate;
    }

    public void setTransaksiDate(Date transaksiDate) {
        this.transaksiDate = transaksiDate;
    }

    public String getTransaksiNama() {
        return transaksiNama;
    }

    public void setTransaksiNama(String transaksiNama) {
        this.transaksiNama = transaksiNama;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getTransaksiTipeId() {
        return transaksiTipeId;
    }

    public void setTransaksiTipeId(int transaksiTipeId) {
        this.transaksiTipeId = transaksiTipeId;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (transaksiId != null ? transaksiId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Transaksi)) {
            return false;
        }
        Transaksi other = (Transaksi) object;
        if ((this.transaksiId == null && other.transaksiId != null) || (this.transaksiId != null && !this.transaksiId.equals(other.transaksiId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Transaksi[ transaksiId=" + transaksiId + " ]";
    }
    
}
