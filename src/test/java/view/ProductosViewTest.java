package view;

import com.view.ProductosView;
import org.junit.jupiter.api.*;
import javax.swing.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ProductosViewTest {

    private ProductosView productosView;

    @BeforeAll
    public void setUp() {
        productosView = new ProductosView();
    }

    @Test
    public void testActualizarTabla() {
        String[][] datos = {
            {"1", "Producto 1", "10.0", "Categoría 1"},
            {"2", "Producto 2", "20.0", "Categoría 2"}
        };
        productosView.actualizarTabla(List.of(datos));
        JTable tabla = productosView.getProductosTable();
        assertEquals(2, tabla.getRowCount(), "La tabla debe tener 2 filas");
    }

    @Test
    public void testMostrarMensaje() {
        assertDoesNotThrow(() -> productosView.mostrarMensaje("Prueba exitosa", "Éxito"));
    }
}
