/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hmsModel;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
@Table(name = "FACILITY")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Facility.findAll", query = "SELECT f FROM Facility f")
    , @NamedQuery(name = "Facility.findByFacilityNumber", query = "SELECT f FROM Facility f WHERE f.facilityNumber = :facilityNumber")
    , @NamedQuery(name = "Facility.findByFacilityName", query = "SELECT f FROM Facility f WHERE f.facilityName = :facilityName")})
public class Facility implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "FACILITY_NUMBER")
    private int facilityNumber;
    @Basic(optional = false)
    @Column(name = "FACILITY_NAME")
    private String facilityName;
    @JoinTable(name = "ROOM_FACILITY", joinColumns = {
        @JoinColumn(name = "FACILITY_NUMBER", referencedColumnName = "FACILITY_NUMBER")}, inverseJoinColumns = {
        @JoinColumn(name = "ROOM_ID", referencedColumnName = "ROOM_ID")})
    @ManyToMany
    private Collection<Room> roomCollection;

    public Facility() {
    }

    public Facility(int facilityNumber) {
        this.facilityNumber = facilityNumber;
    }

    public Facility(int facilityNumber, String facilityName) {
        this.facilityNumber = facilityNumber;
        this.facilityName = facilityName;
    }

    public int getFacilityNumber() {
        return facilityNumber;
    }

    public void setFacilityNumber(int facilityNumber) {
        this.facilityNumber = facilityNumber;
    }

    public String getFacilityName() {
        return facilityName;
    }

    public void setFacilityName(String facilityName) {
        this.facilityName = facilityName;
    }

    @XmlTransient
    public Collection<Room> getRoomCollection() {
        return roomCollection;
    }

    public void setRoomCollection(Collection<Room> roomCollection) {
        this.roomCollection = roomCollection;
    }
    
    @Override
    public String toString() {
        return "Models.Facility[ facilityNumber=" + facilityNumber + " ]";
    }
    
}
