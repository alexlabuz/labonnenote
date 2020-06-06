package model;


import javafx.scene.control.Label;

public class CardAge extends Carte {

    public CardAge(String nom, String description,  String image){
        super(nom, description, image);
    }

    @Override
    public void action(Joueur joueur, Matiere matiere, Cagnote cagnote, Label labelIndication) {
        joueur.anniversaire();

        String text = "Joyeux anniversaire " + joueur.getPseudo() + ", vous avez maintenant " + joueur.getAge() + " ans !";
        if(labelIndication != null){
            labelIndication.setText(text);
        }else{
            System.out.println(text);
        }
    }
}
