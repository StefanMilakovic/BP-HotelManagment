package edu.hotelmanagment.model;

import java.sql.Date;

public class ReservationHasItem
{
    public Integer ReservationHasItemID;
    public java.sql.Date date;
    public Integer Quantity;
    public Double totalPrice;
    public Integer ItemID;
    public Integer ReservationID;

    public ReservationHasItem(Integer reservationHasItemID, Date date, Integer quantity, Integer itemID, Integer reservationID)
    {
        ReservationHasItemID = reservationHasItemID;
        this.date = date;
        Quantity = quantity;
        //this.totalPrice = totalPrice;
        ItemID = itemID;
        ReservationID = reservationID;
    }

    public Integer getReservationHasItemID()
    {
        return ReservationHasItemID;
    }

    public void setReservationHasItemID(Integer reservationHasItemID)
    {
        ReservationHasItemID = reservationHasItemID;
    }

    public Date getDate()
    {
        return date;
    }

    public void setDate(Date date)
    {
        this.date = date;
    }

    public Integer getQuantity()
    {
        return Quantity;
    }

    public void setQuantity(Integer quantity)
    {
        Quantity = quantity;
    }

    public Integer getItemID()
    {
        return ItemID;
    }

    public void setItemID(Integer itemID)
    {
        ItemID = itemID;
    }

    public Integer getReservationID()
    {
        return ReservationID;
    }

    public void setReservationID(Integer reservationID)
    {
        ReservationID = reservationID;
    }

    /*
    public Double getTotalPrice()
    {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice)
    {
        this.totalPrice = totalPrice;
    }

     */

    @Override
    public String toString()
    {
        return "ReservationHasItem{" +
                "ReservationHasItemID=" + ReservationHasItemID +
                ", date=" + date +
                ", Quantity=" + Quantity +
                ", ItemID=" + ItemID +
                ", ReservationID=" + ReservationID +
                '}';
    }
}
