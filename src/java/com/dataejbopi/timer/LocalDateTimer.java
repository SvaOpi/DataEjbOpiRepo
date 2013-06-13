/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dataejbopi.timer;

import com.dataejbopi.entity.Payment;
import com.dataejbopi.facade.PaymentFacade;
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
    
    public Date localDate=new Date();
    
    @Schedule(minute = "*", second = "*/10", dayOfMonth = "*", month = "*", year = "*", hour = "*", dayOfWeek = "*")
    public void myTimer() {
        int lastMonth = localDate.getMonth();
        localDate.setDate(localDate.getDate()+1);
        if(lastMonth!=localDate.getMonth()){
            System.out.println("--------- New Month ---------");
            System.out.println(localDate);
            System.out.println("-----------------------------");
            
        }
        if(localDate.getDate()==2){
            System.out.println("----- Update Extemporaneous Payment ----");
            for(Payment payment:paymentFacade.findAll()){
                paymentFacade.updatePaymentExtemporaneous(payment.getId());
            }
            System.out.println("-----------------------------------------");
        }
    }
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
