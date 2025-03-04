package org.fer.java.jdb.app;

import org.fer.java.jdb.dao.AreaDao;
import org.fer.java.jdb.model.Area;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class crudArea {
    public static void main(String[] args) throws SQLException {
        AreaDao areaDao = new AreaDao();
        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("\n1. Ingresar Área");
            System.out.println("2. Mostrar Áreas");
            System.out.println("3. Actualizar Área");
            System.out.println("4. Eliminar Área");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1 -> ingresarArea(areaDao, scanner);
                case 2 -> mostrarAreas(areaDao);
                case 3 -> actualizarArea(areaDao, scanner);
                case 4 -> eliminarArea(areaDao, scanner);
                case 5 -> System.out.println("Saliendo...");
                default -> System.out.println("Opción inválida.");
            }
        } while (opcion != 5);
    }

    public static void ingresarArea(AreaDao dao, Scanner sc) throws SQLException {
        Area area = new Area();
        System.out.print("Nombre del área: ");
        area.setNombre_area(sc.nextLine());
        System.out.print("Descripción: ");
        area.setDescripcion(sc.nextLine());
        dao.insertar(area);
        System.out.println("Área insertada con éxito.");
    }

    public static void mostrarAreas(AreaDao dao) throws SQLException {
        List<Area> areas = dao.listar();
        areas.forEach(System.out::println);
    }

    public static void actualizarArea(AreaDao dao, Scanner sc) throws SQLException {
        mostrarAreas(dao);
        System.out.print("ID del área a actualizar: ");
        int id = sc.nextInt(); sc.nextLine();

        Area area = new Area();
        area.setId_area(id);
        System.out.print("Nuevo nombre: ");
        area.setNombre_area(sc.nextLine());
        System.out.print("Nueva descripción: ");
        area.setDescripcion(sc.nextLine());

        dao.actualizar(area);
        System.out.println("Área actualizada.");
    }

    public static void eliminarArea(AreaDao dao, Scanner sc) throws SQLException {
        mostrarAreas(dao);
        System.out.print("ID del área a eliminar: ");
        int id = sc.nextInt();
        dao.eliminar(id);
        System.out.println("Área eliminada.");
    }

}
