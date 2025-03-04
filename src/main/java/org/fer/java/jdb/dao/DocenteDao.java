package org.fer.java.jdb.dao;

import org.fer.java.jdb.model.Docente;
import org.fer.java.jdb.util.ConexionBaseDatos;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DocenteDao
{
    public void insertar(Docente docente) throws SQLException {
        String sql = "INSERT INTO Docentes (id_persona, id_area, titulo_academico, especialidad, tipo_contrato) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConexionBaseDatos.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, docente.getId_persona());
            stmt.setInt(2, docente.getId_area());
            stmt.setString(3, docente.getTitulo_academico());
            stmt.setString(4, docente.getEspecialidad());
            stmt.setString(5, docente.getTipo_contrato());

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    docente.setId_docente(rs.getInt(1));
                }
            }
        }
    }

    public List<Docente> listar() throws SQLException {
        List<Docente> docentes = new ArrayList<>();
        String sql = "SELECT * FROM Docentes";

        try (Connection conn = ConexionBaseDatos.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Docente docente = new Docente();
                docente.setId_docente(rs.getInt("id_docente"));
                docente.setId_persona(rs.getInt("id_persona"));
                docente.setId_area(rs.getInt("id_area"));
                docente.setTitulo_academico(rs.getString("titulo_academico"));
                docente.setEspecialidad(rs.getString("especialidad"));
                docente.setTipo_contrato(rs.getString("tipo_contrato"));

                docentes.add(docente);
            }
        }
        return docentes;
    }

    public void actualizar(Docente docente) throws SQLException {
        String sql = "UPDATE Docentes SET id_persona=?, id_area=?, titulo_academico=?, especialidad=?, tipo_contrato=? WHERE id_docente=?";
        try (Connection conn = ConexionBaseDatos.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, docente.getId_persona());
            stmt.setInt(2, docente.getId_area());
            stmt.setString(3, docente.getTitulo_academico());
            stmt.setString(4, docente.getEspecialidad());
            stmt.setString(5, docente.getTipo_contrato());
            stmt.setInt(6, docente.getId_docente());

            stmt.executeUpdate();
        }
    }

    public void eliminar(int id) throws SQLException {
        String sql = "DELETE FROM Docentes WHERE id_docente=?";
        try (Connection conn = ConexionBaseDatos.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}
