package org.fer.java.jdb.app;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class crudDocenteGUI extends JFrame {
    private JTable table;
    private DefaultTableModel model;
    private JButton btnAgregar, btnActualizar, btnEliminar, btnListar, btnRegresar;

    public crudDocenteGUI() {
        setTitle("CRUD Docentes");
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
        model = new DefaultTableModel(new String[]{"ID", "ID Persona", "ID Área", "Título Académico", "Especialidad", "Tipo de Contrato"}, 0);
        table = new JTable(model);
        table.setFillsViewportHeight(true);
        table.setRowHeight(25);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(44, 62, 80));
        table.getTableHeader().setForeground(Color.WHITE);

        add(new JScrollPane(table), BorderLayout.CENTER);

        // Agregar eventos
        btnListar.addActionListener(e -> listarDocentes());
        btnAgregar.addActionListener(e -> agregarDocente());
        btnActualizar.addActionListener(e -> actualizarDocente());
        btnEliminar.addActionListener(e -> eliminarDocente());
        btnRegresar.addActionListener(e -> {
            dispose(); // Cierra la ventana actual
            new crudGui().setVisible(true); // Regresa a la ventana principal
        });

        setVisible(true);
    }

    private void listarDocentes() {
        model.setRowCount(0); // Limpiar la tabla
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/tesvb", "root", "");
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM Docentes")) {

            while (rs.next()) {
                model.addRow(new Object[]{rs.getInt("id_docente"), rs.getInt("id_persona"), rs.getInt("id_area"), rs.getString("titulo_academico"), rs.getString("especialidad"), rs.getString("tipo_contrato")});
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al listar docentes: " + e.getMessage());
        }
    }

    private void agregarDocente() {
        String idPersona = JOptionPane.showInputDialog("Ingrese ID de la Persona:");
        String idArea = JOptionPane.showInputDialog("Ingrese ID del Área:");
        String titulo = JOptionPane.showInputDialog("Ingrese Título Académico:");
        String especialidad = JOptionPane.showInputDialog("Ingrese Especialidad:");
        String tipoContrato = JOptionPane.showInputDialog("Ingrese Tipo de Contrato:");

        if (idPersona != null && idArea != null && titulo != null && especialidad != null && tipoContrato != null) {
            try (Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/tesvb", "root", "");
                 PreparedStatement stmt = conn.prepareStatement("INSERT INTO Docentes (id_persona, id_area, titulo_academico, especialidad, tipo_contrato) VALUES (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {
                stmt.setInt(1, Integer.parseInt(idPersona));
                stmt.setInt(2, Integer.parseInt(idArea));
                stmt.setString(3, titulo);
                stmt.setString(4, especialidad);
                stmt.setString(5, tipoContrato);
                stmt.executeUpdate();
                listarDocentes();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error al agregar docente: " + e.getMessage());
            }
        }
    }

    private void actualizarDocente() {
        String idStr = JOptionPane.showInputDialog("Ingrese ID del Docente a actualizar:");
        if (idStr != null) {
            int id = Integer.parseInt(idStr);
            String nuevoTitulo = JOptionPane.showInputDialog("Ingrese nuevo Título Académico:");
            String nuevaEspecialidad = JOptionPane.showInputDialog("Ingrese nueva Especialidad:");
            String nuevoTipoContrato = JOptionPane.showInputDialog("Ingrese nuevo Tipo de Contrato:");

            if (nuevoTitulo != null && nuevaEspecialidad != null && nuevoTipoContrato != null) {
                try (Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/tesvb", "root", "");
                     PreparedStatement stmt = conn.prepareStatement("UPDATE Docentes SET titulo_academico=?, especialidad=?, tipo_contrato=? WHERE id_docente=?")) {
                    stmt.setString(1, nuevoTitulo);
                    stmt.setString(2, nuevaEspecialidad);
                    stmt.setString(3, nuevoTipoContrato);
                    stmt.setInt(4, id);
                    stmt.executeUpdate();
                    listarDocentes();
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(this, "Error al actualizar docente: " + e.getMessage());
                }
            }
        }
    }

    private void eliminarDocente() {
        String idStr = JOptionPane.showInputDialog("Ingrese ID del Docente a eliminar:");
        if (idStr != null) {
            int id = Integer.parseInt(idStr);
            try (Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/tesvb", "root", "");
                 PreparedStatement stmt = conn.prepareStatement("DELETE FROM Docentes WHERE id_docente=?")) {
                stmt.setInt(1, id);
                stmt.executeUpdate();
                listarDocentes();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error al eliminar docente: " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        new crudDocenteGUI();
    }
}
