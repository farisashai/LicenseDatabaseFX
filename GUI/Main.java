package projDRIVERSLICENSE.GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }
    @Override public void start(Stage primaryStage) throws IOException {
        primaryStage.setTitle("Drivers License Database");
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("Page.fxml"))));
        primaryStage.show();
    }
}
