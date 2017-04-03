/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hmsDataService;

import hmsModel.Room;
import hmsModel.RoomType;
import hmsModel.Facility;
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
public class RoomDataService {
    EntityManagerFactory emfactory = null;
    
    @PersistenceUnit(unitName="HotelManagementSystemPUB")
    EntityManager entitymanager = null;
    
    public RoomDataService(EntityManagerFactory emf)
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
        if (entitymanager != null)
        {
            entitymanager.close();
        }
    }
    
    
    public List<Room> getRooms() throws Exception
    {   
        List<Room> rooms = null;
        
        try
        {
            entitymanager.getTransaction().begin();

            rooms = entitymanager.createNamedQuery("Room.findAll").getResultList();

            if (rooms != null && rooms.size() > 0)
            {
                for (Room g : rooms) 
                {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

                    entitymanager.refresh(g);
                    System.out.println("");                
                }
            }

            entitymanager.getTransaction().commit();
        }
        catch (Exception e)
        {
            throw new Exception("Error in doing the database operation.");
        }
      
      return rooms;
    }
    
    public List<RoomType> getRoomTypes() throws Exception
    {   
        List<RoomType> rooms = null;
        
        try
        {
            entitymanager.getTransaction().begin();

            rooms = entitymanager.createNamedQuery("RoomType.findAll").getResultList();

            entitymanager.getTransaction().commit();
        }
        catch (Exception e)
        {
            throw new Exception("Error in doing the database operation.");
        }
      
      return rooms;
    }
    
    public List<Facility> getRoomFacilities() throws Exception
    {   
        List<Facility> facilities = null;
        
        try
        {
            entitymanager.getTransaction().begin();

            facilities = entitymanager.createNamedQuery("Facility.findAll").getResultList();

            entitymanager.getTransaction().commit();
        }
        catch (Exception e)
        {
            throw new Exception("Error in doing the database operation.");
        }
      
      return facilities;
    }
    
    public boolean createRoom(Room room) throws Exception
    {
        try
        {
            entitymanager.getTransaction().begin();
            entitymanager.persist(room);
            entitymanager.getTransaction().commit();

            getRooms();
            
            return true;
        }
        catch (Exception e)
        {
            throw new Exception("Error in doing the database operation.");
        }
    }
    
    public Room getRoomByRoomId(int roomId) throws Exception
    {
        Room dataRoom = null;
        
        try
        {
            entitymanager.getTransaction().begin();

            // Find the Room 
            dataRoom = entitymanager.find(Room.class, roomId);

            entitymanager.getTransaction().commit();
            
        }
        catch (Exception e)
        {
            throw new Exception("Error in doing the database operation.");
        }
        
        return dataRoom;
    }
    
    public boolean deleteRoom(int roomId) throws Exception
    {
        try
        {
            entitymanager.getTransaction().begin();

            // Find the Room first
            Room dataRoom = entitymanager.find(Room.class, roomId);

            if (dataRoom != null)
            {
                entitymanager.remove(dataRoom);

            }
            
            entitymanager.getTransaction().commit();
            
            return true;
        }
        catch (Exception e)
        {
            throw new Exception("Error in doing the database operation.");
        }
    }
    
    public Room updateRoom(Room newRoom) throws Exception
    {
        Room roomData = null; 
        
        try
        {
            entitymanager.getTransaction().begin();
            
            roomData = entitymanager.find(Room.class, newRoom.getRoomId());

            // Check if it exists
            roomData.setRoomDescription(newRoom.getRoomDescription());
            roomData.setRoomNumber(newRoom.getRoomNumber());
            
            entitymanager.getTransaction().commit();

        }
        catch (Exception e)
        {
            throw new Exception("Error in doing the database operation.");
        }
        
        return roomData;
    }
    
    public List<Room> getRoomByFacilityNumber(int facilityNumber) throws Exception
    {
        List<Room> rooms =  null;
        try
        {
            entitymanager.getTransaction().begin();

            rooms = entitymanager.createNamedQuery("Room.findByRoomFacilityNumber")
                    .setParameter("facilityNumber", facilityNumber).getResultList();

            entitymanager.getTransaction().commit();
        }
        catch (Exception e)
        {
            throw new Exception("Error in doing the database operation."); 
        }
        
        return rooms;
    }
    
    public List<Room> getRoomByType(String roomTypeCode) throws Exception
    {
        List<Room> rooms = null;
        
        try
        {
            entitymanager.getTransaction().begin();

            rooms = entitymanager.createNamedQuery("Room.findByRoomTypeCode")
                    .setParameter("roomTypeCode", roomTypeCode).getResultList();

            entitymanager.getTransaction().commit();
        }
        catch (Exception e)
        {
            throw new Exception("Error in doing the database operation.");
        }
        
        return rooms;
    }
    
    public static void main(String args[]) {
         
        try {
            EntityManagerFactory emfactorya = Persistence.createEntityManagerFactory("HotelManagementSystemPUB");
            RoomDataService x = new RoomDataService(emfactorya);

            List<Room> rList = x.getRooms();

            List<Room> r = x.getRoomByType("DLX");

            List<RoomType> roomTypes = x.getRoomTypes();
            RoomType roomType = null;

            for (RoomType rt : roomTypes)
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
   //         x.getRoomByFacilityNumber(1);

            x.close();
        }
        catch (Exception e)
        {
        }
        
     }
}
