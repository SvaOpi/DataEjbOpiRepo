/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dataejbopi.entity;

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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Usuario1
 */
@Entity
@Table(name = "PIN")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pin.findAll", query = "SELECT p FROM Pin p"),
    @NamedQuery(name = "Pin.findById", query = "SELECT p FROM Pin p WHERE p.id = :id"),
    @NamedQuery(name = "Pin.findByCreationdate", query = "SELECT p FROM Pin p WHERE p.creationdate = :creationdate"),
    @NamedQuery(name = "Pin.findByLimitdate", query = "SELECT p FROM Pin p WHERE p.limitdate = :limitdate"),
    @NamedQuery(name = "Pin.findByPinstate", query = "SELECT p FROM Pin p WHERE p.pinstate = :pinstate")})
public class Pin implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    @Column(name = "CREATIONDATE")
    @Temporal(TemporalType.DATE)
    private Date creationdate;
    @Column(name = "LIMITDATE")
    @Temporal(TemporalType.DATE)
    private Date limitdate;
    @Size(max = 255)
    @Column(name = "PINSTATE")
    private String pinstate;
    @JoinColumn(name = "PERSONS_CEDULE", referencedColumnName = "CEDULE")
    @ManyToOne
    private Person person;
    @OneToOne(orphanRemoval = true)
    private Payment payment;

    public Pin() {
    }

    public Pin(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreationdate() {
        return creationdate;
    }

    public void setCreationdate(Date creationdate) {
        this.creationdate = creationdate;
    }

    public Date getLimitdate() {
        return limitdate;
    }

    public void setLimitdate(Date limitdate) {
        this.limitdate = limitdate;
    }

    public String getPinstate() {
        return pinstate;
    }

    public void setPinstate(String pinstate) {
        this.pinstate = pinstate;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pin)) {
            return false;
        }
        Pin other = (Pin) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dataejbopi.entity.Pin[ id=" + id + " ]";
    }
    
}
