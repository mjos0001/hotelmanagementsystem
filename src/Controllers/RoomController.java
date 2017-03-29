/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Models.*;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.math.*;
import javax.persistence.PersistenceUnit;

/**
 *
 * @author mrkjse
 */
public class RoomController {
    
    
    EntityManagerFactory emfactory = null;
    
    @PersistenceUnit(unitName="HotelManagementSystemPUB")
    EntityManager entitymanager = null;
    
    public RoomController(EntityManagerFactory emf)
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
        if (entitymanager != null)
        {
            entitymanager.close();
        }
    }
    
    
    public List<Room> getRooms()
    {   
        List<Room> rooms = null;
        
        try
        {
            entitymanager.getTransaction( ).begin( );

            rooms = entitymanager.createNamedQuery("Room.findAll").getResultList();

            if (rooms != null && rooms.size() > 0)
            {
                for (Room g : rooms) 
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
    
    public List<RoomType> getRoomTypes()
    {   
        List<RoomType> rooms = null;
        
        try
        {
            entitymanager.getTransaction( ).begin( );

            rooms = entitymanager.createNamedQuery("RoomType.findAll").getResultList();

            entitymanager.getTransaction( ).commit( );
        }
        catch (Exception e)
        {
            
        }
      
      return rooms;
    }
    
    
    public boolean createRoom(Room room)
    {
        try
        {
            entitymanager.getTransaction( ).begin( );

            entitymanager.persist( room );
            entitymanager.getTransaction( ).commit( );

            getRooms();
            
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }
    
    public boolean deleteRoom(int roomId)
    {
        try
        {
            entitymanager.getTransaction( ).begin( );

            // Find the Room first
            Room dataRoom = entitymanager.find( Room.class, roomId);

            if (dataRoom != null)
            {
                entitymanager.remove( dataRoom );
                entitymanager.getTransaction( ).commit( );

                getRooms();
            }
            
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }
    
    public Room updateRoom(Room newRoom)
    {
        Room roomData = null; 
        
        try
        {
            entitymanager.getTransaction( ).begin( );
            
            roomData = entitymanager.find( Room.class, newRoom.getRoomId());

            // Check if it exists
            roomData.setRoomDescription(newRoom.getRoomDescription());
            roomData.setRoomNumber(newRoom.getRoomNumber());
            
            entitymanager.getTransaction( ).commit( );

            getRooms();

        }
        catch (Exception e)
        {
        }
        
        return roomData;
    }
    
    public List<Room> findRoomByFacilityNumber(int facilityNumber)
    {
        List<Room> rooms =  null;
        try
        {
            entitymanager.getTransaction( ).begin( );

            rooms = entitymanager.createNamedQuery("Room.findByRoomFacilityNumber")
                    .setParameter("facilityNumber", facilityNumber).getResultList();

            entitymanager.getTransaction( ).commit( );
        }
        catch (Exception e)
        {
            
        }
        
        return rooms;
    }
    
    public List<Room> findRoomByType(String roomTypeCode)
    {
        List<Room> rooms = null;
        
        try
        {
            entitymanager.getTransaction( ).begin( );

            rooms = entitymanager.createNamedQuery("Room.findByRoomTypeCode")
                    .setParameter("roomTypeCode", roomTypeCode).getResultList();

            entitymanager.getTransaction( ).commit( );
        }
        catch (Exception e)
        {
            
        }
        
        return rooms;
    }
     
    public static void main(String args[]) {
         
        
         EntityManagerFactory emfactorya = Persistence.createEntityManagerFactory( "HotelManagementSystemPUB" );
         RoomController x = new RoomController(emfactorya);
         
         List<Room> rList = x.getRooms();
         
         List<Room> r = x.findRoomByType("DLX");
         
         List<RoomType> roomTypes = x.getRoomTypes();
         RoomType roomType = null;
         
         for (RoomType rt : roomTypes )
         {
             if (rt.getRoomTypeCode().equals("DLX"))
             {
                 roomType = rt;
                 break;
             }
         }
         
//         Room newRoom = new Room();
//         newRoom.setHotelId(1);
//         newRoom.setRoomDescription("Fancy room with white gold trimmings.");
//         newRoom.setRoomType(roomType);
//         newRoom.setRoomNumber("202");
//         newRoom.setRoomPrice(300.50);
//         newRoom.setRoomId(4);
//         
//         x.createRoom(newRoom);
//         
//         newRoom.setRoomDescription("A deluxe room with red carpet and velvet curtains.");
//         
//         x.updateRoom(newRoom);

        //x.deleteRoom(2);
         x.findRoomByFacilityNumber(1);
         
         x.close();
         
     }
 
}
