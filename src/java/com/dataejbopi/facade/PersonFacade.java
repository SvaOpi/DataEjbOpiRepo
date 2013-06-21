/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dataejbopi.facade;

import com.dataejbopi.entity.Eps;
import com.dataejbopi.entity.PersonOpi;
import com.dataejbopi.vo.ROb;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Usuario1
 */
@Stateless
public class PersonFacade extends AbstractFacade<PersonOpi> {
    @PersistenceContext(unitName = "DataEjbOpiPU")
    private EntityManager em;
    @EJB
    private EpsFacade epsFacade = new EpsFacade();

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
            PersonOpi person = findByCedule(cedule).getData(); //Find person on SRA        
            //if(eps!=null && person!=null){
            if(eps!=null ){
                person = new PersonOpi();
                person.setCedule(cedule);
                person.setDtype("CC");
                person.setEps(eps);
                person.setSalary(salary);
                create(person);
                List<PersonOpi> listPerson = findAll();
                person = listPerson.get(listPerson.size()-1);
                //ROb rob2 = CompanyFacade.registerRelation(person.getCedule(), opiID, "user", opiPassword);
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
    
    public ROb<PersonOpi> validateLogin(String userName, String userPassword){
        ROb<PersonOpi> rob = new ROb<PersonOpi>();
        try{
            PersonOpi person = find(1);// Find PersonOpi in OPI  
            //CompanyPerson companyPerson = (CompanyPerson)validateRelation(userNane, opiID, userPassword).getData() //// Validate relation in SRA ( 
            //if(person!=null && companyPerson != null and companyPerson.isSucces()){  //validation data
            if(person!=null){
                //String rol = companyPerson.getRol();
                //rob.setData(rol);
                rob.setData(person);
                rob.setSuccess(true);
            } else {
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
            PersonOpi person = find(cedule);// find person  in OPI 
            //Person person2 = (person)findByCedule(cedule).getData() // find person  in SRA 
            //if(person!=null && person2 != null){  //validation data             
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
                //ROb rob2 = CompanyFacade.removeRelation(person.getCedule(), opiID, opiPassword);
                remove(p);
                rob.setSuccess(true);
                rob.setData(null);
                return rob;
            }         
            rob.setSuccess(false);
            rob.setErr_message("Cant find this object");
            return rob;
        }catch(Exception e){
            rob.setSuccess(false);
            rob.setErr_message("Failed transaction");
            return rob;
        }
    }    
    
}
