/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Models.Customer;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author mrkjse
 */
public class CustomerController {
    
    EntityManagerFactory emfactory = null;
    
    public CustomerController(EntityManagerFactory emf)
    {
        if (!emf.isOpen())
        {
            // Should throw an error
            emfactory = null;
        }
        emfactory = emf;
    }
    
    public List<Customer> getCustomers()
    {   
        List<Customer> rooms = null;
        
        try
        {
        
            EntityManager entitymanager = emfactory.createEntityManager( );
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
            entitymanager.close( );
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
            EntityManager entitymanager = emfactory.createEntityManager( );
            entitymanager.getTransaction( ).begin( );

            entitymanager.persist( room );
            entitymanager.getTransaction( ).commit( );

            getCustomers();

            entitymanager.close( );
            
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
            EntityManager entitymanager = emfactory.createEntityManager( );
            entitymanager.getTransaction( ).begin( );

            // Find the Customer first
            Customer dataCustomer = entitymanager.find( Customer.class, room.getCustomerId());

            if (dataCustomer != null)
            {
                entitymanager.remove( room );
                entitymanager.getTransaction( ).commit( );

                getCustomers();
            }

            entitymanager.close( );
            
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }
    
    public Customer updateCustomer(Customer newCustomer)
    {
        Customer updatedCustomer = null;
        
        try
        {
            EntityManager entitymanager = emfactory.createEntityManager( );
            entitymanager.getTransaction( ).begin( );
            
            Customer oldCustomer = entitymanager.find( Customer.class, newCustomer.getCustomerId());

            // Check if it exists
           
            updatedCustomer = oldCustomer;
            
            entitymanager.getTransaction( ).commit( );

            getCustomers();

            entitymanager.close( );
        }
        catch (Exception e)
        {
        }
        
        return updatedCustomer;
    }
    
    public List<Customer> findCustomerByMembership(String membershipTierCode)
    {
        List<Customer> rooms =  null;
        try
        {
            EntityManager entitymanager = emfactory.createEntityManager( );
            entitymanager.getTransaction( ).begin( );

            rooms = entitymanager.createNamedQuery("Customer.findByMembershipTierCode")
                    .setParameter("membershipTierCode", membershipTierCode).getResultList();

            entitymanager.getTransaction( ).commit( );
            entitymanager.close( );
        }
        catch (Exception e)
        {
            
        }
        
        return rooms;
    }
    
    public List<Customer> findCustomerByType(String CustomerTypeCode)
    {
        List<Customer> rooms = null;
        
        try
        {
            EntityManager entitymanager = emfactory.createEntityManager( );
            entitymanager.getTransaction( ).begin( );

            rooms = entitymanager.createNamedQuery("Customer.findByCustomerTypeCode")
                    .setParameter("CustomerTypeCode", CustomerTypeCode).getResultList();

            entitymanager.getTransaction( ).commit( );
            entitymanager.close( );
        }
        catch (Exception e)
        {
            
        }
        
        return rooms;
    }
    
     public List<Customer> findCustomerByTierCredits(int availableCredits )
    {
        List<Customer> rooms = null;
        
        try
        {
            EntityManager entitymanager = emfactory.createEntityManager( );
            entitymanager.getTransaction( ).begin( );

            rooms = entitymanager.createNamedQuery("Customer.findByTierCredits")
                    .setParameter("tierCredits", availableCredits).getResultList();

            entitymanager.getTransaction( ).commit( );
            entitymanager.close( );
        }
        catch (Exception e)
        {
            
        }
        
        return rooms;
    }
}
