package org.fer.java.jdb.dao;
import org.fer.java.jdb.model.Persona;
import org.fer.java.jdb.util.ConexionBaseDatos;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonaDao {

    public void insertar(Persona persona) throws SQLException {
        String sql = "INSERT INTO Personas (nombre, apellido, fecha_nacimiento, genero, direccion, telefono, correo) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConexionBaseDatos.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, persona.getNombre());
            stmt.setString(2, persona.getApellido());
            stmt.setString(3, persona.getFecha_nacimiento());
            stmt.setString(4, persona.getGenero());
            stmt.setString(5, persona.getDireccion());
            stmt.setString(6, persona.getTelefono());
            stmt.setString(7, persona.getCorreo());

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    persona.setId_persona(rs.getInt(1));
                }
            }
        }
    }

    public List<Persona> listar() throws SQLException {
        List<Persona> personas = new ArrayList<>();
        String sql = "SELECT * FROM Personas";

        try (Connection conn = ConexionBaseDatos.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Persona persona = new Persona();
                persona.setId_persona(rs.getInt("id_persona"));
                persona.setNombre(rs.getString("nombre"));
                persona.setApellido(rs.getString("apellido"));
                persona.setFecha_nacimiento(rs.getString("fecha_nacimiento"));
                persona.setGenero(rs.getString("genero"));
                persona.setDireccion(rs.getString("direccion"));
                persona.setTelefono(rs.getString("telefono"));
                persona.setCorreo(rs.getString("correo"));

                personas.add(persona);
            }
        }
        return personas;
    }

    public void actualizar(Persona persona) throws SQLException {
        String sql = "UPDATE Personas SET nombre=?, apellido=?, fecha_nacimiento=?, genero=?, direccion=?, telefono=?, correo=? WHERE id_persona=?";

        try (Connection conn = ConexionBaseDatos.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, persona.getNombre());
            stmt.setString(2, persona.getApellido());
            stmt.setString(3, persona.getFecha_nacimiento());
            stmt.setString(4, persona.getGenero());
            stmt.setString(5, persona.getDireccion());
            stmt.setString(6, persona.getTelefono());
            stmt.setString(7, persona.getCorreo());
            stmt.setInt(8, persona.getId_persona());

            stmt.executeUpdate();
        }
    }

    public void eliminar(int id) throws SQLException {
        String sql = "DELETE FROM Personas WHERE id_persona=?";

        try (Connection conn = ConexionBaseDatos.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}