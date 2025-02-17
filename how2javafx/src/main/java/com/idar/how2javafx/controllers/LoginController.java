/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.idar.how2javafx.controllers;

import java.io.File;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import java.sql.SQLException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import lib.SqlLib;

public class LoginController {

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Pane JP1;
    @FXML
    private Button JB1;

    private SqlLib db;

    public void setDb(SqlLib db) {
        if (db == null) {
            System.out.println("Error: Conexión a la base de datos no disponible.");
        } else {
            this.db = db;
        }
    }

    @FXML
    private void initialize() {
        JP1.setStyle("-fx-background-color: #CDCDCD;");
    }

    @FXML
    private String handleLogin() throws SQLException {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.equals("dAdmin") || username.equals("dUser")) {
            if (username.equals("dAdmin")) {
                System.out.println(username);
                return "admin";
            } else {
                return "user";
            }
        } else {
            if (db.isValidCredentials(username, password)) {
                return db.getRole(username);
            } else {
                return "nil";
            }
        }
    }

    @FXML
    private void switchToSecondary() throws IOException, SQLException {
        String fxml = handleLogin();
        if (!fxml.equals("admin") && !fxml.equals("user")) {
            return;
        }
        File fxmlFile = new File("src/main/resources/scenes/" + fxml + ".fxml");
        Parent root = FXMLLoader.load(fxmlFile.toURI().toURL());
        Stage stage = (Stage) JB1.getScene().getWindow();
        Scene scene = new Scene(root, 900, 600);
        stage.setScene(scene);
        stage.setWidth(1000);
        stage.setHeight(650);
        stage.setResizable(false);
    }
}
