/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dataejbopi.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Usuario1
 */
@Entity
@Table(name = "PAYMENT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Payment.findAll", query = "SELECT p FROM Payment p"),
    @NamedQuery(name = "Payment.findById", query = "SELECT p FROM Payment p WHERE p.id = :id"),
    @NamedQuery(name = "Payment.findByPaydate", query = "SELECT p FROM Payment p WHERE p.paydate = :paydate"),
    @NamedQuery(name = "Payment.findByTotalvalue", query = "SELECT p FROM Payment p WHERE p.totalvalue = :totalvalue")})
public class Payment implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    @Column(name = "PAYDATE")
    @Temporal(TemporalType.DATE)
    private Date paydate;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "TOTALVALUE")
    private Double totalvalue;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column
    private Double opiServiceValue;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column
    private Double healtServiceValue;
    @OneToOne(cascade = CascadeType.MERGE)
    private Pin pin;

    public Payment() {
    }

    public Payment(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getPaydate() {
        return paydate;
    }

    public void setPaydate(Date paydate) {
        this.paydate = paydate;
    }

    public Double getTotalvalue() {
        return totalvalue;
    }

    public void setTotalvalue(Double totalvalue) {
        this.totalvalue = totalvalue;
    }

    public Double getOpiServiceValue() {
        return opiServiceValue;
    }

    public void setOpiServiceValue(Double opiServiceValue) {
        this.opiServiceValue = opiServiceValue;
    }

    public Double getHealtServiceValue() {
        return healtServiceValue;
    }

    public void setHealtServiceValue(Double healtServiceValue) {
        this.healtServiceValue = healtServiceValue;
    }
   
    public Pin getPin() {
        return pin;
    }

    public void setPin(Pin pin) {
        this.pin = pin;
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
        if (!(object instanceof Payment)) {
            return false;
        }
        Payment other = (Payment) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dataejbopi.entity.Payment[ id=" + id + " ]";
    }
    
}
