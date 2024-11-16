package edu.hotelmanagment.wrapper;

import edu.hotelmanagment.model.Item;
import edu.hotelmanagment.util.ConnectionPool;
import edu.hotelmanagment.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WrapperItem
{
    private static final String SQL_SELECT="select * from item_view";
    private static final String SQL_INSERT="insert into item (Name,ItemTypeID)values(?,?)";
    private static final String SQL_UPDATE="update item set Name=?,ItemTypeID=? where ItemID=?";
    private static final String SQL_DELETE="delete from item where ItemID=?";
    private static final String SQL_SELECT_BY_ID = "select * from item_view where ItemID=?";

    public static List<Item> selectAll()
    {
        List<Item> retVal = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DBUtil.getConnection();
            preparedStatement = connection.prepareStatement(SQL_SELECT);
            resultSet = preparedStatement.executeQuery();


            while (resultSet.next())
                retVal.add(new Item(resultSet.getInt("ItemID"),resultSet.getString("Name"),resultSet.getDouble("Price"),
                       resultSet.getString("Item_Type")));

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

    /*
    public static int insert(Item i)
    {
        int retVal = 0;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DBUtil.getConnection();
            preparedStatement = connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, i.getName());
            preparedStatement.setInt(2, i.getItemTypeID());

            retVal = preparedStatement.executeUpdate();

            //postavljanje istog primarnog kljuca na objektu kao i u bazi
            if(retVal != 0)
            {
                resultSet = preparedStatement.getGeneratedKeys();
                if(resultSet.next())
                {
                    i.setItemID(resultSet.getInt(1));
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




    public static void update(Item i)
    {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try
        {
            connection=DBUtil.getConnection();
            preparedStatement =connection.prepareStatement(SQL_UPDATE);

            preparedStatement.setString(1, i.getName());
            preparedStatement.setInt(2, i.getItemTypeID());
            preparedStatement.setInt(3,i.getItemID());

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

     */

    public static Item selectById(int id)
    {
        Item retVal = null;
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
                retVal=new Item(resultSet.getInt("ItemID"),resultSet.getString("Name"),resultSet.getDouble("Price"),
                        resultSet.getString("Item_Type"));
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

