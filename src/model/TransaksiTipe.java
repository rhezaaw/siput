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
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author fachrul
 */
@Entity
@Table(name = "transaksi_tipe")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TransaksiTipe.findAll", query = "SELECT t FROM TransaksiTipe t")
    , @NamedQuery(name = "TransaksiTipe.findByTransaksiTipeId", query = "SELECT t FROM TransaksiTipe t WHERE t.transaksiTipeId = :transaksiTipeId")
    , @NamedQuery(name = "TransaksiTipe.findByTransaksiTipeNama", query = "SELECT t FROM TransaksiTipe t WHERE t.transaksiTipeNama = :transaksiTipeNama")})
public class TransaksiTipe implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "transaksi_tipe_id")
    private Integer transaksiTipeId;
    @Basic(optional = false)
    @Column(name = "transaksi_tipe_nama")
    private String transaksiTipeNama;
    @Lob
    @Column(name = "transaksi_tipe_keterangan")
    private String transaksiTipeKeterangan;

    public TransaksiTipe() {
    }

    public TransaksiTipe(Integer transaksiTipeId) {
        this.transaksiTipeId = transaksiTipeId;
    }

    public TransaksiTipe(Integer transaksiTipeId, String transaksiTipeNama) {
        this.transaksiTipeId = transaksiTipeId;
        this.transaksiTipeNama = transaksiTipeNama;
    }

    public Integer getTransaksiTipeId() {
        return transaksiTipeId;
    }

    public void setTransaksiTipeId(Integer transaksiTipeId) {
        this.transaksiTipeId = transaksiTipeId;
    }

    public String getTransaksiTipeNama() {
        return transaksiTipeNama;
    }

    public void setTransaksiTipeNama(String transaksiTipeNama) {
        this.transaksiTipeNama = transaksiTipeNama;
    }

    public String getTransaksiTipeKeterangan() {
        return transaksiTipeKeterangan;
    }

    public void setTransaksiTipeKeterangan(String transaksiTipeKeterangan) {
        this.transaksiTipeKeterangan = transaksiTipeKeterangan;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (transaksiTipeId != null ? transaksiTipeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TransaksiTipe)) {
            return false;
        }
        TransaksiTipe other = (TransaksiTipe) object;
        if ((this.transaksiTipeId == null && other.transaksiTipeId != null) || (this.transaksiTipeId != null && !this.transaksiTipeId.equals(other.transaksiTipeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.TransaksiTipe[ transaksiTipeId=" + transaksiTipeId + " ]";
    }
    
}
