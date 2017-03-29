/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Models.*;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnit;
import javax.persistence.TemporalType;

/**
 *
 * @author mrkjse
 */
public class BookingController {

    EntityManagerFactory emfactory = null;

    @PersistenceUnit(unitName = "HotelManagementSystemPUB")
    EntityManager entitymanager = null;

    public BookingController(EntityManagerFactory emf) {
        if (!emf.isOpen()) {
            // Should throw an error
            emfactory = null;
        }
        emfactory = emf;
        entitymanager = emfactory.createEntityManager();
    }

    public void close() {
        if (entitymanager != null) {
            entitymanager.close();
        }
    }

    public List<Booking> getBookings() {
        List<Booking> bookings = null;

        try {
            entitymanager.getTransaction().begin();

            bookings = entitymanager.createNamedQuery("Booking.findAll").getResultList();

            if (bookings.size() > 0) {
                for (Booking g : bookings) {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

                }
            }

            entitymanager.getTransaction().commit();
        } catch (Exception e) {

        }

        return bookings;
    }

    public int createBooking(Booking booking) {
        int id = 0;
        try {
            entitymanager.getTransaction().begin();
            entitymanager.persist(booking);
            entitymanager.flush();
            entitymanager.getTransaction().commit();

            id = booking.getBookingId();

        } catch (Exception e) {

        }

        return id;
    }

