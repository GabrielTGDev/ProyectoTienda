package views;

import dbconnection.DBConnection;
import java.awt.BorderLayout;
import java.awt.Component;
import java.util.Arrays;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import models.*;

public class ProyectoTiendaForm extends JFrame {
    private final DefaultTableModel tm;

    public ProyectoTiendaForm() {
        tm = new DefaultTableModel(Usuario.getFields(), 0);
        initComponents();
        iniciarJPanel();
        setLocationRelativeTo(null);
        DBConnection.openConn();
    }

    private void iniciarJPanel() {
        jPanelProducto.setLayout(new BorderLayout());
        jPanelPedido.setLayout(new BorderLayout());
        jPanelDireccion.setLayout(new BorderLayout());
        jPanelCategoria.setLayout(new BorderLayout());
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbed = new javax.swing.JTabbedPane();
        jPanelUsuario = new javax.swing.JPanel();
        jScrollTable = new javax.swing.JScrollPane();
        jTable = new javax.swing.JTable();
        jPanelProducto = new javax.swing.JPanel();
        jPanelPedido = new javax.swing.JPanel();
        jPanelDireccion = new javax.swing.JPanel();
        jPanelCategoria = new javax.swing.JPanel();
        jButtonSelect = new javax.swing.JButton();
        jButtonInsert = new javax.swing.JButton();
        jButtonUpdate = new javax.swing.JButton();
        jButtonDelete = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Proyecto Tienda");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jTabbed.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTabbedMouseClicked(evt);
            }
        });

        jTable.setModel(tm);
        jScrollTable.setViewportView(jTable);

        javax.swing.GroupLayout jPanelUsuarioLayout = new javax.swing.GroupLayout(jPanelUsuario);
        jPanelUsuario.setLayout(jPanelUsuarioLayout);
        jPanelUsuarioLayout.setHorizontalGroup(
            jPanelUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollTable, javax.swing.GroupLayout.DEFAULT_SIZE, 462, Short.MAX_VALUE)
        );
        jPanelUsuarioLayout.setVerticalGroup(
            jPanelUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelUsuarioLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollTable, javax.swing.GroupLayout.DEFAULT_SIZE, 347, Short.MAX_VALUE))
        );

        jTabbed.addTab("Usuario", jPanelUsuario);

        javax.swing.GroupLayout jPanelProductoLayout = new javax.swing.GroupLayout(jPanelProducto);
        jPanelProducto.setLayout(jPanelProductoLayout);
        jPanelProductoLayout.setHorizontalGroup(
            jPanelProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 462, Short.MAX_VALUE)
        );
        jPanelProductoLayout.setVerticalGroup(
            jPanelProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 353, Short.MAX_VALUE)
        );

        jTabbed.addTab("Producto", jPanelProducto);

        javax.swing.GroupLayout jPanelPedidoLayout = new javax.swing.GroupLayout(jPanelPedido);
        jPanelPedido.setLayout(jPanelPedidoLayout);
        jPanelPedidoLayout.setHorizontalGroup(
            jPanelPedidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 462, Short.MAX_VALUE)
        );
        jPanelPedidoLayout.setVerticalGroup(
            jPanelPedidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 353, Short.MAX_VALUE)
        );

        jTabbed.addTab("Pedido", jPanelPedido);

        javax.swing.GroupLayout jPanelDireccionLayout = new javax.swing.GroupLayout(jPanelDireccion);
        jPanelDireccion.setLayout(jPanelDireccionLayout);
        jPanelDireccionLayout.setHorizontalGroup(
            jPanelDireccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 462, Short.MAX_VALUE)
        );
        jPanelDireccionLayout.setVerticalGroup(
            jPanelDireccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 353, Short.MAX_VALUE)
        );

        jTabbed.addTab("Dirección", jPanelDireccion);

        javax.swing.GroupLayout jPanelCategoriaLayout = new javax.swing.GroupLayout(jPanelCategoria);
        jPanelCategoria.setLayout(jPanelCategoriaLayout);
        jPanelCategoriaLayout.setHorizontalGroup(
            jPanelCategoriaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 462, Short.MAX_VALUE)
        );
        jPanelCategoriaLayout.setVerticalGroup(
            jPanelCategoriaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 353, Short.MAX_VALUE)
        );

        jTabbed.addTab("Categoría", jPanelCategoria);

        jButtonSelect.setText("Buscar");
        jButtonSelect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSelectActionPerformed(evt);
            }
        });

        jButtonInsert.setText("Insertar");
        jButtonInsert.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonInsertActionPerformed(evt);
            }
        });

        jButtonUpdate.setText("Actualizar");
        jButtonUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonUpdateActionPerformed(evt);
            }
        });

        jButtonDelete.setText("Eliminar");
        jButtonDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDeleteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbed)
            .addGroup(layout.createSequentialGroup()
                .addGap(56, 56, 56)
                .addComponent(jButtonSelect)
                .addGap(18, 18, 18)
                .addComponent(jButtonInsert)
                .addGap(18, 18, 18)
                .addComponent(jButtonUpdate)
                .addGap(18, 18, 18)
                .addComponent(jButtonDelete)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonSelect)
                    .addComponent(jButtonInsert)
                    .addComponent(jButtonUpdate)
                    .addComponent(jButtonDelete))
                .addGap(18, 18, 18)
                .addComponent(jTabbed))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonSelectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSelectActionPerformed
