package hmsDataService;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import hmsModel.Hotel;
import java.util.*;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.*;
import java.text.SimpleDateFormat;

/**
 *
 * @author mrkjse
 */
public class HotelDataService {
    
    EntityManagerFactory emfactory = null;
    
    @PersistenceUnit(unitName="HotelManagementSystemPUA")
    EntityManager entitymanager = null;
    
    public HotelDataService(EntityManagerFactory emf) 
    {
        if (!emf.isOpen())
        {
            // Should throw an error
            emfactory = null;
        }
        emfactory = emf;
        entitymanager = emfactory.createEntityManager();
    
    }
    
    public void close()
    {
        if (entitymanager != null)
        {
            entitymanager.close();
        }
        
    }
    
    public List<Hotel> getHotels() throws Exception
    {   
        List<Hotel> hotels = null;
        
        try
        {
            entitymanager.getTransaction().begin();

            hotels = entitymanager.createNamedQuery("Hotel.findAll").getResultList();
            
            for (Hotel h : hotels)
            {
                entitymanager.refresh(h);
            }

            entitymanager.getTransaction().commit();
        }
        catch (Exception e)
        {
            throw new Exception("Error in doing the database operation.");
        }
      
      return hotels;
    }
    
    
    public boolean createHotel(Hotel hotel) throws Exception
    {
        try
        {
            //EntityManager entitymanager = emfactory.createEntityManager();
            entitymanager.getTransaction().begin();
            entitymanager.persist(hotel);
            entitymanager.getTransaction().commit();

            return true;
        }
        catch (Exception e)
        {
            throw new Exception("Error in doing the database operation.");
        }
    }
    
    public boolean deleteHotel(Hotel hotel) throws Exception
    {
        try
        {
            //EntityManager entitymanager = emfactory.createEntityManager();
            entitymanager.getTransaction().begin();

            // Find the hotel first
            Hotel dataHotel = entitymanager.find(Hotel.class, hotel.getHotelId());

            if (dataHotel != null)
            {
                entitymanager.remove(hotel);
                entitymanager.getTransaction().commit();

                getHotels();
            }
            
            return true;
        }
        catch (Exception e)
        {
            throw new Exception("Error in doing the database operation.");
        }
    }
    
    public Hotel updateHotel(Hotel newHotel) throws Exception
    {
        Hotel hotelData = null;
        
        try
        {
            //EntityManager entitymanager = emfactory.createEntityManager();
            entitymanager.getTransaction().begin();
            
            hotelData = entitymanager.find(Hotel.class, newHotel.getHotelId());

            // Check if it exists
            
            hotelData.setHotelName(newHotel.getHotelName());
            hotelData.setConstructionYear(newHotel.getConstructionYear());
            hotelData.setCountry(newHotel.getCountry());
            hotelData.setCity(newHotel.getCity());
            hotelData.setAddress(newHotel.getAddress());
            hotelData.setContactNumber(newHotel.getContactNumber());
            hotelData.setEmailAddress(newHotel.getEmailAddress());
            hotelData.setHotelTypeCode(newHotel.getHotelTypeCode());
            
            entitymanager.getTransaction().commit();

        }
        catch (Exception e)
        {
            throw new Exception("Error in doing the database operation.");
        }
        
        return hotelData;
    }
    
    public Hotel findHotelByName(String hotelName) throws Exception
    {
        Hotel hotel =  null;
        try
        {
            entitymanager.getTransaction().begin();

            hotel = (Hotel)entitymanager.createNamedQuery("Hotel.findByHotelName")
                    .setParameter("hotelName", hotelName).getSingleResult();

            entitymanager.getTransaction().commit();
        }
        catch (Exception e)
        {
            throw new Exception("Error in doing the database operation.");
        }
        
        return hotel;
    }
    
    public List<Hotel> findHotelByType(String hotelTypeCode) throws Exception
    {
        List<Hotel> hotels = null;
        
        try
        {
            entitymanager.getTransaction().begin();

            hotels = entitymanager.createNamedQuery("Hotel.findByHotelTypeCode")
                    .setParameter("hotelTypeCode", hotelTypeCode).getResultList();

            entitymanager.getTransaction().commit();
        }
        catch (Exception e)
        {
            throw new Exception("Error in doing the database operation.");
        }
        
        return hotels;
    }
    
     public static void main(String args[]) {
         
         EntityManagerFactory emfactorya = Persistence.createEntityManagerFactory("HotelManagementSystemPUA");
         HotelDataService x = new HotelDataService(emfactorya);
                 
        try
        {
                     
            List<Hotel> hList = x.getHotels();
            SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
            Date date1 = fmt.parse("2006-02-01");

            Hotel newHotel = new Hotel(0, "El Paseo Hotel", date1, "Philippines", "Makati", "5 Minami-ku Yokohama", "33333", "talktous@elpaseoh.com", "5S");
            x.createHotel(newHotel);
            
            Hotel findHotel = x.findHotelByName("Honmaru Hotel");
         
            List<Hotel> findHotels = x.findHotelByType("5S");
         
            x.close();
 
        }
        catch (Exception e)
        {
            
        }
         
     }
    
}
