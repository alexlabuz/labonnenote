import java.util.ArrayList;

public class Joueur {

    private String pseudo;
    private Matiere specialite;
    private Integer age;
    private ArrayList<Integer> noteEnAttente;
    private ArrayList<Integer> ListNote;
    private Integer casePlateau;
    private Integer motivation;
    private Integer motivationMax;

    public Joueur(String pseudo, Matiere specialite){
        this.pseudo = pseudo;
        this.specialite = specialite;
        this.age = 18;
        this.noteEnAttente = null;
        this.ListNote = null;
        this.casePlateau = 0;

        this.motivationMax = verifMotivationMax(age);
        this.motivation = this.motivationMax;
    }

    private Integer verifMotivationMax(Integer age){
        return 90 + (5 * age - 18);
    }

}
