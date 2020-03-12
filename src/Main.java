import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        System.out.print("LA BONNE NOTE");
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("La Bonne Note (beta)");
        Group root = new Group();
        // Création de la fenêtre
        primaryStage.setScene(new Scene(root, 300, 275, Color.WHITE));
        primaryStage.show();

    }
}
