package com.sracompanyperson.wsc;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.Serializable;
import java.util.Collection;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Usuario1
 */
public class Company implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String name;
    private String password;
    private Collection<CompanyPerson> companyPersonCollection;

    public Company() {
    }

    public Company(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @XmlTransient
    public Collection<CompanyPerson> getCompanyPersonCollection() {
        return companyPersonCollection;
    }

    public void setCompanyPersonCollection(Collection<CompanyPerson> companyPersonCollection) {
        this.companyPersonCollection = companyPersonCollection;
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
        if (!(object instanceof Company)) {
            return false;
        }
        Company other = (Company) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dataejbsra.entity.Company[ id=" + id + " ]";
    }
    
}
