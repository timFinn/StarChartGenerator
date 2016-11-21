/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author Your Name <timothy>
 */
public class Planet extends CelestialObject {
    private String planetName;
    private double lScal;
    private double lProp;
    private double aScal;
    private double aProp;
    private double eScal;
    private double eProp;
    private double iScal;
    private double iProp;
    private double wScal;
    private double wProp;
    private double oScal;
    private double oProp;
    
    private double semimajorAxis;
    private double eccentricity;
    private double inclination;
    private double perihelion;
    private double longAscNode;
    private double meanLong;
    
    private double rightAsc;
    private double declination;

    public Planet(String planetName, double lScal, double lProp, double aScal, double aProp, double eScal, double eProp, double iScal, double iProp, double wScal, double wProp, double oScal, double oProp) {
        this.planetName = planetName;
        this.lScal = lScal;
        this.lProp = lProp;
        this.aScal = aScal;
        this.aProp = aProp;
        this.eScal = eScal;
        this.eProp = eProp;
        this.iScal = iScal;
        this.iProp = iProp;
        this.wScal = wScal;
        this.wProp = wProp;
        this.oScal = oScal;
        this.oProp = oProp;       
    }
    
    public void setElements(double s, double e, double i, double p, double l, double m)
    {
        semimajorAxis = s;
        eccentricity = e;
        inclination = i;
        perihelion = p;
        longAscNode = l;
        meanLong = m;
    }        
    
    public void setRAdec(double r, double d)
    {
        rightAsc = r;
        declination = d;
    }

    public double getlScal() {
        return lScal;
    }

    public double getlProp() {
        return lProp;
    }

    public double getaScal() {
        return aScal;
    }

    public double getaProp() {
        return aProp;
    }

    public double geteScal() {
        return eScal;
    }

    public double geteProp() {
        return eProp;
    }

    public double getiScal() {
        return iScal;
    }

    public double getiProp() {
        return iProp;
    }

    public double getwScal() {
        return wScal;
    }

    public double getwProp() {
        return wProp;
    }

    public double getoScal() {
        return oScal;
    }

    public double getoProp() {
        return oProp;
    }

    public String getPlanetName() {
        return planetName;
    }

    public double getSemimajorAxis() {
        return semimajorAxis;
    }

    public double getEccentricity() {
        return eccentricity;
    }

    public double getInclination() {
        return inclination;
    }

    public double getPerihelion() {
        return perihelion;
    }

    public double getLongAscNode() {
        return longAscNode;
    }

    public double getMeanLong() {
        return meanLong;
    }

    public double getRightAsc() {
        return rightAsc;
    }

    public double getDeclination() {
        return declination;
    }
    
    
}
