/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dataejbopi.entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Usuario1
 */
@Entity
@Table(name = "PERSON")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Person.findAll", query = "SELECT p FROM PersonOpi p"),
    @NamedQuery(name = "Person.findByCedule", query = "SELECT p FROM PersonOpi p WHERE p.cedule = :cedule"),
    @NamedQuery(name = "Person.findByDtype", query = "SELECT p FROM PersonOpi p WHERE p.dtype = :dtype")})
public class PersonOpi implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "CEDULE")
    private Long cedule;
    @Size(max = 31)
    @Column(name = "DTYPE")
    private String dtype;
    @Column
    private Double salary;
    @JoinColumn(name = "EPS_ID", referencedColumnName = "ID")
    @ManyToOne
    private Eps eps;
    @OneToMany(mappedBy = "person", orphanRemoval = true)
    private Collection<Pin> pinCollection;

    public PersonOpi() {
    }

    public PersonOpi(Long cedule) {
        this.cedule = cedule;
    }

    public Long getCedule() {
        return cedule;
    }

    public void setCedule(Long cedule) {
        this.cedule = cedule;
    }

    public String getDtype() {
        return dtype;
    }

    public void setDtype(String dtype) {
        this.dtype = dtype;
    }

    public Eps getEps() {
        return eps;
    }

    public void setEps(Eps eps) {
        this.eps = eps;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
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
        hash += (cedule != null ? cedule.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PersonOpi)) {
            return false;
        }
        PersonOpi other = (PersonOpi) object;
        if ((this.cedule == null && other.cedule != null) || (this.cedule != null && !this.cedule.equals(other.cedule))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dataejbopi.entity.Person[ cedule=" + cedule + " ]";
    }
    
}
