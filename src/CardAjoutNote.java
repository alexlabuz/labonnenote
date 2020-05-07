public class CardAjoutNote extends Carte {

    public CardAjoutNote(String nom, String description, String image){
        super(nom, description, image);
    }

    public void action(Joueur joueur){
        joueur.ajouteNote();
    }
}
