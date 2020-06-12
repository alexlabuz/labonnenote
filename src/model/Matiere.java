package model;

public class Matiere {
    private String nom;
    private Integer id;

    public Matiere(String nom, Integer id){
        this.nom = nom;
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public Integer getId() {
        return id;
    }

    @Override
    public String toString() {
        return this.nom;
    }
}
