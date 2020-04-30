public class CardMotivation extends Carte {

    private Integer changeMotivation;

    public CardMotivation(String nom, String description, Integer rarete, String image, Integer changeMotivation){
        super(nom, description, rarete, image);
        this.changeMotivation = changeMotivation;
    }

    @Override
    public void action(Joueur joueur) {
        joueur.setMotivation(changeMotivation);
    }
}