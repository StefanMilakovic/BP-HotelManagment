package edu.hotelmanagment.dao;

import edu.hotelmanagment.model.EventHasGuest;
import edu.hotelmanagment.util.ConnectionPool;
import edu.hotelmanagment.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EventHasGuestDAO
{
    private static final String SQL_SELECT="select * from event_has_guest";
    private static final String SQL_INSERT="insert into event_has_guest (EventID,GuestID)values(?,?)";

    public static List<EventHasGuest> selectAll()
    {
        List<EventHasGuest> retVal = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DBUtil.getConnection();
            preparedStatement = connection.prepareStatement(SQL_SELECT);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next())
                retVal.add(new EventHasGuest(resultSet.getInt("EventID"),resultSet.getInt("GuestID")));

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

    public static int insert(EventHasGuest eg)
    {
        int retVal = 0;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DBUtil.getConnection();
            preparedStatement = connection.prepareStatement(SQL_INSERT);

            preparedStatement.setInt(1,eg.getEventID());
            preparedStatement.setInt(2,eg.getGuestID());

            retVal = preparedStatement.executeUpdate();

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
}
