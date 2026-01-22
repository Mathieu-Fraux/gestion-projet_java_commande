import logique.LogiqueProjet;
import ui.Menugeneral;

public class Main {
    public static void main(String[] args) {
        //  création la logique (mémoire du projet)
        LogiqueProjet service = new LogiqueProjet();

        //  création l'UI en lui donnant le service
        Menugeneral menu = new Menugeneral(service);

        //  démarrage l'application
        menu.demarrer();
    }
}