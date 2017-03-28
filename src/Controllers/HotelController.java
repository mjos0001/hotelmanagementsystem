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
        List<Hotel> hotels = null;
        
        try
        {
        
            EntityManager entitymanager = emfactory.createEntityManager( );
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
            entitymanager.close( );
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
    
    public Hotel updateHotel(Hotel newHotel)
    {
        Hotel updatedHotel = null;
        
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

            updatedHotel = oldHotel;
            
            entitymanager.getTransaction( ).commit( );

            getHotels();

            entitymanager.close( );
        }
        catch (Exception e)
        {
        }
        
        return updatedHotel;
    }
    
    public Hotel findHotelByName(String hotelName)
    {
        Hotel hotel =  null;
        try
        {
            EntityManager entitymanager = emfactory.createEntityManager( );
            entitymanager.getTransaction( ).begin( );

            hotel = (Hotel)entitymanager.createNamedQuery("Hotel.findByHotelName")
                    .setParameter("hotelName", hotelName).getSingleResult();

            entitymanager.getTransaction( ).commit( );
            entitymanager.close( );
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
            EntityManager entitymanager = emfactory.createEntityManager( );
            entitymanager.getTransaction( ).begin( );

            hotels = entitymanager.createNamedQuery("Hotel.findByHotelTypeCode")
                    .setParameter("hotelTypeCode", hotelTypeCode).getResultList();

            entitymanager.getTransaction( ).commit( );
            entitymanager.close( );
        }
        catch (Exception e)
        {
            
        }
        
        return hotels;
    }
    
}
