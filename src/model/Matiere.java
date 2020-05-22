package model;

public class Matiere {
    private String nom;
    private Integer coef;
    private Integer id;

    public Matiere(String nom, Integer id){
        this.nom = nom;
        this.coef = 1;
        this.id = id;
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

    public Integer getId() {
        return id;
    }
}
