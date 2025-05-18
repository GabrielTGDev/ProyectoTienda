package com.model;

import com.dbconnection.DBConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
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
        } finally {
            DBConnection.closeConnection();
        }

        return productos;
    }

    /**
     * Función para eliminar un producto de la base de datos.
     *
     * @param id ID del producto a eliminar.
     * @return Número de filas afectadas por la eliminación.
     * @throws SQLIntegrityConstraintViolationException Si el producto está siendo utilizado en otros registros.
     */
    public static int eliminarProducto(int id) throws SQLIntegrityConstraintViolationException {
        String query = "DELETE FROM productos WHERE id = ?;";
        int rowsAffected = 0;

        try {
            rowsAffected = DBConnection.executeUpdate(query, id);
        } catch (SQLIntegrityConstraintViolationException e) {
            throw e;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.closeConnection();
        }

        return rowsAffected;
    }

    public int guardarProducto(Object[] datos) {
        if (datos.length < 4) {
            throw new IllegalArgumentException("El objeto debe contener 4 elementos: ID, nombre, precio y categoría.");
        }

        int rowsAffected = 0;

        try {
            if (datos[0] == null) {
                // Insertar si el ID es null
                String queryInsert = "INSERT INTO productos (nombre, precio, categoria_id) VALUES (?, ?, ?);";
                rowsAffected = DBConnection.executeUpdate(queryInsert, datos[1], datos[2], datos[3]);
            } else {
                // Actualizar el producto
                String queryUpdate = "UPDATE productos SET nombre = ?, precio = ?, categoria_id = ? WHERE id = ?;";
                rowsAffected = DBConnection.executeUpdate(queryUpdate, datos[1], datos[2], datos[3], datos[0]);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.closeConnection();
        }

        return rowsAffected;
    }

    public static boolean verificarProducto(int productoId) {
        String query = "SELECT COUNT(*) FROM productos WHERE id = ?;";
        boolean existe = false;

        try {
            ResultSet resultSet = DBConnection.executeQuery(query, productoId);
            if (resultSet.next()) {
                existe = resultSet.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.closeConnection();
        }

        return existe;
    }
}