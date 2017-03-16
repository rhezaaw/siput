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
@Table(name = "deposit_perubahan")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DepositPerubahan.findAll", query = "SELECT d FROM DepositPerubahan d")
    , @NamedQuery(name = "DepositPerubahan.findByDepositPerubahanId", query = "SELECT d FROM DepositPerubahan d WHERE d.depositPerubahanId = :depositPerubahanId")
    , @NamedQuery(name = "DepositPerubahan.findByDepositPerubahanDate", query = "SELECT d FROM DepositPerubahan d WHERE d.depositPerubahanDate = :depositPerubahanDate")
    , @NamedQuery(name = "DepositPerubahan.findByTransaksiId", query = "SELECT d FROM DepositPerubahan d WHERE d.transaksiId = :transaksiId")})
public class DepositPerubahan implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "deposit_perubahan_id")
    private Integer depositPerubahanId;
    @Basic(optional = false)
    @Column(name = "deposit_perubahan_date")
    @Temporal(TemporalType.DATE)
    private Date depositPerubahanDate;
    @Basic(optional = false)
    @Column(name = "transaksi_id")
    private int transaksiId;
    @JoinColumn(name = "deposit_id", referencedColumnName = "deposit_id")
    @ManyToOne(optional = false)
    private Deposit depositId;

    public DepositPerubahan() {
    }

    public DepositPerubahan(Integer depositPerubahanId) {
        this.depositPerubahanId = depositPerubahanId;
    }

    public DepositPerubahan(Integer depositPerubahanId, Date depositPerubahanDate, int transaksiId) {
        this.depositPerubahanId = depositPerubahanId;
        this.depositPerubahanDate = depositPerubahanDate;
        this.transaksiId = transaksiId;
    }

    public Integer getDepositPerubahanId() {
        return depositPerubahanId;
    }

    public void setDepositPerubahanId(Integer depositPerubahanId) {
        this.depositPerubahanId = depositPerubahanId;
    }

    public Date getDepositPerubahanDate() {
        return depositPerubahanDate;
    }

    public void setDepositPerubahanDate(Date depositPerubahanDate) {
        this.depositPerubahanDate = depositPerubahanDate;
    }

    public int getTransaksiId() {
        return transaksiId;
    }

    public void setTransaksiId(int transaksiId) {
        this.transaksiId = transaksiId;
    }

    public Deposit getDepositId() {
        return depositId;
    }

    public void setDepositId(Deposit depositId) {
        this.depositId = depositId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (depositPerubahanId != null ? depositPerubahanId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DepositPerubahan)) {
            return false;
        }
        DepositPerubahan other = (DepositPerubahan) object;
        if ((this.depositPerubahanId == null && other.depositPerubahanId != null) || (this.depositPerubahanId != null && !this.depositPerubahanId.equals(other.depositPerubahanId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.DepositPerubahan[ depositPerubahanId=" + depositPerubahanId + " ]";
    }
    
}
