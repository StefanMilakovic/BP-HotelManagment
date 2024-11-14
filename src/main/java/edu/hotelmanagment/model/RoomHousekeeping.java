package edu.hotelmanagment.model;

import java.sql.Date;

public class RoomHousekeeping
{
    public Integer roomHousekeepingID;
    public java.sql.Date date;
    public Integer roomID;
    public Integer employeeID;

    public RoomHousekeeping(Integer roomHousekeepingID, Date date, Integer roomID, Integer employeeID)
    {
        this.roomHousekeepingID = roomHousekeepingID;
        this.date = date;
        this.roomID = roomID;
        this.employeeID = employeeID;
    }

    public Integer getRoomHousekeepingID()
    {
        return roomHousekeepingID;
    }

    public void setRoomHousekeepingID(Integer roomHousekeepingID)
    {
        this.roomHousekeepingID = roomHousekeepingID;
    }

    public Date getDate()
    {
        return date;
    }

    public void setDate(Date date)
    {
        this.date = date;
    }

    public Integer getRoomID()
    {
        return roomID;
    }

    public void setRoomID(Integer roomID)
    {
        this.roomID = roomID;
    }

    public Integer getEmployeeID()
    {
        return employeeID;
    }

    public void setEmployeeID(Integer employeeID)
    {
        this.employeeID = employeeID;
    }

    @Override
    public String toString()
    {
        return "RoomHousekeeping{" +
                "roomHousekeepingID=" + roomHousekeepingID +
                ", date=" + date +
                ", roomID=" + roomID +
                ", employeeID=" + employeeID +
                '}'+"\n";
    }
}
