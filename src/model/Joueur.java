package model;

import controller.Controller;
import controller.Game;
import javafx.scene.control.Label;

import java.util.ArrayList;

public class Joueur {

    private String pseudo;
    private Matiere specialite;
    private Integer age;
    private ArrayList<Note> noteEnAttente;
    private ArrayList<Note> listNote;
    private Integer positionCasePlateau;
    private Integer motivation;

    public Joueur(String pseudo, Matiere specialite){
        this.pseudo = pseudo;
        this.specialite = specialite;
        this.age = 18;
        this.noteEnAttente = initNoteEnAttente();
        this.listNote = new ArrayList<>();
        this.positionCasePlateau = 0;
        this.motivation = motivationMax();
    }

    /**
     * @return Retourne la motivation max possible
     */
    public Integer motivationMax(){
        return 50 + 5 * (this.age - 18);
    }

    /**
     * Avance le joueur d'un certain nombre de case
     * @param nbDeCase Nombre de case auquel le joueur doit avancer
     */
    public void avanceCase(Integer nbDeCase){
        this.positionCasePlateau = this.positionCasePlateau + nbDeCase;
    }

    /**
     * Ajoute la note de la liste "noteEnAttente" correpondant à la matiére
     * @param matiere Matière a laquelle ajouter la note
     * @param travailNonFait si le joueur à tirer la carte "travail non fait" il à 0
     * @param labelIndication Pour afficher le texte sur l'interface graphique
     */
    public void ajouteNote(Matiere matiere, Boolean travailNonFait, Label labelIndication){
        // Calcul le coefficient
        int coeficient = 1;
        if(matiere.getId().equals(this.specialite.getId())){
            coeficient = 3;
        }

        String text = "";
        // Récupére la note de l'utilisateur et la remet à 0 dans "noteEnAttente"
        for(Note n : this.noteEnAttente){
            if(n.getMatiere().getId().equals(matiere.getId())){

                Matiere m = new Matiere(matiere.getNom(), matiere.getId());
                m.setCoef(coeficient);

                if(!travailNonFait){
                    this.listNote.add(new Note(n.getNoteSur20(), m));
                    text = this.pseudo + " à reçu un " + n.getNoteSur20() + " sur 20 en " + matiere.getNom() + "...";
                    n.setNoteSur20(0);
                }else{
                    this.listNote.add(new Note(0, matiere));
                    text = this.pseudo + " à reçu un 0 pointé en " + matiere.getNom() + "...";
                }
                break;
            }
        }

        if(labelIndication != null){
            labelIndication.setText(text);
        }else{
            System.out.println(text);
        }
    }

    /**
     * @return Retourne la moyenne du joueur
     */
    public Double calculMoyenne(){
        double totalSomme = 0.0; // Somme des notes (avec coeficients)
        double effectif = 0.0; // Nombre de note (en comptent les coeficients)

        for(Note n : this.listNote){
            Integer coef = n.getMatiere().getCoef();

            totalSomme = totalSomme + n.getNoteSur20() * coef;
            effectif = effectif + coef;
        }

        return totalSomme / effectif;
    }

    /**
     * Affiche et retourne les notes du joueur
     */
    public String afficheListNote(){
        String text = "";
        text += "--- Liste de note de " + this.pseudo + " ---\n";

        if(this.listNote.size() > 0){
            for(Note n : this.listNote){
                if(n.getMatiere().getCoef() == 3){
                    text += " - " + n.getMatiere().getNom() + " : " + n.getNoteSur20() + " sur 20 (Coef 3)\n";
                }else{
                    text += " - " + n.getMatiere().getNom() + " : " + n.getNoteSur20() + " sur 20\n";
                }
                text += " coef : " + n.getMatiere().getCoef() + "\n\n";
            }

            text += "Moyenne : " + calculMoyenne() + " sur 20";
        }else{
            text += "Vous n'avez pas encore de note";
        }

        System.out.println(text);
        return text;
    }

    /**
     * Met toutes les notes en attente à 0
     * @return retourne la liste de note initialisé à 0
     */
    private ArrayList<Note> initNoteEnAttente(){
        ArrayList<Note> listNoteEnAttente = new ArrayList<>();
        for(Matiere m : Game.listeMatiere()){
            listNoteEnAttente.add(new Note(0, m));
        }
        return listNoteEnAttente;
    }

    /**
     * Permet d'ajouter ou de retirer de la motivation
     * @param motivationPlus Valeur positive ou négative de motivation à ajouter
     * @param cagnote Cagnote afin de pouvoir de l'incrémenté au cas ou le joueur est au max
     * @param labelIndication Pour afficher le texte sur l'interface graphique
     */
    public void ajouteMotivation(Integer motivationPlus, Cagnote cagnote, Label labelIndication){
        this.motivation += motivationPlus;
        String text = "";

        if(motivationPlus >= 0){
            text = this.pseudo + " gagne " + motivationPlus + " point(s) de motivation(s)\n";
        }else{
            text = this.pseudo + " perd " + Math.abs(motivationPlus) + " point(s) de motivation(s)\n";
        }

        // Si le joueur à un nombre négatif de motivation on le met à 0
        if(this.motivation > this.motivationMax()){
            int motivationEnTrop = this.motivation - this.motivationMax();
            cagnote.setPointMotivation(motivationEnTrop);

            this.motivation = this.motivationMax();
            text += "Cela dépasse la motivation max, " + this.pseudo + " envoie donc " + motivationEnTrop + " point(s) de motivation(s) dans la cagnotte";
        }else if(this.motivation < 0){
            this.motivation = 0;
            text += this.pseudo + " n'a plus de motivation";
        }else{
            text += this.pseudo + " à donc " + this.motivation + " de motivation(s)";
        }

        if(labelIndication != null){
            labelIndication.setText(text);
        }else{
            System.out.println(text);
        }
    }

    /**
     * Monte le niveau du joueur de +1
     */
    public void anniversaire(){
        this.age++;
        this.motivation = this.motivation + 5;
    }


    /* <<< --- GUETTERS ET SETTERS--- >>> */
    public ArrayList<Note> getNoteEnAttente() {
        return noteEnAttente;
    }

    public String getPseudo() {
        return pseudo;
    }

    public Matiere getSpecialite() {
        return specialite;
    }

    public Integer getMotivation() {
        return motivation;
    }

    public void setMotivation(Integer motivation) {
        this.motivation = motivation;
    }

    public Integer getPositionCasePlateau() {
        return positionCasePlateau;
    }

    public Integer getAge() {
        return age;
    }
}
