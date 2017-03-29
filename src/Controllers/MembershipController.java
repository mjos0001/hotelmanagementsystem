/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

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
public class MembershipController {
    
    EntityManagerFactory emfactory = null;
    
    @PersistenceUnit(unitName="HotelManagementSystemPUB")
    EntityManager entitymanager = null;
    
    public MembershipController(EntityManagerFactory emf)
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
    
    public List<Membership> getMemberships()
    {   
        List<Membership> memberships = null;
        
        try
        {
            entitymanager.getTransaction( ).begin( );

            memberships = entitymanager.createNamedQuery("Membership.findAll").getResultList();

            entitymanager.getTransaction( ).commit( );
        }
        catch (Exception e)
        {
            
        }
      
      return memberships;
    }
    
    
    public boolean createMembership(Membership membership)
    {
        try
        {
            entitymanager.getTransaction( ).begin( );
            entitymanager.persist( membership );
            entitymanager.getTransaction( ).commit( );

            getMemberships();
            
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }
    
    public boolean deleteMembership(Membership membership)
    {
        try
        {
            entitymanager.getTransaction( ).begin( );

            // Find the Membership first
            Membership dataMembership = entitymanager.find( Membership.class, membership.getMembershipTierCode());

            if (dataMembership != null)
            {
                entitymanager.remove( membership );

                getMemberships();
            }

            entitymanager.getTransaction( ).commit( );
            
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }
    
    public Membership updateMembership(Membership newMembership)
    {
        Membership updatedMembership = null;
        
        try
        {
            entitymanager.getTransaction( ).begin( );
            
            Membership membershipData = entitymanager.find( Membership.class, newMembership.getMembershipTierCode());

            // Check if it exists
            if (membershipData != null)
            {
                membershipData.setDiscount(newMembership.getDiscount());
                membershipData.setMembershipTier(newMembership.getMembershipTier());
                membershipData.setMembershipTierCode(newMembership.getMembershipTierCode());
                membershipData.setTierCredits(newMembership.getTierCredits());
                membershipData.setOtherRewards(newMembership.getOtherRewards());
            }
            
            entitymanager.getTransaction( ).commit( );

            getMemberships();

        }
        catch (Exception e)
        {
        }
        
        return updatedMembership;
    }
    
    public List<Membership> findMembershipByTierCode(String membershipTierCode)
    {
        List<Membership> memberships =  null;
        
        try
        {
            entitymanager.getTransaction( ).begin( );

            memberships = entitymanager.createNamedQuery("Membership.findByMembershipTierCode")
                    .setParameter("membershipTierCode", membershipTierCode).getResultList();

            entitymanager.getTransaction( ).commit( );
        }
        catch (Exception e)
        {
            
        }
        
        return memberships;
    }
    
    public List<Membership> findMembershipByTierCredits(long availableCredits)
    {
        List<Membership> memberships = null;
        
        try
        {
            EntityManager entitymanager = emfactory.createEntityManager( );
            entitymanager.getTransaction( ).begin( );

            memberships = entitymanager.createNamedQuery("Membership.findByTierCredits")
                    .setParameter("tierCredits", availableCredits).getResultList();

            entitymanager.getTransaction( ).commit( );
            entitymanager.close( );
        }
        catch (Exception e)
        {
            
        }
        
        return memberships;
    }
    
     
    public static void main(String args[]) {
         
         EntityManagerFactory emfactoryb = Persistence.createEntityManagerFactory( "HotelManagementSystemPUB" );
         MembershipController x = new MembershipController(emfactoryb);
         
         List<Membership> newMemberships = x.getMemberships();
         
         newMemberships = x.findMembershipByTierCredits(1200000L);
         
         x.close();
         
    }
}
