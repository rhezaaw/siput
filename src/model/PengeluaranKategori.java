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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author fachrul
 */
@Entity
@Table(name = "pengeluaran_kategori")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PengeluaranKategori.findAll", query = "SELECT p FROM PengeluaranKategori p")
    , @NamedQuery(name = "PengeluaranKategori.findByPengeluaranKategoriId", query = "SELECT p FROM PengeluaranKategori p WHERE p.pengeluaranKategoriId = :pengeluaranKategoriId")
    , @NamedQuery(name = "PengeluaranKategori.findByPengeluaranKategoriNama", query = "SELECT p FROM PengeluaranKategori p WHERE p.pengeluaranKategoriNama = :pengeluaranKategoriNama")
    , @NamedQuery(name = "PengeluaranKategori.findByPengeluaranKategoriWaktu", query = "SELECT p FROM PengeluaranKategori p WHERE p.pengeluaranKategoriWaktu = :pengeluaranKategoriWaktu")})
public class PengeluaranKategori implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pengeluaran_kategori_id")
    private Integer pengeluaranKategoriId;
    @Basic(optional = false)
    @Column(name = "pengeluaran_kategori_nama")
    private String pengeluaranKategoriNama;
    @Basic(optional = false)
    @Column(name = "pengeluaran_kategori_waktu")
    private int pengeluaranKategoriWaktu;

    public PengeluaranKategori() {
    }

    public PengeluaranKategori(Integer pengeluaranKategoriId) {
        this.pengeluaranKategoriId = pengeluaranKategoriId;
    }

    public PengeluaranKategori(Integer pengeluaranKategoriId, String pengeluaranKategoriNama, int pengeluaranKategoriWaktu) {
        this.pengeluaranKategoriId = pengeluaranKategoriId;
        this.pengeluaranKategoriNama = pengeluaranKategoriNama;
        this.pengeluaranKategoriWaktu = pengeluaranKategoriWaktu;
    }

    public Integer getPengeluaranKategoriId() {
        return pengeluaranKategoriId;
    }

    public void setPengeluaranKategoriId(Integer pengeluaranKategoriId) {
        this.pengeluaranKategoriId = pengeluaranKategoriId;
    }

    public String getPengeluaranKategoriNama() {
        return pengeluaranKategoriNama;
    }

    public void setPengeluaranKategoriNama(String pengeluaranKategoriNama) {
        this.pengeluaranKategoriNama = pengeluaranKategoriNama;
    }

    public int getPengeluaranKategoriWaktu() {
        return pengeluaranKategoriWaktu;
    }

    public void setPengeluaranKategoriWaktu(int pengeluaranKategoriWaktu) {
        this.pengeluaranKategoriWaktu = pengeluaranKategoriWaktu;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pengeluaranKategoriId != null ? pengeluaranKategoriId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PengeluaranKategori)) {
            return false;
        }
        PengeluaranKategori other = (PengeluaranKategori) object;
        if ((this.pengeluaranKategoriId == null && other.pengeluaranKategoriId != null) || (this.pengeluaranKategoriId != null && !this.pengeluaranKategoriId.equals(other.pengeluaranKategoriId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.PengeluaranKategori[ pengeluaranKategoriId=" + pengeluaranKategoriId + " ]";
    }
    
}
