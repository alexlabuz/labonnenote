package controller;

import donnee.alea;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.*;

import java.io.IOException;
import java.util.ArrayList;

import static controller.Game.listMatierePlateau;
import static controller.Game.listeCarte;
import static donnee.alea.alea;

public class Controller {
    private ArrayList<Matiere> matieres = listeMatiere();
    private ArrayList<Joueur> joueurs = new ArrayList<>();
    ArrayList<Carte> cartes = listeCarte();
    ArrayList<Matiere> plateau = listMatierePlateau(matieres);

    private TextField[] listZonePseudo = new TextField[6];
    private ComboBox[]  listBoxSpecialite = new ComboBox[6];
    private TextField[] listZoneNote = new TextField[matieres.size()];
    private Label[] listLabelNotes = new Label[matieres.size()];
    Cagnote cagnote = new Cagnote(0);

    private Integer numeroTour = 0;
    int nbJoueurFini = 0;

    @FXML private VBox vBoxLeft;
    @FXML private VBox vBoxRight;
    @FXML private VBox vBoxCenter;
    @FXML private VBox vBoxBottom;
    @FXML private VBox vBoxJeu;

    @FXML private Button btValidPseudo;
    @FXML private Button btValidNote;

    @FXML private Button btLancerDee;
    @FXML private Button btVoirMoyenne;

    @FXML private Label textPtMotivationRestant;
    @FXML private Label labelAfficheInfoJoueur;
    @FXML private Label labelGameDisplay;
    @FXML private Label labelListeJoueur;
    @FXML private Label labelIndicationJeu;

    public Controller(){
        Timeline timeline = new Timeline(new KeyFrame(
                Duration.millis(1),
                ae -> init()));
        timeline.play();
    }

    private void init(){
        afficheZonePseudo();
        afficheZoneNoteAttente();
        this.vBoxRight.setVisible(false);
    }

    private void affichageGame(){
        Joueur joueurActuel = this.joueurs.get(numeroJoueurTour());

        this.labelAfficheInfoJoueur.setText("Au tour de " + joueurActuel.getPseudo() + "\n[Case : " + joueurActuel.getPositionCasePlateau() + "/31] " + "[Age : " + joueurActuel.getAge() + "]");
        this.labelGameDisplay.setText("Que voulez-vous faire " + joueurActuel.getPseudo());
        this.textPtMotivationRestant.setText(joueurActuel.getMotivation() + "/" + joueurActuel.motivationMax());

        Integer i = 0;
        for(Note n : joueurActuel.getNoteEnAttente()){
            this.listLabelNotes[i].setText(n.getNoteSur20() + "/20");
            i++;
        }
    }

    @FXML
    private void lancerDee(ActionEvent actionEvent){
        Joueur j = this.joueurs.get(numeroJoueurTour());
        enableButtonJeu(false);
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        this.vBoxJeu.getChildren().add(vBox);

        Label label = new Label("Vous jetez le dée...");
        vBox.getChildren().add(label);

        PauseTransition wait = new PauseTransition(Duration.seconds(2));
        wait.setOnFinished((e) -> {
            Integer deeNumero = alea.alea(1,6);
            j.avanceCase(deeNumero);

            if(j.getPositionCasePlateau() <= plateau.size()) {
                label.setText("Vous obtenez un " + deeNumero +"\n" +
                    "Vous arrivez sur la case " + j.getPositionCasePlateau() + " : " + plateau.get(j.getPositionCasePlateau()-1).getNom());

                // Tire une carte
                Button btCarte = new Button("Tirer une carte");
                vBox.getChildren().add(btCarte);

                btCarte.setOnMouseClicked(mouseEvent -> {
                    btCarte.setDisable(true);
                    label.setText("Vous piochez une carte...");

                    PauseTransition wait2 = new PauseTransition(Duration.seconds(2));
                    wait2.setOnFinished((e2) -> {
                        Integer numAleaCarte = alea(0, listeCarte().size() - 1);
                        Carte cartePioche = cartes.get(numAleaCarte);


                        label.setText(j.getPseudo() + " : " + cartePioche.getNom() + " - " + cartePioche.getDescription());
                        cartePioche.action(j, plateau.get(j.getPositionCasePlateau() - 1), cagnote);
                        joueurSuivant(vBox);
                    });
                    wait2.play();
                });

            }else{
                this.nbJoueurFini++;
                label.setText("Vous obtenez un " + deeNumero);
                Label label2 = new Label(j.getPseudo() + " à terminé la partie !");
                vBox.getChildren().add(label2);
                joueurSuivant(vBox);
            }
        });
        wait.play();
    }

