/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.io.Serializable;
;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author mrkjse
 */
@Entity
@Table(name = "PAYMENT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Payment.findAll", query = "SELECT p FROM Payment p")
    , @NamedQuery(name = "Payment.findByPaymentNumber", query = "SELECT p FROM Payment p WHERE p.paymentPK.paymentNumber = :paymentNumber")
    , @NamedQuery(name = "Payment.findByPaymentDate", query = "SELECT p FROM Payment p WHERE p.paymentDate = :paymentDate")
    , @NamedQuery(name = "Payment.findByPaymentAmount", query = "SELECT p FROM Payment p WHERE p.paymentAmount = :paymentAmount")
    , @NamedQuery(name = "Payment.findByBookingId", query = "SELECT p FROM Payment p WHERE p.paymentPK.bookingId = :bookingId")
    , @NamedQuery(name = "Payment.findByPaymentMethodCode", query = "SELECT p FROM Payment p WHERE p.paymentMethodCode = :paymentMethodCode")})
public class Payment implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PaymentPK paymentPK;
    @Basic(optional = false)
    @Column(name = "PAYMENT_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date paymentDate;
    @Basic(optional = false)
    @Column(name = "PAYMENT_AMOUNT")
    private double paymentAmount;
    @Column(name = "CURRENCY_CODE")
    private String currencyCode;
    @Basic(optional = false)
    @Column(name = "PAYMENT_METHOD_CODE")
    private String paymentMethodCode;
    @JoinColumn(name = "BOOKING_ID", referencedColumnName = "BOOKING_ID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Booking booking;

    public Payment() {
    }

    public Payment(PaymentPK paymentPK) {
        this.paymentPK = paymentPK;
    }

    public Payment(PaymentPK paymentPK, Date paymentDate, double paymentAmount, String paymentMethodCode) {
        this.paymentPK = paymentPK;
        this.paymentDate = paymentDate;
        this.paymentAmount = paymentAmount;
        this.paymentMethodCode = paymentMethodCode;
    }

    public Payment(long paymentNumber, long bookingId) {
        this.paymentPK = new PaymentPK(paymentNumber, bookingId);
    }

    public PaymentPK getPaymentPK() {
        return paymentPK;
    }

    public void setPaymentPK(PaymentPK paymentPK) {
        this.paymentPK = paymentPK;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public double getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(double paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public String getPaymentMethodCode() {
        return paymentMethodCode;
    }

    public void setPaymentMethodCode(String paymentMethodCode) {
        this.paymentMethodCode = paymentMethodCode;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }
    
    public String getCurrencyCode() {
        return currencyCode;
    }
    
    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (paymentPK != null ? paymentPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Payment)) {
            return false;
        }
        Payment other = (Payment) object;
        if ((this.paymentPK == null && other.paymentPK != null) || (this.paymentPK != null && !this.paymentPK.equals(other.paymentPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Models.Payment[ paymentPK=" + paymentPK + " ]";
    }
    
}
