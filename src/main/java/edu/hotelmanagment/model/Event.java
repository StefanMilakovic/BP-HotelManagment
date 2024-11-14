package edu.hotelmanagment.model;

import java.sql.Date;

public class Event
{
    public Integer eventID;
    public String eventName;
    public java.sql.Date eventDate;
    public String eventLocation;

    public Event(Integer eventID, String eventName, Date eventDate, String eventLocation)
    {
        this.eventID = eventID;
        this.eventName = eventName;
        this.eventDate = eventDate;
        this.eventLocation = eventLocation;
    }

    public Integer getEventID()
    {
        return eventID;
    }

    public void setEventID(Integer eventID)
    {
        this.eventID = eventID;
    }

    public String getEventName()
    {
        return eventName;
    }

    public void setEventName(String eventName)
    {
        this.eventName = eventName;
    }

    public Date getEventDate()
    {
        return eventDate;
    }

    public void setEventDate(Date eventDate)
    {
        this.eventDate = eventDate;
    }

    public String getEventLocation()
    {
        return eventLocation;
    }

    public void setEventLocation(String eventLocation)
    {
        this.eventLocation = eventLocation;
    }

    @Override
    public String toString()
    {
        return "Event{" +
                "eventID=" + eventID +
                ", eventName='" + eventName + '\'' +
                ", eventDate=" + eventDate +
                ", eventLocation='" + eventLocation + '\'' +
                '}';
    }
}
