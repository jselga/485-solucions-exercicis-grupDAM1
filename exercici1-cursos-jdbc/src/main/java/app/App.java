package app;

import exception.BusinessException;
import exception.ConfigurationException;
import exception.DataAccessException;
import model.Curs;
import service.CursService;

import java.util.ArrayList;
import java.util.Scanner;

public class App {

    private final CursService cursService = new CursService();
    private final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        new App().executar();
    }

    private void executar() {
        boolean sortir = false;
        while (!sortir) {
            System.out.println("\n===== EXERCICI 1 · CURSOS =====");
            System.out.println("1. Llistar cursos");
            System.out.println("2. Veure curs per id");
            System.out.println("3. Inserir curs");
            System.out.println("4. Actualitzar curs");
            System.out.println("5. Eliminar curs");
            System.out.println("0. Sortir");

            int opcio = llegirEnter("Opció: ");
            try {
                switch (opcio) {
                    case 1 -> llistarCursos();
                    case 2 -> veureCurs();
                    case 3 -> inserirCurs();
                    case 4 -> actualitzarCurs();
                    case 5 -> eliminarCurs();
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

    private void llistarCursos() {
        ArrayList<Curs> cursos = cursService.obtenirTots();
        for (Curs curs : cursos) {
            System.out.println(curs);
        }
        
    }

    private void veureCurs() {
        int id = llegirEnter("Id del curs: ");
        Curs curs = cursService.obtenirPerId(id);
        System.out.println(curs == null ? "No s'ha trobat cap curs." : curs);
    }

    private void inserirCurs() {
        String nom = llegirText("Nom: ");
        String nivell = llegirText("Nivell: ");
        cursService.crearCurs(nom, nivell);
        System.out.println("Curs inserit correctament.");
    }

    private void actualitzarCurs() {
        int id = llegirEnter("Id del curs: ");
        String nom = llegirText("Nou nom: ");
        String nivell = llegirText("Nou nivell: ");
        cursService.actualitzarCurs(id, nom, nivell);
        System.out.println("Curs actualitzat correctament.");
    }

    private void eliminarCurs() {
        int id = llegirEnter("Id del curs: ");
        cursService.eliminarCurs(id);
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
