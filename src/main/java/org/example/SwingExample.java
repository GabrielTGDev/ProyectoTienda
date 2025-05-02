package org.example;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class SwingExample {

    public static void main(String[] args) {
        // Crear el marco principal
        JFrame frame = new JFrame("Ejemplo de Java Swing");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());
        JScrollPane scrollPane = new JScrollPane();
        frame.add(scrollPane, BorderLayout.CENTER);

        // Crear un panel superior con un título
        JPanel topPanel = new JPanel();
        JLabel titleLabel = new JLabel("Bienvenido a la aplicación");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        topPanel.add(titleLabel);

        // Crear un panel central con varios elementos
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        centerPanel.add(new JLabel("Nombre:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        centerPanel.add(new JTextField(15), gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        centerPanel.add(new JLabel("Correo electrónico:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        centerPanel.add(new JTextField(15), gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(new JButton("Enviar"));
        buttonPanel.add(new JButton("Limpiar"));
        centerPanel.add(buttonPanel, gbc);

        // Crear un panel inferior con una tabla
        JPanel bottomPanel = new JPanel();
        String[] columnNames = {"ID", "Nombre", "Correo"};
        Object[][] data = {
                {1, "Juan Pérez", "juan@example.com"},
                {2, "Ana López", "ana@example.com"},
                {3, "Carlos Ruiz", "carlos@example.com"}
        };
        JTable table = new JTable(new DefaultTableModel(data, columnNames));
        JScrollPane tableScrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);
        bottomPanel.setLayout(new BorderLayout());
        bottomPanel.add(tableScrollPane, BorderLayout.CENTER);

        // Agregar los paneles al marco principal
        scrollPane.setViewportView(new JPanel(new BorderLayout()) {{
            add(topPanel, BorderLayout.NORTH);
            add(centerPanel, BorderLayout.CENTER);
            add(bottomPanel, BorderLayout.SOUTH);
        }});

        // Hacer visible el marco
        frame.setVisible(true);
    }
}