/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.*;
import java.time.*;
import java.util.Date;

/**
 *
 * @author Your Name <timothy>
 */
public class Observer {
    
    static private Observer singletonObserver = null;
    
    public double latitude;
    public double longitude;
    public LocalDate observedDate;
    public LocalTime observedTime;        

    private Observer(double latitude, double longitude, LocalDate observedDate, LocalTime observedTime) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.observedDate = observedDate;
        this.observedTime = observedTime;
        
        System.out.println(latitude);
        System.out.println(longitude);
        System.out.println(observedDate);
        System.out.println(observedTime);
        
        System.out.println(observedTime.getHour());
        System.out.println(observedTime.getMinute());
        System.out.println(observedTime.getSecond());        
    }    
    public static Observer getObserver(double latitude, double longitude, LocalDate observedDate, LocalTime observedTime)
    {
        if(singletonObserver == null)
        {
            singletonObserver = new Observer(latitude, longitude, observedDate, observedTime);
        }
        return singletonObserver;
    }        
}
