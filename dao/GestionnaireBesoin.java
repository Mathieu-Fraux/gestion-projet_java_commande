package dao;

import model.Besoin;
import model.Etatbesoin;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class GestionnaireBesoin {
    private static final String DOSSIER = "data";
    private static final String FICHIER = "data/besoins.csv";

    //sauvegarder la liste dans fichier
    public void sauvegarder(List<Besoin> liste) {
        File dossierData = new File(DOSSIER);
        if (!dossierData.exists()) {
            dossierData.mkdirs();
        }
        // try  permet de fermer le fichier automatiquement à la fin
        try (PrintWriter writer = new PrintWriter(new FileWriter(FICHIER))) {
            for (Besoin besoin : liste) {
                // consruction d'une chaine avec toutes les infos séparées par ";"
                
                String ligne = besoin.getId() + ";"
                        + besoin.getDescription() + ";"
                        + besoin.getEtat() + ";"
                        + besoin.getProgression() + ";"
                        + besoin.getChargeJours() + ";"
                        + (besoin.getDateDebut() != null ? besoin.getDateDebut() : "null") + ";"
                        + (besoin.getDateFin() != null ? besoin.getDateFin() : "null") + ";"
                        + (besoin.getResponsable() != null ? besoin.getResponsable() : "null") + ";"
                        + (besoin.getRaisonAnnulation() != null ? besoin.getRaisonAnnulation() : "null");
                
                writer.println(ligne);
            }
        } catch (IOException e) {
            System.out.println("Erreur lors de la sauvegarde des besoins : " + e.getMessage());
        }
    }


    //chargement du fichier
    public List<Besoin> charger() {
        List<Besoin> liste = new ArrayList<>();
        File file = new File(FICHIER);

        // Si le fichier n'existe pas, on renvoie une liste vide
        if (!file.exists()) return liste;
        //mise en mémoire avec buffer reader
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String ligne;
            while ((ligne = reader.readLine()) != null) {
                String[] parts = ligne.split(";");
                // verification qu'il y a assez de colonnes(utile) 
                if (parts.length >= 4) {
                    // creation objet besoin
                    int id = Integer.parseInt(parts[0]);
                    String desc = parts[1];
                    Besoin besoin = new Besoin(id, desc);

                    // ajouter les attributs de l'objet
                    besoin.setEtat(Etatbesoin.valueOf(parts[2])); //lien entre String et enum
                    besoin.setProgression(Integer.parseInt(parts[3]));
                    
                    // rentrer le reste des donnée si elle existe(Attention aux index !!!!)
                    if (parts.length > 4) besoin.setChargeJours(Integer.parseInt(parts[4]));
                    
                    if (parts.length > 5 && !parts[5].equals("null")) 
                        besoin.setDateDebut(LocalDate.parse(parts[5]));
                        
                    if (parts.length > 6 && !parts[6].equals("null")) 
                        besoin.setDateFin(LocalDate.parse(parts[6]));
                        
                    if (parts.length > 7 && !parts[7].equals("null")) 
                        besoin.setResponsable(parts[7]);
                        
                    if (parts.length > 8 && !parts[8].equals("null")) 
                        besoin.setRaisonAnnulation(parts[8]);

                    liste.add(besoin);
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("Erreur lors du chargement des besoins : " + e.getMessage());
        }
        return liste;
    }


}
