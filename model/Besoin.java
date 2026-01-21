package model;

public class Besoin {



    public enum EtatBesoin {
        A_ANALYSER,      // État initial
        ANALYSE,         // Besoin analysé
        EN_COURS,        // En cours de réalisation
        TERMINE,         // Terminé
        ANNULE           // Annulé
    }

    
    private int id;
    private String description;
    private EtatBesoin etat;
    private String dateAnalysePrevue;
    private double charge;
    private String dateDebut;
    private String dateFin;
    private String responsable;
    private String raisonAnnulation;
    private int tauxProgression;
    private boolean termine;

    public Besoin(int id, String description) {
        this.id = id;
        this.description = description;
        this.etat = EtatBesoin.A_ANALYSER;
        this.tauxProgression = 0;
        this.termine = false;
        this.charge = 0.0;
    }

    // Getters
    public int getId() { return id; }
    public String getDescription() { return description; }
    public EtatBesoin getEtat() { return etat; }
    public String getDateAnalysePrevue() { return dateAnalysePrevue; }
    public double getCharge() { return charge; }
    public String getDateDebut() { return dateDebut; }
    public String getDateFin() { return dateFin; }
    public String getResponsable() { return responsable; }
    public String getRaisonAnnulation() { return raisonAnnulation; }
    public int getTauxProgression() { return tauxProgression; }
    public boolean isTermine() { return termine; }

    // Setters
    public void setEtat(EtatBesoin etat) { this.etat = etat; }
    public void setDateAnalysePrevue(String date) { this.dateAnalysePrevue = date; }
    public void setCharge(double charge) { this.charge = charge; }
    public void setDateDebut(String date) { this.dateDebut = date; }
    public void setDateFin(String date) { this.dateFin = date; }
    public void setResponsable(String resp) { this.responsable = resp; }
    public void setRaisonAnnulation(String raison) { this.raisonAnnulation = raison; }
    public void setTauxProgression(int taux) { this.tauxProgression = taux; }
    public void setTermine(boolean termine) { this.termine = termine; }

    public String toCSV() {
        return id + ";" + description + ";" + etat + ";" + 
               (dateAnalysePrevue != null ? dateAnalysePrevue : "") + ";" +
               charge + ";" + (dateDebut != null ? dateDebut : "") + ";" +
               (dateFin != null ? dateFin : "") + ";" +
               (responsable != null ? responsable : "") + ";" +
               (raisonAnnulation != null ? raisonAnnulation : "") + ";" +
               tauxProgression + ";" + termine;
    }

    public static Besoin fromCSV(String line) {
        String[] parts = line.split(";");
        Besoin b = new Besoin(Integer.parseInt(parts[0]), parts[1]);
        b.setEtat(EtatBesoin.valueOf(parts[2]));
        if (!parts[3].isEmpty()) b.setDateAnalysePrevue(parts[3]);
        b.setCharge(Double.parseDouble(parts[4]));
        if (!parts[5].isEmpty()) b.setDateDebut(parts[5]);
        if (!parts[6].isEmpty()) b.setDateFin(parts[6]);
        if (!parts[7].isEmpty()) b.setResponsable(parts[7]);
        if (!parts[8].isEmpty()) b.setRaisonAnnulation(parts[8]);
        b.setTauxProgression(Integer.parseInt(parts[9]));
        b.setTermine(Boolean.parseBoolean(parts[10]));
        return b;
    }
}

