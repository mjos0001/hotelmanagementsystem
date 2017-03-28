package Controllers;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.sql.*;
import java.util.*;
import Models.*;
//import oracle.jdbc.driver.OracleDriver;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.math.BigDecimal;

/**
 *
 * @author mrkjse
 */
public class HotelController {
    
    Connection conn = null;
    ArrayList<Hotel> hotels = new ArrayList<>();
    
    public static List<Hotel> GetHotels(EntityManagerFactory emFactory)
    {
        
      //EntityManagerFactory emfactory = Persistence.createEntityManagerFactory( "HotelManagementSystemPU" );
      EntityManager entitymanager = emFactory.createEntityManager( );
      entitymanager.getTransaction( ).begin( );
      
      List<Hotel> hotels = entitymanager.createNamedQuery("Hotel.findAll").getResultList();

      if (hotels.size() > 0)
      {
          for (Hotel h : hotels) {
              Calendar c = Calendar.getInstance();
              c.setTime(h.getConstructionYear());
              
              System.out.println(h.getHotelId() + " "
                      + h.getHotelName() + " "
                      + c.get(Calendar.YEAR) + " "
                      + h.getAddress() + " "
                      + h.getCity() + " " 
                      + h.getCountry() + " "
                      + h.getContactNumber() + " "
                      + h.getEmailAddress() + " "
                      + h.getHotelTypeCode());
          }
      }
      
      entitymanager.getTransaction( ).commit( );
      entitymanager.close( );
      
      return hotels;
    }
    
    
    public static boolean CreateHotel(EntityManagerFactory emfactory, Hotel hotel)
    {
        try
        {
            EntityManager entitymanager = emfactory.createEntityManager( );
            entitymanager.getTransaction( ).begin( );

            entitymanager.persist( hotel );
            entitymanager.getTransaction( ).commit( );

            GetHotels(emfactory);

            entitymanager.close( );
            
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }
    
    public static boolean DeleteHotel(EntityManagerFactory emfactory, Hotel hotel)
    {
         try
        {
            EntityManager entitymanager = emfactory.createEntityManager( );
            entitymanager.getTransaction( ).begin( );

            entitymanager.remove( hotel );
            entitymanager.getTransaction( ).commit( );

            GetHotels(emfactory);

            entitymanager.close( );
            
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }
    
    public static boolean UpdateHotel(EntityManagerFactory emfactory, Hotel newHotel)
    {
        try
        {
            EntityManager entitymanager = emfactory.createEntityManager( );
            entitymanager.getTransaction( ).begin( );

            
            Hotel oldHotel = entitymanager.find( Hotel.class, newHotel.getHotelId() );

            //before update
            System.out.println( oldHotel );
            oldHotel.setHotelName(newHotel.getHotelName());
            oldHotel.setConstructionYear(newHotel.getConstructionYear());
            oldHotel.setCountry(newHotel.getCountry());
            oldHotel.setCity(newHotel.getCity());
            oldHotel.setAddress(newHotel.getAddress());
            oldHotel.setContactNumber(newHotel.getContactNumber());
            oldHotel.setEmailAddress(newHotel.getEmailAddress());
            oldHotel.setHotelTypeCode(newHotel.getHotelTypeCode());
            entitymanager.getTransaction( ).commit( );

            //after update
            //System.out.println( employee );

            entitymanager.getTransaction( ).commit( );

            GetHotels(emfactory);

            entitymanager.close( );
            
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }
    
    public static Hotel FindHotelByName(EntityManagerFactory emfactory, String hotelName)
    {
        EntityManager entitymanager = emfactory.createEntityManager( );
        entitymanager.getTransaction( ).begin( );
      
        Hotel hotel = (Hotel)entitymanager.createNamedQuery("Hotel.findByHotelName")
                .setParameter("hotelName", hotelName).getSingleResult();
      
        entitymanager.getTransaction( ).commit( );
        entitymanager.close( );
        
        return hotel;
    }
    
    public static List<Hotel> FindHotelByType(EntityManagerFactory emfactory, String hotelTypeCode)
    {
        EntityManager entitymanager = emfactory.createEntityManager( );
        entitymanager.getTransaction( ).begin( );
      
        List<Hotel> hotels = entitymanager.createNamedQuery("Hotel.findByHotelTypeCode")
                .setParameter("hotelTypeCode", hotelTypeCode).getResultList();
      
        entitymanager.getTransaction( ).commit( );
        entitymanager.close( );
        
        return hotels;
    }
    
      /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory( "HotelManagementSystemPU" );
        //GetHotels(emfactory);

//        Hotel hotel = new Hotel( ); 
//        hotel.setHotelId(new BigDecimal(3));
//        hotel.setHotelName("Christmas Hotel");
//        hotel.setAddress("24 Lygon St. Melbourne VIC");
//        hotel.setCity("Melbourne");
//        hotel.setCountry("Australia");
//        hotel.setConstructionYear(new java.util.Date(2005, 12, 25));
//        hotel.setContactNumber("+61415987654");
//        hotel.setEmailAddress("hello@christmashmelb.com");
//        hotel.setHotelTypeCode((HotelType)hotelTypes.toArray()[0]);
//        
//        CreateHotel(emfactory, new Hotel());
//        
//        Hotel h = FindHotelByName(emfactory, "Christmas Hotel");
//        Calendar c = Calendar.getInstance();
//        c.setTime(h.getConstructionYear());
//        
//        System.out.println(h.getHotelId() + " "
//                     + h.getHotelName() + " "
//                     + c.get(Calendar.YEAR) + " "
//                     + h.getAddress() + " "
//                     + h.getCity() + " " 
//                     + h.getCountry() + " "
//                     + h.getContactNumber() + " "
//                     + h.getEmailAddress());
//        

 //       HotelType hotelType = ((HotelType)(hotelTypes.toArray()[0]));
        
        List<Hotel> hotels = FindHotelByType(emfactory, "5S");
        
        for (Hotel h : hotels) {
              Calendar c = Calendar.getInstance();
              c.setTime(h.getConstructionYear());
              
              System.out.println(h.getHotelId() + " "
                      + h.getHotelName() + " "
                      + c.get(Calendar.YEAR) + " "
                      + h.getAddress() + " "
                      + h.getCity() + " " 
                      + h.getCountry() + " "
                      + h.getContactNumber() + " "
                      + h.getEmailAddress());
          }
        
        emfactory.close();
    }
    
}
