package Controllers;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.*;
import Models.*;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.*;

/**
 *
 * @author mrkjse
 */
public class HotelController {
    
    EntityManagerFactory emfactory = null;
    
    @PersistenceUnit(unitName="HotelManagementSystemPUA")
    EntityManager entitymanager = null;
    
    public HotelController(EntityManagerFactory emf)
    {
        if (!emf.isOpen())
        {
            // Should throw an error
            emfactory = null;
        }
        emfactory = emf;
        entitymanager = emfactory.createEntityManager( );
    
    }
    
    public void close()
    {
        if (entitymanager != null)
        {
            entitymanager.close();
        }
        
    }
    
    public List<Hotel> getHotels()
    {   
        List<Hotel> hotels = null;
        
        try
        {
            entitymanager.getTransaction( ).begin( );

            hotels = entitymanager.createNamedQuery("Hotel.findAll").getResultList();

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
        }
        catch (Exception e)
        {
            
        }
      
      return hotels;
    }
    
    
    public boolean createHotel(Hotel hotel)
    {
        try
        {
            //EntityManager entitymanager = emfactory.createEntityManager( );
            entitymanager.getTransaction( ).begin( );
            entitymanager.persist( hotel );
            entitymanager.getTransaction( ).commit( );

            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }
    
    public boolean deleteHotel(Hotel hotel)
    {
        try
        {
            //EntityManager entitymanager = emfactory.createEntityManager( );
            entitymanager.getTransaction( ).begin( );

            // Find the hotel first
            Hotel dataHotel = entitymanager.find( Hotel.class, hotel.getHotelId() );

            if (dataHotel != null)
            {
                entitymanager.remove( hotel );
                entitymanager.getTransaction( ).commit( );

                getHotels();
            }
            
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }
    
    public Hotel updateHotel(Hotel newHotel)
    {
        Hotel hotelData = null;
        
        try
        {
            //EntityManager entitymanager = emfactory.createEntityManager( );
            entitymanager.getTransaction( ).begin( );
            
            hotelData = entitymanager.find( Hotel.class, newHotel.getHotelId() );

            // Check if it exists
            
            hotelData.setHotelName(newHotel.getHotelName());
            hotelData.setConstructionYear(newHotel.getConstructionYear());
            hotelData.setCountry(newHotel.getCountry());
            hotelData.setCity(newHotel.getCity());
            hotelData.setAddress(newHotel.getAddress());
            hotelData.setContactNumber(newHotel.getContactNumber());
            hotelData.setEmailAddress(newHotel.getEmailAddress());
            hotelData.setHotelTypeCode(newHotel.getHotelTypeCode());
            
            entitymanager.getTransaction( ).commit( );

            getHotels();

        }
        catch (Exception e)
        {
        }
        
        return hotelData;
    }
    
    public Hotel findHotelByName(String hotelName)
    {
        Hotel hotel =  null;
        try
        {
            entitymanager.getTransaction( ).begin( );

            hotel = (Hotel)entitymanager.createNamedQuery("Hotel.findByHotelName")
                    .setParameter("hotelName", hotelName).getSingleResult();

            entitymanager.getTransaction( ).commit( );
        }
        catch (Exception e)
        {
            
        }
        
        return hotel;
    }
    
    public List<Hotel> findHotelByType(String hotelTypeCode)
    {
        List<Hotel> hotels = null;
        
        try
        {
            entitymanager.getTransaction( ).begin( );

            hotels = entitymanager.createNamedQuery("Hotel.findByHotelTypeCode")
                    .setParameter("hotelTypeCode", hotelTypeCode).getResultList();

            entitymanager.getTransaction( ).commit( );
        }
        catch (Exception e)
        {
            
        }
        
        return hotels;
    }
    
     public static void main(String args[]) {
         
         EntityManagerFactory emfactorya = Persistence.createEntityManagerFactory( "HotelManagementSystemPUA" );
         HotelController x = new HotelController(emfactorya);
         
         List<Hotel> hList = x.getHotels();
         
         Hotel newHotel = new Hotel(10, "Maruku Hotel", new Date(2010,5,16), "Japan", "Yokohama", "5 Minami-ku Yokohama", "33333", "talktous@marukuh.com", "5S");
         
         x.createHotel(newHotel);
         
         newHotel.setHotelName("Maruku Yokohama Hotel");
         
         x.updateHotel(newHotel);
         
         Hotel findHotel = x.findHotelByName("Maruku Yokohama Hotel");
         
         List<Hotel> findHotels = x.findHotelByType("5S");
         
         x.close();
     }
    
}
