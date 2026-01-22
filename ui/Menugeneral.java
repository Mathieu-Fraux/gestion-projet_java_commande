package ui;

import model.Besoin;
import model.Contrainte;
import model.Etatbesoin;
import model.Etatcontrainte;
import model.Rapport;
import logique.LogiqueProjet;

import java.time.LocalDate;
import java.util.List;


/*
 cette classe gére l'affichage des différent menu et tout ce qui est affichée,
 il gére aussi l'appel des fonction pour répondre au action de l'utillisateur
*/
public class Menugeneral {

    //acces a la logique metier
    private logique.LogiqueProjet logique;

    public Menugeneral(logique.LogiqueProjet logique){
    this.logique=logique;
    }
    // boucle principale
    public void demarrer(){

        //boucle pour affichage du menue principal
        boolean continuer = true;
        while (continuer) {
            SaisieUtil.afficherTitre("MENU PRINCIPAL AGILE TOOL");
            System.out.println("1. Gérer les Besoins");
            System.out.println("2. Gérer les Contraintes");
            System.out.println("3. Gérer les Rapports");
            System.out.println("0. Quitter");

            int choix = SaisieUtil.lireEntier("Votre choix");

            switch (choix) {
                case 1 -> gererBesoins();
                case 2 -> gererContraintes(); 
                case 3 -> gererRapports();    
                case 0 -> {
                    System.out.println("Au revoir !");
                    continuer = false;
                }
                default -> System.out.println("Choix invalide.");
            }
        }
        
    }
    // sous menue : besoin
    private void gererBesoins() {
        // boucle pour le sous menu
        boolean retour = false;
        while (!retour) {
            SaisieUtil.afficherTitre("GESTION DES BESOINS");
            System.out.println("1. Lister les besoins");
            System.out.println("2. Ajouter un besoin");
            System.out.println("3. Supprimer un besoin");
            System.out.println("4. modifier un besoin");
            System.out.println("0. Retour menu principal");

            int choix = SaisieUtil.lireEntier("Votre choix");

            switch (choix) {
                case 1 -> listerBesoins();
                case 2 -> ajouterBesoin();
                case 3 -> supprimerBesoin();
                case 4-> modifierBesoin();
                case 0 -> retour = true;
                default -> System.out.println("Choix invalide.");
            }
        }
    }
    // menu pour afficher les besoins
    private void listerBesoins() {
        List<Besoin> liste = logique.getBesoins();
        
        if (liste.isEmpty()) {
            System.out.println(" Aucun besoin enregistré.");
            return;
        }

        // Affichage en tableau avec String.format
        // %-5s = colonne de 5 char alignée gauche
        System.out.println("----------------------------------------------------------------------");
        System.out.printf("| %-4s | %-30s | %-15s | %-5s |\n", "ID", "Description", "Etat", "Prog.");
        System.out.println("----------------------------------------------------------------------");
        
        for (Besoin besoin : liste) {
            System.out.printf("| %-4d | %-30s | %-15s | %-4d%% |\n", 
                    besoin.getId(), 
                    // on coupe la description si elle est trop longue pour pas casser le tableau( et ne pas penser qu'il y a un bug)
                    (besoin.getDescription().length() > 28 ? besoin.getDescription().substring(0, 27) + "..." : besoin.getDescription()), 
                    besoin.getEtat(), 
                    besoin.getProgression());
        }
        System.out.println("----------------------------------------------------------------------");
    }
    private void ajouterBesoin() {
        System.out.println("\n--- Nouveau Besoin ---");
        String desc = SaisieUtil.Lirechaine("Description du besoin");
        
        // On demande a la logique de générer un ID 
        int id = logique.genererProchainIdBesoin(); 
        
        Besoin nouveau = new Besoin(id, desc);
        logique.ajouterBesoin(nouveau);
        
        System.out.println(" Besoin ajouté avec succès !");
    }

