package edu.hotelmanagment.model;

import java.sql.Date;

public class ReservationView
{
    public Integer ReservationID;
    public java.sql.Date checkInDate;
    public java.sql.Date checkOutDate;
    public Integer numberOfGuests;
    public String guestName;
    public Integer roomID;
    public String reservationType;
    public  String employeeName;

    public ReservationView(Integer reservationID, Date checkInDate, Date checkOutDate, Integer numberOfGuests, String guestName, Integer roomID, String reservationType, String employeeName)
    {
        ReservationID = reservationID;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.numberOfGuests = numberOfGuests;
        this.roomID = roomID;
        this.guestName = guestName;
        this.reservationType = reservationType;
        this.employeeName = employeeName;
    }


    public Integer getReservationID()
    {
        return ReservationID;
    }

    public void setReservationID(Integer reservationID)
    {
        ReservationID = reservationID;
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

    public String getGuestName()
    {
        return guestName;
    }

    public void setGuestName(String guestName)
    {
        this.guestName = guestName;
    }

    public String getReservationType()
    {
        return reservationType;
    }

    public void setReservationType(String reservationType)
    {
        this.reservationType = reservationType;
    }

    public String getEmployeeName()
    {
        return employeeName;
    }

    public void setEmployeeName(String employeeName)
    {
        this.employeeName = employeeName;
    }

    @Override
    public String toString()
    {
        return "ReservationNOVO{" +
                "ReservationID=" + ReservationID +
                ", checkInDate=" + checkInDate +
                ", checkOutDate=" + checkOutDate +
                ", numberOfGuests=" + numberOfGuests +
                ", roomID=" + roomID +
                ", guestName='" + guestName + '\'' +
                ", reservationType='" + reservationType + '\'' +
                ", employeeName='" + employeeName + '\'' +
                '}';
    }
}
