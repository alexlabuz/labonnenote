import java.util.ArrayList;
import java.util.Scanner;
import static donnee.saisirNombre.*;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        ArrayList<Matiere> matieres = listeMatiere();
        ArrayList<Carte> cartes = new ArrayList<Carte>();
        ArrayList<Joueur> joueurs = new ArrayList<Joueur>();

        Scanner input = new Scanner(System.in);
        System.out.println("----------------------\n--- LA BONNE NOTE ---\n----------------------");
        Thread.sleep(1000);

        System.out.println("//-- Spécialités --//");
        for(int i = 0; i < matieres.size(); i++){
            System.out.println(" - Spécialité n°" + (i+1) + " - " + matieres.get(i).getNom());
        }

        System.out.println("\n//-- Sélection des joueurs et des spécialité --//");
        System.out.println("Saisir (n) une fois terminé");

        for(int i = 0; i < 6; i++){
            System.out.print("Joueur " + (i+1) + " | pseudo : ");
            String pseudo = saisiePseudo(i);
            if(!pseudo.equals("n")){
                System.out.print(pseudo + " | numero spécialité : ");
                Integer numSpe = (saisirEntier(1, matieres.size())-1);
                joueurs.add(new Joueur(pseudo, matieres.get(numSpe)));
            }else{
                break;
            }
        }

        System.out.println("\nQUE LE JEU ... COMMENCEEEEE !!!!");

        for(Joueur j : joueurs){
            System.out.println(j.toString());
        }

    }

    private static String saisiePseudo(int numero){
        Scanner input = new Scanner(System.in);
        String pseudo = input.next();
        if(pseudo.equals("n")) {
            if (numero < 2) {
                System.out.print("Vous devez être au moins 2 joueurs : ");
                return saisiePseudo(numero);
            }
        }
        return pseudo;
    }

    private static ArrayList<Matiere> listeMatiere(){
        ArrayList<Matiere> matiere = new ArrayList<Matiere>();
        matiere.add(new Matiere("Anglais")); // 1
        matiere.add(new Matiere("Mathématique")); // 2
        matiere.add(new Matiere("Culture générale")); // 3
        matiere.add(new Matiere("Eco Droit Management")); // 4
        matiere.add(new Matiere("Développement Web (SI6)")); // 5
        matiere.add(new Matiere("Android (SLAM 2)")); // 6
        matiere.add(new Matiere("Base de données (SLAM 1)")); // 7
        matiere.add(new Matiere("Serveur Web (SI5)")); // 8
        return matiere;
    }

}