    private void supprimerBesoin() {
        listerBesoins(); // On affiche la liste pour que l'user voie les ID pour pouvoir affectuer son action
        int id = SaisieUtil.lireEntier("ID du besoin à supprimer");
        
        boolean success = logique.supprimerBesoinParId(id);
        if (success) {
            System.out.println(" Besoin " + id + " supprimé.");
        } else {
            System.out.println(" Erreur : ID introuvable.");
        }
    }
    private void modifierBesoin() {
        listerBesoins();
        int id = SaisieUtil.lireEntier("ID du besoin à modifier");
        Besoin besoin = logique.trouverBesoinParId(id);

        if (besoin == null) {
            System.out.println(" Erreur : Besoin introuvable.");
            return;
        }

        System.out.println("Modification de : " + besoin.getDescription());
        System.out.println("État actuel : " + besoin.getEtat());
        
        // On propose les nouveaux états possibles
        System.out.println("Choisir le nouvel état :");
        int i = 1;
        for (Etatbesoin e : Etatbesoin.values()) {
            System.out.println(i + ". " + e);
            i++;
        }
        
        int choixEtat = SaisieUtil.lireEntier("Votre choix");
        // recuperation l'enum depuis l'index (attention -1 car tableau commence à 0!)
        if (choixEtat >= 1 && choixEtat <= Etatbesoin.values().length) {
            Etatbesoin nouvelEtat = Etatbesoin.values()[choixEtat - 1];
            besoin.setEtat(nouvelEtat);

            // LOGIQUE SPECIFIQUE SELON L'ETAT CHOISI
            switch (nouvelEtat) {
                case ANALYSE -> {
                    System.out.println(" Passage en ANALYSE. Veuillez préciser :");
                    besoin.setChargeJours(SaisieUtil.lireEntier("Charge (jours/homme)"));
                    besoin.setResponsable(SaisieUtil.Lirechaine("Responsable"));
                    besoin.setDateDebut(SaisieUtil.lireDate("Date de début"));
                    besoin.setDateFin(SaisieUtil.lireDate("Date de fin"));
                }
                case ANNULE -> {
                    System.out.println(" Passage en ANNULE.");
                    besoin.setRaisonAnnulation(SaisieUtil.Lirechaine("Raison de l'annulation"));
                }
                case A_ANALYSER -> {
                    // On remet éventuellement à zéro si on revient en arrière ( pas forcément utile mais bon)
                    besoin.setProgression(0);
                }
                case TERMINE -> {
                    besoin.setProgression(100);
                    System.out.println(" Besoin marqué comme terminé (100%).");
                }
            }
            
            // modifier la progression si ce n'est pas fini/annulé
            if (nouvelEtat == Etatbesoin.ANALYSE) {
                 int prog = SaisieUtil.lireEntier("Mettre à jour la progression (%)");
                 besoin.setProgression(prog);
            }

            System.out.println("Modification enregistrée !");
        } else {
            System.out.println("Choix invalide.");
        }
    }

    //======= Msous menu: contrainte=====

    private void gererContraintes() {
        //affichage menu avec boucle
        boolean retour = false;
        while (!retour) {
            SaisieUtil.afficherTitre("GESTION DES CONTRAINTES");
            System.out.println("1. Lister les contraintes");
            System.out.println("2. Ajouter une contrainte");
            System.out.println("3. Modifier / Vérifier une contrainte");
            System.out.println("4. Supprimer une contrainte");
            System.out.println("0. Retour");

            int choix = SaisieUtil.lireEntier("Votre choix");
            switch (choix) {
                case 1 -> listerContraintes();
                case 2 -> ajouterContrainte();
                case 3 -> modifierContrainte();
                case 4 -> supprimerContrainte();
                case 0 -> retour = true;
                default -> System.out.println("Choix invalide.");
            }
        }
    }

    private void listerContraintes() {
        List<Contrainte> liste = logique.getContraintes();
        if (liste.isEmpty()) {
            System.out.println(" Aucune contrainte.");
            return;
        }
        System.out.println("-----------------------------------------------------------------------------");
        System.out.printf("| %-4s | %-30s | %-20s | %-10s |\n", "ID", "Description", "Etat", "Détail");
        System.out.println("-----------------------------------------------------------------------------");
        for (Contrainte contrainte : liste) {
            // Petite logique d'affichage pour la colonne "Détail"
            String detail = "";
            if (contrainte.getEtat() == Etatcontrainte.VERIFIEE) detail = "Par " + contrainte.getVerificateur();
            if (contrainte.getEtat() == Etatcontrainte.ANNULEE) detail = contrainte.getRaisonAnnulation();
            
            System.out.printf("| %-4d | %-30s | %-20s | %-10s |\n", 
                    contrainte.getId(), 
                    (contrainte.getDescription().length() > 28 ? contrainte.getDescription().substring(0, 27) + "..." : contrainte.getDescription()),
                    contrainte.getEtat(), 
                    detail);
        }
        System.out.println("-----------------------------------------------------------------------------");
    }

    private void ajouterContrainte() {
        System.out.println("\n--- Nouvelle Contrainte ---");
        String desc = SaisieUtil.Lirechaine("Description");
        int id = logique.genererProchainIdContrainte();
        logique.ajouterContrainte(new Contrainte(id, desc));
        System.out.println("Contrainte ajoutée.");
    }

    private void modifierContrainte() {
        listerContraintes();
        int id = SaisieUtil.lireEntier("ID de la contrainte");
        // contrainte est copier-coller de besoins.
        Contrainte contrainte = logique.trouverContrainteParId(id); 

        if (contrainte == null) {
            System.out.println(">> Introuvable.");
            return;
        }

        System.out.println("État actuel : " + contrainte.getEtat());
        System.out.println("Choisir le nouvel état :");
        int i = 1;
        for (Etatcontrainte ec : Etatcontrainte.values()) {
            System.out.println(i + ". " + ec);
            i++;
        }
        
        int choix = SaisieUtil.lireEntier("Choix");
        if (choix >= 1 && choix <= Etatcontrainte.values().length) {
            Etatcontrainte nouvelEtat = Etatcontrainte.values()[choix - 1];
            contrainte.setEtat(nouvelEtat);

            // Logique spécifique Contrainte
            if (nouvelEtat == Etatcontrainte.VERIFIEE) {
                contrainte.setVerificateur(SaisieUtil.Lirechaine("Qui a vérifié ?"));
                contrainte.setDateVerification(SaisieUtil.lireDate("Date de vérification"));
            } else if (nouvelEtat == Etatcontrainte.ANNULEE) {
                contrainte.setRaisonAnnulation(SaisieUtil.Lirechaine("Raison annulation"));
            }
            System.out.println("Contrainte mise à jour.");
        }
    }

