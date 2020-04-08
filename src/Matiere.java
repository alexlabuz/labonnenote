public class Matiere {
    private String nom;
    private int coef;

    public Matiere(String nom){
        this.nom = nom;
        this.coef = 1;
    }

    public int getCoef() {
        return coef;
    }

    public void setCoef(int coef) {
        this.coef = coef;
    }

    public String getNom() {
        return nom;
    }
}
