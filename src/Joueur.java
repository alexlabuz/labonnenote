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
        return 90 + 5 * (this.age - 18);
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
     */
    public void ajouteNote(Matiere matiere, Boolean travailNonFait){
        // Calcul le coefficient
        int coeficient = 1;
        if(matiere == this.specialite){
            coeficient = 3;
        }

        // Récupére la note de l'utilisateur et la remet à 0 dans "noteEnAttente"
        for(Note n : this.noteEnAttente){
            if(n.getMatiere().getId().equals(matiere.getId())){
                matiere.setCoef(coeficient);

                if(!travailNonFait){
                    this.listNote.add(new Note(n.getNoteSur20(), matiere));
                    System.out.println(this.pseudo + " à reçu un " + n.getNoteSur20() + " sur 20 en " + matiere.getNom() + "...");
                    n.setNoteSur20(0);
                }else{
                    this.listNote.add(new Note(0, matiere));
                    System.out.println(this.pseudo + " à reçu un 0 pointé en " + matiere.getNom() + "...");
                }
                break;
            }
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
     * Affiche les notes du joueurs
     */
    public void afficheListNote(){
        System.out.println("--- Liste de note de " + this.pseudo + " ---");

        if(this.listNote.size() > 0){
            for(Note n : this.listNote){
                if(n.getMatiere().getCoef() == 3){
                    System.out.println(" - " + n.getMatiere().getNom() + " : " + n.getNoteSur20() + " sur 20 (Coef 3)");
                }else{
                    System.out.println(" - " + n.getMatiere().getNom() + " : " + n.getNoteSur20() + " sur 20");
                }
            }

            System.out.println("Moyenne : " + calculMoyenne() + " sur 20");
        }else{
            System.out.println("Vous n'avez pas encore de note");
        }
    }

    /**
     * Met toutes les notes en attente à 0
     * @return retourne la liste de note initialisé à 0
     */
    private ArrayList<Note> initNoteEnAttente(){
        ArrayList<Note> listNoteEnAttente = new ArrayList<>();
        for(Matiere m : Main.listeMatiere()){
            listNoteEnAttente.add(new Note(0, m));
        }
        return listNoteEnAttente;
    }

    /**
     * Permet d'ajouter ou de retirer de la motivation
     * @param motivationPlus Valeur positive ou négative de motivation à ajouter
     */
    public void ajouteMotivation(Integer motivationPlus, Cagnote cagnote){
        this.motivation += motivationPlus;

        if(motivationPlus >= 0){
            System.out.println(this.pseudo + " gagne " + motivationPlus + " point de motivation(s)");
        }else{
            System.out.println(this.pseudo + " perd " + Math.abs(motivationPlus) + " point de motivation(s)");
        }

        // Si le joueur à un nombre négatif de motivation on le met à 0
        if(this.motivation > this.motivationMax()){
            int motivationEnTrop = this.motivation - this.motivationMax();
            cagnote.setPointMotivation(motivationEnTrop);

            this.motivation = this.motivationMax();
            System.out.println("Cela dépasse la motivation max, " + this.pseudo + " envoie donc " + motivationEnTrop + " point de motivation dans la cagnote");
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
