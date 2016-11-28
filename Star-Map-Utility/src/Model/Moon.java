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
public class Moon extends CelestialObject {
    public String phase;
    public double RA;
    public double dec;   

    public Moon(String phase, double RA, double dec) {
        this.phase = phase;
        this.RA = RA;
        this.dec = dec;
    }

    public String getPhase() {
        return phase;
    }

    public void setPhase(String phase) {
        this.phase = phase;
    }

    public double getRA() {
        return RA;
    }

    public void setRA(double RA) {
        this.RA = RA;
    }

    public double getDec() {
        return dec;
    }

    public void setDec(double dec) {
        this.dec = dec;
    }
    
    
}
