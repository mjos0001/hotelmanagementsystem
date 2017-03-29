/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Models.*;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnit;
import javax.persistence.TemporalType;

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
               bookingData.setBookingRoomGuestCollection(newBooking.getBookingRoomGuestCollection());
               bookingData.setCheckInDate(newBooking.getCheckInDate());
               bookingData.setCheckOutDate(newBooking.getCheckOutDate());
               bookingData.setContactEmail(newBooking.getContactEmail());
               bookingData.setContactPerson(newBooking.getContactPerson());
               bookingData.setCustomerId(newBooking.getCustomerId());
               bookingData.setGuestCollection(newBooking.getGuestCollection());
               bookingData.setPaymentCollection(newBooking.getPaymentCollection());
               bookingData.setPaymentStatusCode(newBooking.getPaymentStatusCode());
               bookingData.setTotalAmount(newBooking.getTotalAmount());
            }
            
            entitymanager.getTransaction( ).commit( );

            getBookings();

        }
        catch (Exception e)
        {
        }
        
        return updatedBooking;
    }
    
    public List<Booking> getBookingsByCustomerId(int customerId)
    {
        List<Booking> bookings =  null;
        try
        {
            entitymanager.getTransaction( ).begin( );

            bookings = entitymanager.createNamedQuery("Booking.findByCustomerId")
                    .setParameter("customerId", customerId).getResultList();

            entitymanager.getTransaction( ).commit( );
        }
        catch (Exception e)
        {
            
        }
        
        return bookings;
    }
    
    public List<BookingRoomGuest> getBookingRoomGuests()
    {
        List<BookingRoomGuest> bookingRoomGuests = null;
        
        try
        {
            entitymanager.getTransaction( ).begin( );

            bookingRoomGuests = entitymanager.createNamedQuery("BookingRoomGuest.findAll")
                                   .getResultList();

            entitymanager.getTransaction( ).commit( );
        }
        catch (Exception e)
        {
            
        }
        
        return bookingRoomGuests;
    }
    
    public List<Booking> findByTakenBookingDates(Date checkInDate, Date checkOutDate)
    {
        List<Booking> bookings = null;
        
        try
        {
           entitymanager.getTransaction( ).begin( );

           // Find bookings that are within the checkInDate and checkOutDate
            bookings = entitymanager.createNamedQuery("Booking.findByTakenDate")
                        .setParameter("checkInDate", checkInDate, TemporalType.DATE)
                        .setParameter("checkOutDate", checkOutDate, TemporalType.DATE)
                        .getResultList();

            entitymanager.getTransaction( ).commit( ); 
        }
        catch (Exception e)
        {
            
        }
        
        return bookings;
    }
    
    
    public static void main(String args[]) {
         
        
         EntityManagerFactory emfactoryb = Persistence.createEntityManagerFactory( "HotelManagementSystemPUB" );
         BookingController x = new BookingController(emfactoryb);
         
         List<Booking> newBookings = x.getBookings();
         
//         int index = 1;
//         for (Guest g : newBookings.get(0).getGuestCollection())
//         {
//             if (index == 1)
//             {
//                g.setFirstName("Horacio");
//                g.setLastName("Warpole");
//             }
//             else
//             {
//                 g.setFirstName("Viola");
//                 g.setLastName("Davis");
//             }
//         }
//         
//         x.updateBooking(newBookings.get(0));
//        
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        try{
            Date date1 = fmt.parse("2017-02-01");
            Date date2 = fmt.parse("2017-07-02");
            
            newBookings = x.findByTakenBookingDates(date1, date2); //with bkg result
            
            date1 = fmt.parse("2017-01-01");
            date2 = fmt.parse("2017-02-01");
            
            newBookings = x.findByTakenBookingDates(date1, date2); //without bkg result
 
        }
        catch (Exception e)
        {
            
        }
        
        
              
        x.close();
         
     }

}
