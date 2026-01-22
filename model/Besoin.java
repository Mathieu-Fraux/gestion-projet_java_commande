package model;

import java.time.LocalDate;

public class Besoin {

    // les donnée de la class
    private int id;
    private String description;
    private Etatbesoin etat;
    // Champs pour l'état 
    private int chargeJours;        
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private String responsable;
    private String raisonAnnulation;
    private int progression;

    // constructeur
    public Besoin(int id, String description) {
        this.id = id;
        this.description = description;
        this.etat = Etatbesoin.A_ANALYSER;
        this.progression = 0;
    }

    // getter setter
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public Etatbesoin getEtat() {
        return etat;
    }
    public void setEtat(Etatbesoin etat) {
        this.etat = etat;
    }
    public String getRaisonAnnulation() {
        return raisonAnnulation;
    }
    public void setRaisonAnnulation(String raisonAnnulation) {
        this.raisonAnnulation = raisonAnnulation;
    }
    public int getProgression() {
        return progression;
    }
    // verification pour le % de progression
    public void setProgression(int progression) {
        if (progression<0){
            this.progression=0;}
        else if(progression>100){
            this.progression=100;
        }
        else{ 
            this.progression = progression;}
    }
    public String toString() {
        return "Besoin [ID=" + id + ", Desc=" + description + ", Etat=" + etat + ", Prog=" + progression + "%]";
    }
    public int getChargeJours() {
        return chargeJours;
    }
    public LocalDate getDateDebut() {
        return dateDebut;
    }
    public LocalDate getDateFin() {
        return dateFin;
    }
    public String getResponsable() {
        return responsable;
    }
    public void setChargeJours(int chargeJours) {
        this.chargeJours = chargeJours;
    }
    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }
    public void setDateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
    }
    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }   
}