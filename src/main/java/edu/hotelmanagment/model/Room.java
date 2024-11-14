package edu.hotelmanagment.model;

public class Room
{
    public Integer roomID;
    public Integer roomNumber;
    public Integer floor;
    public String roomType;
    public String bedType;
    public Double pricePerNight;
    public String amenities;

    public Room(Integer roomID, Integer roomNumber, Integer floor, String roomType, String bedType, Double pricePerNight, String amenities)
    {
        this.roomID = roomID;
        this.roomNumber = roomNumber;
        this.floor = floor;
        this.roomType = roomType;
        this.bedType = bedType;
        this.pricePerNight = pricePerNight;
        this.amenities = amenities;
    }

    public Integer getRoomID()
    {
        return roomID;
    }

    public void setRoomID(Integer roomID)
    {
        this.roomID = roomID;
    }

    public Integer getRoomNumber()
    {
        return roomNumber;
    }

    public void setRoomNumber(Integer roomNumber)
    {
        this.roomNumber = roomNumber;
    }

    public Integer getFloor()
    {
        return floor;
    }

    public void setFloor(Integer floor)
    {
        this.floor = floor;
    }

    public String getRoomType()
    {
        return roomType;
    }

    public void setRoomType(String roomType)
    {
        this.roomType = roomType;
    }

    public String getBedType()
    {
        return bedType;
    }

    public void setBedType(String bedType)
    {
        this.bedType = bedType;
    }

    public String getAmenities()
    {
        return amenities;
    }

    public void setAmenities(String amenities)
    {
        this.amenities = amenities;
    }

    public Double getPricePerNight()
    {
        return pricePerNight;
    }

    public void setPricePerNight(Double pricePerNight)
    {
        this.pricePerNight = pricePerNight;
    }

    @Override
    public String toString()
    {
        return "Room{" +
                "roomID=" + roomID +
                ", roomNumber=" + roomNumber +
                ", floor=" + floor +
                ", roomType='" + roomType + '\'' +
                ", bedType='" + bedType + '\'' +
                ", pricePerNight=" + pricePerNight +
                ", amenities='" + amenities + '\'' +
                '}'+"\n";
    }
}
