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

/**
 *
 * @author mrkjse
 */
public class MembershipController {
    
    EntityManagerFactory emfactory = null;
    
    public MembershipController(EntityManagerFactory emf)
    {
        if (!emf.isOpen())
        {
            // Should throw an error
            emfactory = null;
        }
        emfactory = emf;
    }
    
    public List<Membership> getMemberships()
    {   
        List<Membership> memberships = null;
        
        try
        {
        
            EntityManager entitymanager = emfactory.createEntityManager( );
            entitymanager.getTransaction( ).begin( );

            memberships = entitymanager.createNamedQuery("Membership.findAll").getResultList();

            if (memberships.size() > 0)
            {
                for (Membership g : memberships) 
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
      
      return memberships;
    }
    
    
    public boolean createMembership(Membership membership)
    {
        try
        {
            EntityManager entitymanager = emfactory.createEntityManager( );
            entitymanager.getTransaction( ).begin( );

            entitymanager.persist( membership );
            entitymanager.getTransaction( ).commit( );

            getMemberships();

            entitymanager.close( );
            
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
            EntityManager entitymanager = emfactory.createEntityManager( );
            entitymanager.getTransaction( ).begin( );

            // Find the Membership first
            Membership dataMembership = entitymanager.find( Membership.class, membership.getMembershipTierCode());

            if (dataMembership != null)
            {
                entitymanager.remove( membership );
                entitymanager.getTransaction( ).commit( );

                getMemberships();
            }

            entitymanager.close( );
            
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
            EntityManager entitymanager = emfactory.createEntityManager( );
            entitymanager.getTransaction( ).begin( );
            
            Membership oldMembership = entitymanager.find( Membership.class, newMembership.getMembershipTierCode());

            // Check if it exists
           
            updatedMembership = oldMembership;
            
            entitymanager.getTransaction( ).commit( );

            getMemberships();

            entitymanager.close( );
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
            EntityManager entitymanager = emfactory.createEntityManager( );
            entitymanager.getTransaction( ).begin( );

            memberships = entitymanager.createNamedQuery("Membership.findByMembershipTierCode")
                    .setParameter("membershipTierCode", membershipTierCode).getResultList();

            entitymanager.getTransaction( ).commit( );
            entitymanager.close( );
        }
        catch (Exception e)
        {
            
        }
        
        return memberships;
    }
    
    public List<Membership> findMembershipByType(String MembershipTypeCode)
    {
        List<Membership> memberships = null;
        
        try
        {
            EntityManager entitymanager = emfactory.createEntityManager( );
            entitymanager.getTransaction( ).begin( );

            memberships = entitymanager.createNamedQuery("Membership.findByMembershipTypeCode")
                    .setParameter("MembershipTypeCode", MembershipTypeCode).getResultList();

            entitymanager.getTransaction( ).commit( );
            entitymanager.close( );
        }
        catch (Exception e)
        {
            
        }
        
        return memberships;
    }
    
     public List<Membership> findMembershipByTierCredits(int availableCredits )
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
}
