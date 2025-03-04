package org.fer.java.jdb.dao;

import org.fer.java.jdb.model.Operativo;
import org.fer.java.jdb.util.ConexionBaseDatos;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class OperativoDao
{
    public void insertar(Operativo operativo) throws SQLException {
        String sql = "INSERT INTO Operativos (id_persona, id_area, cargo, horario, sueldo) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConexionBaseDatos.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, operativo.getId_persona());
            stmt.setInt(2, operativo.getId_area());
            stmt.setString(3, operativo.getCargo());
            stmt.setString(4, operativo.getHorario());
            stmt.setDouble(5, operativo.getSueldo());

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    operativo.setId_operativo(rs.getInt(1));
                }
            }
        }
    }

    public List<Operativo> listar() throws SQLException {
        List<Operativo> operativos = new ArrayList<>();
        String sql = "SELECT * FROM Operativos";

        try (Connection conn = ConexionBaseDatos.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Operativo operativo = new Operativo();
                operativo.setId_operativo(rs.getInt("id_operativo"));
                operativo.setId_persona(rs.getInt("id_persona"));
                operativo.setId_area(rs.getInt("id_area"));
                operativo.setCargo(rs.getString("cargo"));
                operativo.setHorario(rs.getString("horario"));
                operativo.setSueldo(rs.getDouble("sueldo"));

                operativos.add(operativo);
            }
        }
        return operativos;
    }

    public void actualizar(Operativo operativo) throws SQLException {
        String sql = "UPDATE Operativos SET id_persona=?, id_area=?, cargo=?, horario=?, sueldo=? WHERE id_operativo=?";
        try (Connection conn = ConexionBaseDatos.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, operativo.getId_persona());
            stmt.setInt(2, operativo.getId_area());
            stmt.setString(3, operativo.getCargo());
            stmt.setString(4, operativo.getHorario());
            stmt.setDouble(5, operativo.getSueldo());
            stmt.setInt(6, operativo.getId_operativo());

            stmt.executeUpdate();
        }
    }

    public void eliminar(int id) throws SQLException {
        String sql = "DELETE FROM Operativos WHERE id_operativo=?";
        try (Connection conn = ConexionBaseDatos.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}
