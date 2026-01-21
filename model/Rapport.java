package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Rapport {
    //donnée
    private String auteur;
    private LocalDate dateReunion;
    private String participant;

    //liste des action
    private List<ActionItem> actions;

    public Rapport(String auteur, LocalDate dateReunion, String participant, List<ActionItem> actions) {
        this.auteur = auteur;
        this.dateReunion = dateReunion;
        this.participant = participant;
        this.actions = new ArrayList<>();
    }
    public Rapport(String auteur, LocalDate dateReunion, String participant) {
        this.auteur = auteur;
        this.dateReunion = dateReunion;
        this.participant = participant;
        this.actions = new ArrayList<>();
    }

    public void ajouterAction(ActionItem item){
        this.actions.add(item);
    }

    public String getAuteur() {
        return auteur;
    }

    public LocalDate getDateReunion() {
        return dateReunion;
    }

    public String getParticipant() {
        return participant;
    }

    public List<ActionItem> getActions() {
        return actions;
    }
    
    public String toString() {
        // Un affichage simple pour le rapport
        return "Rapport du " + dateReunion + " par " + auteur + " (" + actions.size() + " actions)";
    }
}
