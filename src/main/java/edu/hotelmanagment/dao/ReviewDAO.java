package edu.hotelmanagment.dao;

import edu.hotelmanagment.model.Reservation;
import edu.hotelmanagment.model.Review;
import edu.hotelmanagment.util.ConnectionPool;
import edu.hotelmanagment.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReviewDAO
{
    private static final String SQL_SELECT="select * from review";
    private static final String SQL_INSERT="insert into review (GuestID,Rating,Description,ReservationID)values(?,?,?,?)";

    public static List<Review> selectAll()
    {
        List<Review> retVal = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DBUtil.getConnection();
            preparedStatement = connection.prepareStatement(SQL_SELECT);
            resultSet = preparedStatement.executeQuery();


            while (resultSet.next())
                retVal.add(new Review(resultSet.getInt("ReviewID"),resultSet.getInt("GuestID"),
                        resultSet.getInt("Rating"),resultSet.getString("Description"),
                        resultSet.getInt("ReservationID")));

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


    public static int insert(Review r)
    {
        int retVal = 0;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {

            connection = DBUtil.getConnection();
            preparedStatement = connection.prepareStatement(SQL_INSERT);

            preparedStatement.setInt(1, r.getGuestID());
            preparedStatement.setInt(2, r.getRating());
            preparedStatement.setString(3, r.getDescription());
            preparedStatement.setInt(4, r.getReservationID());


            preparedStatement.executeUpdate();
            if(retVal != 0)
            {
                resultSet = preparedStatement.getGeneratedKeys();
                if(resultSet.next())
                {
                    r.setReviewID(resultSet.getInt(1));
                }
            }

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

    public static Double getAverageRating()
    {
        Double retVal = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try
        {
            connection=DBUtil.getConnection();
            preparedStatement=connection.prepareStatement("SELECT * FROM hotel_database.hotel_average_rating;");
            resultSet=preparedStatement.executeQuery();
            if(resultSet.next())
            {
                retVal=resultSet.getDouble(1);
            }

        }catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
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
