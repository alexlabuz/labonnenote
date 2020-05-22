package model;

public class CardAjoutNote extends Carte {
    private Boolean travailNonFait;

    public CardAjoutNote(String nom, String description, String image, Boolean travailNonFait){
        super(nom, description, image);
        this.travailNonFait = travailNonFait;
    }

    @Override
    public void action(Joueur joueur, Matiere matiere, Cagnote cagnote) {
        joueur.ajouteNote(matiere, this.travailNonFait);
    }

}
