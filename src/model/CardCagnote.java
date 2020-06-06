package model;

import javafx.scene.control.Label;

public class CardCagnote extends Carte {

    public CardCagnote(String nom, String description,  String image){
        super(nom, description, image);
    }

    @Override
    public void action(Joueur joueur, Matiere matiere, Cagnote cagnote, Label labelIndication) {
        joueur.ajouteMotivation(cagnote.getPointMotivation(), cagnote, labelIndication);
        cagnote.setPointMotivation(0);

        String text = "Vous récupérez la cagnote";
        if(labelIndication != null){
            labelIndication.setText(text);
        }else{
            System.out.println(text);
        }
    }
}
