package edu.hotelmanagment.wrapper;

import edu.hotelmanagment.model.Guest;

import edu.hotelmanagment.util.ConnectionPool;
import edu.hotelmanagment.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WrapperGuest
{
    private static final String SQL_SELECT="select * from guest";
    private static final String SQL_INSERT="insert into guest (First_Name,Last_Name,Passport_Number,Email,Phone_Number)values(?,?,?,?,?)";
    private static final String SQL_UPDATE="update guest set First_Name=?,Last_Name=?,Passport_Number=?,Email=?,Phone_Number=? where GuestID=?";
    private static final String SQL_DELETE="delete from guest where GuestID=?";
    private static final String SQL_SELECT_BY_ID = "select * from guest where GuestID=?";

    public static List<Guest> selectAll()
    {
        List<Guest> retVal = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DBUtil.getConnection();
            preparedStatement = connection.prepareStatement(SQL_SELECT);
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

    public static int insert(Guest g)
    {
        int retVal = 0;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DBUtil.getConnection();
            preparedStatement = connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1,g.getFirstName());
            preparedStatement.setString(2,g.getLastName());
            preparedStatement.setString(3,g.getPassportNumber());
            preparedStatement.setString(4,g.getEmail());
            preparedStatement.setString(5,g.getPhoneNumber());

            retVal = preparedStatement.executeUpdate();

            //postavljanje istog primarnog kljuca na objektu kao i u bazi
            if(retVal != 0)
            {
                resultSet = preparedStatement.getGeneratedKeys();
                if(resultSet.next())
                {
                    //System.out.println(resultSet.getInt(1));
                    g.setGuestID(resultSet.getInt(1));
                    System.out.println(g.getGuestID());
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


    public static void update(Guest g)
    {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try
        {
            connection=DBUtil.getConnection();
            preparedStatement =connection.prepareStatement(SQL_UPDATE);

            preparedStatement.setString(1,g.getFirstName());
            preparedStatement.setString(2,g.getLastName());
            preparedStatement.setString(3,g.getPassportNumber());
            preparedStatement.setString(4,g.getEmail());
            preparedStatement.setString(5,g.getPhoneNumber());
            preparedStatement.setInt(6,g.getGuestID());

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

    public static Guest selectById(int id)
    {
        Guest retVal = null;
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
                retVal=new Guest(resultSet.getInt("GuestID"),resultSet.getString("First_Name"),
                        resultSet.getString("Last_Name"),resultSet.getString("Passport_Number"),
                        resultSet.getString("Email"),resultSet.getString("Phone_Number"));
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
