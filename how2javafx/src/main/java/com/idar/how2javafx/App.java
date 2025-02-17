/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.idar.how2javafx;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lib.SqlLib;
import com.idar.how2javafx.controllers.LoginController;
import java.io.File;

public class App extends Application {
    private static SqlLib db;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = loadFXML("login");
        Parent root = loader.load();

        LoginController controller = loader.getController();
        controller.setDb(db);

        Scene scene = new Scene(root, 700, 500);
        stage.setScene(scene);
        stage.show();
    }

    private FXMLLoader loadFXML(String fxml) throws IOException {
        File fxmlFile = new File("src/main/resources/scenes/" + fxml + ".fxml");
        return new FXMLLoader(fxmlFile.toURI().toURL());
    }

    public static void main(String[] args) {
        launch();
    }
}
