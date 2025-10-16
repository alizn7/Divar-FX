package org.example.finaldivar;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class DivarApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(DivarApplication.class.getResource("/login.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 450, 500);
            stage.setTitle("دیوار - ورود به سیستم");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error loading login.fxml: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
