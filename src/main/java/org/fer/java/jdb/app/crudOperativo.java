package org.fer.java.jdb.app;

import org.fer.java.jdb.dao.OperativoDao;
import org.fer.java.jdb.model.Operativo;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class crudOperativo {
    private static final OperativoDao operativoDAO = new OperativoDao();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int opcion;
        do {
            System.out.println("\n=== CRUD OPERATIVOS ===");
            System.out.println("1. Ingresar Operativo");
            System.out.println("2. Mostrar Operativos");
            System.out.println("3. Actualizar Operativo");
            System.out.println("4. Eliminar Operativo");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer

            switch (opcion) {
                case 1 -> ingresarOperativo();
                case 2 -> mostrarOperativos();
                case 3 -> actualizarOperativo();
                case 4 -> eliminarOperativo();
                case 5 -> System.out.println("Saliendo...");
                default -> System.out.println("Opción inválida.");
            }
        } while (opcion != 5);
    }

    public static void ingresarOperativo() {
        try {
            Operativo operativo = new Operativo();

            System.out.print("ID Persona: ");
            operativo.setId_persona(scanner.nextInt());

            System.out.print("ID Área: ");
            operativo.setId_area(scanner.nextInt());
            scanner.nextLine();

            System.out.print("Cargo: ");
            operativo.setCargo(scanner.nextLine());

            System.out.print("Horario: ");
            operativo.setHorario(scanner.nextLine());

            System.out.print("Sueldo: ");
            operativo.setSueldo(scanner.nextDouble());
            scanner.nextLine();

            operativoDAO.insertar(operativo);
            System.out.println("Operativo insertado con éxito. ID: " + operativo.getId_operativo());
        } catch (SQLException e) {
            System.out.println("Error al insertar operativo: " + e.getMessage());
        }
    }

    public static void mostrarOperativos() {
        try {
            List<Operativo> operativos = operativoDAO.listar();
            if (operativos.isEmpty()) {
                System.out.println("No hay operativos registrados.");
            } else {
                operativos.forEach(System.out::println);
            }
        } catch (SQLException e) {
            System.out.println("Error al mostrar operativos: " + e.getMessage());
        }
    }

    public static void actualizarOperativo() {
        try {
            mostrarOperativos();

            System.out.print("Ingrese ID del Operativo a actualizar: ");
            int id = scanner.nextInt();

            Operativo operativo = new Operativo();
            operativo.setId_operativo(id);

            System.out.print("ID Persona: ");
            operativo.setId_persona(scanner.nextInt());

            System.out.print("ID Área: ");
            operativo.setId_area(scanner.nextInt());
            scanner.nextLine();

            System.out.print("Cargo: ");
            operativo.setCargo(scanner.nextLine());

            System.out.print("Horario: ");
            operativo.setHorario(scanner.nextLine());

            System.out.print("Sueldo: ");
            operativo.setSueldo(scanner.nextDouble());
            scanner.nextLine();

            operativoDAO.actualizar(operativo);
            System.out.println("Operativo actualizado con éxito.");
        } catch (SQLException e) {
            System.out.println("Error al actualizar operativo: " + e.getMessage());
        }
    }

    public static void eliminarOperativo() {
        try {
            mostrarOperativos();

            System.out.print("Ingrese ID del Operativo a eliminar: ");
            int id = scanner.nextInt();
            scanner.nextLine();

            operativoDAO.eliminar(id);
            System.out.println("Operativo eliminado con éxito.");
        } catch (SQLException e) {
            System.out.println("Error al eliminar operativo: " + e.getMessage());
        }
    }
}
