/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author timothy
 */
public class Database {
    
    static private Database singletonDB = null;
    static private Connection connection = null;
    
    private Database()
    {
        
    }
////////////////////////////////
// Creates singleton database
////////////////////////////////
    public static Database getDB()
    {
        if (singletonDB == null) 
        {
            singletonDB = new Database();       
        }    
        return singletonDB;
    }
    
////////////////////////////////////////
// Initiates connection with SQL server
////////////////////////////////////////
    public void startConnection() {
//        try {
//            Class.forName("com.mysql.jdbc.Driver");
//        } 
//        catch (ClassNotFoundException e) {
//        System.out.println("Could not find MySQL JDBC Driver");
//            e.printStackTrace();
//        }
//        System.out.println("MySQL JDBC Driver Registered");
        
        try {
            String url = "jdbc:mysql://104.155.185.144";
            String username = "root";
            String password = "76zqmlyk";
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("Connection established");
        } 
        catch (SQLException e) {
            System.out.println("Connection Failed. Check output console");
            e.printStackTrace();
        }
    }
    
//////////////////////////////////////////
// Terminates connection with SQL server
//////////////////////////////////////////
    public void closeConnection()
    {
        try 
        {
            System.out.println("Connection Closed");
            connection.close();
        }
        catch(SQLException ex)
        {
            System.out.println("Attempt failed");
        }
    }
    
    public void populateDB() {
        System.err.println("Fill DB with data");
    }
}