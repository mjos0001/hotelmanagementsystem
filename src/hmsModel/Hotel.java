/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hmsModel;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author mrkjse
 */
@Entity
@Table(name = "HOTEL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Hotel.findAll", query = "SELECT h FROM Hotel h")
    , @NamedQuery(name = "Hotel.findByHotelId", query = "SELECT h FROM Hotel h WHERE h.hotelId = :hotelId")
    , @NamedQuery(name = "Hotel.findByHotelName", query = "SELECT h FROM Hotel h WHERE h.hotelName = :hotelName")
    , @NamedQuery(name = "Hotel.findByConstructionYear", query = "SELECT h FROM Hotel h WHERE h.constructionYear = :constructionYear")
    , @NamedQuery(name = "Hotel.findByCountry", query = "SELECT h FROM Hotel h WHERE h.country = :country")
    , @NamedQuery(name = "Hotel.findByCity", query = "SELECT h FROM Hotel h WHERE h.city = :city")
    , @NamedQuery(name = "Hotel.findByAddress", query = "SELECT h FROM Hotel h WHERE h.address = :address")
    , @NamedQuery(name = "Hotel.findByContactNumber", query = "SELECT h FROM Hotel h WHERE h.contactNumber = :contactNumber")
    , @NamedQuery(name = "Hotel.findByEmailAddress", query = "SELECT h FROM Hotel h WHERE h.emailAddress = :emailAddress")
    , @NamedQuery(name = "Hotel.findByHotelTypeCode", query = "SELECT h FROM Hotel h WHERE h.hotelTypeCode = :hotelTypeCode")})
public class Hotel implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @GeneratedValue(generator="HotelSeq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name="HotelSeq",sequenceName="HOTEL_hotel_id_SEQ", allocationSize=1) 
    @Column(name = "HOTEL_ID")
    private int hotelId;
    @Basic(optional = false)
    @Column(name = "HOTEL_NAME")
    private String hotelName;
    @Basic(optional = false)
    @Column(name = "CONSTRUCTION_YEAR")
    @Temporal(TemporalType.TIMESTAMP)
    private Date constructionYear;
    @Basic(optional = false)
    @Column(name = "COUNTRY")
    private String country;
    @Basic(optional = false)
    @Column(name = "CITY")
    private String city;
    @Basic(optional = false)
    @Column(name = "ADDRESS")
    private String address;
    @Basic(optional = false)
    @Column(name = "CONTACT_NUMBER")
    private String contactNumber;
    @Basic(optional = false)
    @Column(name = "EMAIL_ADDRESS")
    private String emailAddress;
    @Basic(optional = false)
    @Column(name = "HOTEL_TYPE_CODE")
    private String hotelTypeCode;

    public Hotel() {
    }

    public Hotel(int hotelId) {
        this.hotelId = hotelId;
    }

    public Hotel(int hotelId, String hotelName, Date constructionYear, String country, String city, String address, String contactNumber, String emailAddress, String hotelTypeCode) {
        this.hotelId = hotelId;
        this.hotelName = hotelName;
        this.constructionYear = constructionYear;
        this.country = country;
        this.city = city;
        this.address = address;
        this.contactNumber = contactNumber;
        this.emailAddress = emailAddress;
        this.hotelTypeCode = hotelTypeCode;
    }

    public int getHotelId() {
        return hotelId;
    }

    public void setHotelId(int hotelId) {
        this.hotelId = hotelId;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public Date getConstructionYear() {
        return constructionYear;
    }

    public void setConstructionYear(Date constructionYear) {
        this.constructionYear = constructionYear;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getHotelTypeCode() {
        return hotelTypeCode;
    }

    public void setHotelTypeCode(String hotelTypeCode) {
        this.hotelTypeCode = hotelTypeCode;
    }
    
}
