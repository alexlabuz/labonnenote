package controller;

import model.*;

import java.util.ArrayList;
import java.util.Scanner;

import static donnee.alea.alea;
import static donnee.saisirNombre.saisirEntier;

public class Game {

    public Game() throws InterruptedException {
        ArrayList<Matiere> matieres = listeMatiere();
        ArrayList<Joueur> joueurs;
        ArrayList<Carte> cartes = listeCarte();
        ArrayList<Matiere> plateau = listMatierePlateau(matieres);

        boolean end = false;
        int tour = 0;
        Cagnote cagnote = new Cagnote(0);
        int nbJoueurFini = 0;

        Scanner input = new Scanner(System.in);
        System.out.println("----------------------\n--- LA BONNE NOTE ---\n----------------------");
        Thread.sleep(1000);
        afficheMatiere(matieres);

        joueurs = initialisationJoueur(matieres);

        // Saisie des notes
        System.out.println("\n//-- Saisie des notes sur 20 --//");
        for(Joueur j: joueurs){
            modifierNotesEnAttente(j);
        }
        System.out.println("\nQUE LE JEU ... COMMENCEEEEE !!!!\n");

        // Déroulement de la partie
        while(!end){
            System.out.println("-- Tour n°" + (tour+1)+" ! --");
            for(Joueur j : joueurs){
                if(j.getPositionCasePlateau() <= plateau.size()){
                    System.out.println("\n- Au tour de " + j.getPseudo() + " (" + j.getAge() + " ans)");
                    System.out.println("[Motivation : " + j.getMotivation() + "/" + j.motivationMax()+"]\n[" + "Case : " + j.getPositionCasePlateau() + "/31]");
                    System.out.println("[model.Cagnote : " + cagnote.getPointMotivation() + "]");

                    int actionJoueur = 0;
                    while(actionJoueur != 1){
                        System.out.println("\n" + j.getPseudo() + " - Que voulez vous faire [1 : Lancer dé | 2: Afficher moyenne | 3: Changer notes]");

                        actionJoueur = saisirEntier(1,3);
                        if(actionJoueur == 2){
                            j.afficheListNote();
                        }else if(actionJoueur == 3){
                            modifierNotesEnAttente(j);
                        }
                    }

                    // LANCEMENT DU Dé
                    Thread.sleep(900);
                    Integer deeNumero = alea(1,6);
                    System.out.println("Vous avez eu un " + deeNumero);
                    j.avanceCase(deeNumero);

                    if(j.getPositionCasePlateau() <= plateau.size()){
                        //PIOCHE UNE CARTE
                        System.out.println("Vous êtes sur la case n°" + j.getPositionCasePlateau() + " : " + plateau.get(j.getPositionCasePlateau()-1).getNom() + ", Saisissez une lettre pour tirer une carte");
                        input.next();
                        Thread.sleep(900);

                        Integer numAleaCarte = alea(0, listeCarte().size()-1);
                        Carte cartePioche = cartes.get(numAleaCarte);

                        System.out.println(j.getPseudo() + " : " + cartePioche.getNom() + " - " + cartePioche.getDescription());
                        cartePioche.action(j, plateau.get(j.getPositionCasePlateau()-1), cagnote, null);
                        Thread.sleep(1100);
                    }else{
                        System.out.println(j.getPseudo() + " à terminé la partie !");
                        nbJoueurFini++;
                    }
                }
            }

            if(nbJoueurFini >= joueurs.size()){
                end = true;
            }else{
                tour++;
            }
        }
        System.out.println("LE JEU EST TERMINE !");
        Thread.sleep(1000);
        System.out.println("Saisissez une lettre pour continuer");
        input.next();

        System.out.println("LES RESULTATS");

        ArrayList<Joueur> joueurOrdreMoyenne = triJoueurParMoyenne(joueurs);

        int i = 1;
        for(Joueur j : joueurOrdreMoyenne){
            System.out.println(i + ". " + j.getPseudo() + " - " + j.calculMoyenne() + " sur 20");
            i++;
        }

        System.out.println("\nFélicitation " + joueurOrdreMoyenne.get(0).getPseudo() + " ! Vous êtes le vainqueur de La Bonne model.Note !");
        System.out.println("Saisissez une lettre pour quitter");
        input.next();
    }

