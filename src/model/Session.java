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
@Table(name = "session")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Session.findAll", query = "SELECT s FROM Session s")
    , @NamedQuery(name = "Session.findBySessionId", query = "SELECT s FROM Session s WHERE s.sessionId = :sessionId")
    , @NamedQuery(name = "Session.findBySessionTime", query = "SELECT s FROM Session s WHERE s.sessionTime = :sessionTime")
    , @NamedQuery(name = "Session.findBySessionStatus", query = "SELECT s FROM Session s WHERE s.sessionStatus = :sessionStatus")})
public class Session implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "session_id")
    private Integer sessionId;
    @Basic(optional = false)
    @Column(name = "session_time")
    @Temporal(TemporalType.TIME)
    private Date sessionTime;
    @Basic(optional = false)
    @Column(name = "session_status")
    private boolean sessionStatus;
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    @ManyToOne(optional = false)
    private User userId;

    public Session() {
    }

    public Session(Integer sessionId) {
        this.sessionId = sessionId;
    }

    public Session(Integer sessionId, Date sessionTime, boolean sessionStatus) {
        this.sessionId = sessionId;
        this.sessionTime = sessionTime;
        this.sessionStatus = sessionStatus;
    }

    public Integer getSessionId() {
        return sessionId;
    }

    public void setSessionId(Integer sessionId) {
        this.sessionId = sessionId;
    }

    public Date getSessionTime() {
        return sessionTime;
    }

    public void setSessionTime(Date sessionTime) {
        this.sessionTime = sessionTime;
    }

    public boolean getSessionStatus() {
        return sessionStatus;
    }

    public void setSessionStatus(boolean sessionStatus) {
        this.sessionStatus = sessionStatus;
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
        hash += (sessionId != null ? sessionId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Session)) {
            return false;
        }
        Session other = (Session) object;
        if ((this.sessionId == null && other.sessionId != null) || (this.sessionId != null && !this.sessionId.equals(other.sessionId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Session[ sessionId=" + sessionId + " ]";
    }
    
}
