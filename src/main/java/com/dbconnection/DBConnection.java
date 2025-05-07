package com.dbconnection;

import java.sql.*;

public class DBConnection {

    private static final String URL = "jdbc:mysql://localhost:3307/tienda";
    private static final String USER = "root";
    private static final String PSW  = "";

    public static Connection getConnection() {
        try {
            // Load the MySQL JDBC driver (it is marked as deprecated in newer versions)
            // Class.forName("com.mysql.jdbc.Driver");

            Connection connection = DriverManager.getConnection(URL, USER, PSW);
            System.out.println("Connected to the database successfully.");
            return connection;
        } catch (Exception e) {
            System.err.println("Failed to connect to the database: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

}
