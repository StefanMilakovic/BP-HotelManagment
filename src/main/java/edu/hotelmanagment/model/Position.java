package edu.hotelmanagment.model;

import java.util.Objects;

public class Position
{
    public Integer PositionID;
    public String PositionName;
    //public String Description;
    public Double Salary;

    public Position(String positionName, Double salary)
    {
        PositionName = positionName;
        Salary = salary;
    }

    public Position(Integer positionID, String positionName, Double salary)
    {
        PositionID = positionID;
        PositionName = positionName;
        Salary = salary;
    }

    public Integer getPositionID()
    {
        return PositionID;
    }

    public void setPositionID(Integer positionID)
    {
        PositionID = positionID;
    }

    public String getPositionName()
    {
        return PositionName;
    }

    public void setPositionName(String positionName)
    {
        PositionName = positionName;
    }

    /*
    public String getDescription()
    {
        return Description;
    }

    public void setDescription(String description)
    {
        Description = description;
    }

     */

    public Double getSalary()
    {
        return Salary;
    }

    public void setSalary(Double salary)
    {
        Salary = salary;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return Objects.equals(PositionID, position.PositionID) && Objects.equals(PositionName, position.PositionName) && Objects.equals(Salary, position.Salary);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(PositionID, PositionName, Salary);
    }

    @Override
    public String toString()
    {
        return  "PositionID=" + PositionID + ", PositionName='" + PositionName + '\'' + ", Salary=" + Salary +"\n";
    }
}
