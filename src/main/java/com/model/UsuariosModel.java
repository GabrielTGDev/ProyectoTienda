package com.model;

import com.dbconnection.DBConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

public class UsuariosModel {

    /**
     * Obtiene la lista de usuarios desde la base de datos.
     *
     * @return Lista de arreglos con los datos de cada usuario.
     */
    public List<String[]> obtenerUsuarios() {
        List<String[]> usuarios = new ArrayList<>();
        String query = "SELECT u.id, u.nombre, u.apellido, u.email, d.calle, d.ciudad, d.codigo_postal " +
                "FROM usuarios u " +
                "LEFT JOIN direcciones d ON u.id = d.usuario_id " +
                "ORDER BY u.id ASC;";

        try {
            ResultSet resultSet = DBConnection.executeQuery(query);
            while (resultSet.next()) {
                usuarios.add(new String[]{
                        String.valueOf(resultSet.getInt("id")),
                        resultSet.getString("nombre"),
                        resultSet.getString("apellido"),
                        resultSet.getString("email"),
                        resultSet.getString("calle"),
                        resultSet.getString("ciudad"),
                        resultSet.getString("codigo_postal")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        DBConnection.closeConnection();

        return usuarios;
    }

    /**
     * Guarda o actualiza un usuario y su dirección en la base de datos.
     * <p>
     * Si el ID del usuario es nulo, se inserta un nuevo usuario y su dirección.
     * Si el ID del usuario no es nulo, se actualizan los datos del usuario y su dirección.
     *
     * @param datos Arreglo de objetos que contiene los datos del usuario y su dirección:
     *              [0] ID del usuario (puede ser null para inserción),
     *              [1] Nombre del usuario,
     *              [2] Apellido del usuario,
     *              [3] Email del usuario,
     *              [4] Calle de la dirección,
     *              [5] Ciudad de la dirección,
     *              [6] Código postal de la dirección.
     * @return Número de filas afectadas por la operación.
     */
    public int guardarUsuarioYDireccion(Object[] datos) {
        if (datos == null || datos.length < 7) {
            throw new IllegalArgumentException("El arreglo de datos debe contener al menos 7 elementos: ID, nombre, apellido, email, calle, ciudad y código postal.");
        }

        int rowsAffected = 0;

        try {
            if (datos[0] == null) {
                // Insertar nuevo usuario y dirección
                String queryUsuario = "INSERT INTO usuarios (nombre, apellido, email) VALUES (?, ?, ?);";
                rowsAffected = DBConnection.executeUpdate(queryUsuario, datos[1], datos[2], datos[3]);

                if (rowsAffected > 0) {
                    String queryDireccion = "INSERT INTO direcciones (usuario_id, calle, ciudad, codigo_postal) VALUES (LAST_INSERT_ID(), ?, ?, ?);";
                    rowsAffected = DBConnection.executeUpdate(queryDireccion, datos[4], datos[5], datos[6]);
                }
            } else {
                // Actualizar usuario y dirección
                String queryUsuario = "UPDATE usuarios SET nombre = ?, apellido = ?, email = ? WHERE id = ?;";
                rowsAffected = DBConnection.executeUpdate(queryUsuario, datos[1], datos[2], datos[3], datos[0]);

                if (rowsAffected > 0) {
                    String queryDireccion = "UPDATE direcciones SET calle = ?, ciudad = ?, codigo_postal = ? WHERE usuario_id = ?;";
                    rowsAffected = DBConnection.executeUpdate(queryDireccion, datos[4], datos[5], datos[6], datos[0]);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.closeConnection();
        }

        return rowsAffected;
    }

    /**
     * Elimina un usuario de la base de datos.
     *
     * @param id ID del usuario a eliminar.
     * @return Número de filas afectadas.
     */
    public int eliminarUsuario(int id) throws SQLIntegrityConstraintViolationException {
        int rowsAffected = 0;

        try {
            // Verificar si el usuario tiene pedidos asociados
            String queryVerificarPedidos = "SELECT COUNT(*) FROM pedidos WHERE usuario_id = ?;";
            ResultSet resultSet = DBConnection.executeQuery(queryVerificarPedidos, id);
            if (resultSet.next() && resultSet.getInt(1) > 0) {
                throw new SQLIntegrityConstraintViolationException(); // Lanzar excepción si hay pedidos asociados
            }

            // Eliminar las direcciones asociadas al usuario
            String queryDirecciones = "DELETE FROM direcciones WHERE usuario_id = ?;";
            DBConnection.executeUpdate(queryDirecciones, id);

            // Eliminar el usuario
            String queryUsuario = "DELETE FROM usuarios WHERE id = ?;";
            rowsAffected = DBConnection.executeUpdate(queryUsuario, id);
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