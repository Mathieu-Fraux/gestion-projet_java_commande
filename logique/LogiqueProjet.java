package logique;

import model.ActionItem;
import model.Besoin;
import model.Contrainte;
import model.Rapport;


import java.util.ArrayList;
import java.util.List;

public class LogiqueProjet {

    // recuperation des donnée

    private List<Besoin> besoins;
    private List<Contrainte> contraintes;
    private List<Rapport> rapports;


    public LogiqueProjet() {
        // On initialise les listes vides au démarrage de l'app
        this.besoins = new ArrayList<>();
        this.contraintes = new ArrayList<>();
        this.rapports = new ArrayList<>();
    }

    //====== Gestion besoin=====
    // ajouter besoin
    public void ajouterBesoin(Besoin besoin) {
        besoins.add(besoin);
    }

    //lister besoin
    public List<Besoin> getBesoins() {
        return besoins;
    }

    //supprimer besoin
    public boolean supprimerBesoinParId(int id) {
        // removeIf est une méthode de Java 
        // b -> b.getId() == id  veut dire : "Pour chaque besoin 'b', si son ID est égal à l'id fourni"
        return besoins.removeIf(b -> b.getId() == id);

    }

    public Besoin trouverBesoinParId(int id) {
        for (Besoin b : besoins) {
            if (b.getId() == id) {
                return b;
            }
        }
        return null; // Pas trouvé
    }

    //====== Gestion contrainte=====

    //a jouter contrainte
    public void ajouterContrainte(Contrainte contrainte) {
        contraintes.add(contrainte);
    }

    public List<Contrainte> getContraintes() {
        return contraintes;
    }

    public boolean supprimerContrainteParId(int id) {
        return contraintes.removeIf(c -> c.getId() == id);
    }


    //=======Gestion Rapport========

    public void ajouterRapport(Rapport rapport) {
        rapports.add(rapport);
    }

    public List<Rapport> getRapports() {
        return rapports;
    }

    //===generation ID=====
    // generation de l'id
    public int genererProchainIdBesoin() {
        if (besoins.isEmpty()) {
            return 1;
        }
        // On prend l'ID du dernier élément et on ajoute 1
        return besoins.get(besoins.size() - 1).getId() + 1;
    }
    public int genererProchainIdContrainte() {
        if (contraintes.isEmpty()){
            return 1;}
        return contraintes.get(contraintes.size() - 1).getId() + 1;
    }

}
