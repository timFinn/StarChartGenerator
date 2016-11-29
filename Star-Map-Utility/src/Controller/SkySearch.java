/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.*;
import View.DrawVerse;
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
    private Planet earth;
    private Moon m;
    
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
        frm.calcMeanSidereal(obs.observedDate);
        frm.calcRelativeJD();        
    }
    
    public void calcPlanets()
    {
        Iterator<Planet> planetIt = planetArray.iterator();        
        while(planetIt.hasNext())
        {
            planet = planetIt.next();                       
            frm.calcRAdec(planet, earth);
            planet.setRAdec(frm.getRA(), frm.getDec());
        }
    }
    
    public void calcPlanetaryElements()
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
        }
    }
    
    public void findEarth()
    {
        earth = (Planet) planetArray.get(2);
    }
    
    public void calcMoon()
    {       
        this.m = frm.lunarCalc();
    }
    
  public void generateChart()
    {
        createLists();
        calcSpaceTimeWindow();
        findEarth();
        
        calcPlanetaryElements();
        calcPlanets();
        
        ArrayList constellations = this.generateConstellations();
        
        calcMoon();
        
        DrawVerse jt = new DrawVerse( this.starArray,this.messierArray, this.planetArray,  constellations, m,
            this.latitude,  this.longitude);                    
    }

    private ArrayList<Constellation> generateConstellations()
    {
        //array list of constellations
        ArrayList<Constellation> constellations = new ArrayList();
        
        //Cassiopeia constellation
        Constellation Cassiopeia = new Constellation("Cassiopeia");
        CelestialObject tempObject;
        //first set of points
        tempObject = new CelestialObject(2.3295, 59.09);
        Cassiopeia.constellationMembers.add(tempObject);
        tempObject = new CelestialObject(10.2656, 55.32);
        Cassiopeia.constellationMembers.add(tempObject);
        //second set of points
        tempObject = new CelestialObject(10.2656, 55.32);
        Cassiopeia.constellationMembers.add(tempObject);
        tempObject = new CelestialObject(14.3719, 60.43);
        Cassiopeia.constellationMembers.add(tempObject);
        //third set of points
        tempObject = new CelestialObject(14.3719, 60.43);
        Cassiopeia.constellationMembers.add(tempObject);
        tempObject = new CelestialObject(21.7479, 60.14);
        Cassiopeia.constellationMembers.add(tempObject);
        //fourth set of points
        tempObject = new CelestialObject(21.7479, 60.14);
        Cassiopeia.constellationMembers.add(tempObject);
        tempObject = new CelestialObject(28.9977, 63.40);
        Cassiopeia.constellationMembers.add(tempObject);
        //add Cassiopeia to the arrayList
        constellations.add(Cassiopeia);
        
        //Aries constellation
        Constellation Aries = new Constellation("Aries");
        //first set of points
        tempObject = new CelestialObject(2.12, 23.28);
        Aries.constellationMembers.add(tempObject);
        tempObject = new CelestialObject(1.9117, 20.48);
        Aries.constellationMembers.add(tempObject);
        //add Aries to the arrayList
        constellations.add(Aries);
        
        //Taurus constellation
        Constellation Taurus = new Constellation("Taurus");
        //first set of points
        tempObject = new CelestialObject(5.6267, 21.08);
        Taurus.constellationMembers.add(tempObject);
        tempObject = new CelestialObject(4.5983, 16.30);
        Taurus.constellationMembers.add(tempObject);
        //second set of points
        tempObject = new CelestialObject(4.5983, 16.30);
        Taurus.constellationMembers.add(tempObject);
        tempObject = new CelestialObject(4.4767, 15.52);
        Taurus.constellationMembers.add(tempObject);
        //third set of points
        tempObject = new CelestialObject(19.4783, 19.10);
        Taurus.constellationMembers.add(tempObject);
        tempObject = new CelestialObject(5.4433, 23.36);
        Taurus.constellationMembers.add(tempObject);
        //add Taurus to the arrayList
        constellations.add(Aries);
        
        //Gemini constellation
        Constellation Gemini = new Constellation("Gemini");
        //first set of points
        tempObject = new CelestialObject(7.335, 21.59);
        Gemini.constellationMembers.add(tempObject);
        tempObject = new CelestialObject(7.7567, 28.02);
        Gemini.constellationMembers.add(tempObject);
        //second set of points
         tempObject = new CelestialObject(7.7567, 28.02);
        Gemini.constellationMembers.add(tempObject);
        tempObject = new CelestialObject(7.5767, 31.54);
        Gemini.constellationMembers.add(tempObject);
        //third set of points
        tempObject = new CelestialObject(7.5767, 31.54);
        Gemini.constellationMembers.add(tempObject);
        tempObject = new CelestialObject(6.7333, 25.08);
        Gemini.constellationMembers.add(tempObject);
        //add Gemini to the arrayList
        constellations.add(Gemini);
        
        //Leo constellation
        Constellation Leo = new Constellation("Leo");
        //first set of points
        tempObject = new CelestialObject(10.1233, 16.46);
        Leo.constellationMembers.add(tempObject);
        tempObject = new CelestialObject(10.14, 11.58);
        Leo.constellationMembers.add(tempObject);
        //second set of points
        tempObject = new CelestialObject(10.14, 11.58);
        Leo.constellationMembers.add(tempObject);
        tempObject = new CelestialObject(11.2367, 15.26);
        Leo.constellationMembers.add(tempObject);
        //third set of points
        tempObject = new CelestialObject(11.2367, 15.26);
        Leo.constellationMembers.add(tempObject);
        tempObject = new CelestialObject(11.8183, 14.53);
        Leo.constellationMembers.add(tempObject);
        //fourth set of points
        tempObject = new CelestialObject(11.8183, 14.53);
        Leo.constellationMembers.add(tempObject);
        tempObject = new CelestialObject(11.2367, 20.32);
        Leo.constellationMembers.add(tempObject);
        //fifth set of points
         tempObject = new CelestialObject(11.2367, 20.32);
        Leo.constellationMembers.add(tempObject);
        tempObject = new CelestialObject(10.3333, 19.51);
        Leo.constellationMembers.add(tempObject);
        //add Leo to the arrayList
        constellations.add(Leo);
        
        //Virgo constellation
        Constellation Virgo = new Constellation("Virgo");
        //first set of points
        tempObject = new CelestialObject(13.58, -0.36);
        Virgo.constellationMembers.add(tempObject);
        tempObject = new CelestialObject(13.42, -11.09);
        Virgo.constellationMembers.add(tempObject);
        //add Virgo to the arrayList
        constellations.add(Virgo);
        
        //Libra constellation
        Constellation Libra = new Constellation("Libra");
        //first set of points
        tempObject = new CelestialObject(15.2833, -9.23);
        Libra.constellationMembers.add(tempObject);
        tempObject = new CelestialObject(14.8483, -16.02);
        Libra.constellationMembers.add(tempObject);
        //second set of points
        tempObject = new CelestialObject(14.8483, -16.02);
        Libra.constellationMembers.add(tempObject);
        tempObject = new CelestialObject(15.065, -25.16);
        Libra.constellationMembers.add(tempObject);
        //third set of points
       tempObject = new CelestialObject(15.2833, -9.23);
        Libra.constellationMembers.add(tempObject);
        tempObject = new CelestialObject(15.065, -25.16);
        Libra.constellationMembers.add(tempObject);
        //add Libra to the arrayList
        constellations.add(Libra);
        
        //Scorpius constellation
        Constellation Scorpius = new Constellation("Scorpius");
        //first set of points
        tempObject = new CelestialObject(17.5133, -37.17);
        Scorpius.constellationMembers.add(tempObject);
        tempObject = new CelestialObject(17.56, -37.06);
        Scorpius.constellationMembers.add(tempObject);
        //second set of points
        tempObject = new CelestialObject(17.56, -37.06);
        Scorpius.constellationMembers.add(tempObject);
        tempObject = new CelestialObject(17.7083, -39.02);
        Scorpius.constellationMembers.add(tempObject);
        //third set of points
        tempObject = new CelestialObject(17.7083, -39.02);
        Scorpius.constellationMembers.add(tempObject);
        tempObject = new CelestialObject(17.7933, -40.07);
        Scorpius.constellationMembers.add(tempObject);
        //fourth set of points
        tempObject = new CelestialObject(17.7933, -40.07);
        Scorpius.constellationMembers.add(tempObject);
        tempObject = new CelestialObject(17.6217, -43.0);
        Scorpius.constellationMembers.add(tempObject);
        //fifth set of points
        tempObject = new CelestialObject(17.6217, -43.0);
        Scorpius.constellationMembers.add(tempObject);
        tempObject = new CelestialObject(17.2017, -43.15);
        Scorpius.constellationMembers.add(tempObject);
        //sixth set of points
        tempObject = new CelestialObject(16.8650, -38.03);
        Scorpius.constellationMembers.add(tempObject);
        tempObject = new CelestialObject(16.8367, -34.17);
        Scorpius.constellationMembers.add(tempObject);
        //7th set of points
        tempObject = new CelestialObject(16.8367, -34.17);
        Scorpius.constellationMembers.add(tempObject);
        tempObject = new CelestialObject(16.5983, -28.13);
        Scorpius.constellationMembers.add(tempObject);
        //8th set of points
        tempObject = new CelestialObject(16.5983, -28.13);
        Scorpius.constellationMembers.add(tempObject);
        tempObject = new CelestialObject(16.4917, -26.26);
        Scorpius.constellationMembers.add(tempObject);
        //9th set of points
        tempObject = new CelestialObject(16.4917, -26.26);
        Scorpius.constellationMembers.add(tempObject);
        tempObject = new CelestialObject(16.3517, -25.35);
        Scorpius.constellationMembers.add(tempObject);
        //10th set of points
        tempObject = new CelestialObject(16.3517, -25.35);
        Scorpius.constellationMembers.add(tempObject);
        tempObject = new CelestialObject(16.005, -22.38);
        Scorpius.constellationMembers.add(tempObject);
        //11th set of points
        tempObject = new CelestialObject(16.005, -22.38);
        Scorpius.constellationMembers.add(tempObject);
        tempObject = new CelestialObject(16.09, -19.48);
        Scorpius.constellationMembers.add(tempObject);
        //add Scorpius to the arrayList
        constellations.add(Scorpius);
        
        //Sagittarius constellation
        Constellation Sagittarius = new Constellation("Sagittarius");
        //first set of points
        tempObject = new CelestialObject(19.1167, -27.40);
        Sagittarius.constellationMembers.add(tempObject);
        tempObject = new CelestialObject(18.9217, -26.18);
        Sagittarius.constellationMembers.add(tempObject);
        //second set of points
        tempObject = new CelestialObject(18.4667, -25.26);
        Sagittarius.constellationMembers.add(tempObject);
        tempObject = new CelestialObject(18.35, -29.50);
        Sagittarius.constellationMembers.add(tempObject);
        //third set of points
        tempObject = new CelestialObject(18.35, -29.50);
        Sagittarius.constellationMembers.add(tempObject);
        tempObject = new CelestialObject(18.4033, -34.23);
        Sagittarius.constellationMembers.add(tempObject);
        //4th set of points
        tempObject = new CelestialObject(18.4033, -34.23);
        Sagittarius.constellationMembers.add(tempObject);
        tempObject = new CelestialObject(19.0433, -29.53);
        Sagittarius.constellationMembers.add(tempObject);
        //5th set of points
        tempObject = new CelestialObject(18.35, -29.50);
        Sagittarius.constellationMembers.add(tempObject);
        tempObject = new CelestialObject(18.4033, -34.23);
        Sagittarius.constellationMembers.add(tempObject);
        //add Sagittarius to the arrayList
        constellations.add(Sagittarius);
        
        //Aquarius constellation
        Constellation Aquarius = new Constellation("Aquarius");
        //first set of points
        tempObject = new CelestialObject(21.5267, -5.35);
        Aquarius.constellationMembers.add(tempObject);
        tempObject = new CelestialObject(22.0967, -0.2);
        Aquarius.constellationMembers.add(tempObject);
        //add Aquarius to the arrayList
        constellations.add(Aquarius);
        
        //Ursa Minor constellation
        Constellation UrsaMinor  = new Constellation("Ursa Minor");
        //first set of points
        tempObject = new CelestialObject(14.845, 74.1);
        UrsaMinor.constellationMembers.add(tempObject);
        tempObject = new CelestialObject(15.345, 71.5);
        UrsaMinor.constellationMembers.add(tempObject);
        //add Ursa Minor  to the arrayList
        constellations.add(UrsaMinor);
        
        //Ursa Major constellation
        Constellation UrsaMajor  = new Constellation("Ursa Major");
        //first set of points
        tempObject = new CelestialObject(3.7917, 49.19);
        UrsaMajor.constellationMembers.add(tempObject);
        tempObject = new CelestialObject(13.3983, 54.58);
        UrsaMajor.constellationMembers.add(tempObject);
        //second set of points
        tempObject = new CelestialObject(13.3983, 54.58);
        UrsaMajor.constellationMembers.add(tempObject);
        tempObject = new CelestialObject(12.9017, 55.58);
        UrsaMajor.constellationMembers.add(tempObject);
        //third set of points
        tempObject = new CelestialObject(11.8983, 53.42);
        UrsaMajor.constellationMembers.add(tempObject);
        tempObject = new CelestialObject(11.03, 56.23);
        UrsaMajor.constellationMembers.add(tempObject);
        //add Ursa Major  to the arrayList
        constellations.add(UrsaMajor);
        
        //Draco constellation
        Constellation Draco  = new Constellation("Draco");
        //first set of points
        tempObject = new CelestialObject(17.5067, 52.19);
        Draco.constellationMembers.add(tempObject);
        tempObject = new CelestialObject(17.9433, 51.29);
        Draco.constellationMembers.add(tempObject);
        //add Draco  to the arrayList
        constellations.add(Draco);
        
        //Andromeda constellation
        Constellation Andromeda  = new Constellation("Andromeda");
        //first set of points
        tempObject = new CelestialObject(0.14, 29.05);
        Andromeda.constellationMembers.add(tempObject);
        tempObject = new CelestialObject(0.6567, 30.51);
        Andromeda.constellationMembers.add(tempObject);
        //second set of points
        tempObject = new CelestialObject(0.6567, 30.51);
        Andromeda.constellationMembers.add(tempObject);
        tempObject = new CelestialObject(1.1617, 35.37);
        Andromeda.constellationMembers.add(tempObject);
        //3rd set of points
        tempObject = new CelestialObject(1.1617, 35.37);
        Andromeda.constellationMembers.add(tempObject);
        tempObject = new CelestialObject(2.0667, 42.2);
        Andromeda.constellationMembers.add(tempObject);
        //add Andromeda  to the arrayList
        constellations.add(Andromeda);
        
        //Pegasus constellation
        Constellation Pegasus  = new Constellation("Pegasus");
        //first set of points
        tempObject = new CelestialObject(0.2217, 15.11);
        Pegasus.constellationMembers.add(tempObject);
        tempObject = new CelestialObject(0.14, 29.05);
        Pegasus.constellationMembers.add(tempObject);
        //add Pegasus  to the arrayList
        constellations.add(Pegasus);
        
        //Perseus constellation
        Constellation Perseus  = new Constellation("Perseus");
        //first set of points
        tempObject = new CelestialObject(3.0783, 53.3);
        Perseus.constellationMembers.add(tempObject);
        tempObject = new CelestialObject(3.405, 49.51);
        Perseus.constellationMembers.add(tempObject);
        //second set of points
        tempObject = new CelestialObject(3.405, 49.51);
        Perseus.constellationMembers.add(tempObject);
        tempObject = new CelestialObject(3.415, 47.47);
        Perseus.constellationMembers.add(tempObject);
        //3rd set of points
        tempObject = new CelestialObject(3.1367, 40.57);
        Perseus.constellationMembers.add(tempObject);
        tempObject = new CelestialObject(3.0867, 38.5);
        Perseus.constellationMembers.add(tempObject);
        //add Perseus  to the arrayList
        constellations.add(Perseus);
        
        //Triangulum constellation
        Constellation Triangulum  = new Constellation("Triangulum");
        //first set of points
        tempObject = new CelestialObject(1.885, 29.35);
        Triangulum.constellationMembers.add(tempObject);
        tempObject = new CelestialObject(2.1583, 34.59);
        Triangulum.constellationMembers.add(tempObject);
        //add Triangulum  to the arrayList
        constellations.add(Triangulum);
        
        return constellations;
    }

}
