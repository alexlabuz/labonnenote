public class Carte {

    private String nom;
    private String description;
    private Integer rarete;
    private Integer pointPlusMoin;
    private String image;
    private Boolean anniversaire;
    private Boolean ajouteNote;

    public Carte(String nom, String description, Integer rarete, Integer pointPlusMoin, Boolean anniversaire, Boolean ajouteNote) {
        this.nom = nom;
        this.description = description;
        this.rarete = rarete;
        this.pointPlusMoin = pointPlusMoin;
        this.anniversaire = anniversaire;
        this.ajouteNote = ajouteNote;
    }
}
