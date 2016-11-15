/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.StarParser;
import java.util.ArrayList;

/**
 *
 * @author tbf0005
 */
public class DBscrubber {
    
    public DBscrubber()
    {
        
    }
    
    public void createLists()
    {
        String celestialObjectType;
        ArrayList starArray = new ArrayList();
        ArrayList messierArray = new ArrayList();
        ArrayList planetArray = new ArrayList();
        
        celestialObjectType = "Messier";
        messierArray = new StarParser().CSVToArrayList(celestialObjectType, messierArray);
        
        celestialObjectType = "Planet";
        planetArray = new StarParser().CSVToArrayList(celestialObjectType, planetArray);
        
        celestialObjectType = "Star";
        starArray = new StarParser().CSVToArrayList(celestialObjectType, starArray);
        
        System.out.println(messierArray.toString());
        System.out.println(planetArray.toString());
        System.out.println(starArray.toString());
    }
}
