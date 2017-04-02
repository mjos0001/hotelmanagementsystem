/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hmsDataService;

import hmsModel.Payment;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnit;

/**
 *
 * @author mrkjse
 */
public class PaymentDataService {
     EntityManagerFactory emfactory = null;
    
    @PersistenceUnit(unitName="HotelManagementSystemPUB")
    EntityManager entitymanager = null;
    
    public PaymentDataService(EntityManagerFactory emf)
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
        entitymanager.close();
    }
    
    public List<Payment> getPayments() throws Exception
    {   
        List<Payment> payments = null;
        
        try
        {
            entitymanager.getTransaction().begin();

            payments = entitymanager.createNamedQuery("Payment.findAll").getResultList();

            if (payments.size() > 0)
            {
                
            }

            entitymanager.getTransaction().commit();
        }
        catch (Exception e)
        {
            throw new Exception("Error in doing the database operation.");
        }
      
      return payments;
    }
    
    public List<Payment> getPaymentsByBookingId(int bookingId) throws Exception
    {   
        List<Payment> payments = null;
        
        try
        {
            entitymanager.getTransaction().begin();

            payments = entitymanager.createNamedQuery("Payment.findByBookingId").setParameter("bookingId", bookingId).getResultList();

            entitymanager.getTransaction().commit();
        }
        catch (Exception e)
        {
            throw new Exception("Error in doing the database operation.");
        }
      
      return payments;
    }
    
    
    public boolean createPayment(Payment payment) throws Exception
    {
        try
        {
            entitymanager.getTransaction().begin();
            entitymanager.persist(payment);
            entitymanager.getTransaction().commit();
            
            return true;
        }
        catch (Exception e)
        {
            throw new Exception("Error in doing the database operation.");
        }
    }
    
    public boolean deletePayment(Payment payment) throws Exception
    {
        try
        {
            entitymanager.getTransaction().begin();

            // Find the Payment first
            Payment dataPayment = entitymanager.find(Payment.class, payment.getPaymentPK());

            if (dataPayment != null)
            {
                entitymanager.remove(payment);
            }
            
            entitymanager.getTransaction().commit();
            
            return true;
        }
        catch (Exception e)
        {
            throw new Exception("Error in doing the database operation.");
        }
    }
    
    public Payment updatePayment(Payment newPayment) throws Exception
    {
        Payment paymentData = null;
        
        try
        {
            entitymanager.getTransaction().begin();
            
            paymentData = entitymanager.find(Payment.class, newPayment.getPaymentPK());

            if (paymentData != null)
            {
                paymentData.setPaymentAmount(newPayment.getPaymentAmount());
                paymentData.setPaymentDate(newPayment.getPaymentDate());
                paymentData.setPaymentMethodCode(newPayment.getPaymentMethodCode());
            }
            
            entitymanager.getTransaction().commit();

        }
        catch (Exception e)
        {
            throw new Exception("Error in doing the database operation.");
        }
        
        return paymentData;
    }
    
    // findPaymentByBooking
    
    public static void main(String args[]) {
         
        try
        {
            EntityManagerFactory emfactoryb = Persistence.createEntityManagerFactory("HotelManagementSystemPUB");
            PaymentDataService x = new PaymentDataService(emfactoryb);

            List<Payment> newPayments = x.getPayments();

            x.close();
        }
        catch (Exception e)
        {
            
        }
     }

}
