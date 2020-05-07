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
        this.listNote = new ArrayList<Note>();
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
     * Avance le joueur d'un certain nombre de case
     * @param nbDeCase
     */
    public void avanceCase(Integer nbDeCase){
        this.positionCasePlateau = this.positionCasePlateau + nbDeCase;
    }

    /**
     * Ajoute la note de la liste "noteEnAttente" correpondant à la matiére
     */
    public void ajouteNote(){
        // Récupére dans la matière correspondant à la case de l'utilisateur
        ArrayList<Matiere> listMatierePlateau = Main.listMatierePlateau(Main.listeMatiere());
        Matiere matiere = listMatierePlateau.get(this.positionCasePlateau);

        // Calcul le coefficient
        int coeficient = 1;
        if(matiere == this.specialite){
            coeficient = 3;
        }

        // Récupére la note de l'utilisateur et la remet à 0 dans "noteEnAttente"
        for(Note n : this.noteEnAttente){
            // DEBUG
            System.out.println(matiere + " - " + n.getMatiere());

            if(matiere.getId().equals(n.getMatiere().getId())){
                matiere.setCoef(coeficient);
                this.listNote.add(new Note(n.getNoteSur20(), matiere));
                n.setNoteSur20(0);
                System.out.println(this.pseudo + " à reçu un " + n.getNoteSur20() + " sur 20 en" + matiere.getNom() + "...");
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

        if(this.listNote.size() > 0){
            for(Note n : this.listNote){
                System.out.println(" - " + n.getMatiere().getNom() + " : " + n.getNoteSur20() + "sur 20");
            }

            System.out.println("Moyenne : " + calculMoyenne() + " sur 20");
        }else{
            System.out.println("Vous n'avez pas encore de note");
        }
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
        if(motivationPlus >= 0){
            this.motivation = this.motivation + motivationPlus;
            System.out.println(this.pseudo + " gagne " + motivationPlus + " point de motivation");
        }else{
            this.motivation = this.motivation - Math.abs(motivationPlus);
            System.out.println(this.pseudo + " perd " + Math.abs(motivationPlus) + " point de motivation");
        }

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
