public class CardMotivation extends Carte {

    private Integer valeurChangeMotivation;

    public CardMotivation(String nom, String description, String image, Integer valeurChangeMotivation){
        super(nom, description, image);
        this.valeurChangeMotivation = valeurChangeMotivation;
    }

    @Override
    public void action(Joueur joueur) {
        joueur.ajouteMotivation(this.valeurChangeMotivation);
    }
}