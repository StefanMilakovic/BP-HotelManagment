package edu.hotelmanagment.model;

import java.sql.Date;

public class Reservation
{
    public Integer ReservationID;
    public java.sql.Date checkInDate;
    public java.sql.Date checkOutDate;
    public Integer numberOfGuests;
    public Integer guestID;
    public Integer roomID;
    public Integer reservationTypeID;
    public Integer EmployeeID;

    public Reservation(Integer reservationID, Date checkInDate, Date checkOutDate, Integer numberOfGuests, Integer guestID, Integer roomID, Integer reservationTypeID, Integer employeeID)
    {
        ReservationID = reservationID;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.numberOfGuests = numberOfGuests;
        this.guestID = guestID;
        this.roomID = roomID;
        this.reservationTypeID = reservationTypeID;
        EmployeeID = employeeID;
    }
    public Reservation() {}

    public Reservation(Date checkInDate, Date checkOutDate, Integer numberOfGuests, Integer guestID, Integer roomID, Integer reservationTypeID, Integer employeeID)
    {
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.numberOfGuests = numberOfGuests;
        this.guestID = guestID;
        this.roomID = roomID;
        this.reservationTypeID = reservationTypeID;
        EmployeeID = employeeID;
    }

    public Integer getReservationID()
    {
        return ReservationID;
    }

    public void setReservationID(Integer reservationID)
    {
        ReservationID = reservationID;
    }

    public Integer getGuestID()
    {
        return guestID;
    }

    public void setGuestID(Integer guestID)
    {
        guestID = guestID;
    }

    public Date getCheckInDate()
    {
        return checkInDate;
    }

    public void setCheckInDate(Date checkInDate)
    {
        this.checkInDate = checkInDate;
    }

    public Date getCheckOutDate()
    {
        return checkOutDate;
    }

    public void setCheckOutDate(Date checkOutDate)
    {
        this.checkOutDate = checkOutDate;
    }

    public Integer getNumberOfGuests()
    {
        return numberOfGuests;
    }

    public void setNumberOfGuests(Integer numberOfGuests)
    {
        this.numberOfGuests = numberOfGuests;
    }

    public Integer getRoomID()
    {
        return roomID;
    }

    public void setRoomID(Integer roomID)
    {
        this.roomID = roomID;
    }

    public Integer getReservationTypeID()
    {
        return reservationTypeID;
    }

    public void setReservationTypeID(Integer reservationTypeID)
    {
        this.reservationTypeID = reservationTypeID;
    }

    public Integer getEmployeeID()
    {
        return EmployeeID;
    }

    public void setEmployeeID(Integer employeeID)
    {
        EmployeeID = employeeID;
    }


    @Override
    public String toString()
    {
        return "ReservationID=" + ReservationID +
                ", GuestID=" + guestID +
                ", checkInDate=" + checkInDate +
                ", checkOutDate=" + checkOutDate +
                ", numberOfGuests=" + numberOfGuests +
                ", roomID=" + roomID +
                ", reservationTypeID=" + reservationTypeID +
                ", EmployeeID=" + EmployeeID;
    }
}
