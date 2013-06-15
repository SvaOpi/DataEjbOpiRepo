/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dataejbopi.facade;

import com.dataejbopi.entity.Eps;
import com.dataejbopi.entity.Person;
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
public class PersonFacade extends AbstractFacade<Person> {
    @PersistenceContext(unitName = "DataEjbOpiPU")
    private EntityManager em;
    @EJB
    private EpsFacade epsFacade = new EpsFacade();

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PersonFacade() {
        super(Person.class);
    }
    
    public ROb<Person> registerPerson(Long cedule,Double salary, Long epsId){
        ROb<Person> rob = new ROb<Person>();
        try{
            Eps eps = epsFacade.findById(epsId).getData();
            Person person = findByCedule(cedule).getData(); //Find person on SRA        
            //if(eps!=null && person!=null){
            if(eps!=null ){
                person = new Person();
                person.setCedule(cedule);
                person.setDtype("CC");
                person.setEps(eps);
                person.setSalary(salary);
                create(person);
                List<Person> listPerson = findAll();
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
    
    public ROb<Person> validateLogin(String userName, String userPassword){
        ROb<Person> rob = new ROb<Person>();
        try{
            Person person = find(1);// Find Person in OPI  
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
    
    public ROb<Person> findByCedule(Long cedule){
        ROb<Person> rob = new ROb<Person>();
        try{
            Person person = find(cedule);// find person  in OPI 
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
    
    public ROb<Person> removeByCedule(Long cedule){
        ROb<Person> rob = new ROb<Person>();
        try{
           Person p = find(cedule);
            if(p!=null){
                //ROb rob2 = CompanyFacade.removeRelation(person.getCedule(), opiID, opiPassword);
                remove(p);
                rob.setSuccess(true);
                rob.setData(null);
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
