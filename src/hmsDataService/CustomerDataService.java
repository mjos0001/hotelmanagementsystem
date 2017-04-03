/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hmsDataService;

import hmsModel.Customer;
import hmsModel.Membership;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnit;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;

/**
 *
 * @author mrkjse
 */
public class CustomerDataService {
    
    EntityManagerFactory emfactory = null;
    
    @PersistenceUnit(unitName="HotelManagementSystemPUB")
    EntityManager entitymanager = null;
    
    public CustomerDataService(EntityManagerFactory emf)
    {
         if (!emf.isOpen())
        {
            // Should throw an error
            emfactory = null;
        }
        emfactory = emf;
    }
    
    public void open()
    {
        entitymanager = emfactory.createEntityManager();
    }
    
    public void close()
    {
        if(entitymanager != null)
        {
            entitymanager.close();
        }
    }
    
    public List<Customer> getCustomers () throws Exception
    {   
        List<Customer> customers = null;
        open();
        
        try
        {
            entitymanager.getTransaction().begin();
            customers = entitymanager.createNamedQuery("Customer.findAll").getResultList();
            entitymanager.getTransaction().commit();
            
            for (Customer c : customers)
            {
                entitymanager.refresh(c);
            }
        }
        catch (Exception e)
        {
            throw new Exception("Error in doing the database operation.");
        }
        
        close();
      
      return customers;
    }
    
    
    public boolean createCustomer(Customer customer) throws Exception
    {
        open();
        
        try
        {
            entitymanager.getTransaction().begin();
            entitymanager.persist(customer);
            entitymanager.getTransaction().commit();
            
            close();
            
            return true;
        }
        catch (Exception e)
        {
            throw new Exception("Error in doing the database operation.");
        }
        
        
    }
    
    public boolean deleteCustomer(Customer room) throws Exception
    {
        open();
        try
        {
            entitymanager.getTransaction().begin();

            // Find the Customer first
            Customer dataCustomer = entitymanager.find(Customer.class, room.getCustomerId());

            if (dataCustomer != null)
            {
                entitymanager.remove(room);
            }
            else
            {
                // throw an exception - customer does not exist!
                throw new Exception("Error in doing the database operation.");
            }
            
            
            entitymanager.getTransaction().commit();

            close();
            return true;
        }
        catch (Exception e)
        {
            close();
            return false;
        }
    }
    
    public Customer updateCustomer(Customer newCustomer) throws Exception
    {
        Customer customerData = null;
        open();
        try
        {
            entitymanager.getTransaction().begin();
            
            customerData = entitymanager.find(Customer.class, newCustomer.getCustomerId());

            // Check if it exists
            if (customerData != null)
            {
                customerData.setCity(newCustomer.getCity());
                customerData.setCountry(newCustomer.getCountry());
                customerData.setDob(newCustomer.getDob());
                //customerData.setEmailAddress(newCustomer.getEmailAddress());
                customerData.setFirstName(newCustomer.getFirstName());
                customerData.setLastName(newCustomer.getLastName());
                customerData.setMembershipCredits(newCustomer.getMembershipCredits());
                customerData.setMembership(newCustomer.getMembership());
                customerData.setPhoneNumber(newCustomer.getPhoneNumber());
                customerData.setPostalCode(newCustomer.getPostalCode());
                customerData.setStreet(newCustomer.getStreet());
                customerData.setTitle(newCustomer.getTitle());
                
                StoredProcedureQuery storedProcedure = entitymanager.createStoredProcedureQuery("updateEmail");
                storedProcedure.registerStoredProcedureParameter("p_customerid", Integer.class, ParameterMode.IN);
                storedProcedure.registerStoredProcedureParameter("p_email", String.class, ParameterMode.IN);
                storedProcedure.setParameter("p_customerid", newCustomer.getCustomerId());
                storedProcedure.setParameter("p_email", newCustomer.getEmailAddress());
                storedProcedure.execute();

            }
            else
            {
                // throw an exception - the record does not exist!
                throw new Exception("The record you are trying to update does not exist.");
            }
            
            entitymanager.getTransaction().commit();
            
        }
        catch (Exception e)
        {
            // Error in updating the record!
            throw new Exception("Error in doing the database operation.");
        }
        close();
        return customerData;
    }
    
    public List<Customer> findCustomerByMembership(String membershipTierCode) throws Exception
    {
        List<Customer> customers =  null;
        open();
        
        try
        {
            entitymanager.getTransaction().begin();

            customers = entitymanager.createNamedQuery("Customer.findByMembershipTierCode")
                    .setParameter("membershipTierCode", membershipTierCode).getResultList();

            entitymanager.getTransaction().commit();
        }
        catch (Exception e)
        {
            throw new Exception("Error in doing the database operation.");
        }
        
        close();
        return customers;
    }
    
    public Customer findCustomerByBookingId(int bookingId) throws Exception
    {
        Customer customer =  null;
        open();
        try
        {
            entitymanager.getTransaction().begin();

            customer = (Customer)entitymanager.createNamedQuery("Customer.findByBookingId")
                    .setParameter("bookingId", bookingId).getSingleResult();

            entitymanager.getTransaction().commit();
        }
        catch (Exception e)
        {
            // Error in finding customer
            throw new Exception("Error in doing the database operation.");
        }
        close();
        return customer;
    }
    
    public Customer findCustomerById(int customerId) throws Exception
    {
        Customer customer =  null;
        open();
        try
        {
            entitymanager.getTransaction().begin();

            customer = entitymanager.find(Customer.class, customerId);

            entitymanager.getTransaction().commit();
        }
        catch (Exception e)
        {
            throw new Exception("Error in doing the database operation.");
        }
        close();
        return customer;
    }
    
    public static void main(String args[]) {
         
        try
        {
            EntityManagerFactory emfactoryb = Persistence.createEntityManagerFactory("HotelManagementSystemPUB");
            CustomerDataService x = new CustomerDataService(emfactoryb);

            List<Customer> newCustomers = x.getCustomers();

            newCustomers = x.findCustomerByMembership("SLR");

            Customer c = newCustomers.get(0);

            c.setEmailAddress("beyonce@beyonce.com");
            x.updateCustomer(c);
            

            newCustomers = x.getCustomers();
            
            x.close();
        }
        catch(Exception e)
        {
            
        }
         
    }
   
}
