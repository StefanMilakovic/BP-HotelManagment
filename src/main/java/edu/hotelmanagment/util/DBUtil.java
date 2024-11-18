package edu.hotelmanagment.util;

import java.sql.*;

public class DBUtil {

    private static ConnectionPool connectionPool = ConnectionPool.getInstance();

    public static Connection getConnection() throws SQLException {
        Connection c = connectionPool.checkOut();
        return c;
    }

    public static void close(Connection c) {
        if (c != null)
            connectionPool.checkIn(c);
    }

    public static void close(Statement s) {
        if (s != null)
            try {
                s.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }

    public static void close(ResultSet rs) {
        if (rs != null)
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }

    public static void close(ResultSet rs, Statement s, Connection c) {
        close(rs);
        close(s);
        close(c);
    }

    public static void close(Statement s, Connection c) {
        close(s);
        close(c);
    }
}

