package app;

import exception.BusinessException;
import exception.ConfigurationException;
import exception.DataAccessException;
import model.Alumne;
import model.Curs;
import service.InstitutService;

import java.util.Scanner;

public class App {

    private final InstitutService service = new InstitutService();
    private final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        new App().executar();
    }

    private void executar() {
        boolean sortir = false;
        while (!sortir) {
            System.out.println("\n===== EXERCICI 2 · ALUMNES I CURSOS =====");
            System.out.println("1. Llistar cursos");
            System.out.println("2. Llistar alumnes");
            System.out.println("3. Veure alumne per id");
            System.out.println("4. Inserir alumne");
            System.out.println("5. Actualitzar alumne");
            System.out.println("6. Eliminar alumne");
            System.out.println("7. Mostrar curs amb alumnes");
            System.out.println("8. Eliminar curs");
            System.out.println("0. Sortir");

            int opcio = llegirEnter("Opció: ");
            try {
                switch (opcio) {
                    case 1 -> service.obtenirCursos().forEach(System.out::println);
                    case 2 -> service.obtenirTotsElsAlumnes().forEach(System.out::println);
                    case 3 -> veureAlumne();
                    case 4 -> inserirAlumne();
                    case 5 -> actualitzarAlumne();
                    case 6 -> eliminarAlumne();
                    case 7 -> mostrarCursAmbAlumnes();
                    case 8 -> eliminarCurs();
                    case 0 -> sortir = true;
                    default -> System.out.println("Opció no vàlida.");
                }
            } catch (BusinessException e) {
                System.out.println("Operació no vàlida: " + e.getMessage());
            } catch (DataAccessException | ConfigurationException e) {
                System.out.println("Error tècnic: " + e.getMessage());
            }
        }
    }

    private void veureAlumne() {
        Alumne alumne = service.obtenirAlumnePerId(llegirEnter("Id alumne: "));
        System.out.println(alumne == null ? "No s'ha trobat cap alumne." : alumne);
    }

    private void inserirAlumne() {
        service.crearAlumne(llegirText("Nom: "), llegirText("Email: "), llegirEnter("Id del curs: "));
        System.out.println("Alumne inserit correctament.");
    }

    private void actualitzarAlumne() {
        int id = llegirEnter("Id alumne: ");
        String nom = llegirText("Nou nom: ");
        String email = llegirText("Nou email: ");
        int cursId = llegirEnter("Nou id curs: ");
        service.actualitzarAlumne(id, nom, email, cursId);
        System.out.println("Alumne actualitzat correctament.");
    }

    private void eliminarAlumne() {
        service.eliminarAlumne(llegirEnter("Id alumne: "));
        System.out.println("Alumne eliminat correctament.");
    }

    private void mostrarCursAmbAlumnes() {
        System.out.println(service.mostrarCursAmbAlumnes(llegirEnter("Id curs: ")));
    }

    private void eliminarCurs() {
        service.eliminarCurs(llegirEnter("Id curs: "));
        System.out.println("Curs eliminat correctament.");
    }

    private int llegirEnter(String missatge) {
        while (true) {
            System.out.print(missatge);
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Has d'introduir un enter.");
            }
        }
    }

    private String llegirText(String missatge) {
        System.out.print(missatge);
        return scanner.nextLine();
    }
}
