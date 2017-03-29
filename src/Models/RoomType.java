/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
@Table(name = "ROOM_TYPE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RoomType.findAll", query = "SELECT r FROM RoomType r")
    , @NamedQuery(name = "RoomType.findByRoomTypeCode", query = "SELECT r FROM RoomType r WHERE r.roomTypeCode = :roomTypeCode")
    , @NamedQuery(name = "RoomType.findByRoomType", query = "SELECT r FROM RoomType r WHERE r.roomType = :roomType")
    , @NamedQuery(name = "RoomType.findByMaxOccupancy", query = "SELECT r FROM RoomType r WHERE r.maxOccupancy = :maxOccupancy")})
public class RoomType implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ROOM_TYPE_CODE")
    private String roomTypeCode;
    @Basic(optional = false)
    @Column(name = "ROOM_TYPE")
    private String roomType;
    @Basic(optional = false)
    @Column(name = "MAX_OCCUPANCY")
    private int maxOccupancy;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "roomType")
    private Collection<Room> roomCollection;

    public RoomType() {
    }

    public RoomType(String roomTypeCode) {
        this.roomTypeCode = roomTypeCode;
    }

    public RoomType(String roomTypeCode, String roomType, int maxOccupancy) {
        this.roomTypeCode = roomTypeCode;
        this.roomType = roomType;
        this.maxOccupancy = maxOccupancy;
    }

    public String getRoomTypeCode() {
        return roomTypeCode;
    }

    public void setRoomTypeCode(String roomTypeCode) {
        this.roomTypeCode = roomTypeCode;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public int getMaxOccupancy() {
        return maxOccupancy;
    }

    public void setMaxOccupancy(int maxOccupancy) {
        this.maxOccupancy = maxOccupancy;
    }

    @XmlTransient
    public Collection<Room> getRoomCollection() {
        return roomCollection;
    }

    public void setRoomCollection(Collection<Room> roomCollection) {
        this.roomCollection = roomCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (roomTypeCode != null ? roomTypeCode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RoomType)) {
            return false;
        }
        RoomType other = (RoomType) object;
        if ((this.roomTypeCode == null && other.roomTypeCode != null) || (this.roomTypeCode != null && !this.roomTypeCode.equals(other.roomTypeCode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Models.RoomType[ roomTypeCode=" + roomTypeCode + " ]";
    }
    
}
