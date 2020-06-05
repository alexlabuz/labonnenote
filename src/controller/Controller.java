package controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Joueur;
import model.Matiere;

import java.io.IOException;
import java.util.ArrayList;

public class Controller {
    private ArrayList<Matiere> matieres = listeMatiere();
    private ArrayList<Joueur> joueurs = new ArrayList<>();

    private TextField[] listZonePseudo = new TextField[6];
    private ComboBox[]  listBoxSpecialite = new ComboBox[6];
    private TextField[] listZoneNote = new TextField[matieres.size()];

    @FXML private VBox vBoxLeft;
    @FXML private VBox vBoxRight;

    @FXML private Button btValidPseudo;
    @FXML private Button btValidNote;

    @FXML private Label listeJoueurText;
    @FXML private Label textPtMotivation;

    public Controller(){
        Timeline timeline = new Timeline(new KeyFrame(
                Duration.millis(1),
                ae -> init()));
        timeline.play();
    }

    private void init(){
        afficheZonePseudo();
        afficheZoneNoteMatiere();
        this.vBoxRight.setVisible(false);

    }

    public void afficheZonePseudo() {
        ObservableList<Matiere> list = FXCollections.observableArrayList(this.matieres);

        for(int i = 0; i < 6; i++){
            VBox vBox = new VBox();
            ComboBox<Matiere> comboBox = new ComboBox<>();

            TextField textField = new TextField();
            textField.setPromptText("Pseudo " + (i+1));
            textField.setOnKeyReleased(keyEvent -> verifPseudo());

            comboBox.setItems(list);
            comboBox.setPromptText("Spécialité");

            vBox.setPadding(new Insets(4));
            vBox.getChildren().addAll(textField, comboBox);
            this.vBoxLeft.getChildren().add(vBox);
            this.listZonePseudo[i] = textField;
            this.listBoxSpecialite[i] = comboBox;
        }
    }

    public void afficheZoneNoteMatiere(){
        int i = 0;
        for(Matiere m : this.matieres) {
            VBox vBox = new VBox();

            HBox hBox = new HBox();
            Label label = new Label(m.getNom() + " : ");
            Label label1 = new Label(" / 20");
            hBox.getChildren().addAll(label, label1);

            TextField textField = new TextField();
            this.listZoneNote[i] = textField;

            vBox.setPadding(new Insets(4));
            vBox.getChildren().addAll(hBox, textField);
            this.vBoxRight.getChildren().add(vBox);
            i++;
        }
    }

    private void verifPseudo(){
        int compteur = 0;
        for(TextField tf : this.listZonePseudo){
            if(tf.getText().length() > 2){
                compteur++;
            }
        }

        this.btValidPseudo.setDisable(!(compteur >= 2));
    }

    public void validPseudo(ActionEvent actionEvent) {
        this.joueurs.clear();
        int i = 0;
        for(TextField tf : this.listZonePseudo){
            if(tf.getText().length() >= 2){
                Matiere specialite = (Matiere) this.listBoxSpecialite[i].getSelectionModel().getSelectedItem();
                this.joueurs.add(new Joueur(tf.getText(), specialite));
            }
            i++;
        }

        this.vBoxRight.setVisible(true);
        disabledZonePseudo();
        afficheStatJoueur();

        for(Joueur j : joueurs){

        }

    }

    private void modifierNotesEnAttente(Joueur joueur) {

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
            text+= j.getSpecialite().getNom();
        }

        this.listeJoueurText.setText(text);
    }

    private void disabledZonePseudo(){
        for(TextField tf : this.listZonePseudo){
            tf.setDisable(true);
        }
        for(ComboBox cb : this.listBoxSpecialite){
            cb.setDisable(true);
        }
        this.btValidPseudo.setDisable(true);
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
