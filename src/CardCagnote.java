public class CardCagnote extends Carte {

    public CardCagnote(String nom, String description,  String image){
        super(nom, description, image);
    }

    @Override
    void action(Joueur joueur) {
        System.out.println("Vous récupérez la cagnote");
    }
}