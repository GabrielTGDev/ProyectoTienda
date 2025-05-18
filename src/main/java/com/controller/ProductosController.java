package com.controller;

import com.model.ProductosModel;
import com.view.ProductosView;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

public class ProductosController {

    private final ProductosModel model;
    private final ProductosView view;

    public ProductosController(ProductosModel model, ProductosView view) {
        this.model = model;
        this.view = view;
        this.view.setController(this); // Vincular el controlador con la vista
        cargarProductos();
    }

    /**
     * Carga los productos desde el modelo y actualiza la vista.
     */
    public void cargarProductos() {
        List<String[]> productos = model.obtenerProductos();
        view.actualizarTabla(productos);
    }

    /**
     * Elimina un producto utilizando el modelo y actualiza la vista.
     *
     * @param datos Datos del producto a eliminar.
     */
    public void eliminarProducto(Object[] datos) {
        try {
            int idProducto = datos[0] instanceof Integer ? (int) datos[0] : Integer.parseInt(datos[0].toString());

            if (ProductosModel.eliminarProducto(idProducto) > 0) {
                view.mostrarMensaje("El producto \"" + datos[1] + "\" con ID " + datos[0] + " ha sido eliminado con éxito.", "Éxito");
                cargarProductos();
            } else {
                view.mostrarMensaje("No se puede eliminar el producto porque está siendo utilizado en otros registros.", "Error");
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            view.mostrarMensaje("No se puede eliminar el producto porque está siendo utilizado en otros registros.", "Error");
        } catch (NumberFormatException e) {
            view.mostrarMensaje("El ID del producto no es válido.", "Error");
        } catch (Exception e) {
            view.mostrarMensaje("Ocurrió un error al intentar eliminar el producto.", "Error");
            e.printStackTrace();
        }
    }

    public void guardarProducto(Object[] objects) {
        try {
            if (model.guardarProducto(objects) > 0) {
                view.mostrarMensaje("El producto \"" + objects[1] + "\" ha sido guardado con éxito.", "Éxito");
                cargarProductos();
            } else {
                view.mostrarMensaje("No se pudo guardar el producto. Verifique los datos ingresados.", "Error");
            }
        } catch (NumberFormatException e) {
            view.mostrarMensaje("El ID del producto no es válido.", "Error");
        } catch (Exception e) {
            view.mostrarMensaje("Ocurrió un error al intentar guardar el producto.", "Error");
            e.printStackTrace();
        }
    }
}