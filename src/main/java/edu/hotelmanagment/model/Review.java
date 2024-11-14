package edu.hotelmanagment.model;

public class Review
{
    public Integer reviewID;
    public Integer GuestID;
    public Integer rating;
    public String description;
    public Integer ReservationID;

    public Review(Integer reviewID, Integer guestID, Integer rating, String description, Integer reservationID)
    {
        this.reviewID = reviewID;
        GuestID = guestID;
        this.rating = rating;
        this.description = description;
        ReservationID = reservationID;
    }

    public Integer getReviewID()
    {
        return reviewID;
    }

    public void setReviewID(Integer reviewID)
    {
        this.reviewID = reviewID;
    }

    public Integer getGuestID()
    {
        return GuestID;
    }

    public void setGuestID(Integer guestID)
    {
        GuestID = guestID;
    }

    public Integer getRating()
    {
        return rating;
    }

    public void setRating(Integer rating)
    {
        this.rating = rating;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public Integer getReservationID()
    {
        return ReservationID;
    }

    public void setReservationID(Integer reservationID)
    {
        ReservationID = reservationID;
    }

    @Override
    public String toString()
    {
        return "Review{" +
                "reviewID=" + reviewID +
                ", GuestID=" + GuestID +
                ", rating=" + rating +
                ", description='" + description + '\'' +
                ", ReservationID=" + ReservationID +
                '}';
    }
}
