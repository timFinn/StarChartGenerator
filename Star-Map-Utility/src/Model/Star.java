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
public class Star extends CelestialObject {
    public int starID;
    public int hip;
    public int hd;
    public int hr;
    public String gilese;
    public String bayerFlams;
    public String properName;
    public double rightAscension;
    public double declination;
    public double distance;
    public double magnitude;
    public double absMagnitude;
    public String Spectrum;
    public double colorIndex;

    public Star(int starID, String properName, double rightAscension, double declination, double magnitude)  
    {
        this.starID = starID;
        this.properName = properName;
        this.rightAscension = rightAscension;
        this.declination = declination;
        this.magnitude = magnitude;
        super.ra = rightAscension*360/24;
        super.dec = declination;
    }
}
