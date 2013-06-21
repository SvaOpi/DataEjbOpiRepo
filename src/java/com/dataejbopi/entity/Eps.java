/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dataejbopi.entity;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "EPS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Eps.findAll", query = "SELECT e FROM Eps e"),
    @NamedQuery(name = "Eps.findById", query = "SELECT e FROM Eps e WHERE e.id = :id"),
    @NamedQuery(name = "Eps.findByDtype", query = "SELECT e FROM Eps e WHERE e.dtype = :dtype"),
    @NamedQuery(name = "Eps.findByAccountnumber", query = "SELECT e FROM Eps e WHERE e.accountnumber = :accountnumber"),
    @NamedQuery(name = "Eps.findByName", query = "SELECT e FROM Eps e WHERE e.name = :name")})
public class Eps implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    @Size(max = 31)
    @Column(name = "DTYPE")
    private String dtype;
    @Column(name = "ACCOUNTNUMBER")
    private Long accountnumber;
    @Size(max = 255)
    @Column(name = "NAME")
    private String name;
    @OneToMany(mappedBy = "eps", orphanRemoval = true)
    private Collection<PersonOpi> personCollection;

    public Eps() {
    }

    public Eps(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDtype() {
        return dtype;
    }

    public void setDtype(String dtype) {
        this.dtype = dtype;
    }

    public Long getAccountnumber() {
        return accountnumber;
    }

    public void setAccountnumber(Long accountnumber) {
        this.accountnumber = accountnumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlTransient
    public Collection<PersonOpi> getPersonCollection() {
        return personCollection;
    }

    public void setPersonCollection(Collection<PersonOpi> personCollection) {
        this.personCollection = personCollection;
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
        if (!(object instanceof Eps)) {
            return false;
        }
        Eps other = (Eps) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dataejbopi.entity.Eps[ id=" + id + " ]";
    }
    
}
