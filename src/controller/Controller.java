package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller {

    @FXML private Button btDemmare;
    @FXML private BorderPane base;

    public Controller() throws InterruptedException, IOException {

        SaisiePseudo saisiePseudo = new SaisiePseudo();

        System.out.println("d");

    }

    @FXML
    public void startClic() throws IOException {

        BorderPane homePage = FXMLLoader.load(getClass().getResource("../view/game.fxml"));
        base.getChildren().setAll(homePage);

    }

}
