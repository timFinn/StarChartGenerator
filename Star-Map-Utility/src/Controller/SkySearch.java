/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.*;
import View.JOGLtests;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author timothy
 */
public class SkySearch {
    private final Formulas frm;
    private final Observer obs;
    private Planet planet;
    
    private ArrayList starArray;
    private ArrayList planetArray;
    private ArrayList messierArray;
    
    private double minRA;
    private double maxRA;
    private double minDec;
    private double maxDec;
    private double latitude;
    private double longitude;
    
    public SkySearch(double latConverted, double longConverted, LocalDate dateConverted, LocalTime timeConverted)
    {
        latitude = latConverted;
        longitude = longConverted;
        obs = Observer.getObserver(latConverted, longConverted, dateConverted, timeConverted);
        frm = Formulas.getFormulas(timeConverted, dateConverted, latConverted, longConverted);
    }
    
    public void createLists()
    {
        String celestialObjectType;
        starArray = new ArrayList();
        messierArray = new ArrayList();
        planetArray = new ArrayList();
        
        celestialObjectType = "Messier";
        messierArray = new StarParser().CSVToArrayList(celestialObjectType, messierArray);
        
        celestialObjectType = "Planet";
        planetArray = new StarParser().CSVToArrayList(celestialObjectType, planetArray);
        
        celestialObjectType = "Star";
        starArray = new StarParser().CSVToArrayList(celestialObjectType, starArray);
        
        //System.out.println(messierArray.toString());
        //System.out.println(planetArray.toString());
        //System.out.println(starArray.toString());
    }
    
    public ArrayList getStars()
    {
        return starArray;
    }
    
    public ArrayList getPlanets()
    {
        return planetArray;
    }
    
    public ArrayList getMessier()
    {
        return messierArray;
    }
    
    public void calcSpaceTimeWindow()
    {        
        double gms = frm.calcMeanSidereal();
        System.out.println(gms);
    }
    
    public void calcPlanets()
    {
        Iterator<Planet> planetIt = planetArray.iterator();
        
        while(planetIt.hasNext())
        {
            planet = planetIt.next();
            double s = frm.calcSemiAxis(planet.getaScal(), planet.getaProp());
            double e = frm.calcEccentricity(planet.geteScal(), planet.geteProp());
            double i = frm.calcPlaneInclination(planet.getiScal(), planet.getiProp());
            double p = frm.calcPerihelion(planet.getwScal(), planet.getwProp());
            double l = frm.calcLongAscNode(planet.getoScal(), planet.getoProp());
            double m = frm.calcMeanLong(planet.getlScal(), planet.getlProp());            
            
            planet.setElements(s, e, i, p, l, m);
            frm.calcRAdec();
            planet.setRAdec(frm.getRA(), frm.getDec());
        }
    }
    
  public void generateChart()
    {
        calcSpaceTimeWindow();
        //calcPlanets();
        this.createLists();
        //TEST FILE DELETE ME FIXME
             ArrayList constellation = new ArrayList<>();
        JOGLtests jt = new JOGLtests( this.starArray,this.messierArray, this.planetArray,  constellation,
            this.latitude,  this.longitude);
        /*
            relative lat and long are the origin for calculating the viewport
           
        */
    }
}
