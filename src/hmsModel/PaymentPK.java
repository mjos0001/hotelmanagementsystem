/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hmsModel;

import java.io.Serializable;
;
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
    private long paymentNumber;
    @Basic(optional = false)
    @Column(name = "BOOKING_ID")
    private long bookingId;

    public PaymentPK() {
    }

    public PaymentPK(long paymentNumber, long bookingId) {
        this.paymentNumber = paymentNumber;
        this.bookingId = bookingId;
    }

    public long getPaymentNumber() {
        return paymentNumber;
    }

    public void setPaymentNumber(long paymentNumber) {
        this.paymentNumber = paymentNumber;
    }

    public long getBookingId() {
        return bookingId;
    }

    public void setBookingId(long bookingId) {
        this.bookingId = bookingId;
    }

    @Override
    public String toString() {
        return "Models.PaymentPK[ paymentNumber=" + paymentNumber + ", bookingId=" + bookingId + " ]";
    }
    
}
