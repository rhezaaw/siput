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
@Table(name = "pengeluaran_perubahan")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PengeluaranPerubahan.findAll", query = "SELECT p FROM PengeluaranPerubahan p")
    , @NamedQuery(name = "PengeluaranPerubahan.findByPengeluaranPerubahanId", query = "SELECT p FROM PengeluaranPerubahan p WHERE p.pengeluaranPerubahanId = :pengeluaranPerubahanId")
    , @NamedQuery(name = "PengeluaranPerubahan.findByPengeluaranPerubahanNominal", query = "SELECT p FROM PengeluaranPerubahan p WHERE p.pengeluaranPerubahanNominal = :pengeluaranPerubahanNominal")
    , @NamedQuery(name = "PengeluaranPerubahan.findByPengeluaranPerubahanDate", query = "SELECT p FROM PengeluaranPerubahan p WHERE p.pengeluaranPerubahanDate = :pengeluaranPerubahanDate")})
public class PengeluaranPerubahan implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pengeluaran_perubahan_id")
    private Integer pengeluaranPerubahanId;
    @Basic(optional = false)
    @Column(name = "pengeluaran_perubahan_nominal")
    private int pengeluaranPerubahanNominal;
    @Basic(optional = false)
    @Column(name = "pengeluaran_perubahan_date")
    @Temporal(TemporalType.DATE)
    private Date pengeluaranPerubahanDate;
    @JoinColumn(name = "pengeluaran_id", referencedColumnName = "pengeluaran_id")
    @ManyToOne(optional = false)
    private Pengeluaran pengeluaranId;

    public PengeluaranPerubahan() {
    }

    public PengeluaranPerubahan(Integer pengeluaranPerubahanId) {
        this.pengeluaranPerubahanId = pengeluaranPerubahanId;
    }

    public PengeluaranPerubahan(Integer pengeluaranPerubahanId, int pengeluaranPerubahanNominal, Date pengeluaranPerubahanDate) {
        this.pengeluaranPerubahanId = pengeluaranPerubahanId;
        this.pengeluaranPerubahanNominal = pengeluaranPerubahanNominal;
        this.pengeluaranPerubahanDate = pengeluaranPerubahanDate;
    }

    public Integer getPengeluaranPerubahanId() {
        return pengeluaranPerubahanId;
    }

    public void setPengeluaranPerubahanId(Integer pengeluaranPerubahanId) {
        this.pengeluaranPerubahanId = pengeluaranPerubahanId;
    }

    public int getPengeluaranPerubahanNominal() {
        return pengeluaranPerubahanNominal;
    }

    public void setPengeluaranPerubahanNominal(int pengeluaranPerubahanNominal) {
        this.pengeluaranPerubahanNominal = pengeluaranPerubahanNominal;
    }

    public Date getPengeluaranPerubahanDate() {
        return pengeluaranPerubahanDate;
    }

    public void setPengeluaranPerubahanDate(Date pengeluaranPerubahanDate) {
        this.pengeluaranPerubahanDate = pengeluaranPerubahanDate;
    }

    public Pengeluaran getPengeluaranId() {
        return pengeluaranId;
    }

    public void setPengeluaranId(Pengeluaran pengeluaranId) {
        this.pengeluaranId = pengeluaranId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pengeluaranPerubahanId != null ? pengeluaranPerubahanId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PengeluaranPerubahan)) {
            return false;
        }
        PengeluaranPerubahan other = (PengeluaranPerubahan) object;
        if ((this.pengeluaranPerubahanId == null && other.pengeluaranPerubahanId != null) || (this.pengeluaranPerubahanId != null && !this.pengeluaranPerubahanId.equals(other.pengeluaranPerubahanId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.PengeluaranPerubahan[ pengeluaranPerubahanId=" + pengeluaranPerubahanId + " ]";
    }
    
}
