package org.fer.java.jdb.app;

import org.fer.java.jdb.dao.DirectivoDao;
import org.fer.java.jdb.model.Directivo;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;
public class crudDirectivo
{
    private static final DirectivoDao directivoDAO = new DirectivoDao();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int opcion;
        do {
            System.out.println("\n=== CRUD DIRECTIVOS ===");
            System.out.println("1. Ingresar Directivo");
            System.out.println("2. Mostrar Directivos");
            System.out.println("3. Actualizar Directivo");
            System.out.println("4. Eliminar Directivo");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer

            switch (opcion) {
                case 1 -> ingresarDirectivo();
                case 2 -> mostrarDirectivos();
                case 3 -> actualizarDirectivo();
                case 4 -> eliminarDirectivo();
                case 5 -> System.out.println("Saliendo...");
                default -> System.out.println("Opción inválida.");
            }
        } while (opcion != 5);
    }

    public static void ingresarDirectivo() {
        try {
            Directivo directivo = new Directivo();

            System.out.print("ID Persona: ");
            directivo.setId_persona(scanner.nextInt());

            System.out.print("ID Área: ");
            directivo.setId_area(scanner.nextInt());
            scanner.nextLine(); // Limpiar buffer

            System.out.print("Cargo: ");
            directivo.setCargo(scanner.nextLine());

            System.out.print("Nivel Jerárquico: ");
            directivo.setNivel_jerarquico(scanner.nextLine());

            System.out.print("Fecha Inicio Cargo (YYYY-MM-DD): ");
            directivo.setFecha_inicio_cargo(scanner.nextLine());

            directivoDAO.insertar(directivo);
            System.out.println("Directivo insertado con éxito. ID: " + directivo.getId_directivo());
        } catch (SQLException e) {
            System.out.println("Error al insertar directivo: " + e.getMessage());
        }
    }

    public static void mostrarDirectivos() {
        try {
            List<Directivo> directivos = directivoDAO.listar();
            if (directivos.isEmpty()) {
                System.out.println("No hay directivos registrados.");
            } else {
                directivos.forEach(System.out::println);
            }
        } catch (SQLException e) {
            System.out.println("Error al mostrar directivos: " + e.getMessage());
        }
    }

    public static void actualizarDirectivo() {
        try {
            mostrarDirectivos();

            System.out.print("Ingrese ID del Directivo a actualizar: ");
            int id = scanner.nextInt();

            Directivo directivo = new Directivo();
            directivo.setId_directivo(id);

            System.out.print("ID Persona: ");
            directivo.setId_persona(scanner.nextInt());

            System.out.print("ID Área: ");
            directivo.setId_area(scanner.nextInt());
            scanner.nextLine();

            System.out.print("Cargo: ");
            directivo.setCargo(scanner.nextLine());

            System.out.print("Nivel Jerárquico: ");
            directivo.setNivel_jerarquico(scanner.nextLine());

            System.out.print("Fecha Inicio Cargo (YYYY-MM-DD): ");
            directivo.setFecha_inicio_cargo(scanner.nextLine());

            directivoDAO.actualizar(directivo);
            System.out.println("Directivo actualizado con éxito.");
        } catch (SQLException e) {
            System.out.println("Error al actualizar directivo: " + e.getMessage());
        }
    }

    public static void eliminarDirectivo() {
        try {
            mostrarDirectivos();

            System.out.print("Ingrese ID del Directivo a eliminar: ");
            int id = scanner.nextInt();
            scanner.nextLine();

            directivoDAO.eliminar(id);
            System.out.println("Directivo eliminado con éxito.");
        } catch (SQLException e) {
            System.out.println("Error al eliminar directivo: " + e.getMessage());
        }
    }

}
