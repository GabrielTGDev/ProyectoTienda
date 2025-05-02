package org.example;

import dbconnection.DBConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class test extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTable table1;
    private DefaultTableModel tableModel;

    public test() {
        setContentPane(contentPane);
        setModal(true);
        setLayout(new BorderLayout()); // Usar BorderLayout para organizar los componentes
        getRootPane().setDefaultButton(buttonOK);

        // Cambiar texto y color de los botones
        buttonOK.setText("Editar");
        buttonOK.setBackground(Color.YELLOW);
        buttonOK.setOpaque(true);
        buttonOK.setBorderPainted(false);

        buttonCancel.setText("Eliminar");
        buttonCancel.setBackground(Color.RED);
        buttonCancel.setOpaque(true);
        buttonCancel.setBorderPainted(false);

        // Configurar la tabla
        tableModel = new DefaultTableModel(new Object[]{"ID", "Nombre", "Correo"}, 0);
        table1 = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table1); // Agregar la tabla a un JScrollPane

        // Panel inferior para los botones
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(buttonOK);
        buttonPanel.add(buttonCancel);

        // Agregar componentes al layout
        add(scrollPane, BorderLayout.CENTER); // La tabla con scroll en el centro
        add(buttonPanel, BorderLayout.SOUTH); // Los botones en la parte inferior

        // Cargar datos desde la base de datos
        loadTableData();

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onEdit();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onDelete();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        setSize(600, 400); // Tamaño inicial de la ventana
        setLocationRelativeTo(null); // Centrar la ventana
    }

    private void loadTableData() {
        try (Connection connection = DBConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT id, nombre, email FROM usuarios")) {

            tableModel.setRowCount(0); // Limpiar la tabla
            while (resultSet.next()) {
                tableModel.addRow(new Object[]{
                        resultSet.getInt("id"),
                        resultSet.getString("nombre"),
                        resultSet.getString("email")
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar los datos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onEdit() {
        int selectedRow = table1.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Por favor, selecciona una fila para editar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = (int) tableModel.getValueAt(selectedRow, 0);
        String nombre = (String) tableModel.getValueAt(selectedRow, 1);
        String correo = (String) tableModel.getValueAt(selectedRow, 2);

        String nuevoNombre = JOptionPane.showInputDialog(this, "Editar Nombre:", nombre);
        String nuevoCorreo = JOptionPane.showInputDialog(this, "Editar Correo:", correo);

        if (nuevoNombre != null && nuevoCorreo != null) {
            try (Connection connection = DBConnection.getConnection();
                 Statement statement = connection.createStatement()) {

                String query = String.format("UPDATE usuarios SET nombre='%s', email='%s' WHERE id=%d", nuevoNombre, nuevoCorreo, id);
                statement.executeUpdate(query);
                loadTableData(); // Recargar los datos
                JOptionPane.showMessageDialog(this, "Registro actualizado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error al actualizar el registro: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void onDelete() {
        int selectedRow = table1.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Por favor, selecciona una fila para eliminar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = (int) tableModel.getValueAt(selectedRow, 0);

        int confirm = JOptionPane.showConfirmDialog(this, "¿Estás seguro de que deseas eliminar este registro?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try (Connection connection = DBConnection.getConnection();
                 Statement statement = connection.createStatement()) {

                String query = String.format("DELETE FROM usuarios WHERE id=%d", id);
                statement.executeUpdate(query);
                loadTableData(); // Recargar los datos
                JOptionPane.showMessageDialog(this, "Registro eliminado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error al eliminar el registro: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void onCancel() {
        dispose();
    }

    public static void main(String[] args) {
        test dialog = new test();
        dialog.setVisible(true);
        System.exit(0);
    }
}