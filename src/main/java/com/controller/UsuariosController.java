package com.controller;

import com.model.UsuariosModel;
import com.view.UsuariosView;

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

    /**
     * Actualiza la vista de usuarios obteniendo los datos del modelo.
     */
    public void actualizarVista() {
        List<String[]> usuarios = model.obtenerUsuarios();
        view.actualizarTabla(usuarios);
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
        }  catch (NumberFormatException e) {
            view.mostrarMensaje("El ID del usuario no es válido.", "Error");
        } catch (Exception e) {
            view.mostrarMensaje("Ocurrió un error al intentar eliminar el usuario: " + e.getMessage(), "Error");
        }
    }
}