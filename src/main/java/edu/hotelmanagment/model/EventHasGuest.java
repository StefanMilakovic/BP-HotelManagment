package edu.hotelmanagment.model;

public class EventHasGuest
{
    public Integer eventID;
    public Integer guestID;

    public EventHasGuest(Integer eventID, Integer guestID)
    {
        this.eventID = eventID;
        this.guestID = guestID;
    }

    public Integer getEventID()
    {
        return eventID;
    }

    public void setEventID(Integer eventID)
    {
        this.eventID = eventID;
    }

    public Integer getGuestID()
    {
        return guestID;
    }

    public void setGuestID(Integer guestID)
    {
        this.guestID = guestID;
    }
}
