/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.idar.how2javafx.controllers;

import java.util.UUID;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import lib.SqlLib;

public class CreateUserController {

    @FXML
    private Button BAtras;
    @FXML
    private ComboBox CB1;
    @FXML
    private TextField username;

    @FXML
    private PasswordField passwd;

    @FXML
    private Button createBtn;

    private SqlLib db;

    public void setDB() throws SQLException {
        this.db = db.getInstance("", "", "");
    }

    @FXML
    private void initialize() throws SQLException {
        setDB();
    }

    @FXML
    public void create() throws IOException {
        String newUsername = username.getText().trim();
        String newPassword = passwd.getText().trim();
        
        if(newUsername.isEmpty() || newPassword.isEmpty()){
            JOptionPane.showMessageDialog(null, "Por favor, llena todos los campos");
            return;
        }

        if (db.createUser((int) UUID.randomUUID().getLeastSignificantBits(), "user", newUsername, newPassword)) {
            System.out.println("Si se pudo w");
            JOptionPane.showMessageDialog(null, "Usuario creado exitosamente");
        } else {
            System.out.println("No se pudo w");
        }
    }
    
    

    /**
     * Cambia la escena actual a la pantalla de inicio de sesión.
     *
     * @throws IOException si ocurre un error al cargar el archivo FXML.
     */
    @FXML
    private void switchToLogin() throws IOException {
        File fxmlFile = new File("src/main/resources/scenes/login.fxml");
        FXMLLoader loader = new FXMLLoader(fxmlFile.toURI().toURL());
        Parent root = loader.load();
        Stage stage = (Stage) BAtras.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();
    }
}
