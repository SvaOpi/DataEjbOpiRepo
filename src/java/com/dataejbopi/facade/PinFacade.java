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
    
    public ROb<Pin> registerPin(Long personCedule){
        ROb<Pin> rob = new ROb<Pin>();
        try{
            Person person = personFacade.findByCedule(personCedule).getData();  
            Double salary = person.getSalary();
            Date currentDate = new Date();
            currentDate.setYear(localDateTimer.getLocalDate().getYear());
            currentDate.setMonth(localDateTimer.getLocalDate().getMonth());
            currentDate.setDate(localDateTimer.getLocalDate().getDate());
            Date limitDate = new Date(); 
            limitDate.setDate(1);
            limitDate.setMonth(localDateTimer.getLocalDate().getMonth()+1);
            limitDate.setYear(localDateTimer.getLocalDate().getYear());
            if(limitDate.getMonth()==0){
                limitDate.setYear(localDateTimer.getLocalDate().getYear()+1);
            }
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
                Payment payment = paymentFacade.createPaymentForPin(pin.getId(), salary).getData();
                
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
    
    public ROb<Pin> findById(Long id){
        ROb<Pin> rob = new ROb<Pin>();
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
    
    public ROb<Pin> getLastPinCreated (Long cedule){
        ROb<Pin> rob = new ROb<Pin>();
        try{
            Person person = (Person) personFacade.findByCedule(cedule).getData();
            if(person==null){
                rob.setErr_message("Cant Find this Object");
                rob.setSuccess(false);
            } else {
                List<Pin> listPin =  (List<Pin>) person.getPinCollection();
                if(listPin==null || listPin.isEmpty()){
                    rob.setErr_message("Cant Find this Object");
                    rob.setSuccess(false);
                    return rob;
                }
                Pin lastPin = listPin.get(listPin.size()-1);
                rob.setData(lastPin);
                rob.setSuccess(true);
            }
            return rob;
        }catch(Exception e){
            rob.setSuccess(false);
            rob.setErr_message("Failed transaction");
            return rob;
        }
    }
    
    public ROb<Pin> updatePin(Pin pin){
        ROb<Pin> rob = new ROb<Pin>();
        try{
            Pin p = find(pin.getId());
            if(p==null){
                rob.setErr_message("Cant Find this Object");
                rob.setSuccess(false);
            } else {
                edit(pin);
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
    
    public ROb<Pin> removeById(Long id){
        ROb<Pin> rob = new ROb<Pin>();
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
