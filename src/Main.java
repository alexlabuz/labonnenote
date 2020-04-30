import java.util.ArrayList;
import java.util.Scanner;
import static donnee.saisirNombre.*;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        ArrayList<Matiere> matieres = listeMatiere();
        ArrayList<Joueur> joueurs = new ArrayList<Joueur>();
        ArrayList<Carte> cartes = new ArrayList<Carte>();

        Boolean end = false;

        Scanner input = new Scanner(System.in);
        System.out.println("----------------------\n--- LA BONNE NOTE ---\n----------------------");
        Thread.sleep(1000);

        joueurs = initialisationJoueur(matieres);

        // Saisie des notes
        System.out.println("\n//-- Saisie des notes sur 20 --//");
        for(Joueur j: joueurs){
            modifierNotesEnAttente(j);
        }
        System.out.println("\nQUE LE JEU ... COMMENCEEEEE !!!!");

        // DEBUG : Affichage détaillé des joueurs
        for(Joueur j : joueurs){
            System.out.println(j.toString());
            System.out.println(j.retourneNote());
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

    // Permet au joueurs de modifier ses notes en attentes
    private static void modifierNotesEnAttente(Joueur joueur){
        System.out.println(" - " + joueur.getPseudo() + " saisissez vos notes sur 20 :");
        for(Note n : joueur.getNoteEnAttente()){

            System.out.println("(Motivation restante : " + joueur.getMotivation() + ")");
            System.out.print(n.getMatiere().getNom() + " - " + n.getNoteSur20() + "/20 : +");

            // Calcul le maximum que l'utilisateur peut monter sa note
            int difference = 20 - n.getNoteSur20();
            int motivationMax = joueur.getMotivation();
            int saisieMax = Math.min(difference, motivationMax);
            //---

            Integer noteSaisie = saisirEntier(0, saisieMax);
            n.setNoteSur20(n.getNoteSur20() + noteSaisie);

            joueur.setMotivation(joueur.getMotivation() - noteSaisie);
            if(joueur.getMotivation() <= 0){
                System.out.println("Vous n'avez plus de motivation");
                break;
            }

        }
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
