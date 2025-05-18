package com.controller;

import com.model.UsuariosModel;
import com.view.UsuariosView;

import javax.swing.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

public class UsuariosController {

    private final UsuariosModel model;
    private final UsuariosView view;

    /**
     * Constructor de la clase UsuariosController.
     * Inicializa el modelo y la vista, y actualiza la vista con los datos del modelo.
     *
     * @param model Modelo de usuarios.
     * @param view  Vista de usuarios.
     */
    public UsuariosController(UsuariosModel model, UsuariosView view) {
        this.model = model;
        this.view = view;

        this.view.setController(this);
        actualizarVista();
    }

    public List<String[]> obtenerUsuarios() {
        return model.obtenerUsuarios();
    }

    /**
     * Actualiza la vista de usuarios obteniendo los datos del modelo.
     */
    public void actualizarVista() {
        view.actualizarTabla(obtenerUsuarios());
    }

    /**
     * Guarda un usuario y su dirección utilizando el modelo y actualiza la vista.
     *
     * @param datos Datos del usuario y su dirección a guardar.
     */
    public void guardarUsuario(Object[] datos) {
        int filasAfectadas = model.guardarUsuarioYDireccion(datos);
        if (filasAfectadas > 0) {
            view.mostrarMensaje("Usuario y dirección guardados correctamente.", "Éxito");
            actualizarVista();
        } else {
            view.mostrarMensaje("Error al guardar el usuario y la dirección.", "Error");
        }
    }

    /**
     * Elimina un usuario utilizando el modelo y actualiza la vista.
     *
     * @param datos Datos del usuario a eliminar.
     */
    public void eliminarUsuario(Object[] datos) {
        try {
            int idUsuario = datos[0] instanceof Integer
                    ? (int) datos[0]
                    : Integer.parseInt(datos[0].toString());

            int filasAfectadas = model.eliminarUsuario(idUsuario);

            if (filasAfectadas > 0) {
                view.mostrarMensaje("Usuario eliminado correctamente.", "Éxito");
                actualizarVista();
            } else {
                view.mostrarMensaje("Error al eliminar el usuario.", "Error");
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            view.mostrarMensaje("No se puede eliminar el usuario porque está siendo utilizado en otros registros.", "Error");
        } catch (NumberFormatException e) {
            view.mostrarMensaje("El ID del usuario no es válido.", "Error");
        } catch (Exception e) {
            view.mostrarMensaje("Ocurrió un error al intentar eliminar el usuario: " + e.getMessage(), "Error");
        }
    }

    /**
     * Exporta los usuarios a un archivo CSV utilizando un JFileChooser.
     */
    public void exportarUsuarios() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Selecciona la ubicación para exportar los usuarios: ");
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Archivos CSV", "csv"));

        int userSelection = fileChooser.showSaveDialog(null);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            if (!fileToSave.getName().endsWith(".csv")) {
                fileToSave = new File(fileToSave.getAbsolutePath() + ".csv");
            }

            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(fileToSave));
                List<String[]> usuarios = model.obtenerUsuarios();
                writer.write("ID,Nombre,Apellido,Email,Calle,Ciudad,Código Postal\n");
                for (String[] usuario : usuarios) {
                    writer.write(String.join(",", usuario));
                    writer.newLine();
                }
                writer.close();
                view.mostrarMensaje("Usuarios exportados correctamente a: " + fileToSave.getAbsolutePath(), "Éxito");
            } catch (IOException e) {
                view.mostrarMensaje("Error al exportar los usuarios: " + e.getMessage(), "Error");
            }
        }
    }
}