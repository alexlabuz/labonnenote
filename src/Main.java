import controller.Game;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("view/sample.fxml"));
        stage.setTitle("La Bonne Note");
        stage.setScene(new Scene(root,1000,750));
        stage.show();
        stage.setMinWidth(1000);
        stage.setMinHeight(750);
        stage.getIcons().add(new Image("image/lbnlogo.png"));
    }

    public static void main(String[] args) {
        launch(args);
    }
}
