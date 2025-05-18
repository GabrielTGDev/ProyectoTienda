package com.view;

import com.controller.PedidosController;
import com.view.form.FormElement;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class PedidosView extends JPanel {

    private PedidosController controller;
    private JTable pedidosTable;
    private DefaultTableModel tableModel;

    /**
     * Constructor de la vista de pedidos.
     * Inicializa la interfaz gráfica y configura el diseño.
     */
    public PedidosView() {
        setLayout(new BorderLayout());
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(crearMenuSecundario(), BorderLayout.NORTH);
        mainPanel.add(crearTablaPedidos(), BorderLayout.CENTER);
        add(new JScrollPane(mainPanel), BorderLayout.CENTER);
    }

    /**
     * Establece el controlador para la vista de pedidos.
     *
     * @param controller El controlador a establecer.
     */
    public void setController(PedidosController controller) {
        this.controller = controller;
    }

    /**
     * Crea el menú secundario con botones para añadir, editar y eliminar pedidos.
     *
     * @return JPanel que contiene el menú secundario.
     */
    private JPanel crearMenuSecundario() {
        JPanel menuPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 40, 18));
        menuPanel.setBackground(Color.decode("#F9F9F9"));
        menuPanel.setPreferredSize(new Dimension(0, 100));

        menuPanel.add(crearBoton("Nuevo", "#48BA78", e -> abrirFormulario("Añadir Pedido", new Object[]{null, 0, 0, 1, java.time.LocalDate.now().toString()})));
        menuPanel.add(crearBoton("Editar", "#D5843B", e -> {
            int selectedRow = pedidosTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Por favor, seleccione un pedido para editar.", "Error", JOptionPane.WARNING_MESSAGE);
            } else {
                try {
                    Object[] datos = new Object[]{
                            tableModel.getValueAt(selectedRow, 0), // ID del pedido
                            tableModel.getValueAt(selectedRow, 1), // Usuario
                            tableModel.getValueAt(selectedRow, 2), // Producto
                            tableModel.getValueAt(selectedRow, 3), // Cantidad
                            tableModel.getValueAt(selectedRow, 4) // Fecha
                    };
                    abrirFormulario("Editar Pedido", datos);

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Error al abrir el formulario de edición: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }));
        menuPanel.add(crearBoton("Eliminar", "#C54A3D", e -> {
            int selectedRow = pedidosTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Por favor, seleccione un pedido que desee eliminar.", "Error", JOptionPane.WARNING_MESSAGE);
            } else {
                try {
                    Object[] datos = new Object[]{
                            tableModel.getValueAt(selectedRow, 0), // ID del pedido
                            tableModel.getValueAt(selectedRow, 1), // Usuario
                            tableModel.getValueAt(selectedRow, 2), // Producto
                            tableModel.getValueAt(selectedRow, 3), // Cantidad
                            tableModel.getValueAt(selectedRow, 4)  // Fecha
                    };
                    eliminarPedido(datos);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Error al eliminar el pedido: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            }
        }));
        menuPanel.add(crearBoton("Exportar", "#A5571FF", e -> controller.exportarPedidos()));

        // Campos de texto para buscar
        JTextField buscadorUsuario = crearBuscador("Usuario...");
        JTextField buscadorProducto = crearBuscador("Producto...");
        JTextField buscadorFecha = crearBuscador("Fecha...");

        menuPanel.add(buscadorUsuario);
        menuPanel.add(buscadorProducto);
        menuPanel.add(buscadorFecha);

        menuPanel.add(crearBoton("Buscar", "#007BFF", e -> {
            String usuario = buscadorUsuario.getText().equals("Usuario...") ? "" : buscadorUsuario.getText();
            String producto = buscadorProducto.getText().equals("Producto...") ? "" : buscadorProducto.getText();
            String fecha = buscadorFecha.getText().equals("Fecha...") ? "" : buscadorFecha.getText();

            // Validar usuario como entero
            if (!usuario.isEmpty()) {
                try {
                    Integer.parseInt(usuario);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "El ID de usuario debe ser un número entero.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            // Validar producto como entero
            if (!producto.isEmpty()) {
                try {
                    Integer.parseInt(producto);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "El ID de producto debe ser un número entero.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            // Validar fecha con formato \d{4}-\d{2}-\d{2}
            if (!fecha.isEmpty() && !fecha.matches("\\d{4}-\\d{2}-\\d{2}")) {
                JOptionPane.showMessageDialog(this, "La fecha debe tener el formato YYYY-MM-DD.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Realizar la búsqueda si todas las validaciones son correctas
            filtrarTablaPedidos(usuario, producto, fecha);
        }));

        // Botón para limpiar búsqueda
        menuPanel.add(crearBoton("Limpiar", "#C2C2C2", e -> {
            buscadorUsuario.setText("Usuario...");
            buscadorUsuario.setForeground(Color.GRAY);
            buscadorProducto.setText("Producto...");
            buscadorProducto.setForeground(Color.GRAY);
            buscadorFecha.setText("Fecha...");
            buscadorFecha.setForeground(Color.GRAY);
            actualizarTabla(controller.obtenerPedidos());
        }));

        return menuPanel;
    }

    /**
     * Crea un campo de texto para buscar.
     *
     * @param placeholder El texto de marcador de posición.
     * @return JTextField configurado.
     */
    private JTextField crearBuscador(String placeholder) {
        JTextField buscadorField = new JTextField();
        buscadorField.setPreferredSize(new Dimension(130, 60));
        buscadorField.setFont(new Font("Arial", Font.PLAIN, 24));
        buscadorField.setText(placeholder);
        buscadorField.setForeground(Color.GRAY);
        buscadorField.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                if (buscadorField.getText().equals(placeholder)) {
                    buscadorField.setText("");
                    buscadorField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                if (buscadorField.getText().isEmpty()) {
                    buscadorField.setText(placeholder);
                    buscadorField.setForeground(Color.GRAY);
                }
            }
        });
        return buscadorField;
    }

    /**
     * Filtra la tabla de pedidos según los criterios especificados.
     *
     * @param usuario  ID del usuario a filtrar.
     * @param producto ID del producto a filtrar.
     * @param fecha    Fecha a filtrar.
     */
    private void filtrarTablaPedidos(String usuario, String producto, String fecha) {
        List<String[]> pedidosFiltrados = controller.obtenerPedidos().stream()
                .filter(pedido -> {
                    boolean coincideUsuario = true;
                    boolean coincideProducto = true;
                    boolean coincideFecha = true;

                    // Validar usuario como entero
                    if (usuario != null && !usuario.isEmpty()) {
                        try {
                            int usuarioId = Integer.parseInt(usuario);
                            coincideUsuario = pedido[1] != null && Integer.parseInt(pedido[1]) == usuarioId;
                        } catch (NumberFormatException e) {
                            coincideUsuario = false;
                        }
                    }

                    // Validar producto como entero
                    if (producto != null && !producto.isEmpty()) {
                        try {
                            int productoId = Integer.parseInt(producto);
                            coincideProducto = pedido[2] != null && Integer.parseInt(pedido[2]) == productoId;
                        } catch (NumberFormatException e) {
                            coincideProducto = false;
                        }
                    }

                    // Validar fecha con formato \d{4}-\d{2}-\d{2}
                    if (fecha != null && !fecha.isEmpty()) {
                        coincideFecha = pedido[4] != null && pedido[4].matches("\\d{4}-\\d{2}-\\d{2}") && pedido[4].equals(fecha);
                    }

                    return coincideUsuario && coincideProducto && coincideFecha;
                })
                .toList();
        actualizarTabla(pedidosFiltrados);
    }

    /**
     * Crea la tabla de pedidos.
     *
     * @return JScrollPane que contiene la tabla de pedidos.
     */
    private JScrollPane crearTablaPedidos() {
        tableModel = new DefaultTableModel(new String[]{"ID", "Usuario", "Producto", "Cantidad", "Fecha"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        pedidosTable = new JTable(tableModel);
        configurarTabla();
        return new JScrollPane(pedidosTable);
    }

    /**
     * Configura la tabla de pedidos.
     */
    private void configurarTabla() {
        pedidosTable.setFont(new Font("Arial", Font.PLAIN, 18));
        pedidosTable.setBackground(Color.WHITE);
        pedidosTable.setRowHeight(30);
        pedidosTable.setDefaultRenderer(Object.class, crearRendererCeldas());
        personalizarEncabezado(pedidosTable.getTableHeader());
        pedidosTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        pedidosTable.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent e) {
                if (e.getKeyCode() == java.awt.event.KeyEvent.VK_ESCAPE) {
                    pedidosTable.clearSelection(); // Limpia la selección al presionar ESC
                }
            }
        });
    }

    /**
     * Crea un renderer para las celdas de la tabla.
     *
     * @return El renderer creado.
     */
    private DefaultTableCellRenderer crearRendererCeldas() {
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setHorizontalAlignment(SwingConstants.LEFT);
        return renderer;
    }

    /**
     * Personaliza el encabezado de la tabla.
     *
     * @param header El encabezado de la tabla.
     */
    private void personalizarEncabezado(JTableHeader header) {
        header.setFont(new Font("Arial", Font.BOLD, 24));
        header.setBackground(Color.WHITE);
        header.setForeground(Color.BLACK);
    }

    /**
     * Crea un botón con el texto y color de fondo especificados.
     *
     * @param texto      El texto del botón.
     * @param colorFondo El color de fondo del botón en formato hexadecimal.
     * @param action     La acción a realizar al hacer clic en el botón.
     * @return El botón creado.
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

    private MouseAdapter crearMouseListenerBoton(JButton boton, String texto, String colorFondo) {
        return new MouseAdapter() {
            /**
             * Cambia el cursor a una mano y subraya el texto del botón cuando el mouse entra en el área del botón.
             *
             * @param e El evento de entrada del mouse.
             */
            @Override
            public void mouseEntered(MouseEvent e) {
                boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
                boton.setText("<html><u>" + texto + "</u></html>");
            }

            /**
             * Restaura el texto del botón a su estado original cuando el mouse sale del área del botón.
             *
             * @param e El evento de salida del mouse.
             */
            @Override
            public void mouseExited(MouseEvent e) {
                boton.setText(texto);
            }

            /**
             * Cambia la fuente del botón a negrita y ajusta el borde cuando el botón es presionado.
             *
             * @param e El evento de presionar el mouse.
             */
            @Override
            public void mousePressed(MouseEvent e) {
                boton.setFont(boton.getFont().deriveFont(Font.BOLD));
                boton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
            }

            /**
             * Restaura la fuente del botón a su estado original y ajusta el borde cuando el botón es soltado.
             *
             * @param e El evento de soltar el mouse.
             */
            @Override
            public void mouseReleased(MouseEvent e) {
                boton.setFont(boton.getFont().deriveFont(Font.PLAIN));
                boton.setBorder(BorderFactory.createLineBorder(Color.decode(colorFondo), 1));
            }
        };
    }

    private void mostrarFormulario(String titulo, JPanel formularioPanel, Object[] datos) {
        JFrame frame = new JFrame("Formulario - " + titulo);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setMinimumSize(new Dimension(1280, 720));
        frame.setResizable(false);
        frame.add(new FormsView(
                titulo,
                formularioPanel,
                e -> {
                    try {
                        JTextField usuarioField = null;
                        JTextField productoField = null;
                        JTextField cantidadField = null;
                        JTextField fechaField = null;

                        for (Component component : formularioPanel.getComponents()) {
                            if (component instanceof JPanel) {
                                JPanel panel = (JPanel) component;
                                for (Component comp : panel.getComponents()) {
                                    if (comp instanceof JTextField && usuarioField == null) {
                                        usuarioField = (JTextField) comp;
                                    } else if (comp instanceof JTextField && productoField == null) {
                                        productoField = (JTextField) comp;
                                    } else if (comp instanceof JTextField && cantidadField == null) {
                                        cantidadField = (JTextField) comp;
                                    } else if (comp instanceof JTextField && fechaField == null) {
                                        fechaField = (JTextField) comp;
                                    }
                                }
                            }
                        }

                        if (usuarioField == null || productoField == null || cantidadField == null || fechaField == null) {
                            throw new IllegalStateException("No se encontraron los componentes esperados en el formulario.");
                        }

                        int usuario = Integer.parseInt(usuarioField.getText());
                        int producto = Integer.parseInt(productoField.getText());
                        int cantidad = Integer.parseInt(cantidadField.getText());
                        String fecha = fechaField.getText();

                        // Validar y obtener el ID del usuario
                        if (!controller.verificarUsuario(usuario)) {
                            JOptionPane.showMessageDialog(frame, "El ID de usuario no es válido.", "Error", JOptionPane.ERROR_MESSAGE);
                        } else if (!controller.verificarProducto(producto)) {
                            JOptionPane.showMessageDialog(frame, "El ID de producto no es válido.", "Error", JOptionPane.ERROR_MESSAGE);
                        } else if (cantidad <= 0) {
                            JOptionPane.showMessageDialog(frame, "La cantidad debe ser un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
                        } else if (!fecha.matches("\\d{4}-\\d{2}-\\d{2}")) {
                            JOptionPane.showMessageDialog(frame, "La fecha debe tener el formato YYYY-MM-DD.", "Error", JOptionPane.ERROR_MESSAGE);
                        } else {
                            controller.guardarPedido(new Object[]{datos[0], usuario, producto, cantidad, fecha});
                            frame.dispose();
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(frame, "La cantidad debe ser un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
                    } catch (IllegalStateException ex) {
                        JOptionPane.showMessageDialog(frame, "Error al procesar el formulario: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
        ));
        frame.setVisible(true);
    }

    /**
     * Abre un formulario para añadir o editar un pedido.
     *
     * @param titulo Título del formulario.
     * @param datos  Datos del pedido (ID, usuario, producto, cantidad, fecha).
     */
    private void abrirFormulario(String titulo, Object[] datos) {
        JPanel formularioPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        formularioPanel.setBackground(Color.WHITE);
        formularioPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 20));

        formularioPanel.add(FormElement.crearCampoTexto("Usuario:", String.valueOf(datos[1])));
        formularioPanel.add(FormElement.crearCampoTexto("Producto:", String.valueOf(datos[2])));
        formularioPanel.add(FormElement.crearCampoTexto("Cantidad:", String.valueOf(datos[3])));
        formularioPanel.add(FormElement.crearCampoTexto("Fecha:", String.valueOf(datos[4])));

        // Mostrar el formulario
        mostrarFormulario(titulo, formularioPanel, datos);
    }

    public void actualizarTabla(List<String[]> pedidos) {
        tableModel.setRowCount(0);
        pedidos.forEach(tableModel::addRow);
    }

    /**
     * Muestra un mensaje al usuario.
     *
     * @param mensaje El mensaje a mostrar.
     * @param titulo  El título del mensaje.
     */
    public void mostrarMensaje(String mensaje, String titulo) {
        JOptionPane.showMessageDialog(this, mensaje, titulo, JOptionPane.INFORMATION_MESSAGE);
    }

    private void eliminarPedido(Object[] datos) {
        int confirmacion = JOptionPane.showConfirmDialog(
                this,
                "¿Está seguro de que desea eliminar el producto \"" + datos[1] + "\" con ID " + datos[0] + "?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (confirmacion == JOptionPane.YES_OPTION) {
            controller.eliminarPedido(datos); // Delegar al controlador
        }
    }
}