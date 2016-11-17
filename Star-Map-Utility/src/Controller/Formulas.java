/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Controller.Observer;
import static java.lang.Math.floor;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Date;


/**
 *
 * @author tbf0005
 */
public class Formulas {

    private static Formulas singletonFormulas;
    
    private double rightAsc;
    private double declination;
    private double altitude;
    private double azimuth;        
    private double localSidereal;
    private double greenwichSidereal;
    private double orbitEccentricity;
    private double hourAngle;
    private double eclipticObliquity;
    private double parallax;
    private double deltaT;
    private double julianDate;

    private Observer user;
    private double observedLat;
    private double observedLong;
    private int calYear;
    private int year;
    private int month;
    private int day;
    private int hour;
    private int minutes;
    private int seconds;
    private LocalTime time;
    private LocalDate date;       

    private double semimajorAxis;
    private double eclipticInclination;
    private double perihelionArgument;
    private double ascendingNodeLong;
    private double planetMeanLong;

    private double convertedAngle;

    private ArrayList starList;
    private ArrayList planetList;
    private ArrayList messierList;

    public Formulas(LocalTime time, LocalDate date, double observedLat, double observedLong) 
    {        
        this.time = time;
        this.date = date;
        this.year = date.getYear();
        this.month = date.getMonthValue();
        this.day = date.getDayOfYear();
        this.hour = time.getHour();
        this.minutes = time.getMinute();
        this.seconds = time.getSecond();
        this.observedLat = observedLat;
        this.observedLong = observedLong;        
    }
    
    public static Formulas getFormulas(LocalTime time, LocalDate date, double observedLat, double observedLong)
    {
        if(singletonFormulas == null)
        {
            singletonFormulas = new Formulas(time, date, observedLat, observedLong);
        }
        return singletonFormulas;
    }
                      
    public void calcJulianDay()
    {
        year = date.getYear();
        month = date.getMonthValue();
        day = date.getDayOfYear();
        double dd = day + (time.getHour()/24) + (time.getMinute()/60);

        int y;
        int m;
        int A;
        int B;

        if (month > 2)
        {
            y = year;
            m = month;                
        }
        else
        {
            y = year - 1;
            m = month + 12;
        }

        if (date.isAfter(LocalDate.of(1582, 10, 15)))/*(date > "October 15, 1582")*/
        {
            A = (int)(y/100);
            B = 2 - A + (int)(A/4);
        }
        else
        {
            B = 0;
        }
        julianDate = (int)(365.25 * y) + (int)(30.6001 * (m+1))+ dd + 1720994.5 + B;
    }

    public void calcRelativeJD()
    {
        double dd = date.getDayOfYear() + (time.getHour()/24) + (time.getMinute()/60);
        julianDate = (367 * date.getYear()) 
                - (Math.floor(7.0 * (date.getYear() + Math.floor((date.getMonthValue() + 9.0)/12.0))/4.0))
                + (Math.floor(275.0 * date.getMonthValue()/9.0))
                + date.getDayOfYear() - 730531.5 + dd/24.0;
    }

    public void cy()
    {
        calYear = (int) (julianDate/36525);
    }

    public double Deg2Rad()
    {
        return Math.PI / 180.0;
    }

    public double Rad2Deg()
    {
        return 180 / Math.PI;
    }

    public void Mod2Pi(double X)
    {
        //Given angle X in radians B=X/2π
        double b = X/(2 * Math.PI);
        double A = (2 * Math.PI) * (b - abs_floor(b));

        if(A < 0)
            {
                A = (2 * Math.PI) + A;
                convertedAngle = A;
            }
    }

    public void convertRAdeg2HMS()
    {

    }

    public void convertDECdeg2DMS()
    {

    }

    public double calcTrueAnomaly(double e, double M)
    {
        double V;
        double E = M + e * Math.sin(M) * (1.0 + e * Math.cos(M));
        double E_1 = E;

        while(Math.abs(E - E_1) > (1.0*e - 12))
        {
            E_1 = E;
            E = E_1 - (E_1 - e * Math.sin(E_1) - M) / (1 - e * Math.cos(E_1));
        }

        V = 2 * Math.atan(Math.sqrt((1+e) / (1-e)) * Math.tan(0.5 * E));
        if(V < 0)
        {
            V = V + (2 * Math.PI);
        }
        return V;
    }

    public void calcAltAzi()
    {

    }
    /*Given Year (year), Month (month with January = 1), Day (day) of the 
    month, Hour (hour) on a 24 hour clock, Minute (min), Second (sec). All 
    times must be measured from Greenwich mean time (TimeZone = 0).*/
    public void calcMeanSidereal()
    {
        if(month <= 2) 
            year --;
        // Adjust month and year if needed
            month = month + 12;
            double a = floor(year / 100.0);
            double b = 2 - a + floor(a/4);
            double c = floor(365.25 * year);
            double d = floor(30.6001 * (month + 1));
            // Get days since J2000.0
            double jd = b + c + d - 730550.5 + day + (hour + minutes/60 + seconds/3600) / 24; // Get Julian centuries since J2000.0
            double jt = jd / 36525.0;
            // Calculate initial Mean Sidereal Time (mst)
            double mst = 280.46061837 + (360.98564736629 * jd) + (0.000387933 * (jt*jt)) - ((jt*jt*jt) / 38710000) + observedLong; // Clip mst to range 0.0 to 360.0
            if(mst > 0.0)
                while(mst > 360.0) 
                    mst -= 360.0;
            else
                while(mst < 0.0) 
                    mst += 360.0;
    }       

    public void calcMoonPhase()
    {

    }

    public void convertJD2CD()
    {
        int A;
        int B;
        int C;
        int D;
        int E;
        int alpha;
        double decDay;

        julianDate += 0.5;
        int Z = (int)julianDate;
        double F = julianDate - Z;
        if(Z < 229161)
        {
            A = Z;
        }
        else
        {
            alpha = (int)((Z - 1867216.25)/36524.25);
            A = Z + 1 + alpha - (int)(alpha/4);
        }

        B = A + 1524;
        C = (int)((B - 122.1)/365.25);
        D = (int)(365.25 * C);
        E = (int)((B - D)/30.6001);

        decDay = B - D - (int)(30.6001 * E) + F;

        if(E < 13.5)
            month = E - 1;
        else
            month = E - 13;

        if(month > 2.5)
            year = C - 4716;
        else
            year = C - 4715;                    
    }

    public void calcLunarLoc()
    {

    }

    public double abs_floor(double b) 
    {
        if (b >= 0) 
        {
            return Math.floor(b);
        }
        else
        {
            return Math.ceil(b);                
        }
    }
}
