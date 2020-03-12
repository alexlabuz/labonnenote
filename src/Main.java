import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("La Bonne Note (beta)");
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        // Création de la fenêtre
        primaryStage.setScene(new Scene(root, 300, 275, Color.WHITE));
        primaryStage.show();

        /*--- Pas de code ici, allez dans la classe controleur ---*/
    }
}
