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
public class PaymentPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "PAYMENT_NUMBER")
    private BigInteger paymentNumber;
    @Basic(optional = false)
    @Column(name = "BOOKING_ID")
    private BigInteger bookingId;

    public PaymentPK() {
    }

    public PaymentPK(BigInteger paymentNumber, BigInteger bookingId) {
        this.paymentNumber = paymentNumber;
        this.bookingId = bookingId;
    }

    public BigInteger getPaymentNumber() {
        return paymentNumber;
    }

    public void setPaymentNumber(BigInteger paymentNumber) {
        this.paymentNumber = paymentNumber;
    }

    public BigInteger getBookingId() {
        return bookingId;
    }

    public void setBookingId(BigInteger bookingId) {
        this.bookingId = bookingId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (paymentNumber != null ? paymentNumber.hashCode() : 0);
        hash += (bookingId != null ? bookingId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PaymentPK)) {
            return false;
        }
        PaymentPK other = (PaymentPK) object;
        if ((this.paymentNumber == null && other.paymentNumber != null) || (this.paymentNumber != null && !this.paymentNumber.equals(other.paymentNumber))) {
            return false;
        }
        if ((this.bookingId == null && other.bookingId != null) || (this.bookingId != null && !this.bookingId.equals(other.bookingId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Models.PaymentPK[ paymentNumber=" + paymentNumber + ", bookingId=" + bookingId + " ]";
    }
    
}
