/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author mrkjse
 */
@Entity
@Table(name = "CUSTOMER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Customer.findAll", query = "SELECT c FROM Customer c")
    , @NamedQuery(name = "Customer.findByCustomerId", query = "SELECT c FROM Customer c WHERE c.customerId = :customerId")
    , @NamedQuery(name = "Customer.findByMembershipCredits", query = "SELECT c FROM Customer c WHERE c.membershipCredits = :membershipCredits")
    , @NamedQuery(name = "Customer.findByMembershipTierCode", query = "SELECT c FROM Customer c WHERE c.membership.membershipTierCode = :membershipTierCode")
    , @NamedQuery(name = "Customer.findByBookingId", query = "SELECT c FROM Customer c INNER JOIN c.bookingCollection b WHERE b.bookingId = :bookingId")
    , @NamedQuery(name = "Customer.findByTitle", query = "SELECT c FROM Customer c WHERE c.title = :title")
    , @NamedQuery(name = "Customer.findByFirstName", query = "SELECT c FROM Customer c WHERE c.firstName = :firstName")
    , @NamedQuery(name = "Customer.findByLastName", query = "SELECT c FROM Customer c WHERE c.lastName = :lastName")
    , @NamedQuery(name = "Customer.findByDob", query = "SELECT c FROM Customer c WHERE c.dob = :dob")
    , @NamedQuery(name = "Customer.findByCountry", query = "SELECT c FROM Customer c WHERE c.country = :country")
    , @NamedQuery(name = "Customer.findByCity", query = "SELECT c FROM Customer c WHERE c.city = :city")
    , @NamedQuery(name = "Customer.findByStreet", query = "SELECT c FROM Customer c WHERE c.street = :street")
    , @NamedQuery(name = "Customer.findByPostalCode", query = "SELECT c FROM Customer c WHERE c.postalCode = :postalCode")
    , @NamedQuery(name = "Customer.findByPhoneNumber", query = "SELECT c FROM Customer c WHERE c.phoneNumber = :phoneNumber")
    , @NamedQuery(name = "Customer.findByEmailAddress", query = "SELECT c FROM Customer c WHERE c.emailAddress = :emailAddress")})
public class Customer implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(generator="CustSeq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name="CustSeq",sequenceName="CUST_customer_id_SEQ", allocationSize=1) 
    @Basic(optional = false)
    @Column(name = "CUSTOMER_ID")
    private int customerId;
    @Basic(optional = false)
    @Column(name = "MEMBERSHIP_CREDITS")
    private long membershipCredits;
    @Basic(optional = false)
    @Column(name = "TITLE")
    private String title;
    @Basic(optional = false)
    @Column(name = "FIRST_NAME")
    private String firstName;
    @Basic(optional = false)
    @Column(name = "LAST_NAME")
    private String lastName;
    @Basic(optional = false)
    @Column(name = "DOB")
    @Temporal(TemporalType.DATE)
    private Date dob;
    @Basic(optional = false)
    @Column(name = "COUNTRY")
    private String country;
    @Basic(optional = false)
    @Column(name = "CITY")
    private String city;
    @Basic(optional = false)
    @Column(name = "STREET")
    private String street;
    @Basic(optional = false)
    @Column(name = "POSTAL_CODE")
    private String postalCode;
    @Basic(optional = false)
    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;
    @Basic(optional = false)
    @Column(name = "EMAIL_ADDRESS")
    private String emailAddress;
    @JoinColumn(name = "MEMBERSHIP_TIER_CODE", referencedColumnName = "MEMBERSHIP_TIER_CODE")
    @ManyToOne(optional = false)
    private Membership membership;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customer")
    private Collection<Booking> bookingCollection;

    public Customer() {
    }

    public Customer(int customerId) {
        this.customerId = customerId;
    }

    public Customer(int customerId, long membershipCredits, String title, String firstName, String lastName, Date dob, String country, String city, String street, String postalCode, String phoneNumber, String emailAddress) {
        this.customerId = customerId;
        this.membershipCredits = membershipCredits;
        this.title = title;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
        this.country = country;
        this.city = city;
        this.street = street;
        this.postalCode = postalCode;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public long getMembershipCredits() {
        return membershipCredits;
    }

    public void setMembershipCredits(long membershipCredits) {
        this.membershipCredits = membershipCredits;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
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

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public Membership getMembershipTierCode() {
        return membership;
    }

    public void setMembershipTierCode(Membership membership) {
        this.membership = membership;
    }

    @XmlTransient
    public Collection<Booking> getBookingCollection() {
        return bookingCollection;
    }

    public void setBookingCollection(Collection<Booking> bookingCollection) {
        this.bookingCollection = bookingCollection;
    }

    @Override
    public String toString() {
        return "Models.Customer[ customerId=" + customerId + " ]";
    }
    
}
