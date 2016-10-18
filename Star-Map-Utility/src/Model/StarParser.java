// Class: StarParser.java
// Package: StarMap
// Purpose: Parses the Yale Star Catalog CSV for stars of magnitude 6 or less.
// Author: Evan McGraw

package Model;

// Neccessary Imports
import java.io.BufferedReader;
import java.io.FileWriter;
import java.util.Arrays;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;

// This class will create a smaller CSV file containing stars of 6 magnitude or less.
public class StarParser {
    // Main function.
    public static void main(String[] args) throws Exception {
        
        String csvNewFile = "D:\\Evan Documents\\Senior Project\\workspace\\StarMap\\bin\\6MagStars.csv";   // File to write to.
        String csvFile = "D:\\Evan Documents\\Senior Project\\workspace\\StarMap\\bin\\hyg.csv";            // File to read from.
        BufferedReader br = null;                                                                           // File buffer.
        String line = "";                                                                                   // Temporary string for storage.
        String cvsSplitBy = ",";                                                                            // Declare the seperator.
        String magnitude;                                                                                   // Used to convert magnitude to a double.
        FileWriter writer = new FileWriter(csvNewFile);                                                     // Creates a new FileWriter.
        
        DecimalFormat df = new DecimalFormat("#.##");                                                       // Sets the decimal format.
       
        // Scans the CSV file for stars of magnitude 6 or less and moves them into another CSV.
        try {
            // Create a new buffered reader and read the file until it is empty.
            br = new BufferedReader(new FileReader(csvFile));
            br.readLine();
            while ((line = br.readLine()) != null) {

                // Use comma as separator.
                String[] star = line.split(cvsSplitBy);
                // Set magnitude variable to the 10th entry in the table.
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
}