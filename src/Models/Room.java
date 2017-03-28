/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author mrkjse
 */
@Entity
@Table(name = "ROOM")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Room.findAll", query = "SELECT r FROM Room r")
    , @NamedQuery(name = "Room.findByRoomId", query = "SELECT r FROM Room r WHERE r.roomId = :roomId")
    , @NamedQuery(name = "Room.findByRoomNumber", query = "SELECT r FROM Room r WHERE r.roomNumber = :roomNumber")
    , @NamedQuery(name = "Room.findByRoomPrice", query = "SELECT r FROM Room r WHERE r.roomPrice = :roomPrice")
    , @NamedQuery(name = "Room.findByRoomDescription", query = "SELECT r FROM Room r WHERE r.roomDescription = :roomDescription")
    , @NamedQuery(name = "Room.findByHotelId", query = "SELECT r FROM Room r WHERE r.hotelId = :hotelId")
    , @NamedQuery(name = "Room.findByRoomTypeCode", query = "SELECT r FROM Room r WHERE r.roomTypeCode = :roomTypeCode")})
public class Room implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ROOM_ID")
    private BigDecimal roomId;
    @Basic(optional = false)
    @Column(name = "ROOM_NUMBER")
    private String roomNumber;
    @Basic(optional = false)
    @Column(name = "ROOM_PRICE")
    private BigDecimal roomPrice;
    @Basic(optional = false)
    @Column(name = "ROOM_DESCRIPTION")
    private String roomDescription;
    @Basic(optional = false)
    @Column(name = "HOTEL_ID")
    private BigInteger hotelId;
    @Basic(optional = false)
    @Column(name = "ROOM_TYPE_CODE")
    private String roomTypeCode;
    @ManyToMany(mappedBy = "roomCollection")
    private Collection<Facility> facilityCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "room")
    private Collection<BookingRoomGuest> bookingRoomGuestCollection;

    public Room() {
    }

    public Room(BigDecimal roomId) {
        this.roomId = roomId;
    }

    public Room(BigDecimal roomId, String roomNumber, BigDecimal roomPrice, String roomDescription, BigInteger hotelId, String roomTypeCode) {
        this.roomId = roomId;
        this.roomNumber = roomNumber;
        this.roomPrice = roomPrice;
        this.roomDescription = roomDescription;
        this.hotelId = hotelId;
        this.roomTypeCode = roomTypeCode;
    }

    public BigDecimal getRoomId() {
        return roomId;
    }

    public void setRoomId(BigDecimal roomId) {
        this.roomId = roomId;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public BigDecimal getRoomPrice() {
        return roomPrice;
    }

    public void setRoomPrice(BigDecimal roomPrice) {
        this.roomPrice = roomPrice;
    }

    public String getRoomDescription() {
        return roomDescription;
    }

    public void setRoomDescription(String roomDescription) {
        this.roomDescription = roomDescription;
    }

    public BigInteger getHotelId() {
        return hotelId;
    }

    public void setHotelId(BigInteger hotelId) {
        this.hotelId = hotelId;
    }

    public String getRoomTypeCode() {
        return roomTypeCode;
    }

    public void setRoomTypeCode(String roomTypeCode) {
        this.roomTypeCode = roomTypeCode;
    }

    @XmlTransient
    public Collection<Facility> getFacilityCollection() {
        return facilityCollection;
    }

    public void setFacilityCollection(Collection<Facility> facilityCollection) {
        this.facilityCollection = facilityCollection;
    }

    @XmlTransient
    public Collection<BookingRoomGuest> getBookingRoomGuestCollection() {
        return bookingRoomGuestCollection;
    }

    public void setBookingRoomGuestCollection(Collection<BookingRoomGuest> bookingRoomGuestCollection) {
        this.bookingRoomGuestCollection = bookingRoomGuestCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (roomId != null ? roomId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Room)) {
            return false;
        }
        Room other = (Room) object;
        if ((this.roomId == null && other.roomId != null) || (this.roomId != null && !this.roomId.equals(other.roomId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Models.Room[ roomId=" + roomId + " ]";
    }
    
}
