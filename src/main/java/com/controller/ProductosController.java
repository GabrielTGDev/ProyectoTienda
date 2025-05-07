package com.controller;

import com.model.ProductosModel;
import com.view.ProductosView;

import java.util.List;

public class ProductosController {

    private ProductosModel model;
    private ProductosView view;

    public ProductosController(ProductosModel model, ProductosView view) {
        this.model = model;
        this.view = view;
        cargarProductos();
    }

    /**
     * Función para cargar los productos desde el modelo y actualizar la vista.
     */
    private void cargarProductos() {
        List<String[]> productos = model.obtenerProductos();
        view.actualizarTabla(productos);
    }
}