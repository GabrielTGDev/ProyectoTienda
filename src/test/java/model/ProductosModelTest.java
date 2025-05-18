package model;

import com.model.ProductosModel;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ProductosModelTest {

    private ProductosModel productosModel;

    @BeforeAll
    public void setUp() {
        productosModel = new ProductosModel();
    }

    @Test
    public void testObtenerProductos() {
        List<String[]> productos = productosModel.obtenerProductos();
        assertNotNull(productos, "La lista de productos no debe ser nula");
        assertTrue(productos.size() >= 0, "La lista de productos debe contener elementos o estar vacía");
    }

    @Test
    public void testGuardarProducto() {
        Object[] nuevoProducto = {null, "Producto Test", 99.99, 1};
        int filasAfectadas = productosModel.guardarProducto(nuevoProducto);
        assertEquals(1, filasAfectadas, "Debe insertarse un producto");

        // Limpieza
        try {
            productosModel.eliminarProducto(obtenerUltimoProductoId());
        } catch (java.sql.SQLIntegrityConstraintViolationException e) {
            fail("No se pudo eliminar el producto debido a una violación de restricción: " + e.getMessage());
        }
    }

    @Test
    public void testEliminarProducto() {
        Object[] nuevoProducto = {null, "Producto Test", 99.99, 1};
        productosModel.guardarProducto(nuevoProducto);

        int idProducto = obtenerUltimoProductoId();
        try {
            int filasAfectadas = productosModel.eliminarProducto(idProducto);
            assertEquals(1, filasAfectadas, "Debe eliminarse un producto");
        } catch (java.sql.SQLIntegrityConstraintViolationException e) {
            fail("No se pudo eliminar el producto debido a una violación de restricción: " + e.getMessage());
        }
    }

    private int obtenerUltimoProductoId() {
        List<String[]> productos = productosModel.obtenerProductos();
        return Integer.parseInt(productos.get(productos.size() - 1)[0]);
    }
}
