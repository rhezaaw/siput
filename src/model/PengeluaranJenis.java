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
import javax.persistence.Lob;
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
@Table(name = "pengeluaran_jenis")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PengeluaranJenis.findAll", query = "SELECT p FROM PengeluaranJenis p")
    , @NamedQuery(name = "PengeluaranJenis.findByPengeluaranJenisId", query = "SELECT p FROM PengeluaranJenis p WHERE p.pengeluaranJenisId = :pengeluaranJenisId")
    , @NamedQuery(name = "PengeluaranJenis.findByPengeluaranNama", query = "SELECT p FROM PengeluaranJenis p WHERE p.pengeluaranNama = :pengeluaranNama")})
public class PengeluaranJenis implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pengeluaran_jenis_id")
    private Integer pengeluaranJenisId;
    @Basic(optional = false)
    @Column(name = "pengeluaran_nama")
    private String pengeluaranNama;
    @Basic(optional = false)
    @Lob
    @Column(name = "pengeluaran_keterangan")
    private String pengeluaranKeterangan;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pengeluaranJenisId")
    private List<Pengeluaran> pengeluaranList;

    public PengeluaranJenis() {
    }

    public PengeluaranJenis(Integer pengeluaranJenisId) {
        this.pengeluaranJenisId = pengeluaranJenisId;
    }

    public PengeluaranJenis(Integer pengeluaranJenisId, String pengeluaranNama, String pengeluaranKeterangan) {
        this.pengeluaranJenisId = pengeluaranJenisId;
        this.pengeluaranNama = pengeluaranNama;
        this.pengeluaranKeterangan = pengeluaranKeterangan;
    }

    public Integer getPengeluaranJenisId() {
        return pengeluaranJenisId;
    }

    public void setPengeluaranJenisId(Integer pengeluaranJenisId) {
        this.pengeluaranJenisId = pengeluaranJenisId;
    }

    public String getPengeluaranNama() {
        return pengeluaranNama;
    }

    public void setPengeluaranNama(String pengeluaranNama) {
        this.pengeluaranNama = pengeluaranNama;
    }

    public String getPengeluaranKeterangan() {
        return pengeluaranKeterangan;
    }

    public void setPengeluaranKeterangan(String pengeluaranKeterangan) {
        this.pengeluaranKeterangan = pengeluaranKeterangan;
    }

    @XmlTransient
    public List<Pengeluaran> getPengeluaranList() {
        return pengeluaranList;
    }

    public void setPengeluaranList(List<Pengeluaran> pengeluaranList) {
        this.pengeluaranList = pengeluaranList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pengeluaranJenisId != null ? pengeluaranJenisId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PengeluaranJenis)) {
            return false;
        }
        PengeluaranJenis other = (PengeluaranJenis) object;
        if ((this.pengeluaranJenisId == null && other.pengeluaranJenisId != null) || (this.pengeluaranJenisId != null && !this.pengeluaranJenisId.equals(other.pengeluaranJenisId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.PengeluaranJenis[ pengeluaranJenisId=" + pengeluaranJenisId + " ]";
    }
    
}
