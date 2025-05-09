package com.view;

import javax.swing.*;
import java.awt.*;

public class FormsView extends JPanel {

    private JLabel titleLabel; // Etiqueta para el título
    private JPanel contentPanel; // Panel dinámico para el contenido
    private JPanel bottomPanel; // Panel inferior con botones

    /**
     * Constructor de FormsView.
     *
     * @param title   Título que se mostrará en la parte superior.
     * @param content Contenido que se mostrará en el panel dinámico.
     */
    public FormsView(String title, JPanel content) {
        // Configuración del panel principal
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(1280, 720));
        setBackground(Color.decode("#FFFFFF"));

        // Crear y configurar el título
        titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setPreferredSize(new Dimension(1280, 100));
        titleLabel.setOpaque(true);
        titleLabel.setBackground(Color.decode("#ECF0F1"));
        titleLabel.setForeground(Color.decode("#333333"));
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 40));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setVerticalAlignment(SwingConstants.CENTER);

        // Agregar el título al panel principal
        add(titleLabel, BorderLayout.NORTH);

        // Configurar el panel dinámico
        contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(Color.WHITE);

        // Agregar el contenido proporcionado al panel dinámico
        if (content != null) {
            contentPanel.add(content, BorderLayout.CENTER);
        }

        // Agregar el panel dinámico al panel principal
        add(contentPanel, BorderLayout.CENTER);

        // Crear y configurar el panel inferior
        bottomPanel = new JPanel();
        bottomPanel.setPreferredSize(new Dimension(1280, 80));
        bottomPanel.setBackground(Color.decode("#FFFFFF"));
        bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));

        // Crear y configurar los botones
        JButton acceptButton = new JButton("Aceptar");
        acceptButton.setPreferredSize(new Dimension(140, 60));
        acceptButton.setFont(new Font("Arial", Font.PLAIN, 24));
        acceptButton.setBackground(Color.decode("#48BA78"));
        acceptButton.setForeground(Color.WHITE);
        acceptButton.setFocusPainted(false);
        acceptButton.setBorder(BorderFactory.createEmptyBorder());
        acceptButton.setBorder(BorderFactory.createLineBorder(Color.decode("#48BA78"), 1, true));

        JButton cancelButton = new JButton("Cancelar");
        cancelButton.setPreferredSize(new Dimension(140, 60));
        cancelButton.setFont(new Font("Arial", Font.PLAIN, 24));
        cancelButton.setBackground(Color.decode("#C54A3D"));
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setFocusPainted(false);
        cancelButton.setBorder(BorderFactory.createEmptyBorder());
        cancelButton.setBorder(BorderFactory.createLineBorder(Color.decode("#C54A3D"), 1, true));

        // Agregar los botones al panel inferior
        bottomPanel.add(acceptButton);
        bottomPanel.add(cancelButton);

        // Agregar el panel inferior al panel principal
        add(bottomPanel, BorderLayout.SOUTH);
    }
}