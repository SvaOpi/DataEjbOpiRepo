/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dataejbopi.entity;

import java.io.Serializable;
import java.util.Collection;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

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
    @NamedQuery(name = "Payment.findByExtrapay", query = "SELECT p FROM Payment p WHERE p.extrapay = :extrapay"),
    @NamedQuery(name = "Payment.findByPay", query = "SELECT p FROM Payment p WHERE p.pay = :pay"),
    @NamedQuery(name = "Payment.findByTotalvalue", query = "SELECT p FROM Payment p WHERE p.totalvalue = :totalvalue")})
public class Payment implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    @Column(name = "EXTRAPAY")
    @Temporal(TemporalType.DATE)
    private Date extrapay;
    @Column(name = "PAY")
    @Temporal(TemporalType.DATE)
    private Date pay;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "TOTALVALUE")
    private Double totalvalue;
    @JoinColumn(name = "PIN_ID", referencedColumnName = "ID")
    @ManyToOne
    private Pin pinId;
    @OneToMany(mappedBy = "paymentId")
    private Collection<Pin> pinCollection;

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

    public Date getExtrapay() {
        return extrapay;
    }

    public void setExtrapay(Date extrapay) {
        this.extrapay = extrapay;
    }

    public Date getPay() {
        return pay;
    }

    public void setPay(Date pay) {
        this.pay = pay;
    }

    public Double getTotalvalue() {
        return totalvalue;
    }

    public void setTotalvalue(Double totalvalue) {
        this.totalvalue = totalvalue;
    }

    public Pin getPinId() {
        return pinId;
    }

    public void setPinId(Pin pinId) {
        this.pinId = pinId;
    }

    @XmlTransient
    public Collection<Pin> getPinCollection() {
        return pinCollection;
    }

    public void setPinCollection(Collection<Pin> pinCollection) {
        this.pinCollection = pinCollection;
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
