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
    
    public ROb registerPerson(Long cedule,Double salary, Long epsId){
        ROb rob = new ROb();
        try{
            Eps eps = (Eps) epsFacade.findById(epsId).getData();
            Person person = (Person) findByCedule(cedule).getData(); //Find person on SRA        
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
    
    public ROb findByCedule(Long cedule){
        ROb rob = new ROb();
        try{
            Person person = find(cedule);// Find Person with service of SRA   
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
    
    public ROb removeByCedule(Long cedule){
        ROb rob = new ROb();
        try{
            rob = findByCedule(cedule);
            if(rob.isSuccess()==true){
                Person eps = (Person) rob.getData();
                remove(eps);
                rob.setSuccess(true);
                rob.setData(null);
            }            
            return rob;
        }catch(Exception e){
            rob.setSuccess(false);
            rob.setErr_message("Failed transaction");
            return rob;
        }
    }    
    
}
