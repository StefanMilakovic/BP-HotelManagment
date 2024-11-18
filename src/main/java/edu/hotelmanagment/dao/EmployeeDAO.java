package edu.hotelmanagment.dao;

import edu.hotelmanagment.model.Employee;
import edu.hotelmanagment.util.ConnectionPool;
import edu.hotelmanagment.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO
{
    private static final String SQL_SELECT="select * from employee";
    private static final String SQL_INSERT="insert into employee (First_Name,Last_Name,Email,Phone_Number,Hire_Date,isActive,PositionID)values(?,?,?,?,?,?,?)";
    private static final String SQL_UPDATE="update employee set First_Name=?,Last_Name=?,Email=?,Phone_Number=?,Hire_Date=?,isActive=?,PositionID=? where EmployeeID=?";
    private static final String SQL_DELETE="delete from employee where EmployeeID=?";
    private static final String SQL_SELECT_BY_ID = "select * from employee where EmployeeID=?";
    private static final String SQL_SELECT_RECEPTIONISTS="SELECT * FROM hotel_database.receptionists_employees_view;";
    private static final String SQL_SELECT_HOUSEKEEPERS="SELECT * FROM hotel_database.housekeeping_employees_view;";
    private static final String SQL_SELECT_MANAGERS="SELECT * FROM hotel_database.manager_employees_view;";

    private static final String SQL_SET_ACTIVE = "UPDATE employee SET isActive = ? WHERE EmployeeID = ?";


    public static List<Employee> selectAll()
    {
        List<Employee> retVal = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DBUtil.getConnection();
            preparedStatement = connection.prepareStatement(SQL_SELECT);
            resultSet = preparedStatement.executeQuery();


            while (resultSet.next())
                retVal.add(new Employee(resultSet.getInt("EmployeeID"), resultSet.getString("First_Name"),
                        resultSet.getString("Last_Name"), resultSet.getString("Email"),
                        resultSet.getString("Phone_Number"), resultSet.getDate("Hire_Date"),
                        resultSet.getBoolean("isActive"),resultSet.getInt("PositionID")));

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

    public static List<Employee> selectReceptionists()
    {
        List<Employee> retVal = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try
        {
            connection = DBUtil.getConnection();
            preparedStatement = connection.prepareStatement(SQL_SELECT_RECEPTIONISTS);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next())
            { Employee emp=new Employee(resultSet.getInt("EmployeeID"), resultSet.getString("First_Name"),
                    resultSet.getString("Last_Name"), resultSet.getString("Email"),
                    resultSet.getString("Phone_Number"), resultSet.getDate("Hire_Date"),
                    resultSet.getBoolean("isActive"), resultSet.getInt("PositionID"));
                retVal.add(emp);
            }
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

    public static List<Employee> selectHousekeepers()
    {
        List<Employee> retVal = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try
        {
            connection = DBUtil.getConnection();
            preparedStatement = connection.prepareStatement(SQL_SELECT_HOUSEKEEPERS);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next())
            {
                Employee emp = new Employee(resultSet.getInt("EmployeeID"), resultSet.getString("First_Name"),
                        resultSet.getString("Last_Name"), resultSet.getString("Email"),
                        resultSet.getString("Phone_Number"), resultSet.getDate("Hire_Date"),
                        resultSet.getBoolean("isActive"), resultSet.getInt("PositionID"));
                retVal.add(emp);
            }
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

    public static List<Employee> selectManagers()
    {
        List<Employee> retVal = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try
        {
            connection = DBUtil.getConnection();
            preparedStatement = connection.prepareStatement(SQL_SELECT_MANAGERS);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next())
            {
                Employee emp = new Employee(resultSet.getInt("EmployeeID"), resultSet.getString("First_Name"),
                        resultSet.getString("Last_Name"), resultSet.getString("Email"),
                        resultSet.getString("Phone_Number"), resultSet.getDate("Hire_Date"),
                        resultSet.getBoolean("isActive"), resultSet.getInt("PositionID"));
                retVal.add(emp);
            }
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

    public static int insert(Employee employee)
    {
        int retVal = 0;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DBUtil.getConnection();
            preparedStatement = connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, employee.getFirstName());
            preparedStatement.setString(2, employee.getLastName());
            preparedStatement.setString(3, employee.getEmail());
            preparedStatement.setString(4, employee.getPhoneNumber());
            preparedStatement.setDate(5, employee.getHireDate());
            preparedStatement.setBoolean(6, employee.isIsActive());
            preparedStatement.setInt(7, employee.getPositionID());

            retVal = preparedStatement.executeUpdate();

            if(retVal != 0)
            {
                resultSet = preparedStatement.getGeneratedKeys();
                if(resultSet.next())
                {
                    employee.setEmployeeID(resultSet.getInt(1));
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

    public static void update(Employee employee)
    {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try
        {
            connection=DBUtil.getConnection();
            preparedStatement =connection.prepareStatement(SQL_UPDATE);

            preparedStatement.setString(1, employee.getFirstName());
            preparedStatement.setString(2, employee.getLastName());
            preparedStatement.setString(3, employee.getEmail());
            preparedStatement.setString(4, employee.getPhoneNumber());
            preparedStatement.setDate(5, employee.getHireDate());
            preparedStatement.setInt(6, employee.getPositionID());
            preparedStatement.setBoolean(7, employee.isIsActive());
            preparedStatement.setInt(8, employee.getEmployeeID());

            preparedStatement.executeUpdate();

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
            preparedStatement.executeUpdate();

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

    public static Employee selectById(int id)
    {
        Employee retVal = null;
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
                retVal=new Employee(resultSet.getInt("EmployeeID"), resultSet.getString("First_Name"),
                        resultSet.getString("Last_Name"), resultSet.getString("Email"),
                        resultSet.getString("Phone_Number"), resultSet.getDate("Hire_Date"),
                        resultSet.getBoolean("isActive"),resultSet.getInt("PositionID"));
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

    public static void updateEmployeeStatus(Employee employee)
    {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try
        {
            connection=DBUtil.getConnection();
            preparedStatement =connection.prepareStatement(SQL_SET_ACTIVE);

            preparedStatement.setBoolean(1, employee.isIsActive());
            preparedStatement.setInt(2, employee.getEmployeeID());

            preparedStatement.executeUpdate();

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
}
