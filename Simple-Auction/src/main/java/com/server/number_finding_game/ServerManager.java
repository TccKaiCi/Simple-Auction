package com.server.number_finding_game;

import com.client.number_finding_game.LoginForm;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class ServerManager extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(LoginForm.class.getResource("Server.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 674, 420);
            primaryStage.setResizable(false);
            primaryStage.setTitle("3118410043_TangChiChung");
            scene.setFill(Color.TRANSPARENT);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