    private static ArrayList<Joueur> initialisationJoueur(ArrayList<Matiere> listMatiere){
        ArrayList<Joueur> joueurs = new ArrayList<>();

        System.out.println("\n//-- Sélection des joueurs et des spécialité --//");
        System.out.println("Saisir (n) une fois terminé");

        for(int i = 0; i < 6; i++){
            System.out.print("Joueur " + (i+1) + " | pseudo : ");
            String pseudo = saisiePseudo(i);
            if(!pseudo.equals("n")){
                System.out.print(pseudo + " | numero spécialité : ");
                int numSpecialite = (saisirEntier(1, listMatiere.size()) - 1);
                joueurs.add(new Joueur(pseudo, listMatiere.get(numSpecialite)));
            }else{
                break;
            }
        }
        return joueurs;
    }

    // Permet au joueurs de modifier ses notes en attentes
    private static void modifierNotesEnAttente(Joueur joueur){
        System.out.println("\n - " + joueur.getPseudo() + " saisissez vos notes sur 20 :");
        for(Note n : joueur.getNoteEnAttente()){
            if(joueur.getMotivation() <= 0){
                System.out.println("Vous n'avez plus de motivation");
                break;
            }

            System.out.println("(Motivation restante : " + joueur.getMotivation() + ")");

            // Indique à l'utilisateur que le matière est sa spécialité
            if(n.getMatiere().getId().equals(joueur.getSpecialite().getId())){
                System.out.print(n.getMatiere().getNom() + "(Spécialité) - " + n.getNoteSur20() + "/20 : +");
            }else{
                System.out.print(n.getMatiere().getNom() + " - " + n.getNoteSur20() + "/20 : +");
            }

            // Calcul le maximum que l'utilisateur peut monter sa note
            int difference = 20 - n.getNoteSur20();
            int motivationMax = joueur.getMotivation();
            int saisieMax = Math.min(difference, motivationMax);
            //---

            Integer noteSaisie = saisirEntier(0, saisieMax);
            n.setNoteSur20(n.getNoteSur20() + noteSaisie);

            joueur.setMotivation(joueur.getMotivation() - noteSaisie);
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

    public static ArrayList<Joueur> triJoueurParMoyenne(ArrayList<Joueur> listeJoueur){
        Double moyenneMax = 0.0;

        for(int i = 0 ; i < listeJoueur.size(); i++){

            for(int i2 = i ; i2 < listeJoueur.size(); i2++){
                if(listeJoueur.get(i2).calculMoyenne() > moyenneMax){
                    Joueur joueur = listeJoueur.get(i2); // On enregistre la plus grande valeur
                    listeJoueur.set(i2, listeJoueur.get(i)); // On deplace la première valeur du tableau à l'emplacement de la valeur enregistrer
                    listeJoueur.set(i, joueur); // On place la plus grande valeur à la suite du tableau
                    moyenneMax = listeJoueur.get(i2).calculMoyenne();
                }
            }
            moyenneMax = 0.0;

        }

        return listeJoueur;
    }

    /* <<< --- CONTENU DU JEU --- >>> */
    public static ArrayList<Matiere> listeMatiere(){
        ArrayList<Matiere> matiere = new ArrayList<>();
        matiere.add(new Matiere("Anglais", 0)); // 0
        matiere.add(new Matiere("Mathématique", 1)); // 1
        matiere.add(new Matiere("Culture générale", 2 )); // 2
        matiere.add(new Matiere("Eco Droit Management", 3)); // 3
        matiere.add(new Matiere("Développement Web (SI6)", 4)); // 4
        matiere.add(new Matiere("Android (SLAM 2)", 5)); // 5
        matiere.add(new Matiere("Base de données (SLAM 1)", 6)); // 6
        matiere.add(new Matiere("Serveur Web (SI5)", 7)); // 7
        return matiere;
    }

    public static ArrayList<Carte> listeCarte(){
        ArrayList<Carte> cartes = new ArrayList<>();
        cartes.add(new CardAjoutNote("Travail non fait", "On ne vous félicite pas, vous avez 0 !", "", true));
        cartes.add(new CardAjoutNote("Devoir maison", "Quelle sera votre note", "", false));
        cartes.add(new CardAjoutNote("Interogation", "Allez ! je veux sur la table un stylo et une gomme", "", false));
        cartes.add(new CardAjoutNote("Controle surprise", "Avez vous bien révisé ?!", "", false));
        cartes.add(new CardAjoutNote("Oral", "Vous passez à l'oral", "", false));
        cartes.add(new CardAjoutNote("Devoir surveillé !", "Ohh non, c'était aujourd'hui...", "", false));
        cartes.add(new CardAjoutNote("Projet", "Vous présentez votre projet et ... c'est noté !", "", false));
        cartes.add(new CardAjoutNote("TP noté", "Oh non, pas encore", "", false));

        cartes.add(new CardMotivation("Bien dormi", "Quoi de mieux qu'une bonne nuit pour trouver de la motivation", "", 20));
        //cartes.add(new CardMotivation("Pas assez dormi", "Vous avez fait une nuit blanche", "", -2));
        cartes.add(new CardMotivation("Beau temps", "Quel beau soleil, cela vous donne de la motivation", "", 10));
        //cartes.add(new CardMotivation("Mauvais temps", "Quelle pluit... pas la motivation de travailler", "", -1));
        cartes.add(new CardMotivation("Rendez-vous", "Oh non, une ratez une journée de cours", "", -5));
        //cartes.add(new CardMotivation("Sèche les cours", "Aujourd'hui ce n'était vraiment pas le bon jour", "", -2));
        cartes.add(new CardMotivation("En forme", "Aujourd'hui vous allez tout déchirer", "",40));
        //cartes.add(new CardMotivation("Maladie", "AIIGHT, la douleur que vous resentez est inimagimaginable", "",-4));
        cartes.add(new CardMotivation("Eleve sérieux", "Vous êtes un élève exemplaire", "", 20));
        cartes.add(new CardMotivation("Lendemain de soirée", "Vous ^étse boeuréehé@dei", "", -10));
        cartes.add(new CardMotivation("Bien dormi", "Quoi de mieux qu'une bonne nuit pour trouver de la motivation", "", 20));
        cartes.add(new CardMotivation("Amoureux", "Vous êtes amoureux", "", 20));
        cartes.add(new CardMotivation("Confinement", "Raison sanitaire, la France est en panique", "", -8));

        cartes.add(new CardAge("Anniversaire", "Vous fêtez votre anniversaire", ""));
        cartes.add(new CardAge("Maturité", "Vous devenez plus mature", ""));

        cartes.add(new CardCagnote("Cagnotte", "Vous récupérez la cagnote", ""));
        return cartes;
    }

    public static ArrayList<Matiere> listMatierePlateau(ArrayList<Matiere> listeMatiere){
        ArrayList<Matiere> matieres = new ArrayList<>();
        matieres.add(listeMatiere.get(0)); // 1
        matieres.add(listeMatiere.get(1)); // 2
        matieres.add(listeMatiere.get(2)); // 3
        matieres.add(listeMatiere.get(3)); // 4
        matieres.add(listeMatiere.get(4)); // 5
        matieres.add(listeMatiere.get(5)); // 6
        matieres.add(listeMatiere.get(6)); // 7
        matieres.add(listeMatiere.get(7)); // 8
        matieres.add(listeMatiere.get(0)); // 9
        matieres.add(listeMatiere.get(1)); // 10
        matieres.add(listeMatiere.get(2)); // 11
        matieres.add(listeMatiere.get(3)); // 12
        matieres.add(listeMatiere.get(4)); // 13
        matieres.add(listeMatiere.get(5)); // 14
        matieres.add(listeMatiere.get(6)); // 15
        matieres.add(listeMatiere.get(7)); // 16
        matieres.add(listeMatiere.get(0)); // 17
        matieres.add(listeMatiere.get(1)); // 18
        matieres.add(listeMatiere.get(2)); // 19
        matieres.add(listeMatiere.get(3)); // 20
        matieres.add(listeMatiere.get(4)); // 21
        matieres.add(listeMatiere.get(5)); // 22
        matieres.add(listeMatiere.get(6)); // 23
        matieres.add(listeMatiere.get(7)); // 24
        matieres.add(listeMatiere.get(0)); // 25
        matieres.add(listeMatiere.get(1)); // 26
        matieres.add(listeMatiere.get(2)); // 27
        matieres.add(listeMatiere.get(3)); // 28
        matieres.add(listeMatiere.get(4)); // 29
        matieres.add(listeMatiere.get(5)); // 30
        matieres.add(listeMatiere.get(6)); // 31
        return matieres;
    }

}
