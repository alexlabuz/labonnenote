package controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SaisiePseudo {


    public SaisiePseudo() throws IOException {
        Stage saisiePseudo = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("../view/saisiePseudo.fxml"));
        saisiePseudo.setTitle("La Bonne Note");
        saisiePseudo.setScene(new Scene(root,300,400));
        saisiePseudo.setResizable(false);
        saisiePseudo.show();
    }

}
