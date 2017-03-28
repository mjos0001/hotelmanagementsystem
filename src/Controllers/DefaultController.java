package Controllers;


import java.sql.*;
//import oracle.jdbc.driver.OracleDriver;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

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
    public EntityManagerFactory LogIn()
    {
        try 
        {
             EntityManagerFactory emfactory = Persistence.createEntityManagerFactory( "HotelManagementSystemPU" );
             return emfactory;
        } 
        catch(Exception e)
        {
            System.out.println("Error in connection");
            return null;
        }
    }
}
