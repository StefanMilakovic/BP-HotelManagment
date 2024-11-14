package edu.hotelmanagment.wrapper;
import edu.hotelmanagment.model.Position;
import edu.hotelmanagment.util.ConnectionPool;
import edu.hotelmanagment.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WrapperPosition
{
    private static final String SQL_SELECT="select * from position";
    private static final String SQL_INSERT="insert into position (Position_Name,Salary)values(?,?)";
    private static final String SQL_UPDATE="update position set Position_Name=?,Salary=? where PositionID=?";
    private static final String SQL_DELETE="delete from position where PositionID=?";

    public static List<Position> selectAll()
    {
        List<Position> retVal = new ArrayList<Position>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = DBUtil.getConnection();
            statement = connection.prepareStatement(SQL_SELECT);
            resultSet = statement.executeQuery();

            while (resultSet.next())
                retVal.add(new Position(resultSet.getInt("PositionID"),resultSet.getString("Position_Name"), resultSet.getDouble("Salary")));
        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            ConnectionPool.getInstance().checkIn(connection);
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return retVal;
    }

    public static int insert(Position p)
    {
        int retVal = 0;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DBUtil.getConnection();
            preparedStatement = connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, p.getPositionName());
            preparedStatement.setDouble(2, p.getSalary());
            retVal = preparedStatement.executeUpdate();

            //postavljanje istog primarnog kljuca na objektu kao i u bazi
            if(retVal != 0)
            {
                resultSet = preparedStatement.getGeneratedKeys();
                if(resultSet.next())
                {
                    p.setPositionID(resultSet.getInt(1));
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


    public static void update(Position p)
    {
        Connection connection = null;
        PreparedStatement statement = null;
        try
        {
            connection=DBUtil.getConnection();
            statement=connection.prepareStatement(SQL_UPDATE);

            statement.setString(1,p.getPositionName());
            statement.setDouble(2,p.getSalary());
            statement.setInt(3,p.getPositionID());

            statement.executeUpdate();
        }catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally {
            ConnectionPool.getInstance().checkIn(connection);
            try {
                if (statement != null) {
                    statement.close();
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
}
