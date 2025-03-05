package org.fer.java.jdb.app;

import com.jtattoo.plaf.acryl.AcrylLookAndFeel;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Properties;

public class crudGui extends JFrame {
    private JCheckBox checkArea, checkDirectivo, checkDocente, checkOperativo, checkPersona;
    private JButton btnIr;

    public crudGui() {
        try {
            Properties props = new Properties();
            props.put("logoString", "");
            AcrylLookAndFeel.setCurrentTheme(props);
            UIManager.setLookAndFeel(new AcrylLookAndFeel());
        } catch (Exception e) {
            e.printStackTrace();
        }

        setTitle("Seleccionar CRUD");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Crear un panel de título
        JPanel panelTitulo = new JPanel();
        panelTitulo.setBackground(new Color(41, 128, 185));
        panelTitulo.setBorder(new EmptyBorder(15, 15, 15, 15));
        JLabel lblTitulo = new JLabel("Seleccione el módulo CRUD a gestionar:", SwingConstants.CENTER);
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        panelTitulo.add(lblTitulo);
        add(panelTitulo, BorderLayout.NORTH);

        // Panel central con checkboxes
        JPanel panelOpciones = new JPanel();
        panelOpciones.setLayout(new GridLayout(5, 1, 10, 10));
        panelOpciones.setBorder(new EmptyBorder(20, 50, 20, 50));

        checkArea = new JCheckBox("Área");
        checkDirectivo = new JCheckBox("Directivo");
        checkDocente = new JCheckBox("Docente");
        checkOperativo = new JCheckBox("Operativo");
        checkPersona = new JCheckBox("Persona");

        checkArea.setFont(new Font("Arial", Font.PLAIN, 16));
        checkDirectivo.setFont(new Font("Arial", Font.PLAIN, 16));
        checkDocente.setFont(new Font("Arial", Font.PLAIN, 16));
        checkOperativo.setFont(new Font("Arial", Font.PLAIN, 16));
        checkPersona.setFont(new Font("Arial", Font.PLAIN, 16));

        panelOpciones.add(checkArea);
        panelOpciones.add(checkDirectivo);
        panelOpciones.add(checkDocente);
        panelOpciones.add(checkOperativo);
        panelOpciones.add(checkPersona);
        add(panelOpciones, BorderLayout.CENTER);

        // Panel de botón
        JPanel panelBoton = new JPanel();
        btnIr = new JButton("Ir a CRUD seleccionado");
        btnIr.setFont(new Font("Arial", Font.BOLD, 16));
        btnIr.setBackground(new Color(46, 204, 113));
        btnIr.setForeground(Color.WHITE);
        btnIr.setBorderPainted(false);
        btnIr.setFocusPainted(false);
        btnIr.setPreferredSize(new Dimension(250, 40));
        panelBoton.add(btnIr);
        add(panelBoton, BorderLayout.SOUTH);

        btnIr.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (checkArea.isSelected()) {
                    new crudAreaGUI().setVisible(true);
                } else if (checkDirectivo.isSelected()) {
                    new crudDirectivoGUI().setVisible(true);
                } else if (checkDocente.isSelected()) {
                    new crudDocenteGUI().setVisible(true);
                } else if (checkOperativo.isSelected()) {
                    new crudOperativoGUI().setVisible(true);
                } else if (checkPersona.isSelected()) {
                    new crudPersonaGUI().setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Seleccione un módulo para continuar", "Error", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        new crudGui();
    }
}