package edu.hotelmanagment.model;

import java.sql.Date;
import java.util.Objects;

public class Invoice
{
    public Integer InvoiceID;
    public Double totalAmount;
    public java.sql.Date issuedDate;
    public Integer guestID;
    public Integer reservationID;
    public Integer paymentTypeID;

    public Invoice(Integer invoiceID, Double totalAmount, Date issuedDate, Integer guestID, Integer reservationID, Integer paymentTypeID)
    {
        InvoiceID = invoiceID;
        this.totalAmount = totalAmount;
        this.issuedDate = issuedDate;
        this.guestID = guestID;
        this.reservationID = reservationID;
        this.paymentTypeID = paymentTypeID;
    }

    public Integer getInvoiceID()
    {
        return InvoiceID;
    }

    public void setInvoiceID(Integer invoiceID)
    {
        InvoiceID = invoiceID;
    }

    public Double getTotalAmount()
    {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount)
    {
        this.totalAmount = totalAmount;
    }

    public Date getIssuedDate()
    {
        return issuedDate;
    }

    public void setIssuedDate(Date issuedDate)
    {
        this.issuedDate = issuedDate;
    }

    public Integer getGuestID()
    {
        return guestID;
    }

    public void setGuestID(Integer guestID)
    {
        this.guestID = guestID;
    }

    public Integer getReservationID()
    {
        return reservationID;
    }

    public void setReservationID(Integer reservationID)
    {
        this.reservationID = reservationID;
    }

    public Integer getPaymentTypeID()
    {
        return paymentTypeID;
    }

    public void setPaymentTypeID(Integer paymentTypeID)
    {
        this.paymentTypeID = paymentTypeID;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Invoice invoice = (Invoice) o;
        return Objects.equals(InvoiceID, invoice.InvoiceID) && Objects.equals(totalAmount, invoice.totalAmount) && Objects.equals(issuedDate, invoice.issuedDate) && Objects.equals(guestID, invoice.guestID) && Objects.equals(reservationID, invoice.reservationID) && Objects.equals(paymentTypeID, invoice.paymentTypeID);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(InvoiceID, totalAmount, issuedDate, guestID, reservationID, paymentTypeID);
    }

    @Override
    public String toString()
    {
        return "Invoice{" +
                "InvoiceID=" + InvoiceID +
                ", totalAmount=" + totalAmount +
                ", issuedDate=" + issuedDate +
                ", guestID=" + guestID +
                ", reservationID=" + reservationID +
                ", paymentTypeID=" + paymentTypeID +
                '}';
    }
}
