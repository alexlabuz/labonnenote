package model;

public class CardCagnote extends Carte {

    public CardCagnote(String nom, String description,  String image){
        super(nom, description, image);
    }

    @Override
    public void action(Joueur joueur, Matiere matiere, Cagnote cagnote) {
        System.out.println("Vous récupérez la cagnote");
        joueur.ajouteMotivation(cagnote.getPointMotivation(), cagnote);
        cagnote.setPointMotivation(0);
    }
}
