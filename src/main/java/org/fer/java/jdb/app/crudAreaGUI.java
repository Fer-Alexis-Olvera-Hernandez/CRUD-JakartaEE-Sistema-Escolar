package org.fer.java.jdb.app;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class crudAreaGUI extends JFrame {
    private JTable table;
    private DefaultTableModel model;
    private JButton btnAgregar, btnActualizar, btnEliminar, btnListar, btnRegresar;

    public crudAreaGUI() {
        setTitle("CRUD Áreas");
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
        model = new DefaultTableModel(new String[]{"ID", "Nombre Área", "Descripción"}, 0);
        table = new JTable(model);
        table.setFillsViewportHeight(true);
        table.setRowHeight(25);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(44, 62, 80));
        table.getTableHeader().setForeground(Color.WHITE);

        add(new JScrollPane(table), BorderLayout.CENTER);

        // Agregar eventos
        btnListar.addActionListener(e -> listarAreas());
        btnAgregar.addActionListener(e -> agregarArea());
        btnActualizar.addActionListener(e -> actualizarArea());
        btnEliminar.addActionListener(e -> eliminarArea());
        btnRegresar.addActionListener(e -> {
            dispose(); // Cierra la ventana actual
            new crudGui().setVisible(true); // Regresa a la ventana principal
        });

        setVisible(true);
    }

    private void listarAreas() {
        model.setRowCount(0); // Limpiar la tabla
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/tesvb", "root", "");
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM Areas")) {

            while (rs.next()) {
                model.addRow(new Object[]{rs.getInt("id_area"), rs.getString("nombre_area"), rs.getString("descripcion")});
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al listar áreas: " + e.getMessage());
        }
    }

    private void agregarArea() {
        String nombre = JOptionPane.showInputDialog("Ingrese Nombre del Área:");
        String descripcion = JOptionPane.showInputDialog("Ingrese Descripción del Área:");

        if (nombre != null && descripcion != null) {
            try (Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/tesvb", "root", "");
                 PreparedStatement stmt = conn.prepareStatement("INSERT INTO Areas (nombre_area, descripcion) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS)) {
                stmt.setString(1, nombre);
                stmt.setString(2, descripcion);
                stmt.executeUpdate();
                listarAreas();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error al agregar área: " + e.getMessage());
            }
        }
    }

    private void actualizarArea() {
        String idStr = JOptionPane.showInputDialog("Ingrese ID del Área a actualizar:");
        if (idStr != null) {
            int id = Integer.parseInt(idStr);
            String nuevoNombre = JOptionPane.showInputDialog("Ingrese nuevo Nombre del Área:");
            String nuevaDescripcion = JOptionPane.showInputDialog("Ingrese nueva Descripción:");

            if (nuevoNombre != null && nuevaDescripcion != null) {
                try (Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/tesvb", "root", "");
                     PreparedStatement stmt = conn.prepareStatement("UPDATE Areas SET nombre_area=?, descripcion=? WHERE id_area=?")) {
                    stmt.setString(1, nuevoNombre);
                    stmt.setString(2, nuevaDescripcion);
                    stmt.setInt(3, id);
                    stmt.executeUpdate();
                    listarAreas();
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(this, "Error al actualizar área: " + e.getMessage());
                }
            }
        }
    }

    private void eliminarArea() {
        String idStr = JOptionPane.showInputDialog("Ingrese ID del Área a eliminar:");
        if (idStr != null) {
            int id = Integer.parseInt(idStr);
            try (Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/tesvb", "root", "");
                 PreparedStatement stmt = conn.prepareStatement("DELETE FROM Areas WHERE id_area=?")) {
                stmt.setInt(1, id);
                stmt.executeUpdate();
                listarAreas();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error al eliminar área: " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        new crudAreaGUI();
    }
}

