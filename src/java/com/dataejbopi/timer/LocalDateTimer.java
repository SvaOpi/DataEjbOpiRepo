/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dataejbopi.timer;

import com.dataejbopi.entity.Payment;
import com.dataejbopi.entity.PersonOpi;
import com.dataejbopi.entity.Pin;
import com.dataejbopi.facade.PaymentFacade;
import com.dataejbopi.facade.PersonFacade;
import com.dataejbopi.facade.PinFacade;
import java.util.Date;
import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;

/**
 *
 * @author Usuario1
 */
@Singleton
@LocalBean
public class LocalDateTimer {
    @EJB
    private PaymentFacade paymentFacade;
    @EJB
    private PinFacade pinFacade;
    @EJB
    private PersonFacade personFacade;
    
    public Date localDate=new Date();
    
    @Schedule(minute = "*", second = "*/10", dayOfMonth = "*", month = "*", year = "*", hour = "*", dayOfWeek = "*")
    public void myTimer() {
        int lastMonth = localDate.getMonth();
        localDate.setDate(localDate.getDate()+1);
        //Change of month
        if(lastMonth!=localDate.getMonth()){
            System.out.println(">>-------------------------- New Month --------------------------<< ");
            System.out.println(localDate);
            System.out.println(">>---------------------------------------------------------------<<");
            
        }
        //Create Pin on last Day
        if(localDate.getDate()==28){
            System.out.println("X---------------------- Create Automatically Pin ----------------------X");
            for(PersonOpi person:personFacade.findAll()){
                Pin pin = (Pin) pinFacade.getLastPinCreated(person.getCedule()).getData();
                if(pin==null){
                    pinFacade.registerPin(person.getCedule());
                }else if(pin.getCreationdate().getYear()<localDate.getYear() &&  pin.getCreationdate().getMonth()<12){  
                    pinFacade.registerPin(person.getCedule());
                }else if(pin.getCreationdate().getYear()==localDate.getYear() && pin.getCreationdate().getMonth()<localDate.getMonth()){  
                    pinFacade.registerPin(person.getCedule());
                }               
            }
            System.out.println("X------------------------ Create Finished ------------------------X");
        }
        // Update value of Payment, when expired limit date
        if(localDate.getDate()==2){
            System.out.println("<<--------------- Update Extemporaneous Payment ----------------->>");
            for(Payment payment:paymentFacade.findAll()){
                Pin pin = new Pin();
                for(Pin p:pinFacade.findAll()){
                    if(p.getPayment().getId()==payment.getId()){
                        pin=p;
                        break;
                    }
                }
                if(payment.getPaydate()==null && pin.getLimitdate().before(getLocalDate())){
                    paymentFacade.updatePaymentExtemporaneous(payment.getId());
                }
            }
            System.out.println("<<----------------------- Update Finished ------------------------->>");
        }
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    public Date getLocalDate() {
        return localDate;
    }
    
}
