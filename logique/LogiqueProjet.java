package logique;

import dao.GestionnaireBesoin;
import dao.GestionnaireContrainte;
import dao.GestionnaireRapport;
import model.Besoin;
import model.Contrainte;
import model.Rapport;


//import java.util.ArrayList;
import java.util.List;
/* 
    Cette partie fait la gestion mémoire pour garder les fichier en mémoire
    et ne pa avoir a les relire a chaque fois que l'on cherche quelque chose.
*/
public class LogiqueProjet {

    // recuperation des donnée
    private List<Besoin> besoins;
    private List<Contrainte> contraintes;
    private List<Rapport> rapports;

    private GestionnaireBesoin besoinDao = new GestionnaireBesoin();
    private GestionnaireContrainte contrainteDao = new GestionnaireContrainte();
    private GestionnaireRapport rapportDao = new GestionnaireRapport();

    public LogiqueProjet() {
        // On initialise les listes vides au démarrage de l'app
        this.besoins =  besoinDao.charger();
        this.contraintes = contrainteDao.charger();
        this.rapports = rapportDao.charger();
    }

    //====== Gestion besoin=====
    // ajouter besoin
    public void ajouterBesoin(Besoin besoin) {
        besoins.add(besoin);
        besoinDao.sauvegarder(besoins); // Sauvegarde immédiate
    }

    //lister besoin
    public List<Besoin> getBesoins() {
        return besoins;
    }

    //supprimer besoin
    public boolean supprimerBesoinParId(int id) {
        // removeIf est une méthode de Java 
        // b -> b.getId() == id  veut dire : "Pour chaque besoin 'b', si son ID est égal à l'id fourni"
        boolean result = besoins.removeIf(b -> b.getId() == id);
        //verifier si c'est bien supprimée avant de faire la suppression du fichier
        if (result) besoinDao.sauvegarder(besoins);
        return result;
    }

    public void sauvegarderBesoins() {
        besoinDao.sauvegarder(besoins);
    }

    public Besoin trouverBesoinParId(int id) {
        for (Besoin b : besoins) {
            if (b.getId() == id) {
                return b;
            }
        }
        return null; // Pas trouvé
    }
    // generation de l'id besoin
    public int genererProchainIdBesoin() {
        if (besoins.isEmpty()) {
            return 1;
        }
        // On prend l'ID du dernier élément et on ajoute 1
        return besoins.get(besoins.size() - 1).getId() + 1;
    }

    //====== Gestion contrainte=====
    //ajouter contrainte
    public void ajouterContrainte(Contrainte contrainte) {
        contraintes.add(contrainte);
        contrainteDao.sauvegarder(contraintes);
    }
    
    public boolean supprimerContrainteParId(int id) {
        boolean result = contraintes.removeIf(c -> c.getId() == id);
        if (result) contrainteDao.sauvegarder(contraintes);
        return result;
    }
    
    public void sauvegarderContraintes() {
        contrainteDao.sauvegarder(contraintes);
    }

    public List<Contrainte> getContraintes() { 
        return contraintes; 
    }

    public Contrainte trouverContrainteParId(int id) {
        for (Contrainte c : contraintes) {
            if (c.getId() == id) {
                return c;
            }
        }
        return null;
    }

    public int genererProchainIdContrainte() {
        if (contraintes.isEmpty()){
            return 1;}
        return contraintes.get(contraintes.size() - 1).getId() + 1;
    }

    //=======Gestion Rapport========

    public void ajouterRapport(Rapport rapport) {
        rapports.add(rapport);
        rapportDao.sauvegarder(rapports); 
    }
    public List<Rapport> getRapports() {
        return rapports;
    }

    
    
}