    public boolean deleteBooking(Booking booking) {
        try {
            entitymanager.getTransaction().begin();

            // Find the Booking first
            Booking dataBooking = entitymanager.find(Booking.class, booking.getBookingId());

            if (dataBooking != null) {
                entitymanager.remove(booking);

                getBookings();
            }

            entitymanager.getTransaction().commit();

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Booking updateBooking(Booking newBooking) {
        Booking updatedBooking = null;

        try {
            entitymanager.getTransaction().begin();

            Booking bookingData = entitymanager.find(Booking.class, newBooking.getBookingId());

            if (bookingData != null) {
                bookingData.setBookingRoomGuestCollection(newBooking.getBookingRoomGuestCollection());
                bookingData.setCheckInDate(newBooking.getCheckInDate());
                bookingData.setCheckOutDate(newBooking.getCheckOutDate());
                bookingData.setContactEmail(newBooking.getContactEmail());
                bookingData.setContactPerson(newBooking.getContactPerson());
                bookingData.setCustomer(newBooking.getCustomer());
                bookingData.setGuestCollection(newBooking.getGuestCollection());
                bookingData.setPaymentCollection(newBooking.getPaymentCollection());
                bookingData.setPaymentStatusCode(newBooking.getPaymentStatusCode());
                bookingData.setTotalAmount(newBooking.getTotalAmount());
            }

            entitymanager.getTransaction().commit();

            getBookings();

        } catch (Exception e) {
        }

        return updatedBooking;
    }

    public List<Booking> getBookingsByCustomerId(int customerId) {
        List<Booking> bookings = null;
        try {
            entitymanager.getTransaction().begin();

            bookings = entitymanager.createNamedQuery("Booking.findByCustomerId")
                    .setParameter("customerId", customerId).getResultList();

            entitymanager.getTransaction().commit();
        } catch (Exception e) {

        }

        return bookings;
    }

    public List<BookingRoomGuest> getBookingRoomGuests() {
        List<BookingRoomGuest> bookingRoomGuests = null;

        try {
            entitymanager.getTransaction().begin();

            bookingRoomGuests = entitymanager.createNamedQuery("BookingRoomGuest.findAll")
                    .getResultList();

            entitymanager.getTransaction().commit();
        } catch (Exception e) {

        }

        return bookingRoomGuests;
    }

    public List<Booking> getBookingsByTakenDates(Date checkInDate, Date checkOutDate) {
        List<Booking> bookings = null;

        try {
            entitymanager.getTransaction().begin();

            // Find bookings that are within the checkInDate and checkOutDate
            bookings = entitymanager.createNamedQuery("Booking.findByTakenDate")
                    .setParameter("checkInDate", checkInDate, TemporalType.DATE)
                    .setParameter("checkOutDate", checkOutDate, TemporalType.DATE)
                    .getResultList();

            entitymanager.getTransaction().commit();
        } catch (Exception e) {

        }

        return bookings;
    }

    public Booking getBookingByBookingId(int bookingId) {
        Booking booking = null;

        try {
            entitymanager.getTransaction().begin();

            // Find bookings that are within the checkInDate and checkOutDate
            booking = (Booking) entitymanager.createNamedQuery("Booking.findByBookingId")
                    .setParameter("bookingId", bookingId)
                    .getSingleResult();

            entitymanager.getTransaction().commit();
        } catch (Exception e) {

        }

        return booking;
    }

    public boolean createBookingRoomGuest(BookingRoomGuest bookingRoomGuest) {
        try {
            entitymanager.getTransaction().begin();
            entitymanager.persist(bookingRoomGuest);
            entitymanager.getTransaction().commit();
            return true;
        } catch (Exception e) {

        }

        return false;
    }

    public boolean deleteBookingRoomGuest(BookingRoomGuest bookingRoomGuest) {
        try {
            entitymanager.getTransaction().begin();

            // Find the BookingRoomGuest first
            BookingRoomGuest brgData = entitymanager.find(BookingRoomGuest.class, bookingRoomGuest.getBookingRoomGuestPK());

            if (brgData != null) {
                entitymanager.remove(brgData);

            }

            entitymanager.getTransaction().commit();

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // This can only change the room of the guest
    public BookingRoomGuest updateBookingRoomGuest(BookingRoomGuest brg) {
        BookingRoomGuest updatedBRG = null;
        RoomController rc = new RoomController(emfactory);

        try {
            entitymanager.getTransaction().begin();
            BookingRoomGuest brgData = entitymanager.find(BookingRoomGuest.class, brg.getBookingRoomGuestPK());

            if (brgData != null) {
                int newRoomId = brg.getBookingRoomGuestPK().getRoomId();
                Room newRoom = rc.getRoomByRoomId(newRoomId);

                if (newRoom != null) {
                    BookingRoomGuestPK brgDataPK = brgData.getBookingRoomGuestPK();
                    brgDataPK.setRoomId(newRoomId);

                    brgData.setRoom(newRoom);
                } else {
                    // throw error! room cannot be null
                }
            }

            entitymanager.getTransaction().commit();

            getBookings();

        } catch (Exception e) {
        }

        return updatedBRG;
    }

    public static void CreateSampleBooking(EntityManagerFactory emfactoryb) {
        BookingController x = new BookingController(emfactoryb);
        CustomerController y = new CustomerController(emfactoryb);
        GuestController z = new GuestController(emfactoryb);
        RoomController a = new RoomController(emfactoryb);
        PaymentController b = new PaymentController(emfactoryb);
        RoomService rf = new RoomService(emfactoryb);

        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        int newId = 0;
        Booking newBooking = null;

        try {
            Date date1 = fmt.parse("2017-06-01");
            Date date2 = fmt.parse("2017-07-02");

            // 1. Find/Create Customer first
            Customer c = y.findCustomerById(1000);

            // 2. Search Rooms - final product should be ArrayList<Room> availableRooms
            List<Room> rooms = a.getRooms();
            SearchRoomRequest req = new SearchRoomRequest();

            date1 = fmt.parse("2017-04-15");
            date2 = fmt.parse("2017-04-20");

            req.setCheckInDate(date1);
            req.setCheckOutDate(date2);

            ArrayList<String> rtc = new ArrayList<>();
            rtc.add("DLX");
            rtc.add("SGL");
            req.setRoomTypeCode(rtc);

            // 2a Search for available rooms
            ArrayList<Room> availableRooms = rf.findAvailableRooms(req);

            if (availableRooms.size() <= 0) {
                // Throw excepion - sorry no more rooms!
            } else {
                // 2b Pick the rooms that you want
                // IMPORTANT - total occupancy should be checked! (total >= numOfGuests)
                int numOfGuests = 6;

                // 2c put room choices into the rcList
                ArrayList<RoomChoice> rcList = new ArrayList<>();
                RoomChoice rc = new RoomChoice();
                rc.setRoomTypeCode("DLX"); // 1 DLX room
                rc.setQuantity(1);
                rc.setTotalMaxOccupancy(4); // this came from ROOM_TYPE record
                rcList.add(rc);

                rc = new RoomChoice();
                rc.setRoomTypeCode("SGL"); // 2 SGL rooms
                rc.setQuantity(2);
                rc.setTotalMaxOccupancy(2); // this came from ROOM_TYPE record
                rcList.add(rc);

                // Expected result - 4 pax on DLX, 1 on each SGL room
                rcList = rf.allocateRoomPerGuest(rcList, numOfGuests);

                // 2d Show user the pax allocation - do they want to continue? 
                // 3. Create Booking reoord
                newBooking = new Booking();
                newBooking.setBookingId(0);
                newBooking.setCheckInDate(date1);
                newBooking.setCheckOutDate(date2);
                newBooking.setContactEmail("new@mcbeal.com");
                newBooking.setContactPerson("Porschia Derossi");
                newBooking.setCurrencyCode("AUD");
                newBooking.setCustomer(c);
                newBooking.setPaymentStatusCode("UP");
                newBooking.setTotalAmount(1500.00);

                newId = x.createBooking(newBooking);

                newBooking = x.getBookingByBookingId(newId);

                Date date3 = fmt.parse("1990-03-26");

                // 4. Create Guest records
                ArrayList<Guest> guests = new ArrayList<>();

                // Guest 1
                Guest newGuest = new Guest();
                newGuest.setBooking(newBooking);
                newGuest.setCity("Brisbane");
                newGuest.setCountry("Australia");
                newGuest.setDob(date3);
                newGuest.setEmailAddress("mikeesheean@gomo.com");
                newGuest.setFirstName("Briana");
                newGuest.setLastName("Sheean");
                newGuest.setGuestId(0);
                newGuest.setPhoneNumber("+61413456765");
                newGuest.setPostalCode("1234");
                newGuest.setStreet("Spring Street");
                newGuest.setTitle("Mrs.");
                newId = z.createGuest(newGuest);
                newGuest = z.getGuestById(newId);
                guests.add(newGuest);

                // Guest 2
                newGuest = new Guest();
                newGuest.setBooking(newBooking);
                newGuest.setCity("Melbourne");
                newGuest.setCountry("Australia");
                newGuest.setDob(date3);
                newGuest.setEmailAddress("bknowles@gomo.com");
                newGuest.setFirstName("Beyonce");
                newGuest.setLastName("Knowles");
                newGuest.setGuestId(0);
                newGuest.setPhoneNumber("+61413456765");
                newGuest.setPostalCode("1234");
                newGuest.setStreet("Spring Street");
                newGuest.setTitle("Mrs.");
                newId = z.createGuest(newGuest);
                newGuest = z.getGuestById(newId);
                guests.add(newGuest);

                // Guest 3
                newGuest = new Guest();
                newGuest.setBooking(newBooking);
                newGuest.setCity("Brisbane");
                newGuest.setCountry("Australia");
                newGuest.setDob(date3);
                newGuest.setEmailAddress("homersimpson@gomo.com");
                newGuest.setFirstName("Homer");
                newGuest.setLastName("Simpson");
                newGuest.setGuestId(0);
                newGuest.setPhoneNumber("+61413456765");
                newGuest.setPostalCode("1234");
                newGuest.setStreet("Spring Street");
                newGuest.setTitle("Mr.");
                newId = z.createGuest(newGuest);
                newGuest = z.getGuestById(newId);
                guests.add(newGuest);

                // Guest 4
                newGuest = new Guest();
                newGuest.setBooking(newBooking);
                newGuest.setCity("Melbourne");
                newGuest.setCountry("Australia");
                newGuest.setDob(date3);
                newGuest.setEmailAddress("bknowles@gomo.com");
                newGuest.setFirstName("Jules");
                newGuest.setLastName("Knowles");
                newGuest.setGuestId(0);
                newGuest.setPhoneNumber("+61413456765");
                newGuest.setPostalCode("1234");
                newGuest.setStreet("Spring Street");
                newGuest.setTitle("Mr.");
                newId = z.createGuest(newGuest);
                newGuest = z.getGuestById(newId);
                guests.add(newGuest);

                // Guest 5
                newGuest = new Guest();
                newGuest.setBooking(newBooking);
                newGuest.setCity("Brisbane");
                newGuest.setCountry("Australia");
                newGuest.setDob(date3);
                newGuest.setEmailAddress("homersimpson@gomo.com");
                newGuest.setFirstName("Leticia");
                newGuest.setLastName("Simpson");
                newGuest.setGuestId(0);
                newGuest.setPhoneNumber("+61413456765");
                newGuest.setPostalCode("1234");
                newGuest.setStreet("Spring Street");
                newGuest.setTitle("Ms.");
                newId = z.createGuest(newGuest);
                newGuest = z.getGuestById(newId);
                guests.add(newGuest);

                // Guest 6
                newGuest = new Guest();
                newGuest.setBooking(newBooking);
                newGuest.setCity("Sydney");
                newGuest.setCountry("Australia");
                newGuest.setDob(date3);
                newGuest.setEmailAddress("homersimpson@gomo.com");
                newGuest.setFirstName("Peter");
                newGuest.setLastName("Griffin");
                newGuest.setGuestId(0);
                newGuest.setPhoneNumber("+61413456765");
                newGuest.setPostalCode("1234");
                newGuest.setStreet("Spring Street");
                newGuest.setTitle("Mr.");
                newId = z.createGuest(newGuest);
                newGuest = z.getGuestById(newId);
                guests.add(newGuest);

                // 5. Create BookingRoomGuest record
                // Note: Booking Room Guest is created via RoomService.createBookingRoomGuests
                // brgPKList size should be equal to the number of guests
                ArrayList<BookingRoomGuestPK> brgPKList = rf.createBookingRoomGuest(newBooking.getBookingId(), rcList, availableRooms, guests);

                if (brgPKList != null) {
                    for (BookingRoomGuestPK brgPK : brgPKList) {
                        BookingRoomGuest brg = new BookingRoomGuest(brgPK);
                        brg.setBooking(newBooking);
                        brg.setGuest(newGuest);
                        brg.setRoom(FinderService.findRoomByRoomId(availableRooms, brgPK.getRoomId()));
                        x.createBookingRoomGuest(brg);
                    }
                }

                // 6. Add Payment record 
                int numberOfPayments = 2;
                int paymentNumber = 0;

                Payment p1 = new Payment(paymentNumber++, newBooking.getBookingId());
                p1.setBooking(newBooking);
                p1.setPaymentAmount(500.00);
                p1.setCurrencyCode("AUD");
                p1.setPaymentMethodCode(PaymentMethodCode.CASH.code());
                p1.setPaymentDate(new Date());

                b.createPayment(p1);

                p1 = new Payment(paymentNumber++, newBooking.getBookingId());
                p1.setBooking(newBooking);
                p1.setPaymentAmount(1000.00);
                p1.setCurrencyCode("AUD");
                p1.setPaymentMethodCode(PaymentMethodCode.VISA.code());
                p1.setPaymentDate(new Date());

                b.createPayment(p1);

                newBooking = x.getBookingByBookingId(newBooking.getBookingId());
                
                // trigger will change the status of the booking payment status
                // trigger will also reward/deduct points to customers
                // trigger will also upgrade the customer based on current points

                x.close();
                y.close();
                z.close();
                a.close();
            }

        } catch (Exception e) {

        }
        //List<Booking> bList = x.getBookingsByCustomerId(1000);

    }

    public static void main(String args[]) {

        EntityManagerFactory emfactoryb = Persistence.createEntityManagerFactory("HotelManagementSystemPUB");
        BookingController x = new BookingController(emfactoryb);
        CustomerController y = new CustomerController(emfactoryb);
        GuestController z = new GuestController(emfactoryb);
        RoomController a = new RoomController(emfactoryb);

        List<Booking> newBookings = x.getBookings();

//         int index = 1;
//         for (Guest g : newBookings.get(0).getGuestCollection())
//         {
//             if (index == 1)
//             {
//                g.setFirstName("Horacio");
//                g.setLastName("Warpole");
//             }
//             else
//             {
//                 g.setFirstName("Viola");
//                 g.setLastName("Davis");
//             }
//         }
//         
//         x.updateBooking(newBookings.get(0));
//      
        CreateSampleBooking(emfactoryb);
        x.close();
        y.close();
        z.close();
        a.close();

    }

}
