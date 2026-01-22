package dao;

import model.Contrainte;
import model.Etatcontrainte;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/*voir dao besoin pour explication */
public class GestionnaireContrainte {
    private static final String FICHIER = "data/contraintes.csv";

    public void sauvegarder(List<Contrainte> liste) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FICHIER))) {
            for (Contrainte c : liste) {
                String ligne = c.getId() + ";"
                        + c.getDescription() + ";"
                        + c.getEtat() + ";"
                        + (c.getVerificateur() != null ? c.getVerificateur() : "null") + ";"
                        + (c.getDateVerification() != null ? c.getDateVerification() : "null") + ";"
                        + (c.getRaisonAnnulation() != null ? c.getRaisonAnnulation() : "null");
                writer.println(ligne);
            }
        } catch (IOException e) {
            System.out.println("Erreur sauvegarde contraintes : " + e.getMessage());
        }

    }

    public List<Contrainte> charger() {
        List<Contrainte> liste = new ArrayList<>();
        File file = new File(FICHIER);
        if (!file.exists())
            return liste;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String ligne;
            while ((ligne = reader.readLine()) != null) {
                String[] parts = ligne.split(";");
                if (parts.length >= 3) {
                    Contrainte c = new Contrainte(Integer.parseInt(parts[0]), parts[1]);
                    c.setEtat(Etatcontrainte.valueOf(parts[2]));

                    if (parts.length > 3 && !parts[3].equals("null"))
                        c.setVerificateur(parts[3]);
                    if (parts.length > 4 && !parts[4].equals("null"))
                        c.setDateVerification(LocalDate.parse(parts[4]));
                    if (parts.length > 5 && !parts[5].equals("null"))
                        c.setRaisonAnnulation(parts[5]);

                    liste.add(c);
                }
            }
        } catch (Exception e) {
            System.out.println("Erreur chargement contraintes : " + e.getMessage());
        }
        return liste;
    }

}
