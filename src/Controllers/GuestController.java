/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Models.Guest;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.text.SimpleDateFormat;
import javax.persistence.PersistenceUnit;


/**
 *
 * @author mrkjse
 */
public class GuestController {
    
    EntityManagerFactory emfactory = null;
    
    @PersistenceUnit(unitName="HotelManagementSystemPUB")
    EntityManager entitymanager = null;
    
    public GuestController(EntityManagerFactory emf)
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
    
    public List<Guest> getGuests()
    {   
        List<Guest> guests = null;
        
        try
        {
            entitymanager.getTransaction( ).begin( );

            guests = entitymanager.createNamedQuery("Guest.findAll").getResultList();

            if (guests.size() > 0)
            {
                for (Guest g : guests) {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

                    System.out.println(g.getBookingId() + " "
                                       + g.getGuestId() + " " + g.getTitle() + " " 
                            + g.getFirstName() + " " + g.getLastName() + " " 
                            + dateFormat.format(g.getDob()) + " "  + g.getCountry() + " " + g.getCity() + " " + g.getStreet() + " " 
                            + g.getPostalCode() + " " + g.getPhoneNumber() + " " + g.getEmailAddress());                   
                }
            }

            entitymanager.getTransaction( ).commit( );
        }
        catch (Exception e)
        {
            
        }
      
      return guests;
    }
    
    
    public boolean createGuest(Guest guest)
    {
        try
        {
            entitymanager.getTransaction( ).begin( );
            entitymanager.persist( guest );
            entitymanager.getTransaction( ).commit( );

            getGuests();
            
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }
    
    public boolean deleteGuest(Guest guest)
    {
        try
        {
            entitymanager.getTransaction( ).begin( );

            // Find the Guest first
            Guest dataGuest = entitymanager.find( Guest.class, guest.getGuestId() );

            if (dataGuest != null)
            {
                entitymanager.remove( guest );

                getGuests();
            }
            
            entitymanager.getTransaction( ).commit( );
            
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }
    
    public Guest updateGuest(Guest newGuest)
    {
        Guest updatedGuest = null;
        
        try
        {
            entitymanager.getTransaction( ).begin( );
            
            Guest guestData = entitymanager.find( Guest.class, newGuest.getGuestId() );

            if (guestData != null)
            {
                // Check if it exists
                guestData.setBookingId(newGuest.getBookingId());
                guestData.setCity(newGuest.getCity());
                guestData.setCountry(newGuest.getCountry());
                guestData.setDob(newGuest.getDob());
                guestData.setEmailAddress(newGuest.getEmailAddress());
                guestData.setFirstName(newGuest.getFirstName());
                guestData.setLastName(newGuest.getLastName());
                guestData.setPhoneNumber(newGuest.getPhoneNumber());
                guestData.setPostalCode(newGuest.getPostalCode());
                guestData.setStreet(newGuest.getStreet());
                guestData.setTitle(newGuest.getTitle());
            }
            
            entitymanager.getTransaction( ).commit( );

            getGuests();

        }
        catch (Exception e)
        {
        }
        
        return updatedGuest;
    }
    
    public List<Guest> findGuestByName(String firstName, String lastName)
    {
        List<Guest> guests = null;
        
        try
        {
            entitymanager.getTransaction( ).begin( );

            guests = entitymanager.createNamedQuery("Guest.findByGuestTypeCode")
                    .setParameter("firstName", firstName)
                    .setParameter("lastName", lastName)
                    .getResultList();

            entitymanager.getTransaction( ).commit( );
        }
        catch (Exception e)
        {
            
        }
        
        return guests;
    }
    
    public static void main(String args[]) {
         
        
         EntityManagerFactory emfactoryb = Persistence.createEntityManagerFactory( "HotelManagementSystemPUB" );
         GuestController x = new GuestController(emfactoryb);
         
         List<Guest> newGuests = x.getGuests();
         
         x.close();
         
     }

   
}
