public class Note {

    private Integer noteSur20;
    private Integer coefficient;
    private Matiere matiere;

    public Note(Integer noteSur20, Matiere matiere){
        this.noteSur20 = noteSur20;
        this.matiere = matiere;
    }

    public Integer getNoteSur20() {
        return noteSur20;
    }

    public void setNoteSur20(Integer noteSur20) {
        this.noteSur20 = noteSur20;
    }

    public Integer getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(Integer coefficient) {
        this.coefficient = coefficient;
    }

    public Matiere getMatiere() {
        return matiere;
    }

    public void setMatiere(Matiere matiere) {
        this.matiere = matiere;
    }
}