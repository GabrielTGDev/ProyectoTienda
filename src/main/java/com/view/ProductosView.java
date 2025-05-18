package com.view;

import com.model.CategoriasModel;
import com.view.form.FormElement;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

/**
 * Clase que representa la vista de productos en la aplicación.
 * Contiene una tabla para mostrar productos y un menú para realizar acciones como añadir, editar o eliminar productos.
 */
public class ProductosView extends JPanel {

    private JTable productosTable; // Tabla para mostrar los productos
    private DefaultTableModel tableModel; // Modelo de datos para la tabla

    /**
     * Constructor de la clase ProductosView.
     * Configura el diseño principal y añade los componentes necesarios.
     */
    public ProductosView() {
        setLayout(new BorderLayout());
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(crearMenuSecundario(), BorderLayout.NORTH);
        mainPanel.add(crearTablaProductos(), BorderLayout.CENTER);
        add(new JScrollPane(mainPanel), BorderLayout.CENTER);
    }

    /**
     * Crea el menú secundario con botones para añadir, editar y eliminar productos.
     *
     * @return Un JPanel que contiene los botones del menú.
     */
    private JPanel crearMenuSecundario() {
        JPanel menuPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 40, 18));
        menuPanel.setBackground(Color.decode("#F9F9F9"));
        menuPanel.setPreferredSize(new Dimension(0, 100));

        menuPanel.add(crearBoton("Nuevo", "#48BA78", e -> abrirFormulario("Añadir Producto", new Object[]{"", 0.0, "Categoría 1"})));
        menuPanel.add(crearBoton("Editar", "#D5843B", e -> {
            // Obtiene la fila seleccionada en la tabla de productos
            int selectedRow = productosTable.getSelectedRow();

            // Verifica si no hay ninguna fila seleccionada
            if (selectedRow == -1) {
                // Muestra un mensaje de advertencia al usuario
                JOptionPane.showMessageDialog(this, "Por favor, seleccione un producto para editar.", "Error", JOptionPane.WARNING_MESSAGE);
            } else {
                try {
                    // Obtiene los datos del producto seleccionado de la tabla
                    Object[] datos = new Object[]{
                            tableModel.getValueAt(selectedRow, 1), // Nombre del producto
                            Double.parseDouble(tableModel.getValueAt(selectedRow, 2).toString()), // Precio convertido a Double
                            tableModel.getValueAt(selectedRow, 3)  // Categoría del producto
                    };
                    // Abre el formulario para editar el producto con los datos obtenidos
                    abrirFormulario("Editar Producto", datos);
                } catch (NumberFormatException ex) {
                    // Muestra un mensaje de error si el precio no tiene un formato válido
                    JOptionPane.showMessageDialog(this, "El precio no tiene un formato válido.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }));
        menuPanel.add(crearBoton("Eliminar", "#C54A3D", e -> eliminarProducto()));
        return menuPanel;
    }

    /**
     * Abre un formulario para añadir o editar un producto.
     *
     * @param titulo El título del formulario.
     * @param datos  Los datos iniciales del formulario.
     */
    private void abrirFormulario(String titulo, Object[] datos) {
        // Obtener categorías desde el modelo
        CategoriasModel categoriasModel = new CategoriasModel();
        List<String[]> categorias = categoriasModel.obtenerCategorias();

        // Crear un array de nombres de categorías para el JComboBox
        String[] opcionesCategorias = new String[categorias.size() + 1];
        opcionesCategorias[0] = "Seleccione una categoría"; // Opción inicial
        for (int i = 0; i < categorias.size(); i++) {
            opcionesCategorias[i + 1] = categorias.get(i)[1]; // Nombre de la categoría
        }

        // Crear el panel del formulario
        JPanel formularioPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        formularioPanel.setBackground(Color.WHITE);
        formularioPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 20));
        formularioPanel.add(FormElement.crearCampoTexto("Nombre:", (String) datos[0]));
        formularioPanel.add(FormElement.crearCampoPrecio("Precio:", datos[1]));
        formularioPanel.add(FormElement.crearCampoComboBox("Categoría:", opcionesCategorias, datos[2]));

        // Mostrar el formulario
        mostrarFormulario(titulo, formularioPanel);
    }

    /**
     * Crea un JScrollPane que contiene la tabla de productos.
     *
     * @return Un JScrollPane con la tabla de productos.
     */
    private JScrollPane crearTablaProductos() {
        tableModel = new DefaultTableModel(new String[]{"ID", "Nombre", "Precio", "Categoría"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Evita que las celdas sean editables
            }
        };
        productosTable = new JTable(tableModel);
        configurarTabla();
        return new JScrollPane(productosTable);
    }

    /**
     * Configura las propiedades de la tabla de productos, como el estilo y los eventos.
     */
    private void configurarTabla() {
        productosTable.setFont(new Font("Arial", Font.PLAIN, 24));
        productosTable.setBackground(Color.WHITE);
        productosTable.setRowHeight(30);
        productosTable.setDefaultRenderer(Object.class, crearRendererCeldas());
        personalizarEncabezado(productosTable.getTableHeader());
        productosTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        productosTable.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent e) {
                if (e.getKeyCode() == java.awt.event.KeyEvent.VK_ESCAPE) {
                    productosTable.clearSelection(); // Limpia la selección al presionar ESC
                }
            }
        });
    }

    /**
     * Crea un renderizador personalizado para las celdas de la tabla.
     *
     * @return Un DefaultTableCellRenderer configurado.
     */
    private DefaultTableCellRenderer crearRendererCeldas() {
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (component instanceof JLabel) {
                    ((JLabel) component).setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
                }
                return component;
            }
        };
        renderer.setHorizontalAlignment(SwingConstants.LEFT);
        return renderer;
    }

    /**
     * Personaliza el encabezado de la tabla de productos.
     *
     * @param header El encabezado de la tabla.
     */
    private void personalizarEncabezado(JTableHeader header) {
        header.setFont(new Font("Arial", Font.BOLD, 24));
        header.setBackground(Color.WHITE);
        header.setForeground(Color.BLACK);
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(0, 0, 0, 115)));
    }

    /**
     * Crea un botón con estilo personalizado.
     *
     * @param texto      El texto del botón.
     * @param colorFondo El color de fondo del botón en formato hexadecimal.
     * @param action     La acción a realizar al presionar el botón.
     * @return Un JButton configurado.
     */
    private JButton crearBoton(String texto, String colorFondo, java.awt.event.ActionListener action) {
        JButton boton = new JButton(texto);
        boton.setPreferredSize(new Dimension(170, 60));
        boton.setFont(new Font("Arial", Font.PLAIN, 32));
        boton.setForeground(Color.BLACK);
        boton.setBackground(Color.decode(colorFondo));
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createLineBorder(Color.decode(colorFondo), 1));
        boton.addActionListener(action);
        boton.addMouseListener(crearMouseListenerBoton(boton, texto, colorFondo));
        return boton;
    }

    /**
     * Crea un MouseAdapter para manejar eventos de interacción con un botón.
     *
     * @param boton      El botón al que se aplicará el adaptador.
     * @param texto      El texto del botón.
     * @param colorFondo El color de fondo del botón.
     * @return Un MouseAdapter configurado.
     */
    private MouseAdapter crearMouseListenerBoton(JButton boton, String texto, String colorFondo) {
        return new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
                boton.setText("<html><u>" + texto + "</u></html>");
            }

            @Override
            public void mouseExited(MouseEvent e) {
                boton.setText(texto);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                boton.setFont(boton.getFont().deriveFont(Font.BOLD));
                boton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                boton.setFont(boton.getFont().deriveFont(Font.PLAIN));
                boton.setBorder(BorderFactory.createLineBorder(Color.decode(colorFondo), 1));
            }
        };
    }

    /**
     * Muestra un formulario en una nueva ventana.
     *
     * @param titulo          El título de la ventana.
     * @param formularioPanel El panel que contiene el formulario.
     */
    private void mostrarFormulario(String titulo, JPanel formularioPanel) {
        JFrame frame = new JFrame("Formulario - " + titulo);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setMinimumSize(new Dimension(1280, 720));
        frame.setResizable(false);
        frame.add(new FormsView(titulo, formularioPanel));
        frame.setVisible(true);
    }

    /**
     * Actualiza los datos de la tabla de productos.
     *
     * @param productos Una lista de arrays de cadenas que representan los productos.
     */
    public void actualizarTabla(List<String[]> productos) {
        tableModel.setRowCount(0);
        productos.forEach(tableModel::addRow);
    }

    /**
     * Elimina un producto seleccionado de la tabla.
     * (Implementación pendiente)
     */
    private void eliminarProducto() {
        // Lógica para eliminar un producto
    }
}