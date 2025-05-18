package com.view;

import com.controller.PedidosController;
import com.controller.ProductosController;
import com.controller.UsuariosController;
import com.model.PedidosModel;
import com.model.ProductosModel;
import com.model.UsuariosModel;

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
        setSize(1920, 1080);
        setMinimumSize(new Dimension(1280, 720));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.WHITE);
        setLayout(new BorderLayout());

        // Crear el menú superior
        JPanel menuPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 80, 26));
        menuPanel.setPreferredSize(new Dimension(getWidth(), 100));
        menuPanel.setBackground(Color.decode("#ECF0F1"));

        String[] menuOptions = {"Usuarios", "Pedidos", "Productos"};
        for (String option : menuOptions) {
            JButton menuButton = new JButton(option);
            menuButton.setFocusPainted(false);
            menuButton.setBorderPainted(false);
            menuButton.setContentAreaFilled(false);
            menuButton.setFont(new Font("Arial", Font.PLAIN, 40));
            menuButton.setForeground(Color.decode("#333333"));

            // Cambiar el color de fondo al pasar el mouse
            menuButton.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseEntered(java.awt.event.MouseEvent e) {
                    menuButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
                    menuButton.setText("<html><u>" + option + "</u></html>");
                }

                @Override
                public void mouseExited(java.awt.event.MouseEvent e) {
                    if (menuButton != selectedButton) {
                        menuButton.setText(option);
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
                    menuButton.setFont(new Font("Arial", Font.BOLD, 40));
                    menuButton.setForeground(Color.decode("#0068AE"));
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
        contentPanel = new JPanel(new CardLayout());
        contentPanel.setBackground(Color.WHITE);
        add(contentPanel, BorderLayout.CENTER);

        // Mostrar la ventana
        setVisible(true);
    }

    /**
     * Función para cambiar el contenido dinámico del panel central.
     *
     * @param option La opción seleccionada del menú.
     */
    private void showContent(String option) {
        contentPanel.removeAll();

        if ("Usuarios".equals(option)) {
            UsuariosModel model = new UsuariosModel();
            UsuariosView view = new UsuariosView();
            new UsuariosController(model, view);
            contentPanel.add(view, "Usuarios");
        }

        if ("Pedidos".equals(option)) {
            PedidosModel model = new PedidosModel();
            PedidosView view = new PedidosView();
            new PedidosController(model, view);
            contentPanel.add(view, "Pedidos");
        }

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