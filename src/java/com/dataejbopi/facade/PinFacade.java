/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dataejbopi.facade;

import com.dataejbopi.entity.Payment;
import com.dataejbopi.entity.Person;
import com.dataejbopi.entity.Pin;
import com.dataejbopi.timer.LocalDateTimer;
import com.dataejbopi.vo.ROb;
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
    @EJB
    private PaymentFacade paymentFacade;
    @EJB
    private LocalDateTimer localDateTimer;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PinFacade() {
        super(Pin.class);
    }
    
    public ROb registerPin(Long personCedule){
        ROb rob = new ROb();
        try{
            Person person = (Person) personFacade.findByCedule(personCedule).getData();  
            Double salary = person.getSalary();
            Date currentDate =localDateTimer.localDate;
            Date limitDate = new Date(); 
            limitDate.setDate(1);
            limitDate.setMonth(localDateTimer.localDate.getMonth()+1);
            limitDate.setYear(localDateTimer.localDate.getYear());
            if(person != null){
                List<Pin> listPin = findAll();
                for(Pin p:listPin){
                    if(p.getPerson().getCedule()==personCedule && p.getCreationdate().getMonth()==currentDate.getMonth() && p.getCreationdate().getYear()==currentDate.getYear()){
                        rob.setSuccess(false);
                        rob.setErr_message("The pin for this month was already registered!");
                        return rob;
                    }
                }
                Pin pin = new Pin();
                pin.setPinstate("UnPaid"); 
                pin.setCreationdate(currentDate);   
                pin.setLimitdate(limitDate);       
                create(pin);
                
                listPin = findAll();
                pin = listPin.get(listPin.size()-1);    
                Payment payment = (Payment) paymentFacade.createPaymentForPin(pin.getId(), salary).getData();
                
                pin.setPerson(person);   
                pin.setPayment(payment);
                edit(pin);
                System.out.println("Pin "+ pin.getId() +" created for person whit cedule " + personCedule);
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
