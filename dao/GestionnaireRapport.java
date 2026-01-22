package dao;

import model.ActionItem;
import model.Rapport;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class GestionnaireRapport {
    private static final String DOSSIER = "data";
    private static final String FICHIER_INDEX = "data/rapports_index.csv";
    private static final String DOSSIER_RAPPORTS = "data/rapport";
    private static final String SEPARATEUR_TECHNIQUE = "---START_ACTIONS_DATA !!Ne pas toucher!!---";

    // sauvegarde du fichier Index
    public void sauvegarder(List<Rapport> liste) {
        File dossierData = new File(DOSSIER);
        if (!dossierData.exists()) {
            dossierData.mkdirs();
        }
        // On sauvegarde l'index
        try (PrintWriter writer = new PrintWriter(new FileWriter(FICHIER_INDEX))) {
            for (Rapport r : liste) {
                // génèration d'un nom de fichier unique pour les rapport
                String nomFichierDetail = "rapport_" + r.getDateReunion() + "_" + r.getAuteur().replaceAll(" ", "")
                        + ".txt";

                // écriture dans l'index les donnée de l'index
                writer.println(
                        r.getAuteur() + ";" + r.getDateReunion() + ";" + r.getParticipant() + ";" + nomFichierDetail);

                // On sauvegarde les détail du rapport dans le fichier dédié
                sauvegarderDetails(r, nomFichierDetail);
            }
        } catch (IOException e) {
            System.out.println("Erreur sauvegarde index rapports : " + e.getMessage());
        }
    }

    // sauvegarde d'un fichier texte pour le rapport
    private void sauvegarderDetails(Rapport r, String nomFichierSimple) {
        File dossierRapport = new File(DOSSIER_RAPPORTS);
        if (!dossierRapport.exists()) {
            dossierRapport.mkdirs();
        }

        File fichierComplet = new File(dossierRapport, nomFichierSimple);

        try (PrintWriter writer = new PrintWriter(new FileWriter(fichierComplet))) {

            // partie lisible humain
            writer.println("==========================================");
            writer.println(" RAPPORT DE RÉUNION");
            writer.println("==========================================");
            writer.println("Date         : " + r.getDateReunion());
            writer.println("Auteur       : " + r.getAuteur());
            writer.println("Participants : " + r.getParticipant());
            writer.println(""); // Ligne vide
            writer.println("TABLEAU DES ACTIONS :");
            // le programme ne lit pas juste pour les humains
            for (ActionItem item : r.getActions()) {
                writer.println(" - " + item.getTache() + " (" + item.getResponsable() + ", pour le "
                        + item.getDateEcheance() + ")");
            }
            writer.println("");

            // partie machine
            writer.println(SEPARATEUR_TECHNIQUE);

            for (ActionItem action : r.getActions()) {
                // Format strict pour la lecture : Quoi;Qui;Quand
                writer.println(action.getTache() + ";" + action.getResponsable() + ";" + action.getDateEcheance());
            }

        } catch (IOException e) {
            System.out.println("Erreur sauvegarde détail (" + nomFichierSimple + ") : " + e.getMessage());
        }
    }

    // chargement des donnée en mémoire33
    public List<Rapport> charger() {
        List<Rapport> liste = new ArrayList<>();
        File file = new File(FICHIER_INDEX);
        if (!file.exists())
            return liste;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String ligne;
            while ((ligne = reader.readLine()) != null) {
                String[] parts = ligne.split(";");
                if (parts.length >= 4) {
                    // Récupération des infos de l'index
                    String auteur = parts[0];
                    LocalDate date = LocalDate.parse(parts[1]);
                    String participants = parts[2];
                    String nomFichierDetail = parts[3];

                    Rapport r = new Rapport(auteur, date, participants);

                    // chargement des actions depuis le fichier texte associé
                    chargerDetails(r, nomFichierDetail);

                    liste.add(r);
                }
            }
        } catch (Exception e) {
            System.out.println("Erreur chargement rapports : " + e.getMessage());
        }
        return liste;
    }

    // chargement des fichier textes
    private void chargerDetails(Rapport r, String nomFichierSimple) {
        File file = new File(DOSSIER_RAPPORTS, nomFichierSimple);
        if (!file.exists()) {
            System.out.println("AVERTISSEMENT : Le fichier détail " + nomFichierSimple + " est introuvable.");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String ligne;
            boolean sectionDonneesTrouvee = false;

            while ((ligne = reader.readLine()) != null) {
                // tant que ya pas de séparateur, pas de lecture machine
                if (!sectionDonneesTrouvee) {
                    if (ligne.equals(SEPARATEUR_TECHNIQUE)) {
                        sectionDonneesTrouvee = true; // lecture activée aprés avoir trouvée
                    }
                    else{
                    // aprés le séparateur on lit les donnée
                    String[] parts = ligne.split(";");
                    if (parts.length >= 3) {
                        r.ajouterAction(new ActionItem(parts[0], parts[1], parts[2]));
                    }}
                }
                if (!sectionDonneesTrouvee) {
                    System.out.println(" ERREUR : Format invalide pour le fichier '" + nomFichierSimple + "'.");   
                }
            }
        } catch (IOException e) {
            System.out.println("Erreur lecture détail rapport : " + e.getMessage());
        }
    }
}
