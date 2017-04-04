/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hmsService;

import hmsModel.*;
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
    
    public static ArrayList<Guest> findGuestByBookingId(ArrayList<Guest> list, int id)
    {
        ArrayList<Guest> guestList = new ArrayList<>();
        
        for (Guest g : list)
        {
            if (g.getBooking().getBookingId() == id)
            {
                guestList.add(g);
            }
        }
        
        return guestList;
    }
    
    public static Hotel findHotelByName(ArrayList<Hotel> list, String name)
    {
        for (Hotel b : list)
        {
            if (b.getHotelName().toLowerCase().equals(name.toLowerCase()))
            {
                return b;
            }
        }
        
        return null;
    }
    
    public static Hotel findHotelById(ArrayList<Hotel> list, int id)
    {
        for (Hotel b : list)
        {
            if (b.getHotelId() == id)
            {
                return b;
            }
        }
        
        return null;
    }
    
        public static Booking findBookingById(ArrayList<Booking> list, int id)
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
    
    public static ArrayList<Room> findRoomByRoomType(ArrayList<Room> list, String code)
    {
        ArrayList<Room> rooms = new ArrayList<>();
        
        for (Room r : list)
        {
            if (r.getRoomType().getRoomTypeCode().equals(code))
            {
                rooms.add(r);
            }
        }
        
        return rooms;
    }
    
    public static ArrayList<Room> findRoomByHotelId(ArrayList<Room> list, int id)
    {
        ArrayList<Room> rooms = new ArrayList<>();
        
        for (Room r : list)
        {
            if (r.getHotelId() == id)
            {
                rooms.add(r);
            }
        }
        
        return rooms;
    }
    
    public static RoomType findRoomTypeByCode(ArrayList<RoomType> list, String code)
    {
        for (RoomType r : list)
        {
            if (r.getRoomTypeCode().toUpperCase().equals(code.toUpperCase()))
            {
                return r;
            }
        }
        
        return null;
    }
    
    public static ArrayList<BookingRoomGuest> findBookingRoomGuestByRoomId(ArrayList<BookingRoomGuest> list, int id)
    {
        ArrayList<BookingRoomGuest> brgList = new ArrayList<>();
        
        for (BookingRoomGuest r : list)
        {
            if (r.getBookingRoomGuestPK().getRoomId() == id)
            {
                brgList.add(r);
            }
        }
        
        return brgList;
    }
    
    public static ArrayList<BookingRoomGuest> findBookingRoomGuestByGuestId(ArrayList<BookingRoomGuest> list, int id)
    {
        ArrayList<BookingRoomGuest> brgList = new ArrayList<>();
        
        for (BookingRoomGuest r : list)
        {
            if (r.getBookingRoomGuestPK().getGuestId() == id)
            {
                brgList.add(r);
            }
        }
        
        return brgList;
    }
    
    public static Membership findMembershipByMembershipTierCode(ArrayList<Membership> list, String code)
    {
        for (Membership r : list)
        {
            if (r.getMembershipTierCode().toUpperCase().equals(code.toUpperCase()))
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
    
    public static Customer findCustomerByCustomerName(ArrayList<Customer> list, String fn, String ln)
    {
        for (Customer c : list)
        {
            if (c.getFirstName().toLowerCase().equals(fn.toLowerCase()) &&
                c.getLastName().toLowerCase().equals(ln.toLowerCase()))
            {
                return c;
            }
        }
        
        return null;
    }
    
    public static ArrayList<Customer> listCustomerByCustomerName(ArrayList<Customer> list, String fn, String ln)
    {
        ArrayList<Customer> cList = new ArrayList<>();
        
        for (Customer c : list)
        {
            if (c.getFirstName().toLowerCase().equals(fn.toLowerCase()) &&
                c.getLastName().toLowerCase().equals(ln.toLowerCase()))
            {
                cList.add(c);
            }
        }
        
        return cList;
    }

    public static ArrayList<Booking> listBookingByCustomerName(ArrayList<Booking> list, String fn, String ln)
    {
        ArrayList<Booking> cList = new ArrayList<>();
        
        for (Booking b : list)
        {
            Customer c = b.getCustomer();
            
            if (c.getFirstName().toLowerCase().equals(fn.toLowerCase()) &&
                c.getLastName().toLowerCase().equals(ln.toLowerCase()))
            {
                cList.add(b);
            }
        }
        
        return cList;
    }

    public static ArrayList<Payment> listPaymentByCustomerName(ArrayList<Payment> list, String fn, String ln)
    {
        ArrayList<Payment> cList = new ArrayList<>();
        
        for (Payment p : list)
        {
            Booking b = p.getBooking();
            Customer c = b.getCustomer();
            
            if (c.getFirstName().toLowerCase().equals(fn.toLowerCase()) &&
                c.getLastName().toLowerCase().equals(ln.toLowerCase()))
            {
                cList.add(p);
            }
        }
        
        return cList;
    }
    
    public static ArrayList<Payment> findPaymentByFirstName(ArrayList<Payment> list, String fn)
    {
        ArrayList<Payment> cList = new ArrayList<>();
        
        for (Payment p : list)
        {
            Booking b = p.getBooking();
            Customer c = b.getCustomer();
            
            if (c.getFirstName().toLowerCase().equals(fn.toLowerCase()))
            {
                cList.add(p);
            }
        }
        
        return cList;
    }
    
    public static ArrayList<Payment> findPaymentByLastName(ArrayList<Payment> list, String ln)
    {
        ArrayList<Payment> cList = new ArrayList<>();
        
        for (Payment p : list)
        {
            Booking b = p.getBooking();
            Customer c = b.getCustomer();
            
            if (c.getLastName().toLowerCase().equals(ln.toLowerCase()))
            {
                cList.add(p);
            }
        }
        
        return cList;
    }
    
    public static ArrayList<Customer> findCustomerByFirstName(ArrayList<Customer> list, String fn)
    {
        ArrayList<Customer> cList = new ArrayList<>();
        
        for (Customer c : list)
        {
            if (c.getFirstName().toLowerCase().equals(fn.toLowerCase()))
            {
                cList.add(c);
            }
        }
        
        return cList;
    }
    
    public static ArrayList<Booking> findBookingByFirstName(ArrayList<Booking> list, String fn)
    {
        ArrayList<Booking> cList = new ArrayList<>();
        
        for (Booking b : list)
        {
            Customer c = b.getCustomer();
            
            if (c.getFirstName().toLowerCase().equals(fn.toLowerCase()))
            {
                cList.add(b);
            }
        }
        
        return cList;
    }
    
    public static ArrayList<Customer> findCustomerByLastName(ArrayList<Customer> list, String ln)
    {
        ArrayList<Customer> cList = new ArrayList<>();
        
        for (Customer c : list)
        {
            if (c.getLastName().toLowerCase().equals(ln.toLowerCase()))
            {
                cList.add(c);
            }
        }
        
        return cList;
    }
    
    public static ArrayList<Booking> findBookingByLastName(ArrayList<Booking> list, String ln)
    {
        ArrayList<Booking> cList = new ArrayList<>();
        
        for (Booking b : list)
        {
            Customer c = b.getCustomer();
            
            if (c.getLastName().toLowerCase().equals(ln.toLowerCase()))
            {
                cList.add(b);
            }
        }
        
        return cList;
    }
    
    public static Facility findFacilityByName(ArrayList<Facility> list, String name)
    {
        for (Facility f : list)
        {
            if (f.getFacilityName().toUpperCase().equals(name.toUpperCase()))
            {
                return f;
            }
        }
        
        return null;
    }

    public static ArrayList<Guest> listGuestByGuestName(ArrayList<Guest> list, String fn, String ln)
    {
        ArrayList<Guest> cList = new ArrayList<>();
        
        for (Guest c : list)
        {
            if (c.getFirstName().toLowerCase().equals(fn.toLowerCase()) &&
                c.getLastName().toLowerCase().equals(ln.toLowerCase()))
            {
                cList.add(c);
            }
        }
        
        return cList;
    }
    
    public static ArrayList<Guest> findGuestByFirstName(ArrayList<Guest> list, String fn)
    {
        ArrayList<Guest> cList = new ArrayList<>();
        
        for (Guest c : list)
        {
            if (c.getFirstName().toLowerCase().equals(fn.toLowerCase()))
            {
                cList.add(c);
            }
        }
        
        return cList;
    }
    
    public static ArrayList<Guest> findGuestByLastName(ArrayList<Guest> list, String ln)
    {
        ArrayList<Guest> cList = new ArrayList<>();
        
        for (Guest c : list)
        {
            if (c.getLastName().toLowerCase().equals(ln.toLowerCase()))
            {
                cList.add(c);
            }
        }
        
        return cList;
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
    
    public static Payment findPaymentById(ArrayList<Payment> list, int bid, int num)
    {
        for (Payment c : list)
        {
            if (c.getPaymentPK().getBookingId() == bid &&
                c.getPaymentPK().getPaymentNumber() == num)
            {
                return c;
            }
        }
        
        return null;
    }
    
    public static ArrayList<Payment> findPaymentByBookingId(ArrayList<Payment> list, int bid)
    {
        ArrayList<Payment> pList = new ArrayList<>();
        
        for (Payment c : list)
        {
            if (c.getPaymentPK().getBookingId() == bid)
            {
                pList.add(c);
            }
        }
        
        return pList;
    }
    
    
}
