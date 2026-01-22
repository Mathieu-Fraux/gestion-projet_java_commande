package ui;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class SaisieUtil {
    // Scanner unique pour toute l'application (évite les conflits)
    private static final Scanner scanner = new Scanner(System.in);


    // gestion des date pour eviter des probléme
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    // afficher le titre en majuscule et proprement
    public static void afficherTitre(String titre) {
        System.out.println("\n" + "=".repeat(40));
        System.out.println("  " + titre.toUpperCase());
        System.out.println("=".repeat(40));
    }


    // recuperation d'une ligne
    public static String Lirechaine(String message){
        System.out.print(message+" : ");
        return scanner.nextLine();
    }

    //recuperation d'un entier
    public static int lireEntier(String message){
        int nombre = 0;
        boolean good = false;
        while (!good) {
            System.out.print(message + " : ");
            try {
                String input = scanner.nextLine();
                //tente de transformer input en int
                nombre = Integer.parseInt(input);
                // si il y a eu un PB le try catch le bloque avant cette ligne
                good = true;
            } catch (NumberFormatException e) {
                System.out.println("Erreur : Veuillez entrer un nombre entier de la liste.");
            }
        }
        return nombre;
    }

    // recuperation d'une date
    public static LocalDate lireDate(String message) {
        LocalDate date = null;
        boolean good = false;
        while (!good) {
            System.out.print(message + " (format JJ/MM/AAAA) : ");
            String input = scanner.nextLine();
            try {
                // tente de parseer la date dans le bon format
                date = LocalDate.parse(input, DATE_FORMAT);
                good = true;
            } catch (DateTimeParseException e) {
                System.out.println("Erreur : Format de date invalide. Utilisez JJ/MM/AAAA (ex: 25/12/2023)");
            }
        }
        return date;
    }



}
