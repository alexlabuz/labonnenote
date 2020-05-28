package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import model.Joueur;
import model.Matiere;

import java.io.IOException;
import java.util.ArrayList;

public class Controller {
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
    @FXML private Label listeJoueurText;

    private ArrayList<Matiere> matieres = listeMatiere();
    private ArrayList<Joueur> joueurs = new ArrayList<>();

    public Controller() throws InterruptedException {



    }

    private void afficheStatJoueur(){
        String text = "-- Stats joueurs --";
        for(Joueur j : this.joueurs){
            text += "\n - " + j.getPseudo() +  " | ";
            if(j.getPositionCasePlateau() > 31){
                text += "Partie terminé";
            }else{
                text += "Case : " + j.getPositionCasePlateau() +"/31";
            }
        }

        this.listeJoueurText.setText(text);

    }

    public void validPseudo(ActionEvent actionEvent) {
        try{
            TextField[] inputPseudo = new TextField[]{pseudo1, pseudo2, pseudo3, pseudo4, pseudo5,pseudo6};
            TextField[] inputMatiere = new TextField[]{matiereUser1, matiereUser2, matiereUser3, matiereUser4, matiereUser5, matiereUser6};
            this.joueurs = new ArrayList<>();

            for(int i = 0; i < inputPseudo.length; i++){
                if(inputPseudo[i].getText().length() > 0){
                    assert false;
                    this.joueurs.add(new Joueur(inputPseudo[i].getText(), this.matieres.get(Integer.parseInt(inputMatiere[i].getText())-1)));
                }
            }

            for(TextField t : inputPseudo){
                t.setDisable(true);
            }
            for(TextField t : inputMatiere){
                t.setDisable(true);
            }

            this.buttonValidPseudo.setDisable(true);
            afficheStatJoueur();
        }
        catch (Exception ignored){}
    }

    private static void modifierNotesEnAttente(Joueur joueur) throws IOException {
        Parent root = FXMLLoader.load(Controller.class.getResource("../view/..."));
        Stage stage = new Stage();
        stage.setTitle("Note de " + joueur.getPseudo());
        stage.setScene(new Scene(root, 250, 320));
        stage.setResizable(false);
        stage.show();
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

    public void helpWindow(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../view/help.fxml"));
        Stage stage = new Stage();
        stage.setTitle("En savoir plus de La Bonne Note");
        stage.setScene(new Scene(root, 250, 320));
        stage.setResizable(false);
        stage.show();
    }

    public void exitProgram(ActionEvent actionEvent) {

    }
}
