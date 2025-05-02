package dbconnection;

import org.junit.jupiter.api.*;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

class DBConnectionTest {

    @Test
    void testSuccessfulConnection() {
        Connection connection = DBConnection.getConnection();
        assertNotNull(connection, "La conexión no debería ser nula si es exitosa.");
    }
}