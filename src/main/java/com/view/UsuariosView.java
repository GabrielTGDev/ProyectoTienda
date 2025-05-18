package com.view;

import com.controller.UsuariosController;
import com.view.form.FormElement;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.List;

public class UsuariosView extends JPanel {

    private UsuariosController controller;
    private JTable usuariosTable;
    private DefaultTableModel tableModel;

    /**
     * Constructor de la clase UsuariosView.
     * Inicializa la vista de usuarios y configura el diseño.
     */
    public UsuariosView() {
        setLayout(new BorderLayout());
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(crearMenuSecundario(), BorderLayout.NORTH);
        mainPanel.add(crearTablaUsuarios(), BorderLayout.CENTER);
        add(new JScrollPane(mainPanel), BorderLayout.CENTER);
    }

    /**
     * Establece el controlador de la vista.
     *
     * @param controller Controlador de usuarios.
     */
    public void setController(UsuariosController controller) {
        this.controller = controller;
    }

    /**
     * Crea el menú secundario con botones para agregar, editar, eliminar usuarios y un buscador.
     *
     * @return El panel del menú secundario.
     */
    private JPanel crearMenuSecundario() {
        JPanel menuPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 40, 18));
        menuPanel.setBackground(Color.decode("#F9F9F9"));
        menuPanel.setPreferredSize(new Dimension(0, 100));

        // Campo de texto para el buscador

        // Botones existentes
        menuPanel.add(crearBoton("Nuevo", "#48BA78", e -> abrirFormulario("Añadir Usuario", new Object[]{null, "", "", "", "", "", ""})));
        menuPanel.add(crearBoton("Editar", "#D5843B", e -> {
            int selectedRow = usuariosTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Por favor, seleccione un usuario para editar.", "Error", JOptionPane.WARNING_MESSAGE);
            } else {
                Object[] datos = new Object[]{
                        tableModel.getValueAt(selectedRow, 0), // ID
                        tableModel.getValueAt(selectedRow, 1), // Nombre
                        tableModel.getValueAt(selectedRow, 2), // Apellido
                        tableModel.getValueAt(selectedRow, 3), // Email
                        tableModel.getValueAt(selectedRow, 4), // Calle
                        tableModel.getValueAt(selectedRow, 5), // Ciudad
                        tableModel.getValueAt(selectedRow, 6)  // Código Postal
                };
                abrirFormulario("Editar Usuario", datos);
            }
        }));
        menuPanel.add(crearBoton("Eliminar", "#C54A3D", e -> {
            int selectedRow = usuariosTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Por favor, seleccione un usuario que desee eliminar.", "Error", JOptionPane.WARNING_MESSAGE);
            } else {
                try {
                    Object[] datos = new Object[]{
                            tableModel.getValueAt(selectedRow, 0),
                            tableModel.getValueAt(selectedRow, 1),
                            tableModel.getValueAt(selectedRow, 2),
                    };
                    eliminarUsuario(datos);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "El ID del usuario no tiene un formato válido.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }));
        menuPanel.add(crearBoton("Exportar", "#A5571FF", e -> controller.exportarUsuarios()));

        // Campo de texto para el buscador
        JTextField buscadorField = new JTextField();
        buscadorField.setPreferredSize(new Dimension(240, 60));
        buscadorField.setFont(new Font("Arial", Font.PLAIN, 24));
        buscadorField.setText("Buscar..."); // Placeholder inicial
        buscadorField.setForeground(Color.GRAY);
        // Agregar FocusListener para manejar el placeholder
        buscadorField.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                if (buscadorField.getText().equals("Buscar...")) {
                    buscadorField.setText("");
                    buscadorField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                if (buscadorField.getText().isEmpty()) {
                    buscadorField.setText("Buscar...");
                    buscadorField.setForeground(Color.GRAY);
                }
            }
        });
        buscadorField.addActionListener(e -> {
            String textoBusqueda = buscadorField.getText().toLowerCase();
            if (!textoBusqueda.isEmpty()) {
                filtrarTablaUsuarios(textoBusqueda);
            } else {
                actualizarTabla(controller.obtenerUsuarios());
            }
        });
        menuPanel.add(buscadorField);

        // Botón para buscar
        menuPanel.add(crearBoton("Buscar", "#007BFF", e -> {
            String textoBusqueda = buscadorField.getText().toLowerCase();
            if (!textoBusqueda.isEmpty()) {
                filtrarTablaUsuarios(textoBusqueda);
            } else {
                actualizarTabla(controller.obtenerUsuarios());
            }
        }));

        // Botón para limpiar búsqueda
        menuPanel.add(crearBoton("Limpiar", "#C2C2C2", e -> {
            buscadorField.setText("Buscar...");
            buscadorField.setForeground(Color.GRAY);
            actualizarTabla(controller.obtenerUsuarios());
        }));

        return menuPanel;
    }

    /**
     * Filtra los datos de la tabla de usuarios según el texto de búsqueda.
     *
     * @param textoBusqueda Texto ingresado para buscar.
     */
    private void filtrarTablaUsuarios(String textoBusqueda) {
        List<String[]> usuariosFiltrados = controller.obtenerUsuarios().stream()
                .filter(usuario -> {
                    for (String campo : usuario) {
                        if (campo != null && campo.toLowerCase().contains(textoBusqueda)) {
                            return true;
                        }
                    }
                    return false;
                })
                .toList();
        actualizarTabla(usuariosFiltrados);
    }

    /**
     * Abre un formulario para agregar o editar un usuario.
     *
     * @param titulo Título del formulario.
     * @param datos  Datos del usuario a editar (null para nuevo usuario).
     */
    private void abrirFormulario(String titulo, Object[] datos) {
        if (datos.length < 7) {
            throw new IllegalArgumentException("El arreglo de datos debe tener al menos 5 elementos.");
        }

        // Crear el panel del formulario
        JPanel formularioPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        formularioPanel.setBackground(Color.WHITE);
        formularioPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 20));

        // Campos para el usuario
        formularioPanel.add(FormElement.crearCampoTexto("Nombre:", (String) datos[1]));
        formularioPanel.add(FormElement.crearCampoTexto("Apellido:", (String) datos[2]));
        formularioPanel.add(FormElement.crearCampoTexto("Email:", (String) datos[3]));

        // Campos para la dirección
        formularioPanel.add(FormElement.crearCampoTexto("Calle:", (String) datos[4]));
        formularioPanel.add(FormElement.crearCampoTexto("Ciudad:", (String) datos[5]));
        formularioPanel.add(FormElement.crearCampoTexto("Código Postal:", (String) datos[6]));

        mostrarFormulario(titulo, formularioPanel, datos);
    }

    /**
     * Crea la tabla de usuarios.
     *
     * @return El panel con la tabla de usuarios.
     */
    private JScrollPane crearTablaUsuarios() {
        JPanel formularioPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        formularioPanel.setBackground(Color.WHITE);
        tableModel = new DefaultTableModel(new String[]{"ID", "Nombre", "Apellido", "Email"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        usuariosTable = new JTable(tableModel);
        configurarTabla();
        return new JScrollPane(usuariosTable);
    }

    /**
     * Configura la tabla de usuarios.
     */
    private void configurarTabla() {
        tableModel = new DefaultTableModel(new String[]{"ID", "Nombre", "Apellido", "Email", "Calle", "Ciudad", "Código Postal"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Evita que las celdas sean editables
            }
        };
        usuariosTable = new JTable(tableModel);
        usuariosTable.setFont(new Font("Arial", Font.PLAIN, 18));
        usuariosTable.setBackground(Color.WHITE);
        usuariosTable.setRowHeight(25);
        usuariosTable.setDefaultRenderer(Object.class, crearRendererCeldas());
        personalizarEncabezado(usuariosTable.getTableHeader());
        usuariosTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        usuariosTable.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent e) {
                if (e.getKeyCode() == java.awt.event.KeyEvent.VK_ESCAPE) {
                    usuariosTable.clearSelection(); // Limpia la selección al presionar ESC
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
     * Personaliza el encabezado de la tabla.
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
     * Crea un botón con el texto y color de fondo especificados.
     *
     * @param texto      Texto del botón.
     * @param colorFondo Color de fondo del botón en formato hexadecimal.
     * @param action     Acción a realizar al hacer clic en el botón.
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
        return boton;
    }

    /**
     * Muestra un formulario para agregar o editar un usuario.
     *
     * @param titulo          Título del formulario.
     * @param formularioPanel Panel que contiene los campos del formulario.
     * @param datos           Datos del usuario a editar (null para nuevo usuario).
     */
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
                        JTextField nombreField = null;
                        JTextField apellidoField = null;
                        JTextField emailField = null;
                        JTextField calleField = null;
                        JTextField ciudadField = null;
                        JTextField codigoPostalField = null;

                        for (Component component : formularioPanel.getComponents()) {
                            if (component instanceof JPanel) {
                                JPanel panel = (JPanel) component;
                                for (Component innerComponent : panel.getComponents()) {
                                    if (innerComponent instanceof JTextField && nombreField == null) {
                                        nombreField = (JTextField) innerComponent;
                                    } else if (innerComponent instanceof JTextField && apellidoField == null) {
                                        apellidoField = (JTextField) innerComponent;
                                    } else if (innerComponent instanceof JTextField && emailField == null) {
                                        emailField = (JTextField) innerComponent;
                                    } else if (innerComponent instanceof JTextField && calleField == null) {
                                        calleField = (JTextField) innerComponent;
                                    } else if (innerComponent instanceof JTextField && ciudadField == null) {
                                        ciudadField = (JTextField) innerComponent;
                                    } else if (innerComponent instanceof JTextField) {
                                        codigoPostalField = (JTextField) innerComponent;
                                    }
                                }
                            }
                        }

                        if (nombreField == null || apellidoField == null || emailField == null ||
                                calleField == null || ciudadField == null || codigoPostalField == null) {
                            throw new IllegalStateException("No se encontraron los componentes esperados en el formulario.");
                        }

                        String nombre = nombreField.getText();
                        String apellido = apellidoField.getText();
                        String email = emailField.getText();
                        String calle = calleField.getText();
                        String ciudad = ciudadField.getText();
                        String codigoPostal = codigoPostalField.getText();

                        if (nombre.isEmpty() || apellido.isEmpty() || email.isEmpty() ||
                                calle.isEmpty() || ciudad.isEmpty() || codigoPostal.isEmpty()) {
                            JOptionPane.showMessageDialog(frame, "Por favor, complete todos los campos correctamente.", "Error", JOptionPane.ERROR_MESSAGE);
                        } else {
                            controller.guardarUsuario(new Object[]{
                                    datos[0], // ID del usuario
                                    nombre,
                                    apellido,
                                    email,
                                    calle,
                                    ciudad,
                                    codigoPostal
                            });
                            frame.dispose();
                        }
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(frame, "Error al procesar los datos del formulario.", "Error", JOptionPane.ERROR_MESSAGE);
                        ex.printStackTrace();
                    }
                }));
        frame.setVisible(true);
    }

    /**
     * Actualiza la tabla de usuarios con los datos proporcionados.
     *
     * @param usuarios Lista de usuarios a mostrar en la tabla.
     */
    public void actualizarTabla(List<String[]> usuarios) {
        tableModel.setRowCount(0);
        usuarios.forEach(tableModel::addRow);
    }

    /**
     * Muestra un mensaje en un cuadro de diálogo.
     *
     * @param mensaje Mensaje a mostrar.
     * @param titulo  Título del cuadro de diálogo.
     */
    public void mostrarMensaje(String mensaje, String titulo) {
        JOptionPane.showMessageDialog(this, mensaje, titulo, JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Elimina un usuario de la base de datos después de confirmar la acción.
     *
     * @param datos Datos del usuario a eliminar.
     */
    private void eliminarUsuario(Object[] datos) {
        int confirmacion = JOptionPane.showConfirmDialog(
                this,
                "¿Está seguro de que desea eliminar el usuario \"" + datos[1] + " " + datos[2] + "\" con ID " + datos[0] + "?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (confirmacion == JOptionPane.YES_OPTION) {
            controller.eliminarUsuario(datos);
        }
    }
}