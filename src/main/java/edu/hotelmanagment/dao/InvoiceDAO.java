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
    private static final String SQL_INSERT="insert into invoice (Total_Amount,Issued_date,GuestID,ReservationID,PaymentTypeID)values(?,?,?,?,?)";
    private static final String SQL_UPDATE="update invoice set Total_Amount=?,Issued_date=?,GuestID=?,ReservationID=?,PaymentTypeID=? where InvoiceID=?";
    private static final String SQL_DELETE="delete from invoice where InvoiceID=?";
    private static final String SQL_SELECT_BY_ID = "select * from invoice where InvoiceID=?";

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

    public static int insert(Invoice i)
    {
        int retVal = 0;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DBUtil.getConnection();
            preparedStatement = connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setDouble(1, i.getTotalAmount());
            preparedStatement.setDate(2, i.getIssuedDate());
            preparedStatement.setInt(3, i.getGuestID());
            preparedStatement.setInt(4, i.getReservationID());
            preparedStatement.setInt(5, i.getPaymentTypeID());

            retVal = preparedStatement.executeUpdate();

            //postavljanje istog primarnog kljuca na objektu kao i u bazi
            if(retVal != 0)
            {
                resultSet = preparedStatement.getGeneratedKeys();
                if(resultSet.next())
                {
                    i.setInvoiceID(resultSet.getInt(1));
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


    public static void update(Invoice i)
    {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try
        {
            connection=DBUtil.getConnection();
            preparedStatement =connection.prepareStatement(SQL_UPDATE);

            preparedStatement.setDouble(1, i.getTotalAmount());
            preparedStatement.setDate(2, i.getIssuedDate());
            preparedStatement.setInt(3, i.getGuestID());
            preparedStatement.setInt(4, i.getReservationID());
            preparedStatement.setInt(5, i.getPaymentTypeID());
            preparedStatement.setInt(6, i.getInvoiceID());

            int rowsUpdated=preparedStatement.executeUpdate();//vraca broj azuriranih redova

            if (rowsUpdated > 0)
            {
                System.out.println("Rows updated: " + rowsUpdated);
            }
        }catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally {
            ConnectionPool.getInstance().checkIn(connection);
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static int delete(int id)
    {
        int retVal = 0;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DBUtil.getConnection();
            preparedStatement = connection.prepareStatement(SQL_DELETE, Statement.NO_GENERATED_KEYS);
            preparedStatement.setInt(1, id);
            retVal = preparedStatement.executeUpdate();

            //opciono
            if (retVal > 0)
            {
                System.out.println("Rows deleted: " + retVal);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            ConnectionPool.getInstance().checkIn(connection);
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return retVal;
    }

    public static Invoice selectById(int id)
    {
        Invoice retVal = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try
        {
            connection=DBUtil.getConnection();
            preparedStatement=connection.prepareStatement(SQL_SELECT_BY_ID,Statement.NO_GENERATED_KEYS);
            preparedStatement.setInt(1,id);
            resultSet=preparedStatement.executeQuery();
            if(resultSet.next())
            {
                retVal=new Invoice(resultSet.getInt("InvoiceID"),resultSet.getDouble("Total_Amount"),resultSet.getDate("Issued_date"),
                        resultSet.getInt("GuestID"),resultSet.getInt("ReservationID"),resultSet.getInt("PaymentTypeID"));
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
