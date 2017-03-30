/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotelmanagementsystem;

import hmsFrame.MainFrame;
import java.util.ArrayList;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author mrkjse
 */
public class HotelManagementSystem {

    public static ArrayList<EntityManagerFactory> logIn()
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
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        logIn();
        
        MainFrame mf = new MainFrame();
        mf.setVisible(true);
    }
    
}
