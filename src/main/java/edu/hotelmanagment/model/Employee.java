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
    public Integer PositionID;

    public Employee(Integer employeeID, String firstName, String lastName, String email, String phoneNumber, Date hireDate, Integer positionID)
    {
        EmployeeID = employeeID;
        FirstName = firstName;
        LastName = lastName;
        Email = email;
        this.phoneNumber = phoneNumber;
        this.hireDate = hireDate;
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

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Objects.equals(EmployeeID, employee.EmployeeID) && Objects.equals(FirstName, employee.FirstName) && Objects.equals(LastName, employee.LastName) && Objects.equals(Email, employee.Email) && Objects.equals(phoneNumber, employee.phoneNumber) && Objects.equals(hireDate, employee.hireDate) && Objects.equals(PositionID, employee.PositionID);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(EmployeeID, FirstName, LastName, Email, phoneNumber, hireDate, PositionID);
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
                ", PositionID=" + PositionID +
                '}'+"\n";
    }

    public String getFirstAndLastName()
    {
        return FirstName + " " + LastName;
    }
}
