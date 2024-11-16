package edu.hotelmanagment.model;

public class Item
{
    public Integer itemID;
    public String name;
    public Double price;
    public String itemType;

    public Item(Integer itemID, String name, Double price, String itemType)
    {
        this.itemID = itemID;
        this.name = name;
        this.price = price;
        this.itemType = itemType;
    }

    public Integer getItemID()
    {
        return itemID;
    }

    public void setItemID(Integer itemID)
    {
        this.itemID = itemID;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Double getPrice()
    {
        return price;
    }

    public void setPrice(Double price)
    {
        this.price = price;
    }

    public String getItemType()
    {
        return itemType;
    }

    public void setItemType(String itemType)
    {
        this.itemType = itemType;
    }

    @Override
    public String toString()
    {
        return itemID+","+name+","+price+","+itemType;

    }
}
