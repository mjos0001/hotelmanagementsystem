/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author mrkjse
 */
@Entity
@Table(name = "BOOKING_ROOM_GUEST")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BookingRoomGuest.findAll", query = "SELECT b FROM BookingRoomGuest b")
    , @NamedQuery(name = "BookingRoomGuest.findByBookingId", query = "SELECT b FROM BookingRoomGuest b WHERE b.bookingRoomGuestPK.bookingId = :bookingId")
    , @NamedQuery(name = "BookingRoomGuest.findByGuestId", query = "SELECT b FROM BookingRoomGuest b WHERE b.bookingRoomGuestPK.guestId = :guestId")
    , @NamedQuery(name = "BookingRoomGuest.findByRoomId", query = "SELECT b FROM BookingRoomGuest b WHERE b.bookingRoomGuestPK.roomId = :roomId")})
public class BookingRoomGuest implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected BookingRoomGuestPK bookingRoomGuestPK;
    @JoinColumn(name = "BOOKING_ID", referencedColumnName = "BOOKING_ID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Booking booking;
    @JoinColumn(name = "GUEST_ID", referencedColumnName = "GUEST_ID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Guest guest;
    @JoinColumn(name = "ROOM_ID", referencedColumnName = "ROOM_ID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Room room;

    public BookingRoomGuest() {
    }

    public BookingRoomGuest(BookingRoomGuestPK bookingRoomGuestPK) {
        this.bookingRoomGuestPK = bookingRoomGuestPK;
    }

    public BookingRoomGuest(BigInteger bookingId, BigInteger guestId, BigInteger roomId) {
        this.bookingRoomGuestPK = new BookingRoomGuestPK(bookingId, guestId, roomId);
    }

    public BookingRoomGuestPK getBookingRoomGuestPK() {
        return bookingRoomGuestPK;
    }

    public void setBookingRoomGuestPK(BookingRoomGuestPK bookingRoomGuestPK) {
        this.bookingRoomGuestPK = bookingRoomGuestPK;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public Guest getGuest() {
        return guest;
    }

    public void setGuest(Guest guest) {
        this.guest = guest;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (bookingRoomGuestPK != null ? bookingRoomGuestPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BookingRoomGuest)) {
            return false;
        }
        BookingRoomGuest other = (BookingRoomGuest) object;
        if ((this.bookingRoomGuestPK == null && other.bookingRoomGuestPK != null) || (this.bookingRoomGuestPK != null && !this.bookingRoomGuestPK.equals(other.bookingRoomGuestPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Models.BookingRoomGuest[ bookingRoomGuestPK=" + bookingRoomGuestPK + " ]";
    }
    
}
