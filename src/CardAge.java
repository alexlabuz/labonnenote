public class CardAge extends Carte {

    public CardAge(String nom, String description,  String image){
        super(nom, description, image);
    }

    @Override
    public void action(Joueur joueur) {
        joueur.anniversaire();
        System.out.println("Joyeux anniversaire " + joueur.getPseudo() + ", vous avez maintenant " + joueur.getAge() + " ans !");
    }
}
