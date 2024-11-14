package edu.hotelmanagment.controller;

import edu.hotelmanagment.model.Employee;
import edu.hotelmanagment.wrapper.WrapperEmployee;

import java.util.List;

public class ControllerEmployee {
    public List<Employee> getAll()
    {
        return WrapperEmployee.selectAll();
    };

    public void insert(Employee e)
    {
        WrapperEmployee.insert(e);
    };

    public void update(Employee e)
    {
        WrapperEmployee.update(e);
    };

    public void delete(int id)
    {
        WrapperEmployee.delete(id);
    };

    public Employee getGuestById(int id)
    {
        return WrapperEmployee.selectById(id);
    };
}