//        try (ResultSet rs = DBConnection.getStmt().executeQuery("SELECT * FROM USUARIOS;")) {
//            while(rs.next()) {
//                System.out.print(rs.getString("nombre"));
//                System.out.print("\t" + rs.getString("apellido"));
//                System.out.println("\t" + rs.getString("email"));
//            }
//        } catch (Exception ex) {
//            System.out.println("Unconnected");
//        }
    }//GEN-LAST:event_jButtonSelectActionPerformed

    private void jButtonInsertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonInsertActionPerformed
        
    }//GEN-LAST:event_jButtonInsertActionPerformed

    private void jButtonUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonUpdateActionPerformed
        
    }//GEN-LAST:event_jButtonUpdateActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        DBConnection.closeConn(false);
    }//GEN-LAST:event_formWindowClosing

    private void jButtonDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDeleteActionPerformed
//        try {
//            String sql = "DELETE FROM USUARIOS WHERE ID > 50;";
//            System.out.println(sql);
//            System.out.println("SE HAN MODIFICADO: " + DBConnection.getStmt().executeUpdate(sql) + " FILAS.");
//        } catch(Exception ex) { System.err.println(ex.getMessage()); }
    }//GEN-LAST:event_jButtonDeleteActionPerformed

    private void jTabbedMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabbedMouseClicked
        System.out.println(jTabbed.getTitleAt(jTabbed.getSelectedIndex()));
        Component selected = jTabbed.getSelectedComponent();
        if (selected == jPanelUsuario) {
            tm.setColumnIdentifiers(Usuario.getFields());
            jPanelUsuario.setLayout(new BorderLayout());
            jPanelUsuario.add(jScrollTable);
            System.out.println(Arrays.toString(Usuario.getFields()));
        } else if (selected == jPanelProducto) {
            tm.setColumnIdentifiers(Producto.getFields());
            jPanelProducto.add(jScrollTable);
            System.out.println(Arrays.toString(Producto.getFields()));
        } else if (selected == jPanelPedido) {
            tm.setColumnIdentifiers(Pedido.getFields());
            jPanelPedido.add(jScrollTable);
            System.out.println(Arrays.toString(Pedido.getFields()));
        } else if (selected == jPanelDireccion) {
            tm.setColumnIdentifiers(Direccion.getFields());
            jPanelDireccion.add(jScrollTable);
            System.out.println(Arrays.toString(Direccion.getFields()));
        } else {
            tm.setColumnIdentifiers(Categoria.getFields());
            jPanelCategoria.add(jScrollTable);
            System.out.println(Arrays.toString(Categoria.getFields()));
        }
        jTable.setModel(tm);
    }//GEN-LAST:event_jTabbedMouseClicked

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            new ProyectoTiendaForm().setVisible(true);
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonDelete;
    private javax.swing.JButton jButtonInsert;
    private javax.swing.JButton jButtonSelect;
    private javax.swing.JButton jButtonUpdate;
    private javax.swing.JPanel jPanelCategoria;
    private javax.swing.JPanel jPanelDireccion;
    private javax.swing.JPanel jPanelPedido;
    private javax.swing.JPanel jPanelProducto;
    private javax.swing.JPanel jPanelUsuario;
    private javax.swing.JScrollPane jScrollTable;
    private javax.swing.JTabbedPane jTabbed;
    private javax.swing.JTable jTable;
    // End of variables declaration//GEN-END:variables
}