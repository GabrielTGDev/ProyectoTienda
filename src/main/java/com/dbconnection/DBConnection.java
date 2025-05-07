package com.dbconnection;

import java.sql.*;

public class DBConnection {

    private static final String URL = "jdbc:mysql://localhost:3307/tienda";
    private static final String USER = "root";
    private static final String PSW = "";

    public static ResultSet executeQuery(String query) throws SQLException {
        Connection connection = DriverManager.getConnection(URL, USER, PSW);
        Statement statement = connection.createStatement();
        return statement.executeQuery(query);
    }

    public static int executeUpdate(String query) throws SQLException {
        Connection connection = DriverManager.getConnection(URL, USER, PSW);
        Statement statement = connection.createStatement();
        return statement.executeUpdate(query);
    }

}
