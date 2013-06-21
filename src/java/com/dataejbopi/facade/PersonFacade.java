/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dataejbopi.facade;

import com.dataejbopi.entity.Eps;
import com.dataejbopi.entity.PersonOpi;
import com.dataejbopi.vo.ROb;
import com.sracompanyperson.wsc.CompanyPersonWs_Service;
import com.sraperson.wsc.PersonWs_Service;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.xml.ws.WebServiceRef;

/**
 *
 * @author Usuario1
 */
@Stateless
public class PersonFacade extends AbstractFacade<PersonOpi> {
    @WebServiceRef(wsdlLocation = "META-INF/wsdl/localhost_8080/PersonWs/PersonWs.wsdl")
    private PersonWs_Service service_1;
    @WebServiceRef(wsdlLocation = "META-INF/wsdl/localhost_8080/CompanyPersonWs/CompanyPersonWs.wsdl")
    private CompanyPersonWs_Service service;
    @PersistenceContext(unitName = "DataEjbOpiPU")
    private EntityManager em;
    @EJB
    private EpsFacade epsFacade = new EpsFacade();
    
    
    private Long idOpi = Long.getLong("1");
    private String passwordOpi = "opi";

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PersonFacade() {
        super(PersonOpi.class);
    }
    
    public ROb<PersonOpi> registerPerson(Long cedule,Double salary, Long epsId){
        ROb<PersonOpi> rob = new ROb<PersonOpi>();
        try{
            Eps eps = epsFacade.findById(epsId).getData();  
            com.sraperson.wsc.Person personSra = (com.sraperson.wsc.Person) findByCedule_1(cedule).getData();
            if(eps!=null && personSra !=null){
                System.out.println("todo bien" + personSra.toString());
                PersonOpi person = new PersonOpi();
                person.setCedule(cedule);
                person.setDtype("CC");
                person.setEps(eps);
                person.setSalary(salary);
                com.sracompanyperson.wsc.ROb rob2 = registerRelation(person.getCedule(),idOpi,"user",passwordOpi);
                if(rob2.isSuccess()==false){
                    rob.setSuccess(false);
                    rob.setErr_message("Can't relation this person whit the company");
                    return rob;
                }
                create(person);
                List<PersonOpi> listPerson = findAll();
                person = listPerson.get(listPerson.size()-1);                
                rob.setData(person);
                rob.setSuccess(true);
            }else{
                rob.setSuccess(false);
                rob.setErr_message("Cant find that object!");
            }            
            return rob;
        }catch(Exception e){
            rob.setSuccess(false);
            rob.setErr_message("Failed Transaction!");
            return rob;
        }
    }
    
    public ROb<String> validateLogin(String userName, String userPassword){
        ROb<String> rob = new ROb<String>();
        try{ 
            com.sraperson.wsc.ROb rob2 = findByUserName(userName);
            com.sraperson.wsc.Person person = (com.sraperson.wsc.Person) rob2.getData();
            com.sracompanyperson.wsc.ROb rob3 = validateRelation(person.getCedule(), idOpi, userPassword); // Validate relation in SRA ( 
            com.sracompanyperson.wsc.CompanyPerson companyPerson = (com.sracompanyperson.wsc.CompanyPerson) rob3.getData();
            if(person!=null && companyPerson != null && rob3.isSuccess()==true){
                PersonOpi personOpi = find(person.getCedule());
                if(personOpi!=null && person.getPassword().equals(userPassword)){
                    rob.setData(companyPerson.getRol());
                    rob.setSuccess(true);
                } else {
                    rob.setErr_message("Cant Find this Object");
                    rob.setSuccess(false);
                }
            }else{            
                rob.setErr_message("Cant Find this Object");
                rob.setSuccess(false);    
            }
            return rob;
        }catch(Exception e){
            rob.setSuccess(false);
            rob.setErr_message("Failed transaction");
            return rob;
        }
    }
    
    public ROb<PersonOpi> findByCedule(Long cedule){
        ROb<PersonOpi> rob = new ROb<PersonOpi>();
        try{
            PersonOpi person = find(cedule);          
            if(person==null){
                rob.setErr_message("Cant Find this Object");
                rob.setSuccess(false);
            } else {
                rob.setData(person);
                rob.setSuccess(true);
            }
            return rob;
        }catch(Exception e){
            rob.setSuccess(false);
            rob.setErr_message("Failed transaction");
            return rob;
        }
    }
    
    public ROb<PersonOpi> removeByCedule(Long cedule){
        ROb<PersonOpi> rob = new ROb<PersonOpi>();
        try{
           PersonOpi p = find(cedule);
            if(p!=null){
                com.sracompanyperson.wsc.ROb rob2  = removeRelation(cedule, idOpi, passwordOpi);
                if(rob2.isSuccess()==false){                    
                    rob.setSuccess(false);
                    rob.setErr_message("Cant remove relation");
                }else{
                    remove(p);
                    rob.setSuccess(true);
                    rob.setData(null);
                }
            }else{         
                rob.setSuccess(false);
                rob.setErr_message("Cant find this object");
            }
            return rob;
        }catch(Exception e){
            rob.setSuccess(false);
            rob.setErr_message("Failed transaction");
            return rob;
        }
    }    

    private com.sracompanyperson.wsc.ROb registerRelation(java.lang.Long personCedule, java.lang.Long companyId, java.lang.String rolPerson, java.lang.String passwordCompany) {
        com.sracompanyperson.wsc.CompanyPersonWs port = service.getCompanyPersonWsPort();
        return port.registerRelation(personCedule, companyId, rolPerson, passwordCompany);
    }

    private com.sracompanyperson.wsc.ROb removeRelation(java.lang.Long personCedule, java.lang.Long companyId, java.lang.String passwordCompany) {
        com.sracompanyperson.wsc.CompanyPersonWs port = service.getCompanyPersonWsPort();
        return port.removeRelation(personCedule, companyId, passwordCompany);
    }

    private com.sracompanyperson.wsc.ROb validateRelation(java.lang.Long personCedule, java.lang.Long companyId, java.lang.String personPassword) {
        com.sracompanyperson.wsc.CompanyPersonWs port = service.getCompanyPersonWsPort();
        return port.validateRelation(personCedule, companyId, personPassword);
    }

    private com.sraperson.wsc.ROb findByCedule_1(java.lang.Long cedule) {
        com.sraperson.wsc.PersonWs port = service_1.getPersonWsPort();
        return port.findByCedule(cedule);
    }

    private com.sraperson.wsc.ROb findByUserName(java.lang.String userName) {
        com.sraperson.wsc.PersonWs port = service_1.getPersonWsPort();
        return port.findByUserName(userName);
    }
    
}
