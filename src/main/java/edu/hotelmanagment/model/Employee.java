package edu.hotelmanagment.model;

import java.sql.Date;
import java.util.Objects;

public class Employee
{
    public Integer EmployeeID;
    public String FirstName;
    public String LastName;
    public String Email;
    public String phoneNumber;
    public java.sql.Date hireDate;
    public Boolean isActive=true;
    //private Integer isActive;
    public Integer PositionID;

    public Employee(Integer employeeID, String firstName, String lastName, String email, String phoneNumber, Date hireDate, Boolean isActive, Integer positionID)
    {
        EmployeeID = employeeID;
        FirstName = firstName;
        LastName = lastName;
        Email = email;
        this.phoneNumber = phoneNumber;
        this.hireDate = hireDate;
        this.isActive = isActive;
        PositionID = positionID;
    }

    public Integer getEmployeeID()
    {
        return EmployeeID;
    }

    public void setEmployeeID(Integer employeeID)
    {
        EmployeeID = employeeID;
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

    public String getEmail()
    {
        return Email;
    }

    public void setEmail(String email)
    {
        Email = email;
    }

    public String getPhoneNumber()
    {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber)
    {
        this.phoneNumber = phoneNumber;
    }

    public Date getHireDate()
    {
        return hireDate;
    }

    public void setHireDate(Date hireDate)
    {
        this.hireDate = hireDate;
    }

    public Integer getPositionID()
    {
        return PositionID;
    }

    public void setPositionID(Integer positionID)
    {
        PositionID = positionID;
    }

    public Boolean isIsActive()
    {
        return isActive;
    }

    public void setIsActive(Boolean isActive)
    {
        this.isActive = isActive;
    }

    @Override
    public String toString()
    {
        return "Employee{" +
                "EmployeeID=" + EmployeeID +
                ", FirstName='" + FirstName + '\'' +
                ", LastName='" + LastName + '\'' +
                ", Email='" + Email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", hireDate=" + hireDate +
                ", isActive=" + isActive +
                ", PositionID=" + PositionID +
                '}'+"\n";
    }
}
