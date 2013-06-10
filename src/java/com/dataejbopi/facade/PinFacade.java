/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dataejbopi.facade;

import com.dataejbopi.entity.Payment;
import com.dataejbopi.entity.Person;
import com.dataejbopi.entity.Pin;
import com.dataejbsra.vo.ROb;
import java.util.Date;
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
public class PinFacade extends AbstractFacade<Pin> {
    @PersistenceContext(unitName = "DataEjbOpiPU")
    private EntityManager em;
    @EJB
    private PersonFacade personFacade;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PinFacade() {
        super(Pin.class);
    }
    
    public ROb registerPin(Long personCedule, int month){
        ROb rob = new ROb();
        try{
            Person person = (Person) personFacade.findByCedule(personCedule).getData();  
            Double salary = 1000000.0; // Find Saraly from EPS service          
            Date currentDate = new Date();
            if(person != null){
                Pin pin = new Pin();
                pin.setPinstate("UnPaid");
                pin.setPerson(person);                
                currentDate.setMonth(month);
                pin.setCreationdate(currentDate);
                currentDate.setMonth(currentDate.getMonth()+1);
                pin.setLimitdate(currentDate);               
                
                Payment payment = new Payment();
                payment.setHealtServiceValue(salary*0.85);
                payment.setOpiServiceValue(payment.getHealtServiceValue()*0.01);
                payment.setTotalvalue(payment.getHealtServiceValue()+payment.getOpiServiceValue());
                
                pin.setPayment(payment);
                create(pin);
                List<Pin> listPin = findAll();
                pin = listPin.get(listPin.size()-1);
                rob.setData(pin);
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
    
    public ROb findById(Long id){
        ROb rob = new ROb();
        try{
            Pin pin = find(id);
            if(pin==null){
                rob.setErr_message("Cant Find this Object");
                rob.setSuccess(false);
            } else {
                rob.setData(pin);
                rob.setSuccess(true);
            }
            return rob;
        }catch(Exception e){
            rob.setSuccess(false);
            rob.setErr_message("Failed transaction");
            return rob;
        }
    }
    
    public ROb removeById(Long id){
        ROb rob = new ROb();
        try{
            rob = findById(id);
            if(rob.isSuccess()==true){
                Pin pin = (Pin) rob.getData();
                remove(pin);
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
