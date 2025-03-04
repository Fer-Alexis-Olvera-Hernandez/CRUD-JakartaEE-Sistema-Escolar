package org.fer.java.jdb.app;
import org.fer.java.jdb.dao.PersonaDao;
import org.fer.java.jdb.model.Persona;
import java.util.Scanner;
import java.sql.SQLException;
import java.util.List;


public class crudPersonas {
    public static void main(String[] args) {
        PersonaDao personaDao = new PersonaDao();
        Scanner scanner = new Scanner(System.in);

        int opcion;

        do {
            System.out.println("\n=== MENÚ CRUD PERSONAS ===");
            System.out.println("1. Ingresar Persona");
            System.out.println("2. Mostrar Personas");
            System.out.println("3. Actualizar Persona");
            System.out.println("4. Eliminar Persona");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer

            switch (opcion) {
                case 1:
                    ingresarPersona(personaDao, scanner);
                    break;
                case 2:
                    mostrarPersonas(personaDao);
                    break;
                case 3:
                    actualizarPersona(personaDao, scanner);
                    break;
                case 4:
                    eliminarPersona(personaDao, scanner);
                    break;
                case 5:
                    System.out.println("Saliendo del sistema...");
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        } while (opcion != 5);
    }

    private static void ingresarPersona(PersonaDao personaDAO, Scanner scanner) {
        try {
            Persona persona = new Persona();

            System.out.print("Ingrese nombre: ");
            persona.setNombre(scanner.nextLine());

            System.out.print("Ingrese apellido: ");
            persona.setApellido(scanner.nextLine());

            System.out.print("Ingrese fecha de nacimiento (YYYY-MM-DD): ");
            persona.setFecha_nacimiento(scanner.nextLine());

            System.out.print("Ingrese género: ");
            persona.setGenero(scanner.nextLine());

            System.out.print("Ingrese dirección: ");
            persona.setDireccion(scanner.nextLine());

            System.out.print("Ingrese teléfono: ");
            persona.setTelefono(scanner.nextLine());

            System.out.print("Ingrese correo: ");
            persona.setCorreo(scanner.nextLine());

            personaDAO.insertar(persona);
            System.out.println("Persona insertada con éxito. ID: " + persona.getId_persona());
        } catch (SQLException e) {
            System.out.println("Error al insertar persona: " + e.getMessage());
        }
    }

    private static void mostrarPersonas(PersonaDao personaDAO) {
        try {
            List<Persona> personas = personaDAO.listar();

            if (personas.isEmpty()) {
                System.out.println("No hay personas registradas.");
            } else {
                System.out.println("\n=== LISTADO DE PERSONAS ===");
                for (Persona p : personas) {
                    System.out.println("ID: " + p.getId_persona() + " | " + p.getNombre() + " " + p.getApellido() +
                            " | Tel: " + p.getTelefono() + " | Correo: " + p.getCorreo());
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al mostrar personas: " + e.getMessage());
        }
    }

    private static void actualizarPersona(PersonaDao personaDAO, Scanner scanner) {
        try {
            // Mostrar personas antes de pedir el ID
            mostrarPersonas(personaDAO);

            System.out.print("\nIngrese ID de la persona a actualizar: ");
            int id = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer

            Persona persona = new Persona();
            persona.setId_persona(id);

            System.out.print("Nuevo nombre: ");
            persona.setNombre(scanner.nextLine());

            System.out.print("Nuevo apellido: ");
            persona.setApellido(scanner.nextLine());

            System.out.print("Nueva fecha de nacimiento (YYYY-MM-DD): ");
            persona.setFecha_nacimiento(scanner.nextLine());

            System.out.print("Nuevo género: ");
            persona.setGenero(scanner.nextLine());

            System.out.print("Nueva dirección: ");
            persona.setDireccion(scanner.nextLine());

            System.out.print("Nuevo teléfono: ");
            persona.setTelefono(scanner.nextLine());

            System.out.print("Nuevo correo: ");
            persona.setCorreo(scanner.nextLine());

            personaDAO.actualizar(persona);
            System.out.println("Persona actualizada con éxito.");
        } catch (SQLException e) {
            System.out.println("Error al actualizar persona: " + e.getMessage());
        }
    }

    private static void eliminarPersona(PersonaDao personaDAO, Scanner scanner) {
        try {
            // Mostrar personas antes de pedir el ID
            mostrarPersonas(personaDAO);

            System.out.print("\nIngrese ID de la persona a eliminar: ");
            int id = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer

            personaDAO.eliminar(id);
            System.out.println("Persona eliminada con éxito.");
        } catch (SQLException e) {
            System.out.println("Error al eliminar persona: " + e.getMessage());
        }
    }
}