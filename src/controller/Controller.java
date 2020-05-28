package controller;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import model.Joueur;
import model.Matiere;

import java.util.ArrayList;

public class Controller extends Application {
    @FXML private TextField pseudo1;
    @FXML private TextField pseudo2;
    @FXML private TextField pseudo3;
    @FXML private TextField pseudo4;
    @FXML private TextField pseudo5;
    @FXML private TextField pseudo6;

    @FXML private TextField matiereUser1;
    @FXML private TextField matiereUser2;
    @FXML private TextField matiereUser3;
    @FXML private TextField matiereUser4;
    @FXML private TextField matiereUser5;
    @FXML private TextField matiereUser6;

    @FXML private Button buttonValidPseudo;

    private ArrayList<Matiere> matieres = listeMatiere();
    private ArrayList<Joueur> joueurs;

    @Override
    public void start(Stage stage) throws Exception {

    }

    public Controller() throws InterruptedException {
        ArrayList<Matiere> matieres = listeMatiere();



    }


    public void validPseudo(ActionEvent actionEvent) {

    }

    public void verifPseudo(KeyEvent actionEvent) {
        TextField[] listeInput = new TextField[]{this.pseudo1, this.pseudo2, this.pseudo3, this.pseudo4, this.pseudo5, this.pseudo6};
        Integer nbPseudo = 0;
        for(TextField tf : listeInput){
            if(tf.getText().length() > 0){
                nbPseudo++;
            }
        }
        this.buttonValidPseudo.setDisable(!(nbPseudo >= 2));
    }



    /* <<< --- CONTENU DU JEU --- >>> */
    public static ArrayList<Matiere> listeMatiere(){
        ArrayList<Matiere> matiere = new ArrayList<>();
        matiere.add(new Matiere("Anglais", 0)); // 0
        matiere.add(new Matiere("Mathématique", 1)); // 1
        matiere.add(new Matiere("Culture générale", 2 )); // 2
        matiere.add(new Matiere("Eco Droit Management", 3)); // 3
        matiere.add(new Matiere("Développement Web (SI6)", 4)); // 4
        matiere.add(new Matiere("Android (SLAM 2)", 5)); // 5
        matiere.add(new Matiere("Base de données (SLAM 1)", 6)); // 6
        matiere.add(new Matiere("Serveur Web (SI5)", 7)); // 7
        return matiere;
    }
}
