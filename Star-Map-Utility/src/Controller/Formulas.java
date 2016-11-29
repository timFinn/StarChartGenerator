/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Controller.Observer;
import Model.*;
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
    private double greenwichSidereal;   //AKA mean sidereal
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
        this.day = date.getDayOfMonth();
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
        julianDate = (367 * date.getYear()) 
                - (Math.floor(7.0 * (date.getYear() + Math.floor((date.getMonthValue() + 9.0)/12.0))/4.0))
                + (Math.floor(275.0 * date.getMonthValue()/9.0))
                + date.getDayOfMonth() - 730531.5 + (time.getHour()/24.0);
    }

    public int cy()
    {
        int cy = (int) (julianDate/365.25);
        return cy;
    }

    public double RADS()
    {
        return Math.PI / 180.0;
    }

    public double DEGS()
    {
        return 180 / Math.PI;
    }

    public double Mod2Pi(double X)
    {
        //double X = X_in * RADS();
        //Given angle X in radians 
        double b = X/(2 * Math.PI);
        double A = (2 * Math.PI) * (b - abs_floor(b));

        if(A < 0)
        {
            A = (2 * Math.PI) + A;
        }        
        return A;
    }

    public double calcTrueAnomaly(double M, double e)
    {
        //double e = ecc*RADS();
        double V;
        double E = M + e * Math.sin(M) * (1.0 + e * Math.cos(M));
        double E_1;
        //double compare1;
        //double compare2;
            
        do{
            E_1 = E;
            E = (E_1 - (E_1 - e * Math.sin(E_1) - M)) / (1 - e * Math.cos(E_1));
            //compare1 = Math.abs(E - E_1);
            //compare2 = Math.pow(1.0, -12.0);
        }while((Math.abs(E - E_1)) > (Math.pow(1.0, -12.0)));

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
    public double calcMeanSidereal(LocalDate date)
    {
        int side_month = date.getMonthValue();
        int side_year = date.getYear();
        int side_day = date.getDayOfMonth();
        if(date.getMonthValue() <= 2) // Adjust month and year if needed
        {            
            side_year--;        
            side_month = side_month + 12;
        }
        double a = Math.floor(side_year / 100.0);
        double b = 2 - a + Math.floor(a/4);
        double c = Math.floor(365.25 * side_year);
        double d = Math.floor(30.6001 * (side_month + 1));
        // Get days since J2000.0
        double jd = b + c + d - 730550.5 + side_day + (hour + minutes/60 + seconds/3600) / 24; 
        // Get Julian centuries since J2000.0
        double jt = jd / 36525.0;
        // Calculate initial Mean Sidereal Time (greenwichSidereal)
        greenwichSidereal = 280.46061837 + (360.98564736629 * jd) + (0.000387933 * (jt*jt)) - ((jt*jt*jt) / 38710000) + observedLong; 
        // Clip greenwichSidereal to range 0.0 to 360.0
        if(greenwichSidereal > 0.0)
            while(greenwichSidereal > 360.0) 
                greenwichSidereal -= 360.0;
        else
            while(greenwichSidereal < 0.0) 
                greenwichSidereal += 360.0;        
        return greenwichSidereal;
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
  
//
//  Planetary calculations for determining elements used in orbital calculations
//    
    public double calcSemiAxis(double Ascal, double Aprop)
    {
        double semiAxis = Ascal + Aprop * cy();
        return semiAxis;
    }
    
    public double calcEccentricity(double Escal, double Eprop)
    {
        double eccen = Escal + Eprop * cy();
        return eccen;
    }
    
    public double calcPlaneInclination(double Iscal, double Iprop)
    {
        double ecliptInc = ( Iscal - Iprop * cy() / 3600) * RADS();
        return ecliptInc;
    }
    
    public double calcPerihelion(double Wscal, double Wprop)
    {
        double periArg = (Wscal + Wprop * cy() / 3600) * RADS();
        return periArg;
    }
    
    public double calcLongAscNode(double Oscal, double Oprop)
    {
        double longAscNode = (Oscal - Oprop * cy() / 3600) * RADS();
        return longAscNode;
    }
    
    public double calcMeanLong(double Lscal, double Lprop)
    {
        double meanLong = Mod2Pi ((Lscal + Lprop * cy() / 3600) * RADS());
        return meanLong;
    }    
    
    public void calcRAdec(Planet plnt, Planet earth)
    {
        //calculate earths orbital position        
        double m_e = Mod2Pi(earth.getLongAscNode() - earth.getPerihelion());
        double v_e = calcTrueAnomaly(m_e, earth.getEccentricity());
        double r_e = earth.getSemimajorAxis() * (1-(earth.getEccentricity()*earth.getEccentricity())) / (1+earth.getEccentricity()*Math.cos(v_e));
        //calculate heliocentric rectangular coordinates of earth
        double x_e = r_e * Math.cos(v_e + earth.getPerihelion());
        double y_e = r_e * Math.sin(v_e + earth.getPerihelion());
        double z_e = 0.0;
        //calculate positon of the current planet in its orbit
        double m_p = Mod2Pi(plnt.getLongAscNode() - plnt.getPerihelion());
        double v_p = calcTrueAnomaly(m_p, plnt.getEccentricity());
        double r_p = plnt.getSemimajorAxis() * (1-(plnt.getEccentricity()*plnt.getEccentricity())) / (1+plnt.getEccentricity()*Math.cos(v_p));;
        //calculate the heliocentric rectangular coordinates of the current planet
        double x_h;
        double y_h;
        double z_h;
        if(plnt.getPlanetName() == "Earth/Sun")
        {
            x_h = 0.0;
            y_h = 0.0;
            z_h = 0.0;
        }
        else
        {
            x_h = r_p * (Math.cos(plnt.getLongAscNode()) * Math.cos(v_p + plnt.getPerihelion() - plnt.getLongAscNode()) - Math.sin(plnt.getLongAscNode()) * Math.sin(v_p + plnt.getPerihelion() - plnt.getLongAscNode()) * Math.cos(plnt.getInclination()));
            y_h = r_p * (Math.sin(plnt.getLongAscNode()) * Math.cos(v_p + plnt.getPerihelion() - plnt.getLongAscNode()) + Math.cos(plnt.getLongAscNode()) * Math.sin(v_p + plnt.getPerihelion() - plnt.getLongAscNode()) * Math.cos(plnt.getInclination()));
            z_h = r_p * (Math.sin(v_p + plnt.getPerihelion() - plnt.getLongAscNode()) * Math.sin(plnt.getInclination()));
        }
        //convert to geocentric rectangular coordinates
        double x_g = x_h - x_e;
        double y_g = y_h - y_e;
        double z_g = z_h - z_e;
        //rotate around X axis from ecliptic to equatorial coordinates
        double ecl = 23.439281 * RADS();
        double xeq = x_g;
        double yeq = y_g * Math.cos(ecl) - z_g * Math.sin(ecl);
        double zeq = y_g * Math.sin(ecl) + z_g * Math.cos(ecl);
        //calculate RA and dec from rectangular equatorial coordinates        
        rightAsc = Mod2Pi(Math.atan2(yeq, xeq)) * DEGS();
        declination = Math.atan(zeq / (Math.sqrt(xeq*xeq + yeq*yeq))) * DEGS();
        double distance = Math.sqrt((xeq*xeq) + (yeq*yeq) + (zeq*zeq));
    }   
    
    public double getRA()
    {
        return rightAsc;
    }
    
    public double getDec()
    {
        return declination;
    }    
    
    public Moon lunarCalc()
    {
        String phase;
        double RA;
        double dec;
        Moon m;
        
        double tempYear = date.getYear() + (date.getDayOfYear()/365);        
        int k = (int)((tempYear - 1900.0) * 12.3685);        
        double T = k / 1236.85;        
        double JD = 2415020.75933 + (29.53058868 * k) + (0.0001178 * Math.pow(T, 2)) - (0.000000155 * Math.pow(T, 3)) + (0.00033 * Math.sin(((166.56*RADS()) + ((132.87*RADS()) * T) - ((0.009173* RADS()) * Math.pow(T, 2)))));        
        
        // Math.ceil(k) to find next new moon
        // Math.floor(k) to find previous new moon
        // .25 .5 .75 increments to get quarter and full phases
        if((Math.ceil(k) - k >= 0.0 && Math.ceil(k) - k < 0.15) || (k - Math.floor(k) >= 0.0 && k - Math.floor(k) < 0.15))
        {
            phase = "new";
        }
        else if((Math.ceil(k) - k >= 0.15 && Math.ceil(k) - k < 0.35) || (k - Math.floor(k) >= 0.15 && k - Math.floor(k) < 0.35))
        {
            phase = "wax";
        }
        else if((Math.ceil(k) - k >= 0.60 && Math.ceil(k) - k < 1.0) || (k - Math.floor(k) >= 0.60 && k - Math.floor(k) < 1.0))        
        {
            phase = "wane";
        }
        else if((Math.ceil(k) - k >= 0.35 && Math.ceil(k) - k < 0.60) || (k - Math.floor(k) >= 0.35 && k - Math.floor(k) < 0.60))
        {
            phase = "full";
        }
        else
        {
            phase = "full";
        }        
        
        double T2 = (JD - 2415020.0) / 36525;
        double L = 270.434164 + 481267.8831*T2;
        double M = 358.475833 + 35999.0498*T2;
        double M1 = 296.104608 + 477198.8491*T2;
        double D = 350.737486 + 445267.1142*T2;
        double F = 11.250889 + 483202.0251*T2;              
        
        double e = 1 - 0.002495 * T - 0.00000752 * Math.pow(T, 2);
        //All calls to Math.sin need to be made with RADS()
        double geoLong = L + (6.288750 * Math.sin(M1)*RADS()) + (1.274018 * Math.sin(2*D - M1)*RADS()) + (0.658309 * Math.sin(2*D)*RADS()) + (0.213616 * Math.sin(2*M1)*RADS()) - (0.185596 * Math.sin(M)*RADS() * e) - (0.114336 * Math.sin(2*F)*RADS()) + (0.058793 * Math.sin(2*D - 2*M1)*RADS()) +(0.057212 * Math.sin(2*D - M - M1)*RADS() * e) + (0.053320 * Math.sin(2*D + M1)*RADS()) + (0.045874 * Math.sin(2*D - M)*RADS() * e);
        double geoLat = (5.128189 * Math.sin(F)*RADS()) + 0.280606 * (Math.sin(M1 + F)*RADS()) + (0.277693 * Math.sin(M1 - F)*RADS()) + (0.173238 * Math.sin(2*D - F)*RADS()) + (0.055413 * Math.sin(2*D + F - M1)*RADS()) + (0.046272 * Math.sin(2*D - F - M1)*RADS()) + (0.032573 * Math.sin(2*D + F)*RADS()) + (0.017198 * Math.sin(2*M1 + F)*RADS()) + (0.009267 * Math.sin(2*D + M1 - F)*RADS()) + (0.008823 * Math.sin(2*M1 - F)*RADS());        
        
        m = new Moon(phase, geoLong, geoLat);
        return m;
    }
}
