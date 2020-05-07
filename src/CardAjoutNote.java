public class CardAjoutNote extends Carte {
    private Boolean travailNonFait;

    public CardAjoutNote(String nom, String description, String image, Boolean travailNonFait){
        super(nom, description, image);
        this.travailNonFait = travailNonFait;
    }

    @Override
    void action(Joueur joueur, Matiere matiere) {
        joueur.ajouteNote(matiere, travailNonFait);
    }

}
