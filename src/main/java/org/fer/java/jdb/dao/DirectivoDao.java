package org.fer.java.jdb.dao;
import org.fer.java.jdb.model.Directivo;
import org.fer.java.jdb.util.ConexionBaseDatos;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class DirectivoDao
{
    public void insertar(Directivo directivo) throws SQLException {
        String sql = "INSERT INTO Directivos (id_persona, id_area, cargo, nivel_jerarquico, fecha_inicio_cargo) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConexionBaseDatos.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, directivo.getId_persona());
            stmt.setInt(2, directivo.getId_area());
            stmt.setString(3, directivo.getCargo());
            stmt.setString(4, directivo.getNivel_jerarquico());
            stmt.setString(5, directivo.getFecha_inicio_cargo());

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    directivo.setId_directivo(rs.getInt(1));
                }
            }
        }
    }

    public List<Directivo> listar() throws SQLException {
        List<Directivo> directivos = new ArrayList<>();
        String sql = "SELECT * FROM Directivos";

        try (Connection conn = ConexionBaseDatos.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Directivo directivo = new Directivo();
                directivo.setId_directivo(rs.getInt("id_directivo"));
                directivo.setId_persona(rs.getInt("id_persona"));
                directivo.setId_area(rs.getInt("id_area"));
                directivo.setCargo(rs.getString("cargo"));
                directivo.setNivel_jerarquico(rs.getString("nivel_jerarquico"));
                directivo.setFecha_inicio_cargo(rs.getString("fecha_inicio_cargo"));

                directivos.add(directivo);
            }
        }
        return directivos;
    }

    public void actualizar(Directivo directivo) throws SQLException {
        String sql = "UPDATE Directivos SET id_persona=?, id_area=?, cargo=?, nivel_jerarquico=?, fecha_inicio_cargo=? WHERE id_directivo=?";
        try (Connection conn = ConexionBaseDatos.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, directivo.getId_persona());
            stmt.setInt(2, directivo.getId_area());
            stmt.setString(3, directivo.getCargo());
            stmt.setString(4, directivo.getNivel_jerarquico());
            stmt.setString(5, directivo.getFecha_inicio_cargo());
            stmt.setInt(6, directivo.getId_directivo());

            stmt.executeUpdate();
        }
    }

    public void eliminar(int id) throws SQLException {
        String sql = "DELETE FROM Directivos WHERE id_directivo=?";
        try (Connection conn = ConexionBaseDatos.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}
