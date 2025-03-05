package org.fer.java.jdb.app;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class crudDirectivoGUI extends JFrame {
    private JTable table;
    private DefaultTableModel model;
    private JButton btnAgregar, btnActualizar, btnEliminar, btnListar, btnRegresar;

    public crudDirectivoGUI() {
        setTitle("CRUD Directivos");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Crear un panel superior para los botones
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelBotones.setBackground(new Color(41, 128, 185));

        btnAgregar = new JButton("Agregar");
        btnActualizar = new JButton("Actualizar");
        btnEliminar = new JButton("Eliminar");
        btnListar = new JButton("Listar");
        btnRegresar = new JButton("Regresar");

        btnAgregar.setBackground(new Color(46, 204, 113));
        btnActualizar.setBackground(new Color(241, 196, 15));
        btnEliminar.setBackground(new Color(231, 76, 60));
        btnListar.setBackground(new Color(52, 152, 219));
        btnRegresar.setBackground(new Color(128, 128, 128));

        btnAgregar.setForeground(Color.WHITE);
        btnActualizar.setForeground(Color.WHITE);
        btnEliminar.setForeground(Color.WHITE);
        btnListar.setForeground(Color.WHITE);
        btnRegresar.setForeground(Color.WHITE);

        panelBotones.add(btnAgregar);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnListar);
        panelBotones.add(btnRegresar);
        add(panelBotones, BorderLayout.NORTH);

        // Crear la tabla con diseño mejorado
        model = new DefaultTableModel(new String[]{"ID", "ID Persona", "ID Área", "Cargo", "Nivel Jerárquico", "Fecha Inicio"}, 0);
        table = new JTable(model);
        table.setFillsViewportHeight(true);
        table.setRowHeight(25);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(44, 62, 80));
        table.getTableHeader().setForeground(Color.WHITE);

        add(new JScrollPane(table), BorderLayout.CENTER);

        // Agregar eventos
        btnListar.addActionListener(e -> listarDirectivos());
        btnAgregar.addActionListener(e -> agregarDirectivo());
        btnActualizar.addActionListener(e -> actualizarDirectivo());
        btnEliminar.addActionListener(e -> eliminarDirectivo());
        btnRegresar.addActionListener(e -> {
            dispose(); // Cierra la ventana actual
            new crudGui().setVisible(true); // Regresa a la ventana principal
        });

        setVisible(true);
    }

    private void listarDirectivos() {
        model.setRowCount(0); // Limpiar la tabla
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/tesvb", "root", "");
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM Directivos")) {

            while (rs.next()) {
                model.addRow(new Object[]{rs.getInt("id_directivo"), rs.getInt("id_persona"), rs.getInt("id_area"), rs.getString("cargo"), rs.getString("nivel_jerarquico"), rs.getString("fecha_inicio_cargo")});
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al listar directivos: " + e.getMessage());
        }
    }

    private void agregarDirectivo() {
        String idPersona = JOptionPane.showInputDialog("Ingrese ID de la Persona:");
        String idArea = JOptionPane.showInputDialog("Ingrese ID del Área:");
        String cargo = JOptionPane.showInputDialog("Ingrese Cargo:");
        String nivelJerarquico = JOptionPane.showInputDialog("Ingrese Nivel Jerárquico:");
        String fechaInicio = JOptionPane.showInputDialog("Ingrese Fecha de Inicio (YYYY-MM-DD):");

        if (idPersona != null && idArea != null && cargo != null && nivelJerarquico != null && fechaInicio != null) {
            try (Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/tesvb", "root", "");
                 PreparedStatement stmt = conn.prepareStatement("INSERT INTO Directivos (id_persona, id_area, cargo, nivel_jerarquico, fecha_inicio_cargo) VALUES (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {
                stmt.setInt(1, Integer.parseInt(idPersona));
                stmt.setInt(2, Integer.parseInt(idArea));
                stmt.setString(3, cargo);
                stmt.setString(4, nivelJerarquico);
                stmt.setString(5, fechaInicio);
                stmt.executeUpdate();
                listarDirectivos();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error al agregar directivo: " + e.getMessage());
            }
        }
    }

    private void actualizarDirectivo() {
        String idStr = JOptionPane.showInputDialog("Ingrese ID del Directivo a actualizar:");
        if (idStr != null) {
            int id = Integer.parseInt(idStr);
            String nuevoCargo = JOptionPane.showInputDialog("Ingrese nuevo Cargo:");
            String nuevoNivel = JOptionPane.showInputDialog("Ingrese nuevo Nivel Jerárquico:");
            String nuevaFecha = JOptionPane.showInputDialog("Ingrese nueva Fecha de Inicio (YYYY-MM-DD):");

            if (nuevoCargo != null && nuevoNivel != null && nuevaFecha != null) {
                try (Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/tesvb", "root", "");
                     PreparedStatement stmt = conn.prepareStatement("UPDATE Directivos SET cargo=?, nivel_jerarquico=?, fecha_inicio_cargo=? WHERE id_directivo=?")) {
                    stmt.setString(1, nuevoCargo);
                    stmt.setString(2, nuevoNivel);
                    stmt.setString(3, nuevaFecha);
                    stmt.setInt(4, id);
                    stmt.executeUpdate();
                    listarDirectivos();
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(this, "Error al actualizar directivo: " + e.getMessage());
                }
            }
        }
    }

    private void eliminarDirectivo() {
        String idStr = JOptionPane.showInputDialog("Ingrese ID del Directivo a eliminar:");
        if (idStr != null) {
            int id = Integer.parseInt(idStr);
            try (Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/tesvb", "root", "");
                 PreparedStatement stmt = conn.prepareStatement("DELETE FROM Directivos WHERE id_directivo=?")) {
                stmt.setInt(1, id);
                stmt.executeUpdate();
                listarDirectivos();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error al eliminar directivo: " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        new crudDirectivoGUI();
    }
}
