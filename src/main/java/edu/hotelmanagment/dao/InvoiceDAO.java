package edu.hotelmanagment.dao;

import edu.hotelmanagment.model.Invoice;
import edu.hotelmanagment.util.ConnectionPool;
import edu.hotelmanagment.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InvoiceDAO
{

    private static final String SQL_SELECT="select * from invoice";
    private static final String SQL_GENERATE_NEW_INVOCIE="{call GenerateInvoice(?,?,?)}";


    public static int generateNewInvoice(int guestId,int reservationId,int paymentTypeId )
    {
        int retVal = 0;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DBUtil.getConnection();
            preparedStatement = connection.prepareStatement(SQL_GENERATE_NEW_INVOCIE, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setInt(1, guestId);
            preparedStatement.setInt(2, reservationId);
            preparedStatement.setInt(3, paymentTypeId);

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


    public static List<Invoice> selectAll()
    {
        List<Invoice> retVal = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DBUtil.getConnection();
            preparedStatement = connection.prepareStatement(SQL_SELECT);
            resultSet = preparedStatement.executeQuery();


            while (resultSet.next())
                retVal.add(new Invoice(resultSet.getInt("InvoiceID"),resultSet.getDouble("Total_Amount"),resultSet.getDate("Issued_date"),
                        resultSet.getInt("GuestID"),resultSet.getInt("ReservationID"),resultSet.getInt("PaymentTypeID")));

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
