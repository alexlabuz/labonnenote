package model;

import javafx.scene.control.Label;

public class CardMotivation extends Carte {

    private Integer valeurChangeMotivation;

    public CardMotivation(String nom, String description, String image, Integer valeurChangeMotivation){
        super(nom, description, image);
        this.valeurChangeMotivation = valeurChangeMotivation;
    }

    @Override
    public void action(Joueur joueur, Matiere matiere, Cagnote cagnote, Label labelIndication) {
        joueur.ajouteMotivation(this.valeurChangeMotivation, cagnote, labelIndication);
    }
}