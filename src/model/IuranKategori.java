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
@Table(name = "iuran_kategori")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "IuranKategori.findAll", query = "SELECT i FROM IuranKategori i")
    , @NamedQuery(name = "IuranKategori.findByIuranKategoriId", query = "SELECT i FROM IuranKategori i WHERE i.iuranKategoriId = :iuranKategoriId")
    , @NamedQuery(name = "IuranKategori.findByIuranKategoriNama", query = "SELECT i FROM IuranKategori i WHERE i.iuranKategoriNama = :iuranKategoriNama")
    , @NamedQuery(name = "IuranKategori.findByIuranKategoriInterval", query = "SELECT i FROM IuranKategori i WHERE i.iuranKategoriInterval = :iuranKategoriInterval")})
public class IuranKategori implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "iuran_kategori_id")
    private Integer iuranKategoriId;
    @Basic(optional = false)
    @Column(name = "iuran_kategori_nama")
    private String iuranKategoriNama;
    @Column(name = "iuran_kategori_interval")
    private Integer iuranKategoriInterval;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "iuranKategoriId")
    private List<Iuran> iuranList;

    public IuranKategori() {
    }

    public IuranKategori(Integer iuranKategoriId) {
        this.iuranKategoriId = iuranKategoriId;
    }

    public IuranKategori(Integer iuranKategoriId, String iuranKategoriNama) {
        this.iuranKategoriId = iuranKategoriId;
        this.iuranKategoriNama = iuranKategoriNama;
    }

    public Integer getIuranKategoriId() {
        return iuranKategoriId;
    }

    public void setIuranKategoriId(Integer iuranKategoriId) {
        this.iuranKategoriId = iuranKategoriId;
    }

    public String getIuranKategoriNama() {
        return iuranKategoriNama;
    }

    public void setIuranKategoriNama(String iuranKategoriNama) {
        this.iuranKategoriNama = iuranKategoriNama;
    }

    public Integer getIuranKategoriInterval() {
        return iuranKategoriInterval;
    }

    public void setIuranKategoriInterval(Integer iuranKategoriInterval) {
        this.iuranKategoriInterval = iuranKategoriInterval;
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
        hash += (iuranKategoriId != null ? iuranKategoriId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof IuranKategori)) {
            return false;
        }
        IuranKategori other = (IuranKategori) object;
        if ((this.iuranKategoriId == null && other.iuranKategoriId != null) || (this.iuranKategoriId != null && !this.iuranKategoriId.equals(other.iuranKategoriId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.IuranKategori[ iuranKategoriId=" + iuranKategoriId + " ]";
    }
    
}
