/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Models.Customer;
import Models.Membership;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnit;

/**
 *
 * @author mrkjse
 */
public class CustomerController {
    
    EntityManagerFactory emfactory = null;
    
    @PersistenceUnit(unitName="HotelManagementSystemPUB")
    EntityManager entitymanager = null;
    
    public CustomerController(EntityManagerFactory emf)
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
        if(entitymanager != null)
        {
            entitymanager.close();
        }
    }
    
    public List<Customer> getCustomers()
    {   
        List<Customer> rooms = null;
        
        try
        {
            entitymanager.getTransaction( ).begin( );

            rooms = entitymanager.createNamedQuery("Customer.findAll").getResultList();

            if (rooms.size() > 0)
            {
                for (Customer g : rooms) 
                {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

                    System.out.println("");                
                }
            }

            entitymanager.getTransaction( ).commit( );
        }
        catch (Exception e)
        {
            
        }
      
      return rooms;
    }
    
    
    public boolean createCustomer(Customer room)
    {
        try
        {
            entitymanager.getTransaction( ).begin( );

            entitymanager.persist( room );
            entitymanager.getTransaction( ).commit( );

            getCustomers();
            
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }
    
    public boolean deleteCustomer(Customer room)
    {
        try
        {
            entitymanager.getTransaction( ).begin( );

            // Find the Customer first
            Customer dataCustomer = entitymanager.find( Customer.class, room.getCustomerId());

            if (dataCustomer != null)
            {
                entitymanager.remove( room );

                getCustomers();
            }
            
            entitymanager.getTransaction( ).commit( );

            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }
    
    public Customer updateCustomer(Customer newCustomer)
    {
        Customer customerData = null;
        
        try
        {
            entitymanager.getTransaction( ).begin( );
            
            customerData = entitymanager.find( Customer.class, newCustomer.getCustomerId());

            // Check if it exists
            if (customerData != null)
            {
                customerData.setCity(newCustomer.getCity());
                customerData.setCountry(newCustomer.getCountry());
                customerData.setDob(newCustomer.getDob());
                customerData.setEmailAddress(newCustomer.getEmailAddress());
                customerData.setFirstName(newCustomer.getFirstName());
                customerData.setLastName(newCustomer.getLastName());
                customerData.setMembershipCredits(newCustomer.getMembershipCredits());
                customerData.setMembershipTierCode(newCustomer.getMembershipTierCode());
                customerData.setPhoneNumber(newCustomer.getPhoneNumber());
                customerData.setPostalCode(newCustomer.getPostalCode());
                customerData.setStreet(newCustomer.getStreet());
                customerData.setTitle(newCustomer.getTitle());
            }
            else
            {
                // throw an exception - the record does not exist!
            }
            
            
            entitymanager.getTransaction( ).commit( );

            getCustomers();
            
        }
        catch (Exception e)
        {
            // Error in updating the record!
        }
        
        return customerData;
    }
    
    public List<Customer> findCustomerByMembership(String membershipTierCode)
    {
        List<Customer> customers =  null;
        try
        {
            entitymanager.getTransaction( ).begin( );

            customers = entitymanager.createNamedQuery("Customer.findByMembershipTierCode")
                    .setParameter("membershipTierCode", membershipTierCode).getResultList();

            entitymanager.getTransaction( ).commit( );
        }
        catch (Exception e)
        {
            
        }
        
        return customers;
    }
    
    public Customer findCustomerByBookingId(int bookingId)
    {
        Customer customer =  null;
        try
        {
            entitymanager.getTransaction( ).begin( );

            customer = (Customer)entitymanager.createNamedQuery("Customer.findByBookingId")
                    .setParameter("bookingId", bookingId).getSingleResult();

            entitymanager.getTransaction( ).commit( );
        }
        catch (Exception e)
        {
            
        }
        
        return customer;
    }
    
    public Customer findCustomerById(int customerId)
    {
        Customer customer =  null;
        try
        {
            entitymanager.getTransaction( ).begin( );

            customer = (Customer)entitymanager.createNamedQuery("Customer.findByCustomerId")
                    .setParameter("customerId", customerId).getSingleResult();

            entitymanager.getTransaction( ).commit( );
        }
        catch (Exception e)
        {
            
        }
        
        return customer;
    }
    
    public static void main(String args[]) {
         
         EntityManagerFactory emfactoryb = Persistence.createEntityManagerFactory( "HotelManagementSystemPUB" );
         CustomerController x = new CustomerController(emfactoryb);
         
         List<Customer> newCustomers = x.getCustomers();
         
         newCustomers = x.findCustomerByMembership("BRZ");
         
         x.close();
         
    }
   
}
