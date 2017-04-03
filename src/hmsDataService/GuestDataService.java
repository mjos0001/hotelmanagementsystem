/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hmsDataService;

import hmsModel.Guest;
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
public class GuestDataService {
    
    EntityManagerFactory emfactory = null;
    
    @PersistenceUnit(unitName="HotelManagementSystemPUB")
    EntityManager entitymanager = null;
    
    public GuestDataService(EntityManagerFactory emf)
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
        if(entitymanager != null)
        {
            entitymanager.close();
        }
 
    }
    
    public List<Guest> getGuests() throws Exception
    {   
        List<Guest> guests = null;
        
        try
        {
            entitymanager.getTransaction().begin();

            guests = entitymanager.createNamedQuery("Guest.findAll").getResultList();

            if (guests.size() > 0)
            {
                for (Guest g : guests)
                {
                    entitymanager.refresh(g);
                }
            }

            entitymanager.getTransaction().commit();
        }
        catch (Exception e)
        {
            throw new Exception("Error in doing the database operation.");
        }
      
      return guests;
    }
    
    
    public int createGuest(Guest guest) throws Exception
    {
        int id = 0;
        try
        {
            entitymanager.getTransaction().begin();
            entitymanager.persist(guest);
            entitymanager.flush();
            entitymanager.getTransaction().commit();

           id = guest.getGuestId();
            
        }
        catch (Exception e)
        {
            throw new Exception("Error in doing the database operation.");
        }
        
        return id;
    }
    
    public boolean deleteGuest(Guest guest) throws Exception
    {
        try
        {
            entitymanager.getTransaction().begin();

            // Find the Guest first
            Guest dataGuest = entitymanager.find(Guest.class, guest.getGuestId());

            if (dataGuest != null)
            {
                entitymanager.remove(guest);

                getGuests();
            }
            
            entitymanager.getTransaction().commit();
            
            return true;
        }
        catch (Exception e)
        {
            throw new Exception("Error in doing the database operation.");
        }
    }
    
    public Guest updateGuest(Guest newGuest) throws Exception
    {
        Guest updatedGuest = null;
        
        try
        {
            entitymanager.getTransaction().begin();
            
            Guest guestData = entitymanager.find(Guest.class, newGuest.getGuestId());

            if (guestData != null)
            {
                // Check if it exists
                guestData.setBooking(newGuest.getBooking());
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
            else
            {
                throw new Exception("The data being updated does not exist.");
            }
            
            entitymanager.getTransaction().commit();

            getGuests();

        }
        catch (Exception e)
        {
            throw new Exception("Error in doing the database operation.");
        }
        
        return updatedGuest;
    }
    
    public List<Guest> getGuestByName(String firstName, String lastName) throws Exception
    {
        List<Guest> guests = null;
        
        try
        {
            entitymanager.getTransaction().begin();

            guests = entitymanager.createNamedQuery("Guest.findByGuestTypeCode")
                    .setParameter("firstName", firstName)
                    .setParameter("lastName", lastName)
                    .getResultList();

            entitymanager.getTransaction().commit();
        }
        catch (Exception e)
        {
            throw new Exception("Error in doing the database operation.");
        }
        
        return guests;
    }
    
    public Guest getGuestById(int guestId) throws Exception
    {
        Guest guest = null;
        
        try
        {
            entitymanager.getTransaction().begin();

            guest = entitymanager.find(Guest.class, guestId);

            entitymanager.getTransaction().commit();
        }
        catch (Exception e)
        {
            throw new Exception("Error in doing the database operation.");
        }
        
        return guest;
    }
    
    public static void main(String args[]) {
         
        try{
        
            EntityManagerFactory emfactoryb = Persistence.createEntityManagerFactory("HotelManagementSystemPUB");
            GuestDataService x = new GuestDataService(emfactoryb);

            List<Guest> newGuests = x.getGuests();

            x.close();
        }
        catch (Exception e)
        {
        
        }
         
     }

   
}
