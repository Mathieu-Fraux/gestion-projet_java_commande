package model;

public class ActionItem {
    // donnée
    private String tache;   //quoi    
    private String responsable;//qui
    private String dateEcheance;// quand

    public ActionItem(String tache, String responsable, String dateEchance){
        this.tache=tache;
        this.responsable=responsable;
        this.dateEcheance=dateEchance;

    }

    public String getTache() {
        return tache;
    }

    public void setTache(String tache) {
        this.tache = tache;
    }

    public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

    public String getDateEcheance() {
        return dateEcheance;
    }

    public void setDateEcheance(String dateEcheance) {
        this.dateEcheance = dateEcheance;
    }
    // format pour afficher correctement avec les % pour les alignement correct
    public String toString() {
        return String.format("| %-30s | %-15s | %-12s |", tache, responsable, dateEcheance);
    }
}
