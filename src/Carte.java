abstract class Carte {

    private String nom;
    private String description;
    private String image;

    protected Carte(String nom, String description, String image) {
        this.nom = nom;
        this.description = description;
        this.image = image;
    }

    /**
     * Effectue l'action d'une type de carte
     * @param joueur Joueur qui à tirer la carte
     * @param matiere La case matière ou il ce situe quand il tire la carte
     * @param cagnote La cagnote du jeux
     */
    abstract void action(Joueur joueur, Matiere matiere, Cagnote cagnote);

    public String getNom() {
        return nom;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }
}
