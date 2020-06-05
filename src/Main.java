import controller.Game;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("view/sample.fxml"));
        stage.setTitle("La Bonne Note");
        stage.setScene(new Scene(root,900,650));
        stage.show();
        stage.setMinWidth(900);
        stage.setMinHeight(650);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
