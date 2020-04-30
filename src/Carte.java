abstract class Carte {

    private String nom;
    private String description;
    private Integer rarete;
    private String image;

    protected Carte(String nom, String description, Integer rarete, String image) {
        this.nom = nom;
        this.description = description;
        this.rarete = rarete;
        this.image = image;
    }

    abstract void action(Joueur joueur);

    public String getNom() {
        return nom;
    }

    public String getDescription() {
        return description;
    }

    public Integer getRarete() {
        return rarete;
    }

    public String getImage() {
        return image;
    }
}
