package edu.hotelmanagment.dao;

import edu.hotelmanagment.model.Guest;
import edu.hotelmanagment.model.ReservationHasGuest;
import edu.hotelmanagment.util.ConnectionPool;
import edu.hotelmanagment.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservationHasGuestDAO
{
    public static final String SQL_INSERT="insert into reservation_has_guest(ReservationID,GuestID) values(?,?)";
    private static final String SQL_SELECT_ALL_GUESTS_ON_RESERVATION="select * from all_guests_on_reservation where ReservationID=?";

    public static int insert(ReservationHasGuest rg)
    {
        int retVal = 0;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DBUtil.getConnection();
            preparedStatement = connection.prepareStatement(SQL_INSERT);

            preparedStatement.setInt(1, rg.getReservationID());
            preparedStatement.setInt(2, rg.getGuestID());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            ConnectionPool.getInstance().checkIn(connection);
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }

            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return retVal;
    }


    public static List<Guest> selectAllFromReservation(Integer reservationID)
    {
        List<Guest> retVal = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DBUtil.getConnection();
            preparedStatement = connection.prepareStatement(SQL_SELECT_ALL_GUESTS_ON_RESERVATION);
            preparedStatement.setInt(1, reservationID);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next())
                retVal.add(new Guest(resultSet.getInt("GuestID"),resultSet.getString("First_Name"),
                        resultSet.getString("Last_Name"),resultSet.getString("Passport_Number"),
                        resultSet.getString("Email"),resultSet.getString("Phone_Number")));

        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            ConnectionPool.getInstance().checkIn(connection);
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return retVal;
    }
}
