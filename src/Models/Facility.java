/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.io.Serializable;
import java.math.BigDecimal;
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
    , @NamedQuery(name = "Facility.findByDescription", query = "SELECT f FROM Facility f WHERE f.description = :description")})
public class Facility implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "FACILITY_NUMBER")
    private BigDecimal facilityNumber;
    @Basic(optional = false)
    @Column(name = "DESCRIPTION")
    private String description;
    @JoinTable(name = "ROOM_FACILITY", joinColumns = {
        @JoinColumn(name = "FACILITY_NUMBER", referencedColumnName = "FACILITY_NUMBER")}, inverseJoinColumns = {
        @JoinColumn(name = "ROOM_ID", referencedColumnName = "ROOM_ID")})
    @ManyToMany
    private Collection<Room> roomCollection;

    public Facility() {
    }

    public Facility(BigDecimal facilityNumber) {
        this.facilityNumber = facilityNumber;
    }

    public Facility(BigDecimal facilityNumber, String description) {
        this.facilityNumber = facilityNumber;
        this.description = description;
    }

    public BigDecimal getFacilityNumber() {
        return facilityNumber;
    }

    public void setFacilityNumber(BigDecimal facilityNumber) {
        this.facilityNumber = facilityNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
        hash += (facilityNumber != null ? facilityNumber.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Facility)) {
            return false;
        }
        Facility other = (Facility) object;
        if ((this.facilityNumber == null && other.facilityNumber != null) || (this.facilityNumber != null && !this.facilityNumber.equals(other.facilityNumber))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Models.Facility[ facilityNumber=" + facilityNumber + " ]";
    }
    
}
