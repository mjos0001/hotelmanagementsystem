/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hmsService;

import hmsDataService.BookingDataService;
import hmsDataService.RoomDataService;
import hmsModel.RoomChoice;
import hmsModel.Room;
import hmsModel.Guest;
import hmsModel.BookingRoomGuest;
import hmsModel.SearchRoomRequest;
import hmsModel.BookingRoomGuestPK;
import hmsModel.Booking;
import hmsModel.Payment;
import java.text.SimpleDateFormat;
import java.util.*; 

/**
 *
 * @author mrkjse
 */
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

public class RoomAllocatorService {
    
    EntityManagerFactory emfactory = null;
    
    @PersistenceUnit(unitName="HotelManagementSystemPUB")
    EntityManager entitymanager = null;
    
    public RoomAllocatorService(EntityManagerFactory emf)
    {
        if (emf != null)
        {
            emfactory = emf;
        }
        
        entitymanager = emfactory.createEntityManager();
    }
    
    public ArrayList<Room> findAvailableRooms (SearchRoomRequest request) throws Exception
    {
        List<Room> allRoomsList = null;
        ArrayList<Room> allRooms = new ArrayList<>();
        BookingDataService bc = null;
        RoomDataService rc = null;
        
        
        try
        {
            rc = new RoomDataService(emfactory);
            bc = new BookingDataService(emfactory);
            allRoomsList = rc.getRooms();
        }
        catch (Exception e)
        {
            throw new Exception("Error in getting rooms data. Please contact your database administrator.");
        }
        
        if (allRoomsList != null && allRoomsList.size() > 0)
        {
            for (Room r : allRoomsList)
            {
                allRooms.add(r);
            }

            // Check each criteria
            Date checkIn = request.getCheckInDate();
            Date checkOut = request.getCheckOutDate();

            // Check in and check-out: both should be filled up and required
            // check in should be earlier than checkout
            if (request.getCheckInDate() != null && 
                request.getCheckInDate() != null &&
                (checkIn.compareTo(checkOut) < 0))
            {   
                SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
                try
                {
                    // Check BookingRoomGuests
                    List<Booking> takenBookings = bc.getBookingsByTakenDates(request.getCheckInDate(), request.getCheckOutDate());
                    int i = 0;

                    for (Booking b : takenBookings)
                    {
                        for (BookingRoomGuest brg : b.getBookingRoomGuestCollection())
                        {
                            // Find this room in all rooms
                            i = 0;
                            int size = allRooms.size(); 
                            for (i = size - 1; i >= 0; i--)
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
                    throw new Exception("Error in getting bookings data. Please contact your database administrator.");
                }

            }
            else
            {
                // throw exception!!! this is required
                throw new Exception("Check in date and check out date are both required.");
            }
            
            /*
            // Filter by hotel id
            int hotelId = request.getHotelId();
            
            if (hotelId != -1)
            {
                                // Check room type code
                int i = 0;
                
                for (i = allRooms.size() - 1; i >= 0; i--)
                {
                    if (allRooms.get(i).getHotelId() != hotelId)
                    {
                        allRooms.remove(i);
                    }
                }
            } */

            // Filter by room type
            if (request.getRoomTypeCode() != null && request.getRoomTypeCode().size() > 0)
            {
                // Check room type code
                int i = 0;
                int size = allRooms.size(); 
                for (i = size - 1; i >= 0; i--)
                {
                    Room thisRoom = allRooms.get(i);
                    String thisRoomType = thisRoom.getRoomType().getRoomTypeCode();
                    
                    // Check if the room type code is in the request
                    // if yes then don't delete
                    boolean delete = true;
                    ArrayList<String> rooomTypesRequest = request.getRoomTypeCode();
                    
                    for (String rtc : rooomTypesRequest)
                    {
                        if (thisRoomType.equals(rtc))
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
            
            if (request.getMinPrice() > request.getMaxPrice())
            {
                throw new Exception("Minimum price cannot be greater than the maximum price.");
            }

            // Price range: any can be filled up
            if (request.getMinPrice() != 0)
            {
                // Check room type code
                int i = 0;
                int size = allRooms.size(); 
                for (i = size - 1; i >= 0; i--)
                {
                    if (allRooms.get(i).getRoomPrice() < request.getMinPrice())
                    {
                        allRooms.remove(i);
                    }
                }

            }

            if (request.getMaxPrice() != 0)
            {
                // Check room type code
                int i = 0;
                int size = allRooms.size(); 
                for (i = size - 1; i >= 0; i--)
                {
                    if (allRooms.get(i).getRoomPrice() > request.getMaxPrice())
                    {
                        allRooms.remove(i);
                    }
                }
            }
        }
        
        // Get the non-empty lists
        // Get the intersection of all the resulting lists
        
        return allRooms;
    }
    
    public ArrayList<RoomChoice> allocateRoomPerGuest(ArrayList<RoomChoice> roomChoices, int numOfGuests) throws Exception
    {
        int totalMaxOccupancy = 0;
        
        for (RoomChoice rc : roomChoices) {
            totalMaxOccupancy += rc.getTotalMaxOccupancy();
        }
        
        // The number of guests must NOT exceed the max occupancy
        if (numOfGuests > totalMaxOccupancy)
        {
            // throw an exception!!! not allowed
            throw new Exception("Number of guests cannot be more than the total room occupancy.");
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
                    continue;
                }

                // allocate one guest to every excess room
                if(allocatedAll)
                {
                    rc.setAllocatedGuests(1);
                }

            }
        }
        
        return roomChoices;
    }
    
    public ArrayList<BookingRoomGuestPK> createBookingRoomGuest(int bookingId, ArrayList<RoomChoice> roomChoices, ArrayList<Room> availableRooms, ArrayList<Guest> guests)
    {
        ArrayList<BookingRoomGuestPK> brgList = null;
        
        
        if (roomChoices != null && availableRooms != null && guests != null)
        {
            ArrayList<Room> roomsCopy = new ArrayList(availableRooms);
            brgList = new ArrayList<>();

            // Rule of thumb: the roomChoices here should make sure that
            // there are more rooms available for their choices
            for (RoomChoice rc : roomChoices)
            {
                if (roomsCopy.size() > 0)
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
                        ArrayList<Guest> guestsCopy = new ArrayList(guests);
                        Guest guest = null;

                        for (int i = 0; i < rc.getAllocatedGuests(); i++)
                        {
                            // Don't override the guest if we got the last one
                            if (guestsCopy.size() > 0)
                            {
                               guest = guestsCopy.remove(guestsCopy.size() - 1);
                            }

                            brgList.add(new BookingRoomGuestPK(bookingId, guest.getGuestId(), room.getRoomId()));
                        }
                    }

                    roomsCopy.remove(room);
                }
            }
        }
        
        return brgList;
    }
    
    public void updateBookingPaymentStatus(Booking bkg)
    {
        double totalPaid = 0.0;
        
        for (Payment p : bkg.getPaymentCollection())
        {
            totalPaid += p.getPaymentAmount();
        }
        
        if (totalPaid >= bkg.getTotalAmount())
        {
            bkg.setPaymentStatusCode("PD");
        }
        else
        {
            bkg.setPaymentStatusCode("UP");
        }
        
        try
        {
            BookingDataService bc = new BookingDataService(emfactory);
            bc.updateBooking(bkg);
        }
        catch (Exception e)
        {
            
        }
    }

    
    public boolean roomChoiceIntegrityEnsured (ArrayList<RoomChoice> roomChoices, ArrayList<Room> availableRooms)
    {
        // ensure that the total number of rooms in roomChoices is still enough for the guests
        
        // for each room choice * quantity
        // look for a matching room
        // pop that room
        // if we don't have a matching room, return false
        
        return false;
    }
}
