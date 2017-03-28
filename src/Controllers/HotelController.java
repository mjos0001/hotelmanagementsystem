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
import javax.persistence.Persistence;

/**
 *
 * @author mrkjse
 */
public class HotelController {
    
    EntityManagerFactory emfactory = null;
    
    public HotelController(EntityManagerFactory emf)
    {
        if (!emf.isOpen())
        {
            // Should throw an error
            emfactory = Persistence.createEntityManagerFactory( "HotelManagementSystemPUA" );
        }
        emfactory = emf;
    }
    
    public List<Hotel> getHotels()
    {
        
      EntityManager entitymanager = emfactory.createEntityManager( );
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
    
    
    public boolean createHotel(Hotel hotel)
    {
        try
        {
            EntityManager entitymanager = emfactory.createEntityManager( );
            entitymanager.getTransaction( ).begin( );

            entitymanager.persist( hotel );
            entitymanager.getTransaction( ).commit( );

            getHotels();

            entitymanager.close( );
            
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
            EntityManager entitymanager = emfactory.createEntityManager( );
            entitymanager.getTransaction( ).begin( );

            // Find the hotel first
            Hotel dataHotel = entitymanager.find( Hotel.class, hotel.getHotelId() );

            if (dataHotel != null)
            {
                entitymanager.remove( hotel );
                entitymanager.getTransaction( ).commit( );

                getHotels();
            }

            entitymanager.close( );
            
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }
    
    public boolean updateHotel(Hotel newHotel)
    {
        try
        {
            EntityManager entitymanager = emfactory.createEntityManager( );
            entitymanager.getTransaction( ).begin( );
            
            Hotel oldHotel = entitymanager.find( Hotel.class, newHotel.getHotelId() );

            // Check if it exists
            
            oldHotel.setHotelName(newHotel.getHotelName());
            oldHotel.setConstructionYear(newHotel.getConstructionYear());
            oldHotel.setCountry(newHotel.getCountry());
            oldHotel.setCity(newHotel.getCity());
            oldHotel.setAddress(newHotel.getAddress());
            oldHotel.setContactNumber(newHotel.getContactNumber());
            oldHotel.setEmailAddress(newHotel.getEmailAddress());
            oldHotel.setHotelTypeCode(newHotel.getHotelTypeCode());
            entitymanager.getTransaction( ).commit( );

            entitymanager.getTransaction( ).commit( );

            getHotels();

            entitymanager.close( );
            
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }
    
    public Hotel findHotelByName(String hotelName)
    {
        EntityManager entitymanager = emfactory.createEntityManager( );
        entitymanager.getTransaction( ).begin( );
      
        Hotel hotel = (Hotel)entitymanager.createNamedQuery("Hotel.findByHotelName")
                .setParameter("hotelName", hotelName).getSingleResult();
      
        entitymanager.getTransaction( ).commit( );
        entitymanager.close( );
        
        return hotel;
    }
    
    public List<Hotel> findHotelByType(String hotelTypeCode)
    {
        EntityManager entitymanager = emfactory.createEntityManager( );
        entitymanager.getTransaction( ).begin( );
      
        List<Hotel> hotels = entitymanager.createNamedQuery("Hotel.findByHotelTypeCode")
                .setParameter("hotelTypeCode", hotelTypeCode).getResultList();
      
        entitymanager.getTransaction( ).commit( );
        entitymanager.close( );
        
        return hotels;
    }
    
}
