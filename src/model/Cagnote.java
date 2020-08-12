package model;

public class Cagnote {

    private Integer pointMotivation;

    public Cagnote(Integer pointMotivation) {
        this.pointMotivation = pointMotivation;
    }

    public void ajoutPointMotivation(Integer point){
        this.pointMotivation += point;
    }

    public Integer getPointMotivation() {
        return pointMotivation;
    }

    public void setPointMotivation(Integer pointMotivation) {
        this.pointMotivation = pointMotivation;
    }
}
