package com.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ProductosView extends JPanel {
    private JTable productosTable;
    private DefaultTableModel tableModel;

    public ProductosView() {
        setLayout(new BorderLayout());

        // Crear el menú secundario
        JPanel menuPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton nuevoProductoButton = new JButton("Nuevo Producto");
        JButton editarProductoButton = new JButton("Editar Producto");
        JButton eliminarProductoButton = new JButton("Eliminar Producto");
        menuPanel.add(nuevoProductoButton);
        menuPanel.add(editarProductoButton);
        menuPanel.add(eliminarProductoButton);

        // Crear la tabla
        tableModel = new DefaultTableModel(new String[]{"ID", "Nombre", "Precio", "Categoría"}, 0);
        productosTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(productosTable);

        // Agregar componentes al panel
        add(menuPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    /**
     * Método para actualizar la tabla con los datos de productos.
     * @param productos Lista de productos a mostrar en la tabla.
     */
    public void actualizarTabla(List<String[]> productos) {
        tableModel.setRowCount(0); // Limpiar la tabla
        for (String[] producto : productos) {
            tableModel.addRow(producto);
        }
    }
}