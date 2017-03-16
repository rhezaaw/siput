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
@Table(name = "iuran_jenis")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "IuranJenis.findAll", query = "SELECT i FROM IuranJenis i")
    , @NamedQuery(name = "IuranJenis.findByIuranJenisId", query = "SELECT i FROM IuranJenis i WHERE i.iuranJenisId = :iuranJenisId")
    , @NamedQuery(name = "IuranJenis.findByIuranJenisNama", query = "SELECT i FROM IuranJenis i WHERE i.iuranJenisNama = :iuranJenisNama")
    , @NamedQuery(name = "IuranJenis.findByIuranJenisKeterangan", query = "SELECT i FROM IuranJenis i WHERE i.iuranJenisKeterangan = :iuranJenisKeterangan")})
public class IuranJenis implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "iuran_jenis_id")
    private Integer iuranJenisId;
    @Basic(optional = false)
    @Column(name = "iuran_jenis_nama")
    private String iuranJenisNama;
    @Basic(optional = false)
    @Column(name = "iuran_jenis_keterangan")
    private String iuranJenisKeterangan;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "iuranJenisId")
    private List<Iuran> iuranList;

    public IuranJenis() {
    }

    public IuranJenis(Integer iuranJenisId) {
        this.iuranJenisId = iuranJenisId;
    }

    public IuranJenis(Integer iuranJenisId, String iuranJenisNama, String iuranJenisKeterangan) {
        this.iuranJenisId = iuranJenisId;
        this.iuranJenisNama = iuranJenisNama;
        this.iuranJenisKeterangan = iuranJenisKeterangan;
    }

    public Integer getIuranJenisId() {
        return iuranJenisId;
    }

    public void setIuranJenisId(Integer iuranJenisId) {
        this.iuranJenisId = iuranJenisId;
    }

    public String getIuranJenisNama() {
        return iuranJenisNama;
    }

    public void setIuranJenisNama(String iuranJenisNama) {
        this.iuranJenisNama = iuranJenisNama;
    }

    public String getIuranJenisKeterangan() {
        return iuranJenisKeterangan;
    }

    public void setIuranJenisKeterangan(String iuranJenisKeterangan) {
        this.iuranJenisKeterangan = iuranJenisKeterangan;
    }

    @XmlTransient
    public List<Iuran> getIuranList() {
        return iuranList;
    }

    public void setIuranList(List<Iuran> iuranList) {
        this.iuranList = iuranList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iuranJenisId != null ? iuranJenisId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof IuranJenis)) {
            return false;
        }
        IuranJenis other = (IuranJenis) object;
        if ((this.iuranJenisId == null && other.iuranJenisId != null) || (this.iuranJenisId != null && !this.iuranJenisId.equals(other.iuranJenisId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.IuranJenis[ iuranJenisId=" + iuranJenisId + " ]";
    }
    
}
