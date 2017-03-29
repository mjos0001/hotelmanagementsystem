/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Models.Payment;
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
public class PaymentController {
     EntityManagerFactory emfactory = null;
    
    @PersistenceUnit(unitName="HotelManagementSystemPUB")
    EntityManager entitymanager = null;
    
    public PaymentController(EntityManagerFactory emf)
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
    
    public List<Payment> getPayments()
    {   
        List<Payment> guests = null;
        
        try
        {
            entitymanager.getTransaction( ).begin( );

            guests = entitymanager.createNamedQuery("Payment.findAll").getResultList();

            if (guests.size() > 0)
            {
                for (Payment g : guests) {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");                   
                }
            }

            entitymanager.getTransaction( ).commit( );
        }
        catch (Exception e)
        {
            
        }
      
      return guests;
    }
    
    
    public boolean createPayment(Payment guest)
    {
        try
        {
            entitymanager.getTransaction( ).begin( );
            entitymanager.persist( guest );
            entitymanager.getTransaction( ).commit( );

            getPayments();
            
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }
    
    public boolean deletePayment(Payment guest)
    {
        try
        {
            entitymanager.getTransaction( ).begin( );

            // Find the Payment first
            Payment dataPayment = entitymanager.find( Payment.class, guest.getPaymentPK() );

            if (dataPayment != null)
            {
                entitymanager.remove( guest );

                getPayments();
            }
            
            entitymanager.getTransaction( ).commit( );
            
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }
    
    public Payment updatePayment(Payment newPayment)
    {
        Payment paymentData = null;
        
        try
        {
            entitymanager.getTransaction( ).begin( );
            
            paymentData = entitymanager.find( Payment.class, newPayment.getPaymentPK() );

            if (paymentData != null)
            {
                paymentData.setPaymentAmount(newPayment.getPaymentAmount());
                paymentData.setPaymentDate(newPayment.getPaymentDate());
                paymentData.setPaymentMethodCode(newPayment.getPaymentMethodCode());
            }
            
            entitymanager.getTransaction( ).commit( );

        }
        catch (Exception e)
        {
        }
        
        return paymentData;
    }
    
    public List<Payment> findPaymentByName(String firstName, String lastName)
    {
        List<Payment> guests = null;
        
        try
        {
            entitymanager.getTransaction( ).begin( );

            guests = entitymanager.createNamedQuery("Payment.findByPaymentTypeCode")
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
         PaymentController x = new PaymentController(emfactoryb);
         
         List<Payment> newPayments = x.getPayments();
         
         x.close();
         
     }

}
