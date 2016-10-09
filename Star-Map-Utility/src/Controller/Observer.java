/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.time.*;
import java.util.Date;

/**
 *
 * @author Your Name <timothy>
 */
public class Observer {
    public double latitude;
    public double longitude;
    public Date observedDate;
    public Date observedTime;

    public Observer(double latitude, double longitude, Date observedDate, Date observedTime) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.observedDate = observedDate;
        this.observedTime = observedTime;
        
        System.out.println(latitude);
        System.out.println(longitude);
        System.out.println(observedDate);
        System.out.println(observedTime);
    }

    
    
    
    
}