    private void supprimerContrainte() {
        listerContraintes();
        int id = SaisieUtil.lireEntier("ID à supprimer");
        if(logique.supprimerContrainteParId(id)) System.out.println(" Supprimé.");
        else System.out.println("Introuvable.");
    }


    // ====== sous menu: rapport=======
    private void gererRapports() {
        boolean retour = false;
        while (!retour) {
            SaisieUtil.afficherTitre("GESTION DES RAPPORTS");
            System.out.println("1. Lister les rapports existants");
            System.out.println("2. Créer un nouveau rapport (et ses actions)");
            System.out.println("3. Afficher le détail d'un rapport");
            System.out.println("0. Retour");

            int choix = SaisieUtil.lireEntier("Votre choix");
            switch (choix) {
                case 1 -> listerRapports();
                case 2 -> creerRapport();
                case 3 -> afficherDetailRapport();
                case 0 -> retour = true;
                default -> System.out.println("Choix invalide.");
            }
        }
    }

    private void listerRapports() {
        List<Rapport> liste = logique.getRapports();
        if (liste.isEmpty()) {
            System.out.println(" Aucun rapport.");
            return;
        }
        System.out.println("---------------------------------------------------------");
        System.out.printf("| %-3s | %-12s | %-15s | %-10s |\n", "N°", "Date", "Auteur", "Nb Actions");
        System.out.println("---------------------------------------------------------");
        
        // On utilise un index "i" simple pour choisir le rapport car ils n'ont pas d'ID
        for (int i = 0; i < liste.size(); i++) {
            Rapport rapport = liste.get(i);
            System.out.printf("| %-3d | %-12s | %-15s | %-10d |\n", 
                    (i + 1), // On affiche 1 pour le premier élément (index 0)
                    rapport.getDateReunion(), 
                    rapport.getAuteur(), 
                    rapport.getActions().size());
        }
        System.out.println("---------------------------------------------------------");
    }

    private void creerRapport() {
        System.out.println("\n--- Nouveau Rapport de Réunion ---");
        // 1. Infos générales
        String auteur = SaisieUtil.Lirechaine("Auteur du compte-rendu");
        LocalDate date = SaisieUtil.lireDate("Date de la réunion");
        String participants = SaisieUtil.Lirechaine("Participants (séparés par virgule)");

        Rapport rapport = new Rapport(auteur, date, participants);

        // 2. Boucle pour ajouter les actions (Quoi/Qui/Quand)
        boolean ajouterAction = true;
        while (ajouterAction) {
            System.out.println("\n--- Ajouter une action au tableau ---");
            String quoi = SaisieUtil.Lirechaine("Quoi (Tâche)");
            String qui = SaisieUtil.Lirechaine("Qui (Responsable)");
            String quand = SaisieUtil.Lirechaine("Quand (Date échéance texte)");

            // On utilise la méthode de la classe Rapport pour ajouter l'item
            //  objet ActionItem est créé directement ici
            rapport.ajouterAction(new model.ActionItem(quoi, qui, quand));

            // On demande si on continue
            String rep = SaisieUtil.Lirechaine("Ajouter une autre action ? (O/N)");
            //aide pour ignorer si c'est en majuscule ou en minuscule
            if (rep.equalsIgnoreCase("N")) {
                ajouterAction = false;
            }
        }

        // 3. Enregistrement dans le logique
        logique.ajouterRapport(rapport);
        System.out.println("Rapport créé et sauvegardé en mémoire !");
    }

    private void afficherDetailRapport() {
        listerRapports();
        int index = SaisieUtil.lireEntier("Numéro du rapport à afficher") - 1;

        if (index >= 0 && index < logique.getRapports().size()) {
            Rapport rapport = logique.getRapports().get(index);
            
            // Affichage Header
            SaisieUtil.afficherTitre("RAPPORT DU " + rapport.getDateReunion());
            System.out.println("Auteur       : " + rapport.getAuteur());
            System.out.println("Participants : " + rapport.getParticipant());
            System.out.println("\nTABLEAU DES ACTIONS :");
            
            // Affichage Tableau Actions
            System.out.println("----------------------------------------------------------------");
            System.out.printf("| %-30s | %-30s | %-12s |\n", "QUOI", "QUI", "QUAND");
            System.out.println("----------------------------------------------------------------");
            for (model.ActionItem item : rapport.getActions()) {
                // On utilise le toString() de ActionItem ou on formate ici
                // Comme ActionItem.toString() a déjà le formatage, on peut l'utiliser, 
                // mais attention il ajoute déjà les '|'.
                System.out.println(item.toString());
            }
            System.out.println("----------------------------------------------------------------");
            
        } else {
            System.out.println(" Numéro invalide.");
        }
    }
}


