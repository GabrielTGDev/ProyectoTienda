package dbconnection;

import java.sql.DriverManager;

public class Connection {

    private static final String URL = "jdbc:mysql://localhost:3307/tienda";
    private static final String USER = "root";
    private static final String PSW  = "";

    public static void connect() {
        try {
            java.sql.Connection connection = DriverManager.getConnection(URL, USER, PSW);
            System.out.println("Connected to the database successfully.");
        } catch (Exception e) {
            System.out.println("Failed to connect to the database.");
            e.printStackTrace();
        }
    }

}
