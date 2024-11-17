package edu.hotelmanagment.model;

public class ReservationHasGuest
{
    public Integer reservationID;
    public Integer guestID;

    public ReservationHasGuest(Integer reservationID, Integer guestID)
    {
        this.reservationID = reservationID;
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

    public Integer getGuestID()
    {
        return guestID;
    }

    public void setGuestID(Integer guestID)
    {
        this.guestID = guestID;
    }

    @Override
    public String toString()
    {
        return "ReservationHasGuest{" +
                "reservationID=" + reservationID +
                ", guestID=" + guestID +
                '}';
    }
}
