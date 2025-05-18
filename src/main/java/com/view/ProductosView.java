package com.view;

import com.controller.ProductosController;
import com.dbconnection.DBConnection;
import com.model.CategoriasModel;
import com.model.ProductosModel;
import com.view.form.CategoriaItem;
import com.view.form.FormElement;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

import com.view.form.CategoriaItem;

/**
 * Clase que representa la vista de productos en la aplicación.
 * Contiene una tabla para mostrar productos y un menú para realizar acciones como añadir, editar o eliminar productos.
 */
public class ProductosView extends JPanel {

    private ProductosController controller; // Referencia al controlador
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
     * Vincula el controlador con la vista.
     *
     * @param controller El controlador a vincular.
     */
    public void setController(ProductosController controller) {
        this.controller = controller;
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

        menuPanel.add(crearBoton("Nuevo", "#48BA78", e -> abrirFormulario("Añadir Producto", new Object[]{null, "", 0.0, "Categoría 1"})));
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
                            tableModel.getValueAt(selectedRow, 0), // ID del producto
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
        menuPanel.add(crearBoton("Eliminar", "#C54A3D", e -> {
            // Obtiene la fila seleccionada en la tabla de productos
            int selectedRow = productosTable.getSelectedRow();

            // Verifica si no hay ninguna fila seleccionada
            if (selectedRow == -1) {
                // Muestra un mensaje de advertencia al usuario
                JOptionPane.showMessageDialog(this, "Por favor, seleccione un producto que desee eliminar.", "Error", JOptionPane.WARNING_MESSAGE);
            } else {
                try {
                    // Obtiene los datos del producto seleccionado de la tabla
                    Object[] datos = new Object[]{
                            tableModel.getValueAt(selectedRow, 0), // ID del producto
                            tableModel.getValueAt(selectedRow, 1), // Nombre del producto
                    };
                    // Abre el formulario para editar el producto con los datos obtenidos
                    eliminarProducto(datos);
                } catch (Exception ex) {
                    // Muestra un mensaje de error genérico si ocurre un problema al eliminar el producto
                    JOptionPane.showMessageDialog(this, "Ocurrió un error al intentar eliminar el producto.", "Error", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            }
        }));
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

        // Crear un array de objetos CategoriaItem para el JComboBox
        CategoriaItem[] opcionesCategorias = new CategoriaItem[categorias.size() + 1];
        opcionesCategorias[0] = new CategoriaItem(0, "Seleccione una categoría"); // Opción inicial
        for (int i = 0; i < categorias.size(); i++) {
            int id = Integer.parseInt(categorias.get(i)[0]);
            String nombre = categorias.get(i)[1];
            opcionesCategorias[i + 1] = new CategoriaItem(id, nombre);
        }

        // Crear el panel del formulario
        JPanel formularioPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        formularioPanel.setBackground(Color.WHITE);
        formularioPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 20));
        formularioPanel.add(FormElement.crearCampoTexto("Nombre:", (String) datos[1]));
        formularioPanel.add(FormElement.crearCampoPrecio("Precio:", datos[2]));
        formularioPanel.add(FormElement.crearCampoComboBox("Categoría:", opcionesCategorias,
                opcionesCategorias[0].getNombre().equals(datos[3]) ? opcionesCategorias[0] :
                        java.util.Arrays.stream(opcionesCategorias)
                                .filter(categoria -> categoria.toString().equals(datos[3]))
                                .findFirst()
                                .orElse(opcionesCategorias[0])
        ));

        // Mostrar el formulario
        mostrarFormulario(titulo, formularioPanel, datos);
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

    /**
     * Muestra un formulario en una nueva ventana.
     *
     * @param titulo          El título de la ventana.
     * @param formularioPanel El panel que contiene el formulario.
     * @param datos           Los datos iniciales del formulario.
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
                        // Buscar los componentes dentro de los paneles anidados
                        JTextField nombreField = null;
                        JTextField precioField = null;
                        JComboBox<?> categoriaBox = null;

                        for (Component component : formularioPanel.getComponents()) {
                            if (component instanceof JPanel) {
                                JPanel panel = (JPanel) component;
                                for (Component innerComponent : panel.getComponents()) {
                                    if (innerComponent instanceof JTextField && nombreField == null) {
                                        nombreField = (JTextField) innerComponent;
                                    } else if (innerComponent instanceof JTextField) {
                                        precioField = (JTextField) innerComponent;
                                    } else if (innerComponent instanceof JComboBox) {
                                        categoriaBox = (JComboBox<?>) innerComponent;
                                    }
                                }
                            }
                        }

                        if (nombreField == null || precioField == null || categoriaBox == null) {
                            throw new IllegalStateException("No se encontraron los componentes esperados en el formulario.");
                        }

                        // Obtener los valores
                        String nombre = nombreField.getText();
                        String precioTexto = precioField.getText().replace(",", "."); // Reemplazar coma por punto
                        double precio = Double.parseDouble(precioTexto);

                        // Validar y obtener el ID de la categoría
                        if (categoriaBox.getSelectedItem() instanceof CategoriaItem) {
                            int categoriaId = ((CategoriaItem) categoriaBox.getSelectedItem()).getId();

                            if (nombre.isEmpty() || precio <= 0 || categoriaId == 0) {
                                JOptionPane.showMessageDialog(frame, "Por favor, complete todos los campos correctamente.", "Error", JOptionPane.ERROR_MESSAGE);
                            } else {
                                // Llamar al controlador para añadir o editar el producto
                                controller.guardarProducto(new Object[]{datos[0], nombre, precio, categoriaId});
                                frame.dispose(); // Cerrar el formulario
                            }
                        } else {
                            JOptionPane.showMessageDialog(frame, "Seleccione una categoría válida.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (ClassCastException ex) {
                        JOptionPane.showMessageDialog(frame, "Error al procesar los datos del formulario. Verifique los componentes.", "Error", JOptionPane.ERROR_MESSAGE);
                        ex.printStackTrace();
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(frame, "El precio debe ser un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
                        ex.printStackTrace();
                    } catch (IllegalStateException ex) {
                        JOptionPane.showMessageDialog(frame, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }));
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
     * Muestra un mensaje al usuario.
     *
     * @param mensaje El mensaje a mostrar.
     * @param titulo  El título del mensaje.
     */
    public void mostrarMensaje(String mensaje, String titulo) {
        JOptionPane.showMessageDialog(this, mensaje, titulo, JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Elimina un producto seleccionado de la tabla.
     */
    private void eliminarProducto(Object[] datos) {
        int confirmacion = JOptionPane.showConfirmDialog(
                this,
                "¿Está seguro de que desea eliminar el producto \"" + datos[1] + "\" con ID " + datos[0] + "?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (confirmacion == JOptionPane.YES_OPTION) {
            controller.eliminarProducto(datos); // Delegar al controlador
        }
    }

    public JTable getProductosTable() {
        return productosTable;
    }
}