package hmsDataService;


//import oracle.jdbc.driver.OracleDriver;
import java.util.*;
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
public class DefaultDataService {
    public ArrayList<EntityManagerFactory> LogIn()
    {
        ArrayList<EntityManagerFactory> emfList = new ArrayList<>();
        try 
        {
             EntityManagerFactory emfactoryA = Persistence.createEntityManagerFactory("HotelManagementSystemPUA");
             EntityManagerFactory emfactoryB = Persistence.createEntityManagerFactory("HotelManagementSystemPUB");
             emfList.add(emfactoryA);
             emfList.add(emfactoryB);
                     
             return emfList;
        } 
        catch(Exception e)
        {
            System.out.println("Error in connection");
            return null;
        }
    }
}
