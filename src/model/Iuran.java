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
@Table(name = "iuran")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Iuran.findAll", query = "SELECT i FROM Iuran i")
    , @NamedQuery(name = "Iuran.findByIuranId", query = "SELECT i FROM Iuran i WHERE i.iuranId = :iuranId")
    , @NamedQuery(name = "Iuran.findByIuranNama", query = "SELECT i FROM Iuran i WHERE i.iuranNama = :iuranNama")
    , @NamedQuery(name = "Iuran.findByIuranNominal", query = "SELECT i FROM Iuran i WHERE i.iuranNominal = :iuranNominal")})
public class Iuran implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "iuran_id")
    private Integer iuranId;
    @Basic(optional = false)
    @Column(name = "iuran_nama")
    private String iuranNama;
    @Basic(optional = false)
    @Column(name = "iuran_nominal")
    private int iuranNominal;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "iuranId")
    private List<IuranPerubahan> iuranPerubahanList;
    @JoinColumn(name = "iuran_kategori_id", referencedColumnName = "iuran_kategori_id")
    @ManyToOne(optional = false)
    private IuranKategori iuranKategoriId;
    @JoinColumn(name = "iuran_jenis_id", referencedColumnName = "iuran_jenis_id")
    @ManyToOne(optional = false)
    private IuranJenis iuranJenisId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "iuranId")
    private List<PengeluaranUser> pengeluaranUserList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "iuranId")
    private List<IuranUser> iuranUserList;

    public Iuran() {
    }

    public Iuran(Integer iuranId) {
        this.iuranId = iuranId;
    }

    public Iuran(Integer iuranId, String iuranNama, int iuranNominal) {
        this.iuranId = iuranId;
        this.iuranNama = iuranNama;
        this.iuranNominal = iuranNominal;
    }

    public Integer getIuranId() {
        return iuranId;
    }

    public void setIuranId(Integer iuranId) {
        this.iuranId = iuranId;
    }

    public String getIuranNama() {
        return iuranNama;
    }

    public void setIuranNama(String iuranNama) {
        this.iuranNama = iuranNama;
    }

    public int getIuranNominal() {
        return iuranNominal;
    }

    public void setIuranNominal(int iuranNominal) {
        this.iuranNominal = iuranNominal;
    }

    @XmlTransient
    public List<IuranPerubahan> getIuranPerubahanList() {
        return iuranPerubahanList;
    }

    public void setIuranPerubahanList(List<IuranPerubahan> iuranPerubahanList) {
        this.iuranPerubahanList = iuranPerubahanList;
    }

    public IuranKategori getIuranKategoriId() {
        return iuranKategoriId;
    }

    public void setIuranKategoriId(IuranKategori iuranKategoriId) {
        this.iuranKategoriId = iuranKategoriId;
    }

    public IuranJenis getIuranJenisId() {
        return iuranJenisId;
    }

    public void setIuranJenisId(IuranJenis iuranJenisId) {
        this.iuranJenisId = iuranJenisId;
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
        hash += (iuranId != null ? iuranId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Iuran)) {
            return false;
        }
        Iuran other = (Iuran) object;
        if ((this.iuranId == null && other.iuranId != null) || (this.iuranId != null && !this.iuranId.equals(other.iuranId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Iuran[ iuranId=" + iuranId + " ]";
    }
    
}
