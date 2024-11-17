package edu.hotelmanagment.wrapper;

import edu.hotelmanagment.model.Item;
import edu.hotelmanagment.model.ReservationHasItem;
import edu.hotelmanagment.util.ConnectionPool;
import edu.hotelmanagment.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WrapperReservationHasItem
{
    public static final String SQL_INSERT="insert into reservation_has_item(Date,Quantity,ItemID,ReservationID) values(?,?,?,?)";

    public static int insert(ReservationHasItem ri)
    {
        int retVal = 0;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DBUtil.getConnection();
            preparedStatement = connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setDate(1,ri.getDate());
            preparedStatement.setInt(2,ri.getQuantity());
            preparedStatement.setDouble(3,ri.getItemID());
            preparedStatement.setInt(4,ri.getReservationID());

            preparedStatement.executeUpdate();

            //postavljanje istog primarnog kljuca na objektu kao i u bazi
            if(retVal != 0)
            {
                resultSet = preparedStatement.getGeneratedKeys();
                if(resultSet.next())
                {
                    ri.setReservationHasItemID(resultSet.getInt(1));
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
}
