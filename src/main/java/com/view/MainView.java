package com.view;

        import com.controller.ProductosController;
        import com.model.ProductosModel;

        import javax.swing.*;
        import java.awt.*;
        import java.awt.event.ActionEvent;
        import java.awt.event.ActionListener;

        /**
         * Clase MainView que representa la ventana principal de la aplicación.
         * Contiene un menú superior con opciones y un panel dinámico para mostrar contenido.
         */
        public class MainView extends JFrame {

            private JPanel contentPanel; // Panel para el contenido dinámico
            private JButton selectedButton; // Botón actualmente seleccionado

            /**
             * Constructor de la clase MainView.
             * Configura la ventana principal, el menú superior y el panel dinámico.
             */
            public MainView() {
                // Configuración de la ventana principal
                setTitle("Tienda - Ventana Principal");
                setSize(1920, 1080); // Tamaño inicial de la ventana
                setMinimumSize(new Dimension(1280, 720)); // Tamaño mínimo de la ventana
                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Cerrar la aplicación al salir
                getContentPane().setBackground(Color.WHITE); // Fondo blanco para la ventana
                setLayout(new BorderLayout()); // Layout principal de la ventana

                // Crear el menú superior
                JPanel menuPanel = new JPanel();
                menuPanel.setPreferredSize(new Dimension(getWidth(), 100)); // Altura del menú
                menuPanel.setBackground(Color.decode("#ECF0F1")); // Color de fondo del menú
                menuPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 80, 26)); // Espaciado entre botones

                // Opciones del menú
                String[] menuOptions = {"Usuarios", "Pedidos", "Productos", "Categorías"};
                for (String option : menuOptions) {
                    // Crear un botón para cada opción del menú
                    JButton menuButton = new JButton(option);
                    menuButton.setFocusPainted(false); // Eliminar borde de enfoque
                    menuButton.setBorderPainted(false); // Eliminar borde del botón
                    menuButton.setContentAreaFilled(false); // Fondo transparente
                    menuButton.setFont(new Font("Arial", Font.PLAIN, 40)); // Fuente inicial
                    menuButton.setForeground(Color.decode("#333333")); // Color inicial del texto

                    // Eventos de mouse para hover
                    menuButton.addMouseListener(new java.awt.event.MouseAdapter() {
                        @Override
                        public void mouseEntered(java.awt.event.MouseEvent e) {
                            menuButton.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Cambiar cursor a mano
                            menuButton.setText("<html><u>" + option + "</u></html>"); // Subrayar texto al hacer hover
                        }

                        @Override
                        public void mouseExited(java.awt.event.MouseEvent e) {
                            if (menuButton != selectedButton) {
                                menuButton.setText(option); // Eliminar subrayado al salir del botón
                            }
                        }
                    });

                    // Evento de clic para seleccionar una opción
                    menuButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (selectedButton != null) {
                                // Restaurar estilo del botón previamente seleccionado
                                selectedButton.setFont(new Font("Arial", Font.PLAIN, 40));
                                selectedButton.setForeground(Color.decode("#333333"));
                                selectedButton.setText(selectedButton.getText().replace("<html><u>", "").replace("</u></html>", ""));
                            }
                            // Estilizar el botón seleccionado
                            menuButton.setFont(new Font("Arial", Font.BOLD, 40)); // Fuente en negrita
                            menuButton.setForeground(Color.decode("#0068AE")); // Color azul
                            menuButton.setText(option); // Asegurarse de que no tenga subrayado
                            selectedButton = menuButton; // Actualizar el botón seleccionado

                            // Cambiar el contenido dinámico según la opción seleccionada
                            showContent(option);
                        }
                    });

                    // Añadir el botón al panel del menú
                    menuPanel.add(menuButton);
                }

                // Agregar el menú superior a la ventana
                add(menuPanel, BorderLayout.NORTH);

                // Crear el panel de contenido dinámico
                contentPanel = new JPanel(new CardLayout()); // CardLayout para cambiar vistas
                contentPanel.setBackground(Color.WHITE); // Fondo blanco para el contenido
                contentPanel.setPreferredSize(null); // Asegura que ocupe el ancho y alto el espacio disponible
                add(contentPanel, BorderLayout.CENTER); // Asegura que se coloque en el centro y ocupe el espacio restante

                // Mostrar la ventana
                setVisible(true);
            }

            /**
             * Función para cambiar el contenido dinámico del panel central.
             * @param option La opción seleccionada del menú.
             */
            private void showContent(String option) {
                contentPanel.removeAll();

                if ("Productos".equals(option)) {
                    ProductosModel model = new ProductosModel();
                    ProductosView view = new ProductosView();
                    new ProductosController(model, view);
                    contentPanel.add(view, "Productos");
                }

                ((CardLayout) contentPanel.getLayout()).show(contentPanel, option);
                contentPanel.revalidate();
                contentPanel.repaint();
            }
        }