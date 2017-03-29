/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Models.*;
import java.text.SimpleDateFormat;
import java.util.*; 

/**
 *
 * @author mrkjse
 */
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
public class RoomFinder {
    
    EntityManagerFactory emfactory = null;
    
    @PersistenceUnit(unitName="HotelManagementSystemPUB")
    EntityManager entitymanager = null;
    
    public RoomFinder(EntityManagerFactory emf)
    {
        if (emf != null)
        {
            emfactory = emf;
        }
        
        entitymanager = emfactory.createEntityManager( );
    }
    
    public ArrayList<Room> findRooms (SearchRoomRequest request)
    {
        
        RoomController rc = new RoomController(emfactory);
        BookingController bc = new BookingController(emfactory);
        List<Room> allRoomsList = rc.getRooms();
        ArrayList<Room> allRooms = new ArrayList<>();
        
        for (Room r : allRoomsList)
        {
            allRooms.add(r);
        }
        
        // Check each criteria
        
        // Check in and check-out: both should be filled up and required
        if (request.getCheckInDate() != null && request.getCheckInDate() != null)
        {   
            SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
            try
            {
                // Check BookingRoomGuests
                List<Booking> takenBookings = bc.findByTakenBookingDates(request.getCheckInDate(), request.getCheckOutDate());
                int i = 0;
                
                for (Booking b : takenBookings)
                {
                    for (BookingRoomGuest brg : b.getBookingRoomGuestCollection())
                    {
                        // Find this room in all rooms
                        i = 0;
                        for (i = allRooms.size() - 1; i >= 0; i-- )
                        {
                            if (allRooms.get(i).getRoomId() == brg.getRoom().getRoomId())
                            {
                                allRooms.remove(i);
                            }
                        }
                    }
                } 
                
            }
            catch (Exception e)
            {

            }
            
        }
        else
        {
            // throw exception!!! this is required
        }
        
        // Room Type
        if (request.getRoomTypeCode() != null && request.getRoomTypeCode().size() > 0)
        {
            // Check room type code
            int i = 0;
            for (i = allRooms.size() - 1; i >= 0; i--)
            {
                // Check if the room type code is in the request
                // if yes then don't delete
                boolean delete = true;
                for (String rtc : request.getRoomTypeCode())
                {
                    if (allRooms.get(i).getRoomType().getRoomTypeCode().equals(rtc))
                    {
                        delete = false;
                        break;
                    }
                }

                if (delete)
                {
                    allRooms.remove(i);
                }
            }
               
        }
        // Occupancy
        
        // Price range: any can be filled up
        if (request.getMinPrice() != -1L)
        {
            // Check room type code
            int i = 0;
            
            for (i = allRooms.size() - 1; i >= 0; i--)
            {
                if (allRooms.get(i).getRoomPrice() < request.getMinPrice())
                {
                    allRooms.remove(i);
                }
            }
            
        }
        
        
        if (request.getMaxPrice() != -1L)
        {
            // Check room type code
            int i = 0;
            
            for (i = allRooms.size() - 1; i >= 0; i--)
            {
                if (allRooms.get(i).getRoomPrice() > request.getMaxPrice())
                {
                    allRooms.remove(i);
                }
            }
        }
        
        // Get the non-empty lists
        // Get the intersection of all the resulting lists
        
        return allRooms;
    }
    
    public ArrayList<RoomChoice> allocateRoomPerGuest(ArrayList<RoomChoice> roomChoices, int numOfGuests)
    {
        int totalMaxOccupancy = 0;
        
        for (RoomChoice rc : roomChoices)
        {
            totalMaxOccupancy += rc.getTotalMaxOccupancy();
        }
        
        // The number of guests must NOT exceed the max occupancy
        if (numOfGuests > totalMaxOccupancy)
        {
            // throw an exception!!! not allowed
        }
        else
        {
            boolean allocatedAll = false;
            for (RoomChoice rc : roomChoices)
            {
                int maxOccupancy = rc.getTotalMaxOccupancy();

                if (maxOccupancy < numOfGuests)
                {
                    rc.setAllocatedGuests(maxOccupancy);
                    numOfGuests -= maxOccupancy;


                }
                // if there are more space for the room put everyone else in this allocation
                else
                {
                    rc.setAllocatedGuests(numOfGuests);
                    allocatedAll = true;
                }

                // allocate one guest to every excess room
                if(allocatedAll)
                {
                    rc.setAllocatedGuests(1);
                }

            }
        }
        
        // By this time i should allocated everything or only have rooms without allocations
        
        
        return roomChoices;
    }
    
    public ArrayList<BookingRoomGuestPK> createBookingRoomGuest(ArrayList<RoomChoice> roomChoices, ArrayList<Room> availableRooms, ArrayList<Guest> guests)
    {
        ArrayList<BookingRoomGuestPK> brgList = null;
        ArrayList<Room> roomsCopy = new ArrayList(availableRooms);
        
        for (RoomChoice rc : roomChoices)
        {
            // for each room choice
            // find a room that is of the same type
            // allocate the number of guests in that room
            
            Room room = null;
            
            for (Room r : roomsCopy)
            {
                if (r.getRoomType().getRoomTypeCode().equals(rc.getRoomTypeCode()))
                {
                    room = r;
                    break;
                }
            }
            
            if (room == null)
            {
                // Should not happen! Throw error (Picked room is now unavailable!)
            }
            else
            {
                brgList = new ArrayList<>();
                ArrayList<Guest> guestsCopy = new ArrayList(guests);

                for (int i = 0; i < rc.getAllocatedGuests(); i++)
                {
                    Guest guest = guests.remove(guestsCopy.size() - 1);
                    brgList.add(new BookingRoomGuestPK(0, guest.getGuestId(), room.getRoomId()));
                }
            }
            
            availableRooms.remove(room);
        }
        
        return brgList;
    }
}
