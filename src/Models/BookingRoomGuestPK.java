/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.io.Serializable;
import java.math.BigInteger;
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
    private BigInteger bookingId;
    @Basic(optional = false)
    @Column(name = "GUEST_ID")
    private BigInteger guestId;
    @Basic(optional = false)
    @Column(name = "ROOM_ID")
    private BigInteger roomId;

    public BookingRoomGuestPK() {
    }

    public BookingRoomGuestPK(BigInteger bookingId, BigInteger guestId, BigInteger roomId) {
        this.bookingId = bookingId;
        this.guestId = guestId;
        this.roomId = roomId;
    }

    public BigInteger getBookingId() {
        return bookingId;
    }

    public void setBookingId(BigInteger bookingId) {
        this.bookingId = bookingId;
    }

    public BigInteger getGuestId() {
        return guestId;
    }

    public void setGuestId(BigInteger guestId) {
        this.guestId = guestId;
    }

    public BigInteger getRoomId() {
        return roomId;
    }

    public void setRoomId(BigInteger roomId) {
        this.roomId = roomId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (bookingId != null ? bookingId.hashCode() : 0);
        hash += (guestId != null ? guestId.hashCode() : 0);
        hash += (roomId != null ? roomId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BookingRoomGuestPK)) {
            return false;
        }
        BookingRoomGuestPK other = (BookingRoomGuestPK) object;
        if ((this.bookingId == null && other.bookingId != null) || (this.bookingId != null && !this.bookingId.equals(other.bookingId))) {
            return false;
        }
        if ((this.guestId == null && other.guestId != null) || (this.guestId != null && !this.guestId.equals(other.guestId))) {
            return false;
        }
        if ((this.roomId == null && other.roomId != null) || (this.roomId != null && !this.roomId.equals(other.roomId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Models.BookingRoomGuestPK[ bookingId=" + bookingId + ", guestId=" + guestId + ", roomId=" + roomId + " ]";
    }
    
}
