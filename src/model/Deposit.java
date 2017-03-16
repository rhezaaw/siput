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
@Table(name = "deposit")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Deposit.findAll", query = "SELECT d FROM Deposit d")
    , @NamedQuery(name = "Deposit.findByDepositId", query = "SELECT d FROM Deposit d WHERE d.depositId = :depositId")
    , @NamedQuery(name = "Deposit.findByDepositJumlah", query = "SELECT d FROM Deposit d WHERE d.depositJumlah = :depositJumlah")})
public class Deposit implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "deposit_id")
    private Integer depositId;
    @Basic(optional = false)
    @Column(name = "deposit_jumlah")
    private int depositJumlah;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "depositId")
    private List<DepositPerubahan> depositPerubahanList;
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    @ManyToOne(optional = false)
    private User userId;

    public Deposit() {
    }

    public Deposit(Integer depositId) {
        this.depositId = depositId;
    }

    public Deposit(Integer depositId, int depositJumlah) {
        this.depositId = depositId;
        this.depositJumlah = depositJumlah;
    }

    public Integer getDepositId() {
        return depositId;
    }

    public void setDepositId(Integer depositId) {
        this.depositId = depositId;
    }

    public int getDepositJumlah() {
        return depositJumlah;
    }

    public void setDepositJumlah(int depositJumlah) {
        this.depositJumlah = depositJumlah;
    }

    @XmlTransient
    public List<DepositPerubahan> getDepositPerubahanList() {
        return depositPerubahanList;
    }

    public void setDepositPerubahanList(List<DepositPerubahan> depositPerubahanList) {
        this.depositPerubahanList = depositPerubahanList;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (depositId != null ? depositId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Deposit)) {
            return false;
        }
        Deposit other = (Deposit) object;
        if ((this.depositId == null && other.depositId != null) || (this.depositId != null && !this.depositId.equals(other.depositId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Deposit[ depositId=" + depositId + " ]";
    }
    
}
