/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dataejbopi.timer;

import com.dataejbopi.entity.Payment;
import com.dataejbopi.entity.Person;
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
            System.out.println("             "+localDate);
            System.out.println(">>---------------------------------------------------------------<<");
            
        }
        //Create Pin on last Day
        if(localDate.getDate()==28){
            System.out.println("----- Create Automatically Pin ----");
            for(Person person:personFacade.findAll()){
                Pin pin = (Pin) pinFacade.getLastPinCreated(person.getCedule()).getData();
                if(pin==null){
                    pinFacade.registerPin(person.getCedule());
                }else if(pin.getCreationdate().getYear()<localDate.getYear() &&  pin.getCreationdate().getMonth()<12){  
                    pinFacade.registerPin(person.getCedule());
                }else if(pin.getCreationdate().getYear()==localDate.getYear() && pin.getCreationdate().getMonth()<localDate.getMonth()){  
                    pinFacade.registerPin(person.getCedule());
                }               
            }
            System.out.println("---------Create Finished------------");
        }
        // Update value of Payment, when expired limit date
        if(localDate.getDate()==2){
            System.out.println("----- Update Extemporaneous Payment ----");
            for(Payment payment:paymentFacade.findAll()){
                if(payment.getPaydate()==null && payment.getPin().getLimitdate().before(getLocalDate())){
                    System.out.println(paymentFacade.updatePaymentExtemporaneous(payment.getId()).getErr_message());
                }
            }
            System.out.println("---------Update Finished------------");
        }
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    public Date getLocalDate() {
        return localDate;
    }
    
}
