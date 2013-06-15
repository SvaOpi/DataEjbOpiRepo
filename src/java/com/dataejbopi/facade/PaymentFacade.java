/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dataejbopi.facade;

import com.dataejbopi.entity.Payment;
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
public class PaymentFacade extends AbstractFacade<Payment> {
    @PersistenceContext(unitName = "DataEjbOpiPU")
    private EntityManager em;
    @EJB
    private PinFacade pinFacade = new PinFacade();
    @EJB
    private LocalDateTimer localDateTimer;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PaymentFacade() {
        super(Payment.class);
    }
    
    public ROb<Payment> createPaymentForPin(Long idPin, Double salary){
        ROb<Payment> rob = new ROb<Payment>();
        try{     
            Pin pin = (Pin) pinFacade.findById(idPin).getData();
            if(pin != null){
                Payment payment = new Payment();
                payment.setHealtServiceValue(salary*0.085);
                payment.setOpiServiceValue(payment.getHealtServiceValue()*0.01);
                payment.setTotalvalue(payment.getHealtServiceValue()+payment.getOpiServiceValue());
                create(payment);
                List<Payment> listPayment = findAll();
                payment = listPayment.get(listPayment.size()-1);
                rob.setData(payment);
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
    
    public ROb<Payment> registerPaymentComplete(Long idPayment, Long idTransaction){
        ROb<Payment> rob = new ROb<Payment>();
        try{     
            Payment payment = findById(idPayment).getData();
            // Find and validate with a bank transaciont
            if(payment != null){
                Pin pin = new Pin();
                for(Pin p:pinFacade.findAll()){
                    if(p.getPayment().getId()==payment.getId()){
                        pin=p;
                        break;
                    }
                }
                payment.setPaydate(localDateTimer.localDate);
                pin.setPinstate("Paid");
                pin.setPayment(payment);
                edit(payment);
                pinFacade.updatePin(pin);
                rob.setData(payment);
                rob.setSuccess(true);
            }else{
                rob.setSuccess(false);
                rob.setErr_message("Cant find that object!");                
            }          
            return rob;
        }catch(Exception e){
            e.printStackTrace();
            rob.setSuccess(false);
            rob.setErr_message("Failed Transaction!");
            return rob;
        }
    }
    
    public ROb<Payment> updatePaymentExtemporaneous(Long idPayment){
        ROb<Payment> rob = new ROb<Payment>();
        try{     
            Payment payment =  (Payment) findById(idPayment).getData();
            Date currentDate = localDateTimer.getLocalDate();
            Date limitDate = new Date(); 
            Double newHealtServiceValue;
            if(payment != null && payment.getPaydate()==null){
                Pin pin = new Pin();
                for(Pin p:pinFacade.findAll()){
                    if(p.getPayment().getId()==payment.getId()){
                        pin=p;
                        break;
                    }
                }
                while(pin.getId()!=null && currentDate.after(pin.getLimitdate()) && currentDate.getYear()==pin.getLimitdate().getYear()){
                    limitDate.setDate(1);
                    limitDate.setMonth(currentDate.getMonth()+1);
                    limitDate.setYear(currentDate.getYear());
                    if(limitDate.getMonth()==0){
                        limitDate.setYear(localDateTimer.getLocalDate().getYear()+1);
                    }
                    newHealtServiceValue = payment.getHealtServiceValue()*1.1;
                    
                    payment.setHealtServiceValue(newHealtServiceValue);
                    payment.setOpiServiceValue(newHealtServiceValue*0.01);
                    payment.setTotalvalue(payment.getHealtServiceValue()+payment.getOpiServiceValue());
                    edit(payment);
                    pin.setLimitdate(limitDate);
                    pinFacade.updatePin(pin);
                    
                    System.out.println("Update payment:"+idPayment + " for date:" + pin.getLimitdate());
                    rob.setData(payment);
                    rob.setSuccess(true);
                }
            }else{
                rob.setSuccess(false);
                rob.setErr_message("Cant Find/Update that object!");                
            }          
            return rob;
        }catch(Exception e){
            e.printStackTrace();
            rob.setSuccess(false);
            rob.setErr_message("Failed Transaction!");
            return rob;
        }
    }
    
    public ROb<Payment> findById(Long id){
        ROb<Payment> rob = new ROb<Payment>();
        try{
            Payment payment = find(id);
            if(payment==null){
                rob.setErr_message("Cant Find this Object");
                rob.setSuccess(false);
            } else {
                rob.setData(payment);
                rob.setSuccess(true);
            }
            return rob;
        }catch(Exception e){
            rob.setSuccess(false);
            rob.setErr_message("Failed transaction");
            return rob;
        }
    }
    
    public ROb<Payment> removeById(Long id){
        ROb<Payment> rob = new ROb<Payment>();
        try{
            rob = findById(id);
            if(rob.isSuccess()==true){
                Payment payment = rob.getData();
                remove(payment);
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
