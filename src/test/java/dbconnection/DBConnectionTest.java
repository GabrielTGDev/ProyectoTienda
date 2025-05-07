package dbconnection;

import com.dbconnection.DBConnection;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class DBConnectionTest {

    @Test
    void testExecuteQuery() {
        assertDoesNotThrow(() -> {
            var resultSet = DBConnection.executeQuery("SELECT 1");
            assertNotNull(resultSet, "El ResultSet no debería ser nulo para una consulta válida.");
            assertTrue(resultSet.next(), "El ResultSet debería contener al menos un registro.");
            assertEquals(1, resultSet.getInt(1), "El valor devuelto debería ser 1.");
        }, "El método executeQuery no debería lanzar excepciones para una consulta válida.");
    }

    @Test
    void testExecuteUpdate() {
        assertDoesNotThrow(() -> {
            int rowsAffected = DBConnection.executeUpdate("CREATE TEMPORARY TABLE test_table (id INT);");
            assertEquals(0, rowsAffected, "La creación de una tabla temporal no debería afectar filas.");

            rowsAffected = DBConnection.executeUpdate("INSERT INTO test_table (id) VALUES (1);");
            assertEquals(1, rowsAffected, "Debería haberse insertado una fila en la tabla temporal.");

            rowsAffected = DBConnection.executeUpdate("DROP TABLE test_table;");
            assertEquals(0, rowsAffected, "La eliminación de la tabla no debería afectar filas.");
        }, "El método executeUpdate no debería lanzar excepciones para consultas válidas.");
    }

    @Test
    void testInvalidQuery() {
        Exception exception = assertThrows(SQLException.class, () -> {
            DBConnection.executeQuery("SELECT * FROM tabla_inexistente;");
        }, "Debería lanzarse una SQLException para una consulta inválida.");
        assertNotNull(exception.getMessage(), "El mensaje de la excepción no debería ser nulo.");
    }

    @Test
    void testDatabaseConnection() {
        assertDoesNotThrow(() -> {
            Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3307/tienda", "root", ""
            );
            assertNotNull(connection, "La conexión a la base de datos no debería ser nula.");
            assertFalse(connection.isClosed(), "La conexión a la base de datos no debería estar cerrada.");
            connection.close();
        }, "No debería lanzarse ninguna excepción al establecer la conexión a la base de datos.");
    }
}