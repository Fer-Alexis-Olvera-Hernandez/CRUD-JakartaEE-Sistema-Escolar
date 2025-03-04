package org.fer.java.jdb.app;


import org.fer.java.jdb.dao.DocenteDao;
import org.fer.java.jdb.model.Docente;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class CrudDocente {
    private static final DocenteDao docenteDAO = new DocenteDao();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int opcion;
        do {
            System.out.println("\n=== CRUD DOCENTES ===");
            System.out.println("1. Ingresar Docente");
            System.out.println("2. Mostrar Docentes");
            System.out.println("3. Actualizar Docente");
            System.out.println("4. Eliminar Docente");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer

            switch (opcion) {
                case 1 -> ingresarDocente();
                case 2 -> mostrarDocentes();
                case 3 -> actualizarDocente();
                case 4 -> eliminarDocente();
                case 5 -> System.out.println("Saliendo...");
                default -> System.out.println("Opción inválida.");
            }
        } while (opcion != 5);
    }

    public static void ingresarDocente() {
        try {
            Docente docente = new Docente();

            System.out.print("ID Persona: ");
            docente.setId_persona(scanner.nextInt());

            System.out.print("ID Área: ");
            docente.setId_area(scanner.nextInt());
            scanner.nextLine();

            System.out.print("Título Académico: ");
            docente.setTitulo_academico(scanner.nextLine());

            System.out.print("Especialidad: ");
            docente.setEspecialidad(scanner.nextLine());

            System.out.print("Tipo de Contrato: ");
            docente.setTipo_contrato(scanner.nextLine());

            docenteDAO.insertar(docente);
            System.out.println("Docente insertado con éxito. ID: " + docente.getId_docente());
        } catch (SQLException e) {
            System.out.println("Error al insertar docente: " + e.getMessage());
        }
    }

    public static void mostrarDocentes() {
        try {
            List<Docente> docentes = docenteDAO.listar();
            if (docentes.isEmpty()) {
                System.out.println("No hay docentes registrados.");
            } else {
                docentes.forEach(System.out::println);
            }
        } catch (SQLException e) {
            System.out.println("Error al mostrar docentes: " + e.getMessage());
        }
    }

    public static void actualizarDocente() {
        try {
            mostrarDocentes();

            System.out.print("Ingrese ID del Docente a actualizar: ");
            int id = scanner.nextInt();

            Docente docente = new Docente();
            docente.setId_docente(id);

            System.out.print("ID Persona: ");
            docente.setId_persona(scanner.nextInt());

            System.out.print("ID Área: ");
            docente.setId_area(scanner.nextInt());
            scanner.nextLine();

            System.out.print("Título Académico: ");
            docente.setTitulo_academico(scanner.nextLine());

            System.out.print("Especialidad: ");
            docente.setEspecialidad(scanner.nextLine());

            System.out.print("Tipo de Contrato: ");
            docente.setTipo_contrato(scanner.nextLine());

            docenteDAO.actualizar(docente);
            System.out.println("Docente actualizado con éxito.");
        } catch (SQLException e) {
            System.out.println("Error al actualizar docente: " + e.getMessage());
        }
    }

    public static void eliminarDocente() {
        try {
            mostrarDocentes();

            System.out.print("Ingrese ID del Docente a eliminar: ");
            int id = scanner.nextInt();
            scanner.nextLine();

            docenteDAO.eliminar(id);
            System.out.println("Docente eliminado con éxito.");
        } catch (SQLException e) {
            System.out.println("Error al eliminar docente: " + e.getMessage());
        }
    }
}
