package edu.hotelmanagment.controller;

import edu.hotelmanagment.model.Reservation;
import edu.hotelmanagment.wrapper.WrapperReservation;

import java.util.List;

public class ControllerReservation
{
    public List<Reservation> getAll()
    {
        return WrapperReservation.selectAll();
    };

    public void insert(Reservation r)
    {
        WrapperReservation.insert(r);
    };

    public void update(Reservation r)
    {
        WrapperReservation.update(r);
    };

    public void delete(int id)
    {
        WrapperReservation.delete(id);
    };


    public Reservation getReservationById(int id)
    {
        return WrapperReservation.selectById(id);
    };
}
