import java.util.ArrayList;
import java.util.Scanner;
import static donnee.saisirNombre.*;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        ArrayList<Matiere> matieres = listeMatiere();
        ArrayList<Carte> cartes = new ArrayList<Carte>();
        ArrayList<Joueur> joueurs = new ArrayList<Joueur>();
        Boolean end = false;

        Scanner input = new Scanner(System.in);
        System.out.println("----------------------\n--- LA BONNE NOTE ---\n----------------------");
        Thread.sleep(1000);

        joueurs = initialisationJoueur(matieres);

        // Saisie des notes
        System.out.println("\n//-- Saisie des notes sur 20 --//");
        for(Joueur j: joueurs){
            System.out.println(" - " + j.getPseudo() + " saisissez vos notes sur 20 :");
            for(Note n : j.getNoteEnAttente()){

                System.out.println("(Motivation restante : " + j.getMotivation() + ")");
                System.out.print(n.getMatiere().getNom() + " : ");

                Integer noteSaisie;
                if(j.getMotivation() >= 20){
                    noteSaisie = saisirEntier(0, 20);
                }else{
                    noteSaisie = saisirEntier(0, j.getMotivation());
                }

                n.setNoteSur20(noteSaisie);
                j.setMotivation(j.getMotivation() - noteSaisie);
            }
        }

        System.out.println("\nQUE LE JEU ... COMMENCEEEEE !!!!");

        // DEBUG : Affichage détaillé des joueurs
        for(Joueur j : joueurs){
            System.out.println(j.toString());
        }

        // Déroulement de la partie
        while(!end){
            for(Joueur j : joueurs){

            }
            end = true;
        }

    }

    private static ArrayList<Joueur> initialisationJoueur(ArrayList<Matiere> listMatiere){
        ArrayList<Joueur> joueurs = new ArrayList<Joueur>();

        afficheMatiere(listMatiere);
        System.out.println("\n//-- Sélection des joueurs et des spécialité --//");
        System.out.println("Saisir (n) une fois terminé");

        for(int i = 0; i < 6; i++){
            System.out.print("Joueur " + (i+1) + " | pseudo : ");
            String pseudo = saisiePseudo(i);
            if(!pseudo.equals("n")){
                System.out.print(pseudo + " | numero spécialité : ");
                Integer numSpe = (saisirEntier(1, listMatiere.size()) - 1);
                joueurs.add(new Joueur(pseudo, listMatiere.get(numSpe)));
            }else{
                break;
            }
        }
        return joueurs;
    }

    private static void afficheMatiere(ArrayList<Matiere> matieres){
        System.out.println("//-- Spécialités --//");
        for(int i = 0; i < matieres.size(); i++){
            System.out.println(" - Spécialité n°" + (i+1) + " - " + matieres.get(i).getNom());
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

    /* <<< --- CONTENU DU JEU --- >>> */
    public static ArrayList<Matiere> listeMatiere(){
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
