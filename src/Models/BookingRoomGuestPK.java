/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.io.Serializable;
;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author mrkjse
 */
@Embeddable
public class BookingRoomGuestPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "BOOKING_ID")
    private int bookingId;
    @Basic(optional = false)
    @Column(name = "GUEST_ID")
    private int guestId;
    @Basic(optional = false)
    @Column(name = "ROOM_ID")
    private int roomId;

    public BookingRoomGuestPK() {
    }

    public BookingRoomGuestPK(int bookingId, int guestId, int roomId) {
        this.bookingId = bookingId;
        this.guestId = guestId;
        this.roomId = roomId;
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public int getGuestId() {
        return guestId;
    }

    public void setGuestId(int guestId) {
        this.guestId = guestId;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }
    
    @Override
    public String toString() {
        return "Models.BookingRoomGuestPK[ bookingId=" + bookingId + ", guestId=" + guestId + ", roomId=" + roomId + " ]";
    }
    
}
