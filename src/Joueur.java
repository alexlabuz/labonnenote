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
        this.listNote = null;
        this.positionCasePlateau = 0;
        this.motivation = motivationMax();
    }

    /**
     * Retourne la motivation max possible
     * @return
     */
    private Integer motivationMax(){
        return 90 + 5 * (this.age - 18);
    }

    /**
     * Ajoute la note de la liste "noteEnAttente" correpondant à la matiére
     * @param matiere
     */
    public void ajouteNote(Matiere matiere){
        // Calcul le coefficient
        int coeficient = 1;
        if(matiere == this.specialite){
            coeficient = 3;
        }

        // Récupére la note de l'utilisateur et la remet à 0 dans "noteEnAttente"
        for(Note n : this.noteEnAttente){
            if(matiere == n.getMatiere()){
                matiere.setCoef(coeficient);
                this.listNote.add(new Note(n.getNoteSur20(), matiere));
                n.setNoteSur20(0);
                System.out.println(this.pseudo + " à reçu un " + n.getNoteSur20() + " sur 20...");
                break;
            }
        }
    }

    /**
     * Retourne la moyenne du joueur
     * @return
     */
    public Double calculMoyenne(){
        Double totalSomme = null; // Somme des notes (avec coeficients)
        Double effectif = null; // Nombre de note (en comptent les coeficients)

        for(Note n : this.listNote){
            Integer coef = n.getMatiere().getCoef();

            totalSomme = totalSomme + n.getNoteSur20() * coef;
            effectif = effectif + coef;
        }

        return totalSomme / effectif;
    }

    /**
     * Affiche les notes du joueurs
     */
    public void afficheListNote(){
        System.out.println("--- Liste de note de " + this.pseudo + " ---");

        for(Note n : this.listNote){
            System.out.println(" - " + n.getMatiere().getNom() + " : " + n.getNoteSur20() + "sur 20");
        }

        System.out.println("Moyenne : " + calculMoyenne() + " sur 20");
    }

    /**
     * Met toutes les notes en attente à 0
     * @return
     */
    private ArrayList<Note> initNoteEnAttente(){
        ArrayList<Note> listNoteEnAttente = new ArrayList<Note>();
        for(Matiere m : Main.listeMatiere()){
            listNoteEnAttente.add(new Note(0, m));
        }
        return listNoteEnAttente;
    }

    /**
     * Permet d'ajouter ou de retirer de la motivation
     * @param motivationPlus
     */
    public void ajouteMotivation(Integer motivationPlus){
        System.out.println(this.pseudo + "Gagne " + this.motivation + " point de motivation");
        this.motivation = this.motivation + motivationPlus;

        if(this.motivation > motivationMax()){
            this.motivation = motivationMax();
            System.out.println("Cela dépasse la motivation max, " + this.pseudo + " à donc " + this.motivation + " de motivation");
        }else if(this.motivation < 0){
            this.motivation = 0;
            System.out.println(this.pseudo + " n'a plus de motivation");
        }else{
            System.out.println(this.pseudo + " à donc " + this.motivation + " de motivation");
        }
    }


    /* <<< --- GUETTERS ET SETTERS--- >>> */
    public ArrayList<Note> getNoteEnAttente() {
        return noteEnAttente;
    }

    public String getPseudo() {
        return pseudo;
    }

    public Integer getMotivation() {
        return motivation;
    }

    public void setMotivation(Integer motivation) {
        this.motivation = motivation;
    }

    @Override
    public String toString() {
        return "Joueur{" +
                "pseudo='" + pseudo + '\'' +
                ", specialite=" + specialite.getNom() +
                ", age=" + age +
                ", listNote=" + listNote +
                ", casePlateau=" + positionCasePlateau +
                ", motivation=" + motivation +
                '}';

    }
    public String retourneNote(){
        String text = "noteEnAttente{";
        for(Note n : this.noteEnAttente){
            text = text + ", "+n.getNoteSur20();
        }
        text = text + "}";
        return text;
    }
}

