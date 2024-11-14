package edu.hotelmanagment.model;

import java.util.Objects;

public class Guest
{
    public Integer GuestID;
    public String FirstName;
    public String LastName;
    public String passportNumber;
    public String email;
    public String phoneNumber;

    public Guest(String firstName, String lastName, String passportNumber, String email, String phoneNumber)
    {
        FirstName = firstName;
        LastName = lastName;
        this.passportNumber = passportNumber;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public Guest(Integer guestID, String firstName, String lastName, String passportNumber, String email, String phoneNumber)
    {
        GuestID = guestID;
        FirstName = firstName;
        LastName = lastName;
        this.passportNumber = passportNumber;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public Integer getGuestID()
    {
        return GuestID;
    }

    public void setGuestID(Integer guestID)
    {
        GuestID = guestID;
    }

    public String getFirstName()
    {
        return FirstName;
    }

    public void setFirstName(String firstName)
    {
        FirstName = firstName;
    }

    public String getLastName()
    {
        return LastName;
    }

    public void setLastName(String lastName)
    {
        LastName = lastName;
    }

    public String getPassportNumber()
    {
        return passportNumber;
    }

    public void setPassportNumber(String passportNumber)
    {
        this.passportNumber = passportNumber;
    }

    public String getPhoneNumber()
    {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber)
    {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Guest guest = (Guest) o;
        return Objects.equals(GuestID, guest.GuestID) && Objects.equals(FirstName, guest.FirstName) && Objects.equals(LastName, guest.LastName) && Objects.equals(passportNumber, guest.passportNumber) && Objects.equals(email, guest.email) && Objects.equals(phoneNumber, guest.phoneNumber);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(GuestID, FirstName, LastName, passportNumber, email, phoneNumber);
    }

    @Override
    public String toString()
    {
        return "Guest{" +
                "GuestID=" + GuestID +
                ", FirstName='" + FirstName + '\'' +
                ", LastName='" + LastName + '\'' +
                ", passportNumber='" + passportNumber + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}'+"\n";
    }
}
