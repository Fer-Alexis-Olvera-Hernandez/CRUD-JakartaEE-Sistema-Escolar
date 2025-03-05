package org.fer.java.jdb.app;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class crudOperativoGUI extends JFrame {
    private JTable table;
    private DefaultTableModel model;
    private JButton btnAgregar, btnActualizar, btnEliminar, btnListar, btnRegresar;

    public crudOperativoGUI() {
        setTitle("CRUD Operativos");
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
        model = new DefaultTableModel(new String[]{"ID", "ID Persona", "ID Área", "cargo", "Horario"}, 0);
        table = new JTable(model);
        table.setFillsViewportHeight(true);
        table.setRowHeight(25);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(44, 62, 80));
        table.getTableHeader().setForeground(Color.WHITE);

        add(new JScrollPane(table), BorderLayout.CENTER);

        // Agregar eventos
        btnListar.addActionListener(e -> listarOperativos());
        btnAgregar.addActionListener(e -> agregarOperativo());
        btnActualizar.addActionListener(e -> actualizarOperativo());
        btnEliminar.addActionListener(e -> eliminarOperativo());
        btnRegresar.addActionListener(e -> {
            dispose(); // Cierra la ventana actual
            new crudGui().setVisible(true); // Regresa a la ventana principal
        });

        setVisible(true);
    }

    private void listarOperativos() {
        model.setRowCount(0); // Limpiar la tabla
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/tesvb", "root", "");
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM Operativos")) {

            while (rs.next()) {
                model.addRow(new Object[]{rs.getInt("id_operativo"), rs.getInt("id_persona"), rs.getInt("id_area"), rs.getString("cargo"), rs.getString("horario")});
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al listar operativos: " + e.getMessage());
        }
    }

    private void agregarOperativo() {
        String idPersona = JOptionPane.showInputDialog("Ingrese ID de la Persona:");
        String idArea = JOptionPane.showInputDialog("Ingrese ID del Área:");
        String puesto = JOptionPane.showInputDialog("Ingrese Puesto:");
        String horario = JOptionPane.showInputDialog("Ingrese Horario:");

        if (idPersona != null && idArea != null && puesto != null && horario != null) {
            try (Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/tesvb", "root", "");
                 PreparedStatement stmt = conn.prepareStatement("INSERT INTO Operativos (id_persona, id_area, cargo, horario) VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {
                stmt.setInt(1, Integer.parseInt(idPersona));
                stmt.setInt(2, Integer.parseInt(idArea));
                stmt.setString(3, puesto);
                stmt.setString(4, horario);
                stmt.executeUpdate();
                listarOperativos();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error al agregar operativo: " + e.getMessage());
            }
        }
    }

    private void actualizarOperativo() {
        String idStr = JOptionPane.showInputDialog("Ingrese ID del Operativo a actualizar:");
        if (idStr != null) {
            int id = Integer.parseInt(idStr);
            String nuevoPuesto = JOptionPane.showInputDialog("Ingrese nuevo Puesto:");
            String nuevoHorario = JOptionPane.showInputDialog("Ingrese nuevo Horario:");

            if (nuevoPuesto != null && nuevoHorario != null) {
                try (Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/tesvb", "root", "");
                     PreparedStatement stmt = conn.prepareStatement("UPDATE Operativos SET cargo=?, horario=? WHERE id_operativo=?")) {
                    stmt.setString(1, nuevoPuesto);
                    stmt.setString(2, nuevoHorario);
                    stmt.setInt(3, id);
                    stmt.executeUpdate();
                    listarOperativos();
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(this, "Error al actualizar operativo: " + e.getMessage());
                }
            }
        }
    }

    private void eliminarOperativo() {
        String idStr = JOptionPane.showInputDialog("Ingrese ID del Operativo a eliminar:");
        if (idStr != null) {
            int id = Integer.parseInt(idStr);
            try (Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/tesvb", "root", "");
                 PreparedStatement stmt = conn.prepareStatement("DELETE FROM Operativos WHERE id_operativo=?")) {
                stmt.setInt(1, id);
                stmt.executeUpdate();
                listarOperativos();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error al eliminar operativo: " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        new crudOperativoGUI();
    }
}
