package dbconnection;

import javax.swing.*;
import java.sql.*;

public class DBConnection {
    private static final String DBMS = "mysql";
    private static final String HOST = "localhost";
    private static final int PORT = 3306;
    private static final String DB = "tienda";
    private static final String URL = "jdbc:" + DBMS + "://" + HOST + ":" + PORT + "/" + DB;
    private static final String USER = "root";
    private static final String PASSWORD = "1234";
    private static Connection conn = null;
    private static Statement stmt = null;

//    Se intenta establecer al conexión
    public static void openConn() {
        try {
            String url = URL + "?zeroDateTimeBehavior=convertToNull";
            conn = DriverManager.getConnection(url, USER, PASSWORD);
            System.out.println(url);
//            Se carga el objeto de la clase Statement que se utilizará para realizar las consultas
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error en la conexión");
        }
    }

//    Cuando se cierre la aplicación hay que cerrar la conexión a la BBDD
    public static void closeConn(Boolean showMsg) {
        try {
            conn.close();
            System.out.println("Unconnected");
            if (showMsg)
                JOptionPane.showMessageDialog(null, "Se cerró la conexión con la BBDD");
        } catch (Exception ex) {
            if (showMsg)
                JOptionPane.showMessageDialog(null, "Error al cerrar la conexión");
        }
    }

    public static Connection getConn() throws NullPointerException {
        return conn;
    }

    public static Statement getStmt() throws NullPointerException {
        return stmt;
    }
}