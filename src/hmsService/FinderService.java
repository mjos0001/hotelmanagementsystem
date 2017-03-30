/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hmsService;
import hmsModel.Room;
import hmsModel.Guest;
import hmsModel.Customer;
import hmsModel.Booking;
import hmsModel.Payment;
import java.util.*;

/**
 *
 * @author mrkjse
 */
public class FinderService {
    
    public static Guest findGuestByGuestId(ArrayList<Guest> list, int id)
    {
        for (Guest g : list)
        {
            if (g.getGuestId() == id)
            {
                return g;
            }
        }
        
        return null;
    }
    
    public static Booking findBookingByBookingId(ArrayList<Booking> list, int id)
    {
        for (Booking b : list)
        {
            if (b.getBookingId() == id)
            {
                return b;
            }
        }
        
        return null;
    }
    
    public static Room findRoomByRoomId(ArrayList<Room> list, int id)
    {
        for (Room r : list)
        {
            if (r.getRoomId() == id)
            {
                return r;
            }
        }
        
        return null;
    }
    
    public static Customer findCustomerByCustomerId(ArrayList<Customer> list, int id)
    {
        for (Customer c : list)
        {
            if (c.getCustomerId() == id)
            {
                return c;
            }
        }
        
        return null;
    }
    
    public static Payment findPaymentByMethod(ArrayList<Payment> list, String code)
    {
        for (Payment c : list)
        {
            if (c.getPaymentMethodCode().equals(code))
            {
                return c;
            }
        }
        
        return null;
    }
}
