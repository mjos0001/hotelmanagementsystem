/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
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
    , @NamedQuery(name = "Room.findByRoomTypeCode", query = "SELECT r FROM Room r WHERE r.roomType.roomTypeCode = :roomTypeCode")
    , @NamedQuery(name = "Room.findByRoomFacilityNumber", query = "SELECT r FROM Room r INNER JOIN r.facilityCollection rf WHERE rf.facilityNumber = :facilityNumber")
    , @NamedQuery(name = "Room.findByRoomPriceRange", query = "SELECT r FROM Room r WHERE r.roomPrice >= :minRoomPrice AND r.roomPrice <= :maxRoomPrice")
    , @NamedQuery(name = "Room.findByRoomDescription", query = "SELECT r FROM Room r WHERE r.roomDescription = :roomDescription")
    , @NamedQuery(name = "Room.findByHotelId", query = "SELECT r FROM Room r WHERE r.hotelId = :hotelId")
    , @NamedQuery(name = "Room.findByOccupancy", query = "SELECT r FROM Room r INNER JOIN r.roomType rt WHERE rt.maxOccupancy <= :maxOccupancy") })
public class Room implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ROOM_ID")
    private int roomId;
    @Basic(optional = false)
    @Column(name = "ROOM_NUMBER")
    private String roomNumber;
    @Basic(optional = false)
    @Column(name = "ROOM_PRICE")
    private double roomPrice;
    @Basic(optional = false)
    @Column(name = "ROOM_DESCRIPTION")
    private String roomDescription;
    @Basic(optional = false)
    @Column(name = "HOTEL_ID")
    private int hotelId;
    @ManyToMany(mappedBy = "roomCollection")
    private Collection<Facility> facilityCollection;
    @JoinColumn(name = "ROOM_TYPE_CODE", referencedColumnName = "ROOM_TYPE_CODE")
    @ManyToOne(optional = false)
    private RoomType roomType;

    public Room() {
    }

    public Room(int roomId) {
        this.roomId = roomId;
    }

    public Room(int roomId, String roomNumber, double roomPrice, String roomDescription, int hotelId) {
        this.roomId = roomId;
        this.roomNumber = roomNumber;
        this.roomPrice = roomPrice;
        this.roomDescription = roomDescription;
        this.hotelId = hotelId;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public double getRoomPrice() {
        return roomPrice;
    }

    public void setRoomPrice(double roomPrice) {
        this.roomPrice = roomPrice;
    }

    public String getRoomDescription() {
        return roomDescription;
    }

    public void setRoomDescription(String roomDescription) {
        this.roomDescription = roomDescription;
    }

    public int getHotelId() {
        return hotelId;
    }

    public void setHotelId(int hotelId) {
        this.hotelId = hotelId;
    }

    @XmlTransient
    public Collection<Facility> getFacilityCollection() {
        return facilityCollection;
    }

    public void setFacilityCollection(Collection<Facility> facilityCollection) {
        this.facilityCollection = facilityCollection;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomType roomTypeCode) {
        this.roomType = roomTypeCode;
    }

    @Override
    public String toString() {
        return "Models.Room[ roomId=" + roomId + " ]";
    }
    
}
