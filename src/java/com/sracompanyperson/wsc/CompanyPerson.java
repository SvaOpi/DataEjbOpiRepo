package com.sracompanyperson.wsc;

import com.sraperson.wsc.Person;
import java.io.Serializable;

public class CompanyPerson implements Serializable {
    private static final long serialVersionUID = 1L;
    protected CompanyPersonPK companyPersonPK;
    private String rol;
    private Person person;
    private Company company;

    public CompanyPerson() {
    }

    public CompanyPerson(CompanyPersonPK companyPersonPK) {
        this.companyPersonPK = companyPersonPK;
    }

    public CompanyPerson(long companiesId, long personsCedule) {
        this.companyPersonPK = new CompanyPersonPK(companiesId, personsCedule);
    }

    public CompanyPersonPK getCompanyPersonPK() {
        return companyPersonPK;
    }

    public void setCompanyPersonPK(CompanyPersonPK companyPersonPK) {
        this.companyPersonPK = companyPersonPK;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (companyPersonPK != null ? companyPersonPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CompanyPerson)) {
            return false;
        }
        CompanyPerson other = (CompanyPerson) object;
        if ((this.companyPersonPK == null && other.companyPersonPK != null) || (this.companyPersonPK != null && !this.companyPersonPK.equals(other.companyPersonPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dataejbsra.entity.CompanyPerson[ companyPersonPK=" + companyPersonPK + " ]";
    }
    
}
