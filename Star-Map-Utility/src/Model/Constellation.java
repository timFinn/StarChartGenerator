/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.ArrayList;

/**
 *
 * @author Your Name <timothy>
 */
public class Constellation {
    public ArrayList<CelestialObject> constellationMembers = new ArrayList<>();
    public String constellationName;

    public Constellation(String constellationName) {
      //  this.constellationMembers = constellationMembers;
        this.constellationName = constellationName;
    }
}
