package com.model;

import com.dbconnection.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PedidosModel {

    public List<String[]> obtenerPedidos() {
        List<String[]> pedidos = new ArrayList<>();
        String query = "SELECT p.id, u.id AS usuario, pr.id AS producto, p.cantidad, p.fecha " +
                "FROM pedidos p " +
                "JOIN usuarios u ON p.usuario_id = u.id " +
                "JOIN productos pr ON p.producto_id = pr.id " +
                "ORDER BY p.id ASC";
        try {
            ResultSet resultSet = DBConnection.executeQuery(query);
            while (resultSet.next()) {
                pedidos.add(new String[]{
                        String.valueOf(resultSet.getInt("id")),
                        resultSet.getString("usuario"),
                        resultSet.getString("producto"),
                        String.valueOf(resultSet.getInt("cantidad")),
                        resultSet.getDate("fecha").toString()
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.closeConnection();
        }

        return pedidos;
    }

    public int guardarPedido(Object[] datos) {
        if (datos.length < 5) {
            throw new IllegalArgumentException("El objeto debe contener 5 elementos: ID, Usuario, Producto, Cantidad y Fecha.");
        }

        int rowsAffected = 0;

        try {
            if (datos[0] == null) {
                String queryInsert = "INSERT INTO pedidos (usuario_id, producto_id, cantidad, fecha) VALUES (?, ?, ?, ?)";
                rowsAffected = DBConnection.executeUpdate(queryInsert, datos[1], datos[2], datos[3], datos[4]);
            } else {
                String queryUpdate = "UPDATE pedidos SET usuario_id = ?, producto_id = ?, cantidad = ?, fecha = ? WHERE id = ?";
                rowsAffected = DBConnection.executeUpdate(queryUpdate, datos[1], datos[2], datos[3], datos[4], datos[0]);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.closeConnection();
        }

        return rowsAffected;
    }

    public static int eliminarPedido(int id) throws SQLIntegrityConstraintViolationException  {
        String query = "DELETE FROM pedidos WHERE id = ?";
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
}