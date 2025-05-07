package com.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainView extends JFrame {

    private JPanel contentPanel; // Panel para el contenido dinámico
    private JButton selectedButton; // Botón actualmente seleccionado

    public static void main(String[] args) {
        new MainView();
    }

    public MainView() {
        // Configuración de la ventana principal
        setTitle("Tienda - Ventana Principal");
        setSize(1920, 1080);
        setMinimumSize(new Dimension(1280, 720));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.WHITE);
        setLayout(new BorderLayout());

        // Crear el menú superior
        JPanel menuPanel = new JPanel();
        menuPanel.setPreferredSize(new Dimension(getWidth(), 100));
        menuPanel.setBackground(Color.decode("#ECF0F1"));
        menuPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 80, 26)); // Padding y espaciado

        // Crear botones del menú
        String[] menuOptions = {"Usuarios", "Productos", "Categorías", "Pedidos"};
        for (String option : menuOptions) {
            JButton menuButton = new JButton(option);
            menuButton.setFocusPainted(false);
            menuButton.setBorderPainted(false);
            menuButton.setContentAreaFilled(false);
            menuButton.setFont(new Font("Arial", Font.PLAIN, 40));
            menuButton.setForeground(Color.decode("#333333"));

            menuButton.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseEntered(java.awt.event.MouseEvent e) {
                    menuButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
                    menuButton.setText("<html><u>" + option + "</u></html>"); // Subrayado al hacer hover
                }

                @Override
                public void mouseExited(java.awt.event.MouseEvent e) {
                    if (menuButton != selectedButton) {
                        menuButton.setText(option); // Eliminar subrayado al salir del botón
                    }
                }
            });

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
                    selectedButton = menuButton;

                    // Cambiar el contenido dinámico (por ahora vacío)
                    showContent(option);
                }
            });

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

    // Método para cambiar el contenido dinámico
    private void showContent(String option) {
        // Aquí se cargarán las vistas secundarias según la opción seleccionada
        System.out.println("Opción seleccionada: " + option);
    }
}