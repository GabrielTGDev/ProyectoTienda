package org.example;

import dbconnection.DBConnection;
import java.sql.*;

public class Main {
    public static void main(String[] args) {
        Connection connection = DBConnection.getConnection();
    }
}