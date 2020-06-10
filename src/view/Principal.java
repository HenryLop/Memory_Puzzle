/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.net.URL;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * Principal Stage from my game.
 *
 * @author Pardo
 */
public class Principal extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Memory puzzle game");

        FXMLLoader loader = new FXMLLoader();
        URL xmlUrl = getClass().getResource("/view/Principal.fxml");
        loader.setLocation(xmlUrl);
        Parent root = loader.load();

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();

        primaryStage.getIcons().add(new Image(ClassLoader.getSystemResourceAsStream("view/images/icon.png")));

    }

    public static void main(String[] args) {
        launch(args);
    }
}
