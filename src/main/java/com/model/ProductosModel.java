package com.model;

import com.dbconnection.DBConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductosModel {

    /**
     * Función para obtener la lista de productos con sus categorías desde la base de datos.
     *
     * @return Lista de productos, donde cada producto es un arreglo de cadenas con sus datos.
     */
    public List<String[]> obtenerProductos() {
        List<String[]> productos = new ArrayList<>();
        String query = "SELECT p.id, p.nombre, p.precio, c.nombre AS categoria"
                + " FROM productos p JOIN categorias c ON p.categoria_id = c.id"
                + " ORDER BY p.id ASC;";

        try {
            ResultSet resultSet = DBConnection.executeQuery(query);
            while (resultSet.next()) {
                productos.add(new String[]{
                        String.valueOf(resultSet.getInt("id")),
                        resultSet.getString("nombre"),
                        String.valueOf(resultSet.getDouble("precio")),
                        resultSet.getString("categoria")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        DBConnection.closeConnection();

        return productos;
    }
}