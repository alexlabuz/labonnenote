import java.util.ArrayList;

public class Joueur {

    private String pseudo;
    private Matiere specialite;
    private Integer age;
    private ArrayList<Note> noteEnAttente;
    private ArrayList<Note> listNote;
    private Integer casePlateau;
    private Integer motivation;
    private Integer motivationMax;

    public Joueur(String pseudo, Matiere specialite){
        this.pseudo = pseudo;
        this.specialite = specialite;
        this.age = 18;
        this.noteEnAttente = null;
        this.listNote = null;
        this.casePlateau = 0;

        this.motivationMax = verifMotivationMax(age);
        this.motivation = this.motivationMax;
    }

    private Integer verifMotivationMax(Integer age){
        return 90 + (5 * age - 18);
    }

    public void ajouteNote(Matiere matiere){
        // Calcul le coefficient
        int coeficient = 1;
        if(matiere == this.specialite){
            coeficient = 3;
        }

        // Récupére la note de l'utilisateur et la remet à 0 dans "noteEnAttente"
        for(Note n : this.noteEnAttente){
            if(matiere == n.getMatiere()){
                this.listNote.add(new Note(n.getNoteSur20(), matiere));
                n.setNoteSur20(0);
                System.out.println(this.pseudo + " à reçu un " + n.getNoteSur20() + " sur 20...");
                break;
            }
        }
    }

    public Double calculMoyenne(){
        Double totalSomme = null; // Somme des notes (avec coeficients)
        Double effectif = null; // Nombre de note (en comptent les coeficients)

        for(Note n : this.listNote){
            Integer coef = 1;
            if(this.specialite == n.getMatiere()){
                coef = 3;
            }

            totalSomme = totalSomme + n.getNoteSur20() * coef;
            effectif = effectif + coef;
        }

        return totalSomme / effectif;
    }

    public void afficheListNote(){
        System.out.println("--- Liste de note de " + this.pseudo + " ---");

        for(Note n : this.listNote){
            System.out.println(" - " + n.getMatiere().getNom() + " : " + n.getNoteSur20() + "sur 20");
        }

        System.out.println("Moyenne : " + calculMoyenne() + " sur 20");
    }

    @Override
    public String toString() {
        return "Joueur{" +
                "pseudo='" + pseudo + '\'' +
                ", specialite=" + specialite.getNom() +
                ", age=" + age +
                ", noteEnAttente=" + noteEnAttente +
                ", listNote=" + listNote +
                ", casePlateau=" + casePlateau +
                ", motivation=" + motivation +
                ", motivationMax=" + motivationMax +
                '}';
    }
}
