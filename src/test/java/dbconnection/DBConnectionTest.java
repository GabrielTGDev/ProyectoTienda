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
}