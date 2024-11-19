package edu.hotelmanagment.dao;

import edu.hotelmanagment.model.ReservationView;
import edu.hotelmanagment.util.ConnectionPool;
import edu.hotelmanagment.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservationViewDAO
{
    private static final String SQL_SELECT="SELECT * FROM reservation_detailed_view";

    public static List<ReservationView> selectAll()
    {
        List<ReservationView> retVal = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DBUtil.getConnection();
            preparedStatement = connection.prepareStatement(SQL_SELECT);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next())
                retVal.add(new ReservationView(resultSet.getInt("ReservationID"),resultSet.getDate("CheckInDate"),
                        resultSet.getDate("CheckOutDate"),resultSet.getInt("NumberOfGuests"),
                        resultSet.getString("GuestName"),resultSet.getInt("RoomID"),
                        resultSet.getString("ReservationType"),resultSet.getString("EmployeeName")));
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
