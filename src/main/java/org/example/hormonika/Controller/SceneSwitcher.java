package org.example.hormonika.Controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneSwitcher extends Application {

    private static Stage primaryStage;

    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;
        switchTo("calendar-view");   // Start på kalenderen
        stage.setTitle("Hårmoni'ka");
        stage.show();
    }

    public static void switchTo(String fxmlName) {
        try {
            FXMLLoader loader = new FXMLLoader(SceneSwitcher.class.getResource(fxmlName + ".fxml"));
            Scene scene = new Scene(loader.load());
            primaryStage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}