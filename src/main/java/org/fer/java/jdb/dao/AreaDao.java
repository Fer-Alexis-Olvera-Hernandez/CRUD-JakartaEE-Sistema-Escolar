package org.fer.java.jdb.dao;

import org.fer.java.jdb.model.Area;
import org.fer.java.jdb.util.ConexionBaseDatos;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class AreaDao
{

    public void insertar(Area area) throws SQLException {
        String sql = "INSERT INTO Areas (nombre_area, descripcion) VALUES (?, ?)";
        try (Connection conn = ConexionBaseDatos.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, area.getNombre_area());
            stmt.setString(2, area.getDescripcion());
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    area.setId_area(rs.getInt(1));
                }
            }
        }
    }

    public List<Area> listar() throws SQLException {
        List<Area> areas = new ArrayList<>();
        String sql = "SELECT * FROM Areas";
        try (Connection conn = ConexionBaseDatos.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Area area = new Area();
                area.setId_area(rs.getInt("id_area"));
                area.setNombre_area(rs.getString("nombre_area"));
                area.setDescripcion(rs.getString("descripcion"));
                areas.add(area);
            }
        }
        return areas;
    }

    public void actualizar(Area area) throws SQLException {
        String sql = "UPDATE Areas SET nombre_area=?, descripcion=? WHERE id_area=?";
        try (Connection conn = ConexionBaseDatos.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, area.getNombre_area());
            stmt.setString(2, area.getDescripcion());
            stmt.setInt(3, area.getId_area());
            stmt.executeUpdate();
        }
    }

    public void eliminar(int id) throws SQLException {
        String sql = "DELETE FROM Areas WHERE id_area=?";
        try (Connection conn = ConexionBaseDatos.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}
