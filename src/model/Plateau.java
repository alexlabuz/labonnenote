package model;

import java.util.ArrayList;

public class Plateau {

    private ArrayList<Matiere> listMatiereCase;

    public Plateau(ArrayList<Matiere> listMatiereCase){
        this.listMatiereCase = listMatiereCase;
    }

    public ArrayList<Matiere> getListMatiereCase() {
        return listMatiereCase;
    }
}
