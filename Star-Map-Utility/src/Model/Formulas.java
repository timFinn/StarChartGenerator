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
public class Formulas {
    
    // Converts a degrees-minutes-seconds object to decimal-degrees
    public double DMSToDecimal(DMSObject dms) 
    {
        // do the conversion
        double decDegrees = dms.degrees + (dms.minutes / 60) + (dms.seconds / 3600);
        // return it
        return decDegrees;
    }
    
    // Converts decimal-degrees into a degrees-minutes-seconds object
    public DMSObject DecimalToDMS (double decimal)
    {
        // create a new dms object
        DMSObject dms = new DMSObject();
        // calculate each element and assign them to the object's accociative fields
        dms.degrees = (int)decimal;
        dms.minutes = (int)((decimal - dms.degrees) * 60);
        dms.seconds = (((decimal - dms.degrees) * 60) - ((int)((decimal - dms.degrees) * 60))) * 60;
       
        //return it
        return dms;
    }
}
