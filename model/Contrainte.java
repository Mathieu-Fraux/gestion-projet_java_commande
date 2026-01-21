package model;

import java.time.LocalDate;

public class Contrainte {
    //donnée
    private int id;
    private String description;
    private Etatcontrainte etat; 
    
    // Champs spécifiques selon l'état
    private String verificateur; 
    private LocalDate dateVerification;
    private String raisonAnnulation;

    //constructeur
    public Contrainte(int id, String description){
        this.id=id;
        this.description=description;
        this.etat=Etatcontrainte.A_PRENDRE_COMPTE;
    }

    //getter setter
    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public Etatcontrainte getEtat() {
        return etat;
    }

    public String getRaisonAnnulation() {
        return raisonAnnulation;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setEtat(Etatcontrainte etat) {
        this.etat = etat;
    }

    public void setRaisonAnnulation(String raisonAnnulation) {
        this.raisonAnnulation = raisonAnnulation;
    }
    //affichage clean
    public String toString() {
        return "Contrainte [ID=" + id + ", Desc=" + description + ", Etat=" + etat + "]";
    }

    public String getVerificateur() {
        return verificateur;
    }

    public void setVerificateur(String verificateur) {
        this.verificateur = verificateur;
    }

    public LocalDate getDateVerification() {
        return dateVerification;
    }

    public void setDateVerification(LocalDate dateVerification) {
        this.dateVerification = dateVerification;
    }

    
    
}
