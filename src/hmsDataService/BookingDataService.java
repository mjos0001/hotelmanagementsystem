/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hmsDataService;

import hmsService.BookingService;
import hmsModel.Room;
import hmsModel.BookingRoomGuest;
import hmsModel.BookingRoomGuestPK;
import hmsModel.Booking;
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
public class BookingDataService {

    EntityManagerFactory emfactory = null;

    @PersistenceUnit(unitName = "HotelManagementSystemPUB")
    EntityManager entitymanager = null;

    public BookingDataService(EntityManagerFactory emf) {
        if (!emf.isOpen()) {
            // Should throw an error - no connection found!
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
            entitymanager.getTransaction().commit();
        } catch (Exception e) {
            // Error in getting bookings
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
            // Error in inserting booking record
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
        RoomDataService rc = new RoomDataService(emfactory);

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
                    // throw error! room cannot be found in record
                }
            }

            entitymanager.getTransaction().commit();

        } catch (Exception e) {
        }

        return updatedBRG;
    }

    public static void main(String args[]) {

        EntityManagerFactory emfactoryb = Persistence.createEntityManagerFactory("HotelManagementSystemPUB");
        BookingDataService x = new BookingDataService(emfactoryb);
        CustomerDataService y = new CustomerDataService(emfactoryb);
        GuestDataService z = new GuestDataService(emfactoryb);
        RoomDataService a = new RoomDataService(emfactoryb);

        List<Booking> newBookings = x.getBookings();

        BookingService.CreateSampleBooking(emfactoryb);
        x.close();
        y.close();
        z.close();
        a.close();

    }

}
