package com.controller;

import com.model.PedidosModel;
import com.model.UsuariosModel;
import com.model.ProductosModel;
import com.view.PedidosView;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

public class PedidosController {

    private PedidosModel model;
    private PedidosView view;

    /**
     * Constructor del controlador de pedidos.
     *
     * @param model El modelo de pedidos.
     * @param view  La vista de pedidos.
     */
    public PedidosController(PedidosModel model, PedidosView view) {
        this.model = model;
        this.view = view;
        this.view.setController(this);
        cargarPedidos();
    }

    /**
     * Carga los pedidos desde el modelo y actualiza la vista.
     */
    public void cargarPedidos() {
        List<String[]> pedidos = model.obtenerPedidos();
        view.actualizarTabla(pedidos);
    }

    /**
     * Guarda un pedido utilizando el modelo y actualiza la vista.
     *
     * @param datos Datos del pedido a guardar.
     */
    public void guardarPedido(Object[] datos) {
        if (model.guardarPedido(datos) > 0) {
            cargarPedidos();
            view.mostrarMensaje("Pedido guardado correctamente.", "Éxito");
        } else {
            view.mostrarMensaje("Error al guardar el pedido.", "Error");
        }
    }

    /**
     * Elimina un pedido utilizando el modelo y actualiza la vista.
     *
     * @param datos Datos del pedido a eliminar.
     */
    public void eliminarPedido(Object[] datos) {
        try {
            int idPedido = datos[0] instanceof Integer ? (int) datos[0] : Integer.parseInt(datos[0].toString());

            if (PedidosModel.eliminarPedido(idPedido) > 0) {
                view.mostrarMensaje("El pedido con ID " + datos[0] + " ha sido eliminado con éxito.", "Éxito");
                cargarPedidos();
            } else {
                view.mostrarMensaje("No se puede eliminar el pedido porque está siendo utilizado en otros registros.", "Error");
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            view.mostrarMensaje("No se puede eliminar el pedido porque está siendo utilizado en otros registros.", "Error");
        } catch (NumberFormatException e) {
            view.mostrarMensaje("El ID del pedido no es válido.", "Error");
        } catch (Exception e) {
            view.mostrarMensaje("Ocurrió un error al intentar eliminar el pedido.", "Error");
            e.printStackTrace();
        }
    }

    public boolean verificarUsuario(int usuarioId) {
        return UsuariosModel.verificarUsuario(usuarioId);
    }

    public boolean verificarProducto(int productoId) {
        return ProductosModel.verificarProducto(productoId);
    }
}