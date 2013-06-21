/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dataejbsra.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Usuario1
 */
@Embeddable
public class CompanyPersonPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "COMPANIES_ID")
    private long companiesId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PERSONS_CEDULE")
    private long personsCedule;

    public CompanyPersonPK() {
    }

    public CompanyPersonPK(long companiesId, long personsCedule) {
        this.companiesId = companiesId;
        this.personsCedule = personsCedule;
    }

    public long getCompaniesId() {
        return companiesId;
    }

    public void setCompaniesId(long companiesId) {
        this.companiesId = companiesId;
    }

    public long getPersonsCedule() {
        return personsCedule;
    }

    public void setPersonsCedule(long personsCedule) {
        this.personsCedule = personsCedule;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) companiesId;
        hash += (int) personsCedule;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CompanyPersonPK)) {
            return false;
        }
        CompanyPersonPK other = (CompanyPersonPK) object;
        if (this.companiesId != other.companiesId) {
            return false;
        }
        if (this.personsCedule != other.personsCedule) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dataejbsra.entity.CompanyPersonPK[ companiesId=" + companiesId + ", personsCedule=" + personsCedule + " ]";
    }
    
}
