/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hmsDataService;

import hmsModel.Membership;
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
public class MembershipDataService {
    
    EntityManagerFactory emfactory = null;
    
    @PersistenceUnit(unitName="HotelManagementSystemPUB")
    EntityManager entitymanager = null;
    
    public MembershipDataService(EntityManagerFactory emf)
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
    
    public List<Membership> getMemberships() throws Exception
    {   
        List<Membership> memberships = null;
        
        try
        {
            entitymanager.getTransaction().begin();

            memberships = entitymanager.createNamedQuery("Membership.findAll").getResultList();
            
            for (Membership m : memberships)
            {
                entitymanager.refresh(m);
            }

            entitymanager.getTransaction().commit();
        }
        catch (Exception e)
        {
            throw new Exception("Error in doing the database operation.");
        }
      
      return memberships;
    }
    
    
    public boolean createMembership(Membership membership) throws Exception
    {
        try
        {
            entitymanager.getTransaction().begin();
            entitymanager.persist(membership);
            entitymanager.getTransaction().commit();

            getMemberships();
            
            return true;
        }
        catch (Exception e)
        {
            throw new Exception("Error in doing the database operation.");
        }
    }
    
    public boolean deleteMembership(Membership membership) throws Exception
    {
        try
        {
            entitymanager.getTransaction().begin();

            // Find the Membership first
            Membership dataMembership = entitymanager.find(Membership.class, membership.getMembershipTierCode());

            if (dataMembership != null)
            {
                entitymanager.remove(membership);
            }

            entitymanager.getTransaction().commit();
            
            return true;
        }
        catch (Exception e)
        {
            throw new Exception("Error in doing the database operation.");
        }
    }
    
    public Membership updateMembership(Membership newMembership) throws Exception
    {
        Membership updatedMembership = null;
        
        try
        {
            entitymanager.getTransaction().begin();
            
            Membership membershipData = entitymanager.find(Membership.class, newMembership.getMembershipTierCode());

            // Check if it exists
            if (membershipData != null)
            {
                membershipData.setDiscount(newMembership.getDiscount());
                membershipData.setMembershipTier(newMembership.getMembershipTier());
                membershipData.setMembershipTierCode(newMembership.getMembershipTierCode());
                membershipData.setTierCredits(newMembership.getTierCredits());
                membershipData.setOtherRewards(newMembership.getOtherRewards());
            }
            
            entitymanager.getTransaction().commit();

            getMemberships();

        }
        catch (Exception e)
        {
            throw new Exception("Error in doing the database operation.");
        }
        
        return updatedMembership;
    }
    
    public Membership findMembershipByTierCode(String membershipTierCode) throws Exception
    {
        Membership membership =  null;
        
        try
        {
            entitymanager.getTransaction().begin();

            membership = (Membership)entitymanager.createNamedQuery("Membership.findByMembershipTierCode")
                    .setParameter("membershipTierCode", membershipTierCode).getSingleResult();

            entitymanager.getTransaction().commit();
        }
        catch (Exception e)
        {
            throw new Exception("Error in doing the database operation.");
        }
        
        return membership;
    }
    
    public List<Membership> findMembershipByTierCredits(long availableCredits) throws Exception
    {
        List<Membership> memberships = null;
        
        try
        {
            EntityManager entitymanager = emfactory.createEntityManager();
            entitymanager.getTransaction().begin();

            memberships = entitymanager.createNamedQuery("Membership.findByTierCredits")
                    .setParameter("tierCredits", availableCredits).getResultList();

            entitymanager.getTransaction().commit();
            entitymanager.close();
        }
        catch (Exception e)
        {
            throw new Exception("Error in doing the database operation.");
        }
        
        return memberships;
    }
    
     
    public static void main(String args[]) {
         
        try
        {
            EntityManagerFactory emfactoryb = Persistence.createEntityManagerFactory("HotelManagementSystemPUB");
            MembershipDataService x = new MembershipDataService(emfactoryb);

            List<Membership> newMemberships = x.getMemberships();

            newMemberships = x.findMembershipByTierCredits(1200000L);

            x.close();
        }
        catch (Exception e)
        {
            
        }
         
    }
}