    private void joueurSuivant(VBox vBox){
        Button btNext = new Button("Joueur suivant");
        vBox.getChildren().add(btNext);
        btNext.setOnMouseClicked(mouseEvent -> {
            this.vBoxJeu.getChildren().remove(vBox);
            this.numeroTour++;
            affichageGame();
            afficheStatJoueur();
            enableButtonJeu(true);
        });

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
            comboBox.getSelectionModel().select(0);
            comboBox.setPromptText("Spécialité");

            vBox.setPadding(new Insets(4));
            vBox.getChildren().addAll(textField, comboBox);
            this.vBoxLeft.getChildren().add(vBox);
            this.listZonePseudo[i] = textField;
            this.listBoxSpecialite[i] = comboBox;
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

        this.vBoxJeu.setVisible(true);
        // Lancemant de la partie
        affichageGame();
    }

    public void afficheZoneNoteAttente(){
        int i = 0;
        for(Matiere m : this.matieres) {
            VBox vBox = new VBox();

            HBox hBox = new HBox();
            Label label = new Label(m.getNom() + " : ");
            Label label1 = new Label(" / 20");
            hBox.getChildren().addAll(label, label1);

            TextField textField = new TextField();
            textField.setOnKeyReleased(keyEvent -> verifNotesEnAttente());
            textField.setText("0");
            this.listZoneNote[i] = textField;
            this.listLabelNotes[i] = label1;

            vBox.setPadding(new Insets(4));
            vBox.getChildren().addAll(hBox, textField);
            this.vBoxRight.getChildren().add(vBox);
            i++;
        }

        this.btValidNote.setOnMouseClicked(mouseEvent -> validNotesEnAttente(joueurs.get(numeroJoueurTour())));
    }

    private void verifNotesEnAttente(){
        Boolean disableButton = false;
        try{
            Integer motivationUtilise = 0;
            Joueur j = this.joueurs.get(numeroJoueurTour());

            Integer i = 0;
            for(TextField tf : this.listZoneNote){
                if(Integer.parseInt(tf.getText()) > (20 - j.getNoteEnAttente().get(i).getNoteSur20())){
                    disableButton = true;
                    System.out.println("Erreur de note");
                }
                motivationUtilise += Integer.parseInt(tf.getText());
                i++;
            }

            Integer motivationRestante = j.getMotivation() - motivationUtilise;
            this.textPtMotivationRestant.setText(motivationRestante + "/" + j.motivationMax());

            if(motivationRestante < 0){
                System.out.println("Erreur de motivation");
                disableButton = true;
            }
        }
        catch (Exception e){
            System.out.println("Erreur syntaxe");
            disableButton = true;
        }

        this.btValidNote.setDisable(disableButton);
    }

    private void validNotesEnAttente(Joueur joueur) {
            Integer motivationDepense = 0;

            Integer i  = 0;
            for(Note n : joueur.getNoteEnAttente()){
                Integer noteSaisie = Integer.parseInt(this.listZoneNote[i].getText());
                n.setNoteSur20(n.getNoteSur20() + noteSaisie);

                motivationDepense += noteSaisie;
                i++;
            }

            joueur.setMotivation(joueur.getMotivation() - motivationDepense);

            for(TextField tf : this.listZoneNote){
                tf.setText("0");
            }
            this.btValidNote.setDisable(true);
            affichageGame();
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

        this.labelListeJoueur.setText(text);
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

    private void enableButtonJeu(Boolean enabled){
        if(enabled){
            for(TextField tf : this.listZoneNote){
                tf.setDisable(false);
            }
            this.btValidNote.setDisable(false);
            this.btLancerDee.setDisable(false);
            this.btVoirMoyenne.setDisable(false);
        }else{
            for(TextField tf : this.listZoneNote){
                tf.setDisable(true);
            }
            this.btValidNote.setDisable(true);
            this.btLancerDee.setDisable(true);
            this.btVoirMoyenne.setDisable(true);
        }
    }

    private Integer numeroJoueurTour(){
        return this.numeroTour % this.joueurs.size();
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
