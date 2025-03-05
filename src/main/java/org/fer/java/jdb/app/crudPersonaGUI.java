package org.fer.java.jdb.app;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class crudPersonaGUI extends JFrame {
    private JTable table;
    private DefaultTableModel model;
    private JButton btnAgregar, btnActualizar, btnEliminar, btnListar;

    public crudPersonaGUI() {
        setTitle("CRUD Personas");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Crear la tabla y modelo
        model = new DefaultTableModel(new String[]{"ID", "Nombre", "Apellido", "Fecha Nac."}, 0);
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Panel de botones
        JPanel panelBotones = new JPanel();
        btnAgregar = new JButton("Agregar");
        btnActualizar = new JButton("Actualizar");
        btnEliminar = new JButton("Eliminar");
        btnListar = new JButton("Listar");

        panelBotones.add(btnAgregar);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnListar);
        add(panelBotones, BorderLayout.SOUTH);

        // Agregar eventos
        btnListar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listarPersonas();
            }
        });

        btnAgregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarPersona();
            }
        });

        btnActualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarPersona();
            }
        });

        btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarPersona();
            }
        });

        setVisible(true);
    }

    private void listarPersonas() {
        model.setRowCount(0); // Limpiar la tabla
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/tesvb", "root", "");
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM Personas")) {

            while (rs.next()) {
                model.addRow(new Object[]{rs.getInt("id_persona"), rs.getString("nombre"), rs.getString("apellido"), rs.getString("fecha_nacimiento")});
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al listar personas: " + e.getMessage());
        }
    }

    private void agregarPersona() {
        String nombre = JOptionPane.showInputDialog("Ingrese Nombre:");
        String apellido = JOptionPane.showInputDialog("Ingrese Apellido:");
        String fechaNacimiento = JOptionPane.showInputDialog("Ingrese Fecha de Nacimiento (YYYY-MM-DD):");

        if (nombre != null && apellido != null && fechaNacimiento != null) {
            try (Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/tesvb", "root", "");
                 PreparedStatement stmt = conn.prepareStatement("INSERT INTO Personas (nombre, apellido, fecha_nacimiento) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {
                stmt.setString(1, nombre);
                stmt.setString(2, apellido);
                stmt.setString(3, fechaNacimiento);
                stmt.executeUpdate();
                listarPersonas();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error al agregar persona: " + e.getMessage());
            }
        }
    }

    private void actualizarPersona() {
        String idStr = JOptionPane.showInputDialog("Ingrese ID de la persona a actualizar:");
        if (idStr != null) {
            int id = Integer.parseInt(idStr);
            String nuevoNombre = JOptionPane.showInputDialog("Ingrese nuevo Nombre:");
            String nuevoApellido = JOptionPane.showInputDialog("Ingrese nuevo Apellido:");
            String nuevaFecha = JOptionPane.showInputDialog("Ingrese nueva Fecha de Nacimiento (YYYY-MM-DD):");

            if (nuevoNombre != null && nuevoApellido != null && nuevaFecha != null) {
                try (Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/tesvb", "root", "");
                     PreparedStatement stmt = conn.prepareStatement("UPDATE Personas SET nombre=?, apellido=?, fecha_nacimiento=? WHERE id_persona=?")) {
                    stmt.setString(1, nuevoNombre);
                    stmt.setString(2, nuevoApellido);
                    stmt.setString(3, nuevaFecha);
                    stmt.setInt(4, id);
                    stmt.executeUpdate();
                    listarPersonas();
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(this, "Error al actualizar persona: " + e.getMessage());
                }
            }
        }
    }

    private void eliminarPersona() {
        String idStr = JOptionPane.showInputDialog("Ingrese ID de la persona a eliminar:");
        if (idStr != null) {
            int id = Integer.parseInt(idStr);
            try (Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/tesvb", "root", "");
                 PreparedStatement stmt = conn.prepareStatement("DELETE FROM Personas WHERE id_persona=?")) {
                stmt.setInt(1, id);
                stmt.executeUpdate();
                listarPersonas();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error al eliminar persona: " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        new crudPersonaGUI();
    }
}
