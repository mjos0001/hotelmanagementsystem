/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hmsModel;

/**
 *
 * @author mrkjse
 */
public class RoomChoice {
    // Type of room selected
    String roomTypeCode;
    // How many rooms for this type?
    int quantity;
    // What's the maximum allowable occupancy for this room?
    // quantity * maxOccupancy
    int totalMaxOccupancy;
    // How many guests to allocated into this room choice?
    int allocatedGuests;
    
    // Algorithm: assign a random room of this type for n guests
    
    public RoomChoice(){
        quantity = 0;
        totalMaxOccupancy = 0;
        allocatedGuests = 0;
    }
    
    public String getRoomTypeCode()
    {
        return roomTypeCode;
    }
    
    public int getQuantity()
    {
        return quantity;
    }
    
    public int getTotalMaxOccupancy()
    {
        return totalMaxOccupancy;
    }
    
    public int getAllocatedGuests()
    {
        return allocatedGuests;
    }
    
    public boolean canStillAllocateGuest()
    {
        return totalMaxOccupancy > allocatedGuests;
    }
    
    public void setRoomTypeCode(String value)
    {
        roomTypeCode = value;
    }
    
    public void setQuantity(int value)
    {
        quantity = value;
    }
    
    public void setTotalMaxOccupancy(int value)
    {
        totalMaxOccupancy = value;
    }
    
    public boolean setAllocatedGuests(int value)
    {
        if (canStillAllocateGuest())
        {
            allocatedGuests = value;
            return true;
        }
        
        return false;
    }
    
}
