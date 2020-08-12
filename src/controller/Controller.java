package controller;

import donnee.alea;
import javafx.animation.*;
import javafx.application.Platform;
import javafx.collections.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.*;

import java.io.IOException;
import java.util.ArrayList;

import static controller.Game.listMatierePlateau;
import static controller.Game.listeCarte;
import static donnee.alea.alea;

public class Controller {
    private ArrayList<Matiere> matieres = Game.listeMatiere();
    private ArrayList<Joueur> joueurs = new ArrayList<>();
    private ArrayList<Carte> cartes = listeCarte();
    private ArrayList<Matiere> plateau = listMatierePlateau(matieres);

    private TextField[] listZonePseudo = new TextField[6];
    private ComboBox[]  listBoxSpecialite = new ComboBox[6];
    private TextField[] listZoneNote = new TextField[matieres.size()];
    private Label[] listLabelNotes = new Label[matieres.size()];
    private Cagnote cagnote = new Cagnote(0);

    private Integer numeroTour = 0;
    private int nbJoueurFini = 0;

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
        PauseTransition load = new PauseTransition(Duration.seconds(0.001));
        load.setOnFinished((e) -> init());
        load.play();
    }

    private void init(){
        afficheZonePseudo();
        afficheZoneNoteAttente();
        this.vBoxRight.setVisible(false);
    }

    private void affichageGame(){
        Joueur joueurActuel = this.joueurs.get(numeroJoueurTour());

        this.labelAfficheInfoJoueur.setText("Tour n°" + (this.numeroTour+1) + " : Au tour de " + joueurActuel.getPseudo() + "\n" +
                "Case : " + joueurActuel.getPositionCasePlateau() + "/31 | " + joueurActuel.getAge() + " ans | Cagnotte : " + cagnote.getPointMotivation() + "");
        this.labelGameDisplay.setText("Que voulez-vous faire " + joueurActuel.getPseudo());
        this.textPtMotivationRestant.setText(joueurActuel.getMotivation() + "/" + joueurActuel.motivationMax());

        Integer i = 0;
        for(Note n : joueurActuel.getNoteEnAttente()){
            this.listLabelNotes[i].setText(n.getNoteSur20() + "/20");
            i++;
        }

        afficheStatJoueur();
    }

    @FXML
    private void lancerDee(ActionEvent actionEvent){
        Joueur j = this.joueurs.get(numeroJoueurTour());
        enableButtonJeu(false);

        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        this.vBoxJeu.getChildren().add(vBox);

        Label label = new Label("Vous jetez le dé...");
        label.setFont(new Font(16.0));
        label.setPadding(new Insets(5));
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
                        cartePioche.action(j, plateau.get(j.getPositionCasePlateau() - 1), cagnote, this.labelIndicationJeu);
                        affichageGame();
                        joueurSuivant(vBox);
                    });
                    wait2.play();
                });

            }else{
                this.nbJoueurFini++;
                label.setText("Vous obtenez un " + deeNumero + "\n" +
                        j.getPseudo() + " à terminé la partie !");
                joueurSuivant(vBox);
            }
        });
        wait.play();
    }

    private void joueurSuivant(VBox vBox){
        Button btNext = new Button("Joueur suivant");
        vBox.getChildren().add(btNext);
        btNext.setOnMouseClicked(mouseEvent -> {
            for(TextField tf : this.listZoneNote){tf.setText("0");}
            labelIndicationJeu.setText("");
            this.vBoxJeu.getChildren().remove(vBox);

            if(this.nbJoueurFini > this.joueurs.size()-1){
                finPartie();
            }else{
                this.numeroTour++;
                while (this.joueurs.get(numeroJoueurTour()).getPositionCasePlateau() > plateau.size()){
                    this.numeroTour++;
                }
                affichageGame();
                enableButtonJeu(true);
            }
        });
    }

    private void finPartie(){
        this.labelAfficheInfoJoueur.setText("LE JEU EST TERMINE !");
        vBoxJeu.setVisible(false);
        Button btFin = new Button("Accéder au résultat");
        vBoxCenter.getChildren().add(btFin);

        btFin.setOnMouseClicked(mouseEvent -> {
            vBoxCenter.getChildren().remove(btFin);
            this.labelAfficheInfoJoueur.setText("LES RESULTATS!");

            ArrayList<Joueur> joueurOrdreMoyenne = Game.triJoueurParMoyenne(joueurs);

            String text = "";
            Integer i = 1;
            for(Joueur j : joueurOrdreMoyenne){
                text += i + ". " + j.calculMoyenne() + " sur 20 - " + j.getPseudo() + "\n";
                i++;
            }

            Label classement = new Label();
            classement.setFont(new Font(20));
            classement.setText(text);

            Label phraseFinal = new Label("\nFélicitation " + joueurOrdreMoyenne.get(0).getPseudo() + " !\nVous êtes le vainqueur de La Bonne Note !");
            phraseFinal.setFont(new Font(22));

            vBoxCenter.getChildren().addAll(classement, phraseFinal);
        });
    }

    public void afficheMoyenne(ActionEvent actionEvent) {
        Joueur j = joueurs.get(numeroJoueurTour());
        Group root = new Group();
        Stage stage = new Stage();
        stage.setTitle("Moyenne de " + j.getPseudo());
        stage.setScene(new Scene(root, 350, 420));
        stage.setResizable(false);
        stage.getIcons().add(new Image("image/lbnlogo.png"));
        stage.show();

        Label labelListeNotes = new Label(j.afficheListNote());
        labelListeNotes.setPadding(new Insets(4));
        labelListeNotes.setFont(new Font(18));
        root.getChildren().add(labelListeNotes);
    }

    public void afficheZonePseudo() {
        ObservableList<Matiere> list = FXCollections.observableArrayList(this.matieres);

        for(int i = 0; i < 6; i++){
            VBox vBox = new VBox();
            ComboBox<Matiere> comboBox = new ComboBox<>();

            TextField textField = new TextField();
            textField.setPromptText("Pseudo " + (i+1));
            textField.setOnKeyTyped(keyEvent -> verifPseudo());

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
            if(tf.getText().length() > 0){
                compteur++;
            }
        }
        this.btValidPseudo.setDisable(!(compteur >= 2));
    }

    public void validPseudo(ActionEvent actionEvent) {
        this.joueurs.clear();
        int i = 0;
        for(TextField tf : this.listZonePseudo){
            if(tf.getText().length() > 0){
                Matiere specialite = (Matiere) this.listBoxSpecialite[i].getSelectionModel().getSelectedItem();
                this.joueurs.add(new Joueur(tf.getText(), specialite));
            }
            i++;
        }

        this.vBoxRight.setVisible(true);
        enabledZonePseudo(false);

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
            textField.setOnKeyTyped(keyEvent -> verifNotesEnAttente());
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
        boolean disableButton = false;
        try{
            Integer motivationUtilise = 0;
            Joueur j = this.joueurs.get(numeroJoueurTour());

            int i = 0;
            for(TextField tf : this.listZoneNote){
                if(Integer.parseInt(tf.getText()) > (20 - j.getNoteEnAttente().get(i).getNoteSur20()) || Integer.parseInt(tf.getText()) < 0){
                    disableButton = true;
                }
                motivationUtilise += Integer.parseInt(tf.getText());
                i++;
            }

            int motivationRestante = j.getMotivation() - motivationUtilise;
            this.textPtMotivationRestant.setText(motivationRestante + "/" + j.motivationMax());

            if(motivationRestante < 0){
                this.textPtMotivationRestant.setTextFill(Color.RED);
                disableButton = true;
            }else{
                this.textPtMotivationRestant.setTextFill(Color.rgb(233,233,233));
            }
        }
        catch (Exception e){
            disableButton = true;
        }

        this.btValidNote.setDisable(disableButton);
    }

    private void validNotesEnAttente(Joueur joueur) {
        this.textPtMotivationRestant.setTextFill(Color.rgb(233,233,233));
        int i  = 0;
        for(Note n : joueur.getNoteEnAttente()){
            Integer noteSaisie = Integer.parseInt(this.listZoneNote[i].getText());
            n.setNoteSur20(n.getNoteSur20() + noteSaisie);

            joueur.setMotivation(joueur.getMotivation() - noteSaisie);
            i++;
        }

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
                text += "[Partie terminé] ";
            }else{
                text += "[Case : " + j.getPositionCasePlateau() +"/31] ";
            }
            text += "[Motivation : " + j.getMotivation() + "/" + j.motivationMax()+"]";
        }

        this.labelListeJoueur.setText(text);
    }

    private void enabledZonePseudo(Boolean enabled){
        for(TextField tf : this.listZonePseudo){
            tf.setDisable(!enabled);
        }
        for(ComboBox cb : this.listBoxSpecialite){
            cb.setDisable(!enabled);
        }
        this.btValidPseudo.setDisable(!enabled);
    }

    private void enableButtonJeu(Boolean enabled){
        for(TextField tf : this.listZoneNote){
            tf.setDisable(!enabled);
        }
        this.btValidNote.setDisable(!enabled);
        this.btLancerDee.setDisable(!enabled);
        this.btVoirMoyenne.setDisable(!enabled);
    }

    private Integer numeroJoueurTour(){
        return this.numeroTour % this.joueurs.size();
    }

    @FXML
    private void resetGame(ActionEvent actionEvent){
        this.joueurs.clear();
        this.numeroTour = 0;
        this.nbJoueurFini = 0;

        this.vBoxRight.setVisible(false);
        enabledZonePseudo(true);
        enableButtonJeu(true);
        this.vBoxJeu.setVisible(false);
        this.labelListeJoueur.setText("");
        this.labelAfficheInfoJoueur.setText("Bienvenue sur La Bonne Note");
    }

    @FXML
    public void helpWindow(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../view/help.fxml"));
        Stage stage = new Stage();
        stage.setTitle("En savoir plus de La Bonne Note");
        stage.setScene(new Scene(root,350,380));
        stage.setResizable(false);
        stage.getIcons().add(new Image("image/lbnlogo.png"));
        stage.show();
    }

    public void exitProgram(ActionEvent actionEvent) {
        Platform.exit();
    }

}
