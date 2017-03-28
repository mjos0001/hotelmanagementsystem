package Controllers;


import java.sql.*;
import oracle.jdbc.driver.OracleDriver;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author mrkjse
 */
public class DefaultController {
    
    Connection conn;
    String url = "jdbc:oracle:thin:@hippo.its.monash.edu.au:1521:FIT5148A";
    String username = "S28066049";
    String password = "student";
    
    public boolean LogIn()
    {
        try 
        {
            DriverManager.registerDriver(new OracleDriver());
            conn = DriverManager.getConnection(url, username, password);
            System.out.println("Connected to Oracle");
            return true;
            
        } 
        catch(SQLException e)
        {
            System.out.println("Error in connection");
            return false;
        }
    }
}
