import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        ArrayList<Matiere> matieres = listeMatiere();
        ArrayList<Carte> cartes = new ArrayList<Carte>();
        ArrayList<Joueur> joueurs = new ArrayList<Joueur>();

        Scanner input = new Scanner(System.in);

        System.out.println("Bienvenue sur la bonne note !\n");
        int nbJoueurMax = 6;

        System.out.println("------------------------------\n--- Selection des joueurs ---\n------------------------------\n");

        System.out.println("Saisir (n) une fois terminé");

        for(int i = 0; i < nbJoueurMax; i++){
            System.out.print("Joueur " + (i+1) + " | pseudo : ");
            String pseudo = input.next();
            if(pseudo.equals("n")){break;}
        }

    }

    static ArrayList<Matiere> listeMatiere(){
        ArrayList<Matiere> matiere = new ArrayList<Matiere>();
        matiere.add(new Matiere("Anglais")); // 1
        matiere.add(new Matiere("Mathématique")); // 2
        matiere.add(new Matiere("Culture générale")); // 3
        matiere.add(new Matiere("Eco Droit Management")); // 4
        matiere.add(new Matiere("Développement Web (SI6)")); // 5
        matiere.add(new Matiere("Android (SLAM 2)")); // 6
        matiere.add(new Matiere("Base de données (SLAM 1)")); // 7
        matiere.add(new Matiere("Serveur Web (SI5")); // 8
        return matiere;
    }

}
