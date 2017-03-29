/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Models.Booking;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnit;

/**
 *
 * @author mrkjse
 */
public class BookingController {
     EntityManagerFactory emfactory = null;
    
    @PersistenceUnit(unitName="HotelManagementSystemPUB")
    EntityManager entitymanager = null;
    
    public BookingController(EntityManagerFactory emf)
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
        entitymanager.close();
    }
    
    public List<Booking> getBookings()
    {   
        List<Booking> bookings = null;
        
        try
        {
            entitymanager.getTransaction( ).begin( );

            bookings = entitymanager.createNamedQuery("Booking.findAll").getResultList();

            if (bookings.size() > 0)
            {
                for (Booking g : bookings) {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

                }
            }

            entitymanager.getTransaction( ).commit( );
        }
        catch (Exception e)
        {
            
        }
      
      return bookings;
    }
    
    
    public boolean createBooking(Booking booking)
    {
        try
        {
            entitymanager.getTransaction( ).begin( );
            entitymanager.persist( booking );
            entitymanager.getTransaction( ).commit( );

            getBookings();
            
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }
    
    public boolean deleteBooking(Booking booking)
    {
        try
        {
            entitymanager.getTransaction( ).begin( );

            // Find the Booking first
            Booking dataBooking = entitymanager.find( Booking.class, booking.getBookingId() );

            if (dataBooking != null)
            {
                entitymanager.remove( booking );

                getBookings();
            }
            
            entitymanager.getTransaction( ).commit( );
            
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }
    
    public Booking updateBooking(Booking newBooking)
    {
        Booking updatedBooking = null;
        
        try
        {
            entitymanager.getTransaction( ).begin( );
            
            Booking bookingData = entitymanager.find( Booking.class, newBooking.getBookingId() );

            if (bookingData != null)
            {
                
            }
            
            entitymanager.getTransaction( ).commit( );

            getBookings();

        }
        catch (Exception e)
        {
        }
        
        return updatedBooking;
    }
    
    
    
    public static void main(String args[]) {
         
        
         EntityManagerFactory emfactoryb = Persistence.createEntityManagerFactory( "HotelManagementSystemPUB" );
         BookingController x = new BookingController(emfactoryb);
         
         List<Booking> newBookings = x.getBookings();
         
         x.close();
         
     }

}
