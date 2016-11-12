// Class: StarParser.java
// Package: StarMap
// Purpose: Parses the Yale Star Catalog CSV for stars of magnitude 6 or less.
// Author: Evan McGraw

package Model;
// Neccessary Imports
import java.util.List;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.util.Arrays;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

// This class will consist of functions that will utilize the 'CSVUtils' class to transfer our data from one container to another.
public class StarParser {

    String csvFile;                                                                                     // File to read from.
    BufferedReader br = null;                                                                           // File buffer.
    String line = "";                                                                                   // Temporary string for storage.
    String cvsSplitBy = ",";                                                                            // Declare the seperator.
    DecimalFormat df = new DecimalFormat("#.##");                                                       // Sets the decimal format.
    String celestialObjectType;
    ArrayList celestialObjectArray;
    
    /*************************************************************************************
    *  Function:    CSVToArrayList
    *  Purpose:     To generate a collection object inside of our program that contains
    *               celestial objects, of a certain type, visible to the user.
    *  Usage:       Given a celsetiaObjectType(string), the function will read from its 
    *               corresponding CSV file and move the entries into an arrayList. That
    *               array list will then be returned.
    *  Note:        Later versions of this function will also accept parameters to 
    *               query the CSV file for. i.e. you may want to search for all stars
    *               within a particular declination.
    **************************************************************************************/
    public ArrayList CSVToArrayList(String celestialObjectType, ArrayList celestialObjectArray)
    {
        this.celestialObjectType = celestialObjectType;
        this.celestialObjectArray = celestialObjectArray;
        
        if (celestialObjectType.equals("Planet")) {
            csvFile = "D:\\Evan Documents\\Senior Project\\cs499\\Star-Map-Utility\\src\\Model\\Planets.csv";
        }
        else if (celestialObjectType.equals("Star")) {
            csvFile = "D:\\Evan Documents\\Senior Project\\cs499\\Star-Map-Utility\\src\\Model\\6MagStars.csv";
        }
        else if (celestialObjectType.equals("Messier")) {
            csvFile = "D:\\Evan Documents\\Senior Project\\cs499\\Star-Map-Utility\\src\\Model\\Messier.csv";
        }
        
        try {
            // Create a new buffered reader and read the file until it is empty.
            br = new BufferedReader(new FileReader(csvFile));
            br.readLine();
            
            while ((line = br.readLine()) != null) 
            {
                String[] entry = line.split(cvsSplitBy);                        // Use comma as separator.
                         
                CelestialObject objectForArray = new CelestialObject();
                
                if (celestialObjectType.equals("Star"))
                {
                    objectForArray = new Star(Integer.valueOf(entry[0]), entry[6], Double.parseDouble(entry[7]), Double.parseDouble(entry[8]), Double.parseDouble(entry[10])); 
                }
                
                if (celestialObjectType.equals("Planet"))
                {
                    objectForArray = new Planet(entry[0], Double.parseDouble(entry[1]), Double.parseDouble(entry[2]), Double.parseDouble(entry[3]), Double.parseDouble(entry[4]), 
                            Double.parseDouble(entry[5]), Double.parseDouble(entry[6]), Double.parseDouble(entry[7]), Double.parseDouble(entry[8]), Double.parseDouble(entry[9]), 
                            Double.parseDouble(entry[10]), Double.parseDouble(entry[11]), Double.parseDouble(entry[12]));
                }
                
                if (celestialObjectType.equals("Messier"))
                {
                    objectForArray = new Messier(Integer.parseInt(entry[0]), Integer.parseInt(entry[1]), entry[2], entry[3], entry[4]);
                }
                
                celestialObjectArray.add(objectForArray);
            }
        }
            // IO Exceptions.
        catch (FileNotFoundException e) {               
            e.printStackTrace();
        } 
        catch (IOException e) {
            e.printStackTrace();
        } 
        finally {
            if (br != null) {
                try {
                    br.close();
                } 
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
          
        return celestialObjectArray;
    }
    
    
    
    /***********************************************************
    * This section is for parsing the original 'hyg' file
    * *********************************************************
    // Main function.
    public static void main(String[] args) throws Exception {
        
        String csvNewFile = "D:\\Evan Documents\\Senior Project\\workspace\\StarMap\\bin\\6MagStars.csv";   // File to write to.
        
        BufferedReader br = null;                                                                           // File buffer.
        String line = "";                                                                                   // Temporary string for storage.
        String cvsSplitBy = ",";                                                                            // Declare the seperator.
        String magnitude;                                                                                   // Used to convert magnitude to a double.
        FileWriter writer = new FileWriter(csvNewFile);                                                     // Creates a new FileWriter
        
        DecimalFormat df = new DecimalFormat("#.##");                                                       // Sets the decimal format.
       
        // Scans the CSV file for stars of magnitude 6 or less and moves them into another CSV.
        try {
            // Create a new buffered reader and read the file until it is empty.
            br = new BufferedReader(new FileReader(csvFile));
            br.readLine();
            while ((line = br.readLine()) != null) {

                // Use comma as separator.
                String[] star = line.split(cvsSplitBy);
                
                // Set magnitude variable to the 10th entry in the table
                magnitude = star[10];

                if (Double.parseDouble(magnitude) <= 6.00)
                    // Moves a table entry into the new CSV.
                    CSVUtils.writeLine(writer, Arrays.asList(star[0], star[1], star[2], star[3], star[4], star[5], star[6], star[7], star[8], star[9], star[10], star[11], star[12], star[13]));
            }
            // Prepare the file for closing and then close it.
            writer.flush();
            writer.close();

        } 
        // IO Exceptions.
        catch (FileNotFoundException e) {               
            e.printStackTrace();
        } 
        catch (IOException e) {
            e.printStackTrace();
        } 
        finally {
            if (br != null) {
                try {
                    br.close();
                } 
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    ************************************************************/
}

