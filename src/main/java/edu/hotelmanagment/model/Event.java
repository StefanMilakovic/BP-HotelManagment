package edu.hotelmanagment.model;

import java.sql.Date;

public class Event
{
    public Integer eventID;
    public String name;
    public java.sql.Date eventDate;
    public String location;
    public String description;
    public Integer EmployeeID;

    public Event(Integer eventID, String name, Date eventDate, String location, String description, Integer employeeID)
    {
        this.eventID = eventID;
        this.name = name;
        this.eventDate = eventDate;
        this.location = location;
        this.description = description;
        EmployeeID = employeeID;
    }

    public Integer getEventID()
    {
        return eventID;
    }

    public void setEventID(Integer eventID)
    {
        this.eventID = eventID;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Date getEventDate()
    {
        return eventDate;
    }

    public void setEventDate(Date eventDate)
    {
        this.eventDate = eventDate;
    }

    public String getLocation()
    {
        return location;
    }

    public void setLocation(String location)
    {
        this.location = location;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
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
        return "Event{" +
                "eventID=" + eventID +
                ", name='" + name + '\'' +
                ", eventDate=" + eventDate +
                ", location='" + location + '\'' +
                ", description='" + description + '\'' +
                ", EmployeeID=" + EmployeeID +
                '}';
    }
}
