/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author fachrul
 */
@Entity
@Table(name = "iuran_perubahan")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "IuranPerubahan.findAll", query = "SELECT i FROM IuranPerubahan i")
    , @NamedQuery(name = "IuranPerubahan.findByIuranPerubahanId", query = "SELECT i FROM IuranPerubahan i WHERE i.iuranPerubahanId = :iuranPerubahanId")
    , @NamedQuery(name = "IuranPerubahan.findByIuranPerubahanNominal", query = "SELECT i FROM IuranPerubahan i WHERE i.iuranPerubahanNominal = :iuranPerubahanNominal")
    , @NamedQuery(name = "IuranPerubahan.findByIuranPerubahanDate", query = "SELECT i FROM IuranPerubahan i WHERE i.iuranPerubahanDate = :iuranPerubahanDate")})
public class IuranPerubahan implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "iuran_perubahan_id")
    private Integer iuranPerubahanId;
    @Basic(optional = false)
    @Column(name = "iuran_perubahan_nominal")
    private int iuranPerubahanNominal;
    @Basic(optional = false)
    @Column(name = "iuran_perubahan_date")
    @Temporal(TemporalType.DATE)
    private Date iuranPerubahanDate;
    @JoinColumn(name = "iuran_id", referencedColumnName = "iuran_id")
    @ManyToOne(optional = false)
    private Iuran iuranId;

    public IuranPerubahan() {
    }

    public IuranPerubahan(Integer iuranPerubahanId) {
        this.iuranPerubahanId = iuranPerubahanId;
    }

    public IuranPerubahan(Integer iuranPerubahanId, int iuranPerubahanNominal, Date iuranPerubahanDate) {
        this.iuranPerubahanId = iuranPerubahanId;
        this.iuranPerubahanNominal = iuranPerubahanNominal;
        this.iuranPerubahanDate = iuranPerubahanDate;
    }

    public Integer getIuranPerubahanId() {
        return iuranPerubahanId;
    }

    public void setIuranPerubahanId(Integer iuranPerubahanId) {
        this.iuranPerubahanId = iuranPerubahanId;
    }

    public int getIuranPerubahanNominal() {
        return iuranPerubahanNominal;
    }

    public void setIuranPerubahanNominal(int iuranPerubahanNominal) {
        this.iuranPerubahanNominal = iuranPerubahanNominal;
    }

    public Date getIuranPerubahanDate() {
        return iuranPerubahanDate;
    }

    public void setIuranPerubahanDate(Date iuranPerubahanDate) {
        this.iuranPerubahanDate = iuranPerubahanDate;
    }

    public Iuran getIuranId() {
        return iuranId;
    }

    public void setIuranId(Iuran iuranId) {
        this.iuranId = iuranId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iuranPerubahanId != null ? iuranPerubahanId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof IuranPerubahan)) {
            return false;
        }
        IuranPerubahan other = (IuranPerubahan) object;
        if ((this.iuranPerubahanId == null && other.iuranPerubahanId != null) || (this.iuranPerubahanId != null && !this.iuranPerubahanId.equals(other.iuranPerubahanId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.IuranPerubahan[ iuranPerubahanId=" + iuranPerubahanId + " ]";
    }
    
}
