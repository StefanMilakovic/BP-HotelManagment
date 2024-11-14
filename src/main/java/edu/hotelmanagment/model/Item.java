package edu.hotelmanagment.model;

public class Item
{
    public Integer itemID;
    public String name;
    public Double price;
    public Integer itemTypeID;

    public Item(Integer itemID, String name, Double price, Integer itemTypeID)
    {
        this.itemID = itemID;
        this.name = name;
        this.price = price;
        this.itemTypeID = itemTypeID;
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

    public Integer getItemTypeID()
    {
        return itemTypeID;
    }

    public void setItemTypeID(Integer itemTypeID)
    {
        this.itemTypeID = itemTypeID;
    }

    @Override
    public String toString()
    {
        return "Item{" +
                "itemID=" + itemID +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", itemTypeID=" + itemTypeID +
                '}'+"\n";
    }
}
