package edu.hotelmanagment.wrapper;


import edu.hotelmanagment.model.Room;
import edu.hotelmanagment.util.ConnectionPool;
import edu.hotelmanagment.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WrapperRoom
{
    //private static final String SQL_SELECT="select * from room";
    //private static final String SQL_INSERT="insert into room (Room_Number,Floor,RoomTypeID,BedTypeID)values(?,?,,?,?)";
    private static final String SQL_INSERT="{call AddNewRoom(?,?,?,?)}";

    private static final String SQL_UPDATE="update room set Room_Number=?,Floor=?,RoomTypeID=?,BedTypeID=? where RoomID=?";
    private static final String SQL_DELETE="delete from room where RoomID=?";
    private static final String SQL_SELECT_BY_ID = "select * from room where RoomID=?";
    private static final String SQL_SELECT="SELECT * FROM hotel_database.room_details_view;";

    private static final String SQL_AVAILABLE_ROOMS="{call GetAvailableRooms(?,?)}";




    public static List<Room> selectAll()
    {
        List<Room> retVal = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DBUtil.getConnection();
            preparedStatement = connection.prepareStatement(SQL_SELECT);
            resultSet = preparedStatement.executeQuery();


            while (resultSet.next())
                retVal.add(new Room(resultSet.getInt("RoomID"),resultSet.getInt("Room_Number"),
                        resultSet.getInt("Floor"), resultSet.getString("Room_Type"),
                        resultSet.getString("Bed_Type"),resultSet.getDouble("Price_Per_Night"),resultSet.getString("Amenities")));

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

    public static List<Room> getAvailableRooms(java.sql.Date checkIn,java.sql.Date checkOut)
    {
        List<Room> retVal = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try
        {
            connection = DBUtil.getConnection();
            preparedStatement = connection.prepareStatement(SQL_AVAILABLE_ROOMS);

            preparedStatement.setDate(1,checkIn);
            preparedStatement.setDate(2,checkOut);

            resultSet = preparedStatement.executeQuery();


            while (resultSet.next())
                retVal.add(new Room(resultSet.getInt("RoomID"), resultSet.getInt("Room_Number"),
                        resultSet.getInt("Floor"), resultSet.getString("Room_Type"),
                        resultSet.getString("Bed_Type"), resultSet.getDouble("Price_Per_Night"), resultSet.getString("Amenities")));

        } catch (SQLException e)
        {
            e.printStackTrace();

        } finally
        {
            ConnectionPool.getInstance().checkIn(connection);
            try
            {
                if (resultSet != null)
                {
                    resultSet.close();
                }
                if (preparedStatement != null)
                {
                    preparedStatement.close();
                }
            } catch (SQLException ex)
            {
                ex.printStackTrace();
            }
        }
        return retVal;
    }


    public static int insert(Room r)
    {
        int retVal = 0;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {

            connection = DBUtil.getConnection();
            preparedStatement = connection.prepareStatement(SQL_INSERT);

            preparedStatement.setInt(1,r.getFloor());
            preparedStatement.setString(2,r.getRoomType());
            preparedStatement.setString(3, r.getBedType());
            preparedStatement.setString(4,r.getAmenities());

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

/*
    public static void update(Room r)
    {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try
        {
            connection=DBUtil.getConnection();
            preparedStatement =connection.prepareStatement(SQL_UPDATE);

            preparedStatement.setInt(1,r.getRoomNumber());
            preparedStatement.setInt(2,r.getFloor());
            preparedStatement.setInt(3,r.getRoomTypeID());
            preparedStatement.setInt(4,r.getBedTypeID());
            preparedStatement.setInt(5,r.getRoomID());

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
*/
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
/*
    public static Room selectById(int id)
    {
        Room retVal = null;
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
                retVal=new Room(resultSet.getInt("RoomID"),resultSet.getInt("Room_Number"),
                        resultSet.getInt("Floor"), resultSet.getInt("RoomTypeID"),
                        resultSet.getInt("BedTypeID"));
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

     */
}
