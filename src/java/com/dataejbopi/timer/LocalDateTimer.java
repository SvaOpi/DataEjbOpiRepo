/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dataejbopi.timer;

import java.util.Date;
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
    public Date localDate=new Date();
    @Schedule(minute = "*", second = "*/10", dayOfMonth = "*", month = "*", year = "*", hour = "*", dayOfWeek = "*")
    public void myTimer() {
        localDate.setDate(localDate.getDate()+1);
        System.out.println(" ----------- New Day -----------");
        System.out.println(localDate);
        System.out.println(" -------------------------------");
    }
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
