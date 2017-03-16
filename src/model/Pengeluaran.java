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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "pengeluaran")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pengeluaran.findAll", query = "SELECT p FROM Pengeluaran p")
    , @NamedQuery(name = "Pengeluaran.findByPengeluaranId", query = "SELECT p FROM Pengeluaran p WHERE p.pengeluaranId = :pengeluaranId")
    , @NamedQuery(name = "Pengeluaran.findByPengeluaranNama", query = "SELECT p FROM Pengeluaran p WHERE p.pengeluaranNama = :pengeluaranNama")
    , @NamedQuery(name = "Pengeluaran.findByPengeluaranNominal", query = "SELECT p FROM Pengeluaran p WHERE p.pengeluaranNominal = :pengeluaranNominal")})
public class Pengeluaran implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pengeluaran_id")
    private Integer pengeluaranId;
    @Basic(optional = false)
    @Column(name = "pengeluaran_nama")
    private String pengeluaranNama;
    @Basic(optional = false)
    @Column(name = "pengeluaran_nominal")
    private int pengeluaranNominal;
    @JoinColumn(name = "pengeluaran_jenis_id", referencedColumnName = "pengeluaran_jenis_id")
    @ManyToOne(optional = false)
    private PengeluaranJenis pengeluaranJenisId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pengeluaranId")
    private List<PengeluaranPerubahan> pengeluaranPerubahanList;

    public Pengeluaran() {
    }

    public Pengeluaran(Integer pengeluaranId) {
        this.pengeluaranId = pengeluaranId;
    }

    public Pengeluaran(Integer pengeluaranId, String pengeluaranNama, int pengeluaranNominal) {
        this.pengeluaranId = pengeluaranId;
        this.pengeluaranNama = pengeluaranNama;
        this.pengeluaranNominal = pengeluaranNominal;
    }

    public Integer getPengeluaranId() {
        return pengeluaranId;
    }

    public void setPengeluaranId(Integer pengeluaranId) {
        this.pengeluaranId = pengeluaranId;
    }

    public String getPengeluaranNama() {
        return pengeluaranNama;
    }

    public void setPengeluaranNama(String pengeluaranNama) {
        this.pengeluaranNama = pengeluaranNama;
    }

    public int getPengeluaranNominal() {
        return pengeluaranNominal;
    }

    public void setPengeluaranNominal(int pengeluaranNominal) {
        this.pengeluaranNominal = pengeluaranNominal;
    }

    public PengeluaranJenis getPengeluaranJenisId() {
        return pengeluaranJenisId;
    }

    public void setPengeluaranJenisId(PengeluaranJenis pengeluaranJenisId) {
        this.pengeluaranJenisId = pengeluaranJenisId;
    }

    @XmlTransient
    public List<PengeluaranPerubahan> getPengeluaranPerubahanList() {
        return pengeluaranPerubahanList;
    }

    public void setPengeluaranPerubahanList(List<PengeluaranPerubahan> pengeluaranPerubahanList) {
        this.pengeluaranPerubahanList = pengeluaranPerubahanList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pengeluaranId != null ? pengeluaranId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pengeluaran)) {
            return false;
        }
        Pengeluaran other = (Pengeluaran) object;
        if ((this.pengeluaranId == null && other.pengeluaranId != null) || (this.pengeluaranId != null && !this.pengeluaranId.equals(other.pengeluaranId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Pengeluaran[ pengeluaranId=" + pengeluaranId + " ]";
    }
    
}
