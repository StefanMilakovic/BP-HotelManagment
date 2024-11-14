package edu.hotelmanagment.wrapper;

import edu.hotelmanagment.model.Reservation;
import edu.hotelmanagment.util.ConnectionPool;
import edu.hotelmanagment.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WrapperReservation
{
    //ALTER TABLE table_name AUTO_INCREMENT = value;


    private static final String SQL_SELECT="select * from reservation";
    private static final String SQL_INSERT="insert into reservation (Check_in_date,Check_out_date,Number_of_Guests," +
            "GuestID,RoomID,ReservationTypeID,EmployeeID)values(?,?,?,?,?,?,?)";
    private static final String SQL_UPDATE="update reservation set Check_in_date=?,Check_out_date=?,Number_of_Guests=?," +
            "GuestID=?,RoomID=?,ReservationTypeID=?,EmployeeID=? where ReservationID=?";
    private static final String SQL_DELETE="delete from reservation where ReservationID=?";
    private static final String SQL_SELECT_BY_ID = "select * from reservation where ReservationID=?";


    public static List<Reservation> selectAll()
    {
        List<Reservation> retVal = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DBUtil.getConnection();
            preparedStatement = connection.prepareStatement(SQL_SELECT);
            resultSet = preparedStatement.executeQuery();
            System.out.println("cita rezervacije");
            while (resultSet.next())
                retVal.add(new Reservation(resultSet.getInt("ReservationID"),resultSet.getDate("Check_in_date"),
                        resultSet.getDate("Check_out_date"),resultSet.getInt("Number_of_Guests"),
                        resultSet.getInt("GuestID"),resultSet.getInt("RoomID"),
                        resultSet.getInt("ReservationTypeID"),resultSet.getInt("EmployeeID")));
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

        public static int insert(Reservation r)
    {
        int retVal = 0;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DBUtil.getConnection();
            preparedStatement = connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setDate(1,r.getCheckInDate());
            preparedStatement.setDate(2,r.getCheckOutDate());
            preparedStatement.setInt(3,r.getNumberOfGuests());
            preparedStatement.setInt(4,r.getGuestID());
            preparedStatement.setInt(5,r.getRoomID());
            preparedStatement.setInt(6,r.getReservationTypeID());
            preparedStatement.setInt(7,r.getEmployeeID());

            retVal = preparedStatement.executeUpdate();

            //postavljanje istog primarnog kljuca na objektu kao i u bazi
            if(retVal != 0)
            {
                resultSet = preparedStatement.getGeneratedKeys();
                if(resultSet.next())
                {
                    r.setReservationID(resultSet.getInt(1));
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


    public static void update(Reservation r)
    {
        System.out.println(r);
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try
        {
            connection=DBUtil.getConnection();
            preparedStatement =connection.prepareStatement(SQL_UPDATE);

            preparedStatement.setDate(1,r.getCheckInDate());
            preparedStatement.setDate(2,r.getCheckOutDate());
            preparedStatement.setInt(3,r.getNumberOfGuests());
            preparedStatement.setInt(4,r.getGuestID());
            preparedStatement.setInt(5,r.getRoomID());
            preparedStatement.setInt(6,r.getReservationTypeID());
            preparedStatement.setInt(7,r.getEmployeeID());
            preparedStatement.setInt(8,r.getReservationID());

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

    public static Reservation selectById(int id)
    {
        Reservation retVal = null;
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

                retVal=new Reservation(resultSet.getInt("ReservationID"),resultSet.getDate("Check_in_date"),
                        resultSet.getDate("Check_out_date"),resultSet.getInt("Number_of_Guests"),
                        resultSet.getInt("GuestID"),resultSet.getInt("RoomID"),
                        resultSet.getInt("ReservationTypeID"),resultSet.getInt("EmployeeID"));
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
