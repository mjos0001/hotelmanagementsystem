/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hmsService;

import hmsDataService.BookingDataService;
import hmsDataService.CustomerDataService;
import hmsDataService.GuestDataService;
import hmsDataService.PaymentDataService;
import hmsDataService.RoomDataService;
import hmsService.RoomAllocatorService;
import hmsService.FinderService;
import hmsModel.Booking;
import hmsModel.BookingRoomGuest;
import hmsModel.BookingRoomGuestPK;
import hmsModel.Customer;
import hmsModel.Guest;
import hmsModel.Payment;
import hmsModel.PaymentMethodCode;
import hmsModel.Room;
import hmsModel.RoomChoice;
import hmsModel.SearchRoomRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author mrkjse
 */
public class BookingService {
    
    public static void CreateSampleBooking(EntityManagerFactory emfactoryb) 
    {     
        try {
            BookingDataService x = new BookingDataService(emfactoryb);
            CustomerDataService y = new CustomerDataService(emfactoryb);
            GuestDataService z = new GuestDataService(emfactoryb);
            RoomDataService a = new RoomDataService(emfactoryb);
            PaymentDataService b = new PaymentDataService(emfactoryb);
            RoomAllocatorService rf = new RoomAllocatorService(emfactoryb);

            SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
            int newId = 0;
            Booking newBooking = null;

        
            Date date1 = fmt.parse("2017-06-01");
            Date date2 = fmt.parse("2017-07-02");

            // 1. Find/Create Customer first
            Customer c = y.findCustomerById(1000);

            // 2. Search Rooms - final product should be ArrayList<Room> availableRooms
            List<Room> rooms = a.getRooms();
            SearchRoomRequest req = new SearchRoomRequest();

            // add check in and check out to the request
            date1 = fmt.parse("2017-04-15");
            date2 = fmt.parse("2017-04-20");

            req.setCheckInDate(date1);
            req.setCheckOutDate(date2);

            // add room type specs to the request
            ArrayList<String> rtc = new ArrayList<>();
            rtc.add("DLX");
            rtc.add("SGL");
            req.setRoomTypeCode(rtc);

            // 2a Search for available rooms
            ArrayList<Room> availableRooms = rf.findAvailableRooms(req);

            if (availableRooms.size() <= 0) {
                // Throw excepion - sorry no more rooms!
                throw new Exception("No rooms are available to accommodate guest!");
            } else {
                // 2b Pick the rooms that you want
                // IMPORTANT - total occupancy should be checked! (totalOcc >= numOfGuests)
                int numOfGuests = 6;

                // 2c put room choices into the rcList
                ArrayList<RoomChoice> rcList = new ArrayList<>();
                RoomChoice rc = new RoomChoice();
                rc.setRoomTypeCode("DLX"); // 1 DLX room
                rc.setQuantity(1);
                rc.setTotalMaxOccupancy(4); // this came from ROOM_TYPE.MAX_OCCUPANCY column
                rcList.add(rc);

                rc = new RoomChoice();
                rc.setRoomTypeCode("SGL"); // 2 SGL rooms
                rc.setQuantity(2);
                rc.setTotalMaxOccupancy(2); // this came from ROOM_TYPE.MAX_OCCUPANCY column
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
                // Note: Booking Room Guest is created via RoomAllocatorService.createBookingRoomGuests
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
                
                // 6. Compute for the total booking amount
                
                // 6a. calculate the prices of the rooms
                
                // 6b. consider any customer discounts

                // 7. Add Payment record 
                
                // 7a. ask for the number of payments
                int numberOfPayments = 2;
                int paymentNumber = 0;

                // 7b. add payment records
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

    
}
