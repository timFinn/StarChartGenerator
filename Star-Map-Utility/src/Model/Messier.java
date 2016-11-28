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
public class Messier extends CelestialObject {
    public int nosInCat;
    public int newNos;
    public String rightAscensionHMS;
    public String rightAscensionDMS;
    public String declenationDMS;
    public String declenationDecimal;

    public Messier(int nosInCat, int newNos, String rightAscensionHMS, String rightAscensionDMS, String rightAscensionDecimal, String declenationDMS, String declenationDecimal) {
        this.nosInCat = nosInCat;
        this.newNos = newNos;
        this.rightAscensionHMS = rightAscensionHMS;
        this.rightAscensionDMS = rightAscensionDMS;
        this.declenationDMS = declenationDMS;
        this.declenationDecimal = declenationDecimal;
        super.dec = Double.parseDouble(declenationDecimal);
        super.ra = Double.parseDouble(rightAscensionDecimal); //THIS ONE Won't WORK YET.
    }
}
