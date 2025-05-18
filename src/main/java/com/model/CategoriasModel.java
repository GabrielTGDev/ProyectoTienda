package com.model;

import com.dbconnection.DBConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoriasModel {

    /**
     * Obtiene la lista de categorías desde la base de datos.
     *
     * @return Lista de arreglos con el id y nombre de cada categoría.
     */
    public List<String[]> obtenerCategorias() {
        List<String[]> categorias = new ArrayList<>();
        String query = "SELECT id, nombre FROM categorias ORDER BY nombre ASC;";

        try {
            ResultSet resultSet = DBConnection.executeQuery(query);
            while (resultSet.next()) {
                categorias.add(new String[]{
                        String.valueOf(resultSet.getInt("id")),
                        resultSet.getString("nombre")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        DBConnection.closeConnection();

        return categorias;
    }
}