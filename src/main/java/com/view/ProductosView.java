package com.view;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

/**
 * Clase ProductosView que representa la vista de productos en la aplicación.
 * Contiene un menú secundario con botones de acción y una tabla para mostrar los productos.
 */
public class ProductosView extends JPanel {

    private JTable productosTable; // Tabla para mostrar los productos
    private DefaultTableModel tableModel; // Modelo de datos para la tabla

    /**
     * Constructor de ProductosView.
     * Configura el diseño, el menú secundario y la tabla de productos.
     */
    public ProductosView() {
        setLayout(new BorderLayout()); // Establece el diseño principal del panel

        // Crear el panel principal que contendrá el menú y la tabla
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Crear y configurar el menú secundario
        JPanel menuPanel = crearMenuSecundario();

        // Crear y configurar la tabla de productos
        JScrollPane tableScrollPane = crearTablaProductos();

        // Agregar el menú y la tabla al panel principal
        mainPanel.add(menuPanel, BorderLayout.NORTH);
        mainPanel.add(tableScrollPane, BorderLayout.CENTER);

        // Envolver el panel principal en un JScrollPane para permitir el desplazamiento
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        // Agregar el JScrollPane al panel principal
        add(scrollPane, BorderLayout.CENTER);
    }

    /**
     * Crea el menú secundario con botones de acción.
     *
     * @return JPanel con los botones del menú secundario.
     */
    private JPanel crearMenuSecundario() {
        JPanel menuPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 40, 18));
        menuPanel.setBackground(Color.decode("#F9F9F9")); // Fondo del menú
        menuPanel.setPreferredSize(new Dimension(0, 100)); // Altura del menú

        // Crear botones del menú
        JButton nuevoProductoButton = crearBoton("Nuevo", "#48BA78");
        JButton editarProductoButton = crearBoton("Editar", "#D5843B");
        JButton eliminarProductoButton = crearBoton("Eliminar", "#C54A3D");

        // Abrir formulario añadir producto
        nuevoProductoButton.addActionListener(e -> abrirFormulario("Añadir Producto", new String[]{"", "", "Categoría 1"}));

        // Abrir formulario editar producto
        editarProductoButton.addActionListener(e -> abrirFormulario("Editar Producto", new String[]{"Producto existente", "100.00", "Categoría 1"}));


        // Agregar botones al menú
        menuPanel.add(nuevoProductoButton);
        menuPanel.add(editarProductoButton);
        menuPanel.add(eliminarProductoButton);

        return menuPanel;
    }

    private void abrirFormulario(String titulo, String[] datos) {
        JPanel formularioPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        formularioPanel.setBackground(Color.WHITE);
        formularioPanel.add(new JLabel("Nombre:"));
        formularioPanel.add(new JTextField(datos[0]));
        formularioPanel.add(new JLabel("Precio:"));
        formularioPanel.add(new JTextField(datos[1]));
        formularioPanel.add(new JLabel("Categoría:"));
        formularioPanel.add(new JComboBox<>(new String[]{"Categoría 1", "Categoría 2"}));

        FormsView formsView = new FormsView(titulo, formularioPanel);

        JFrame frame = new JFrame("Formulario - " + titulo);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setMinimumSize(new Dimension(1280, 720));
        frame.setResizable(false);
        frame.add(formsView);
        frame.setVisible(true);
    }

    /**
     * Crea un botón con estilo personalizado.
     *
     * @param texto      Texto del botón.
     * @param colorFondo Color de fondo del botón en formato hexadecimal.
     * @return JButton con el estilo configurado.
     */
    private JButton crearBoton(String texto, String colorFondo) {
        JButton boton = new JButton(texto);
        boton.setPreferredSize(new Dimension(170, 60)); // Tamaño del botón
        boton.setFont(new Font("Arial", Font.PLAIN, 32)); // Fuente del texto
        boton.setForeground(Color.decode("#000000")); // Color del texto
        boton.setBackground(Color.decode(colorFondo)); // Color de fondo
        boton.setFocusPainted(false); // Eliminar borde de enfoque
        boton.setBorder(BorderFactory.createLineBorder(Color.decode(colorFondo), 1)); // Borde del botón
        boton.setContentAreaFilled(true); // Relleno del botón

        // Eventos de hover y clic
        boton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                boton.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Cambiar el cursor a mano
                boton.setText("<html><u>" + texto + "</u></html>"); // Subrayar el texto
            }

            @Override
            public void mouseExited(MouseEvent e) {
                boton.setText(texto); // Restaurar el texto original sin subrayado
            }

            @Override
            public void mousePressed(MouseEvent e) {
                boton.setFont(boton.getFont().deriveFont(Font.BOLD)); // Cambiar a negrita al presionar
                boton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1)); // Cambiar borde
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                boton.setFont(boton.getFont().deriveFont(Font.PLAIN)); // Restaurar fuente
                boton.setBorder(BorderFactory.createLineBorder(Color.decode(colorFondo), 1)); // Restaurar borde
            }
        });

        return boton;
    }

    /**
     * Crea y configura la tabla de productos.
     *
     * @return JScrollPane que contiene la tabla de productos.
     */
    private JScrollPane crearTablaProductos() {
        // Configurar el modelo de la tabla
        tableModel = new DefaultTableModel(new String[]{"ID", "Nombre", "Precio", "Categoría"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hace que las celdas no sean editables
            }
        };

        // Crear la tabla con el modelo
        productosTable = new JTable(tableModel);
        productosTable.setFont(new Font("Arial", Font.PLAIN, 24)); // Fuente de las celdas
        productosTable.setBackground(Color.WHITE); // Fondo de las celdas
        productosTable.setRowHeight(30); // Altura de las filas

        // Personalizar las celdas
        personalizarCeldas();

        // Personalizar el encabezado de la tabla
        personalizarEncabezado();

        // Configurar la selección de filas
        productosTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Listener para deseleccionar filas con la tecla ESC
        productosTable.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent e) {
                if (e.getKeyCode() == java.awt.event.KeyEvent.VK_ESCAPE) {
                    productosTable.clearSelection(); // Elimina la selección de las filas
                }
            }
        });

        return new JScrollPane(productosTable); // Envolver la tabla en un JScrollPane
    }

    /**
     * Personaliza las celdas de la tabla.
     */
    private void personalizarCeldas() {
        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (component instanceof JLabel) {
                    JLabel label = (JLabel) component;
                    label.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16)); // Agregar relleno
                }
                return component;
            }
        };
        cellRenderer.setHorizontalAlignment(SwingConstants.LEFT); // Alinear texto a la izquierda
        productosTable.setDefaultRenderer(Object.class, cellRenderer);
    }

    /**
     * Personaliza el encabezado de la tabla.
     */
    private void personalizarEncabezado() {
        JTableHeader header = productosTable.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 24)); // Fuente del encabezado
        header.setBackground(Color.WHITE); // Fondo del encabezado
        header.setForeground(Color.BLACK); // Color del texto del encabezado
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(0, 0, 0, 115))); // Borde inferior
    }

    /**
     * Función para actualizar la tabla con los datos de productos.
     *
     * @param productos Lista de productos a mostrar en la tabla.
     */
    public void actualizarTabla(List<String[]> productos) {
        tableModel.setRowCount(0); // Limpiar la tabla
        for (String[] producto : productos) {
            tableModel.addRow(producto); // Agregar cada producto como una fila
        }
    }
}