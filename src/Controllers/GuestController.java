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


/**
 *
 * @author mrkjse
 */
public class GuestController {
     EntityManagerFactory emfactory = null;
    
    public GuestController(EntityManagerFactory emf)
    {
        if (!emf.isOpen())
        {
            // Should throw an error
            emfactory = null;
        }
        emfactory = emf;
    }
    
    public List<Guest> getGuests()
    {   
        List<Guest> guests = null;
        
        try
        {
        
            EntityManager entitymanager = emfactory.createEntityManager( );
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
            entitymanager.close( );
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
            EntityManager entitymanager = emfactory.createEntityManager( );
            entitymanager.getTransaction( ).begin( );

            entitymanager.persist( guest );
            entitymanager.getTransaction( ).commit( );

            getGuests();

            entitymanager.close( );
            
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
            EntityManager entitymanager = emfactory.createEntityManager( );
            entitymanager.getTransaction( ).begin( );

            // Find the Guest first
            Guest dataGuest = entitymanager.find( Guest.class, guest.getGuestId() );

            if (dataGuest != null)
            {
                entitymanager.remove( guest );
                entitymanager.getTransaction( ).commit( );

                getGuests();
            }

            entitymanager.close( );
            
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
            EntityManager entitymanager = emfactory.createEntityManager( );
            entitymanager.getTransaction( ).begin( );
            
            Guest oldGuest = entitymanager.find( Guest.class, newGuest.getGuestId() );

            // Check if it exists
           
            updatedGuest = oldGuest;
            
            entitymanager.getTransaction( ).commit( );

            getGuests();

            entitymanager.close( );
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
            EntityManager entitymanager = emfactory.createEntityManager( );
            entitymanager.getTransaction( ).begin( );

            guests = entitymanager.createNamedQuery("Guest.findByGuestTypeCode")
                    .setParameter("firstName", firstName)
                    .setParameter("lastName", lastName)
                    .getResultList();

            entitymanager.getTransaction( ).commit( );
            entitymanager.close( );
        }
        catch (Exception e)
        {
            
        }
        
        return guests;
    }
   
}
