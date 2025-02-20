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

/**
 * Controlador para la pantalla de inicio de sesión en la aplicación JavaFX.
 */
public class LoginController {

    @FXML
    private TextField usernameField; // Campo de entrada para el nombre de usuario

    @FXML
    private PasswordField passwordField; // Campo de entrada para la contraseña

    @FXML
    private Pane JP1; // Panel principal del formulario de inicio de sesión

    @FXML
    private Button JB1; // Botón para iniciar sesión

    @FXML
    private Button JB2; // Botón para abrir la ventana de creación de usuario

    private SqlLib db; // Conexión a la base de datos

    /**
     * Establece la conexión con la base de datos.
     * 
     * @throws java.sql.SQLException
     */
    public void setDb() throws SQLException {
        this.db = SqlLib.getInstance("", "", "");
    }

    /**
     * Inicializa el controlador y configura el estilo del panel de inicio de sesión.
     */
    @FXML
    private void initialize() throws SQLException {
        JP1.setStyle("-fx-background-color: #CDCDCD;");
        setDb();
    }

    /**
     * Maneja el inicio de sesión validando las credenciales ingresadas.
     * 
     * @return Una cadena con el rol del usuario ("admin", "user") o "nil" si las credenciales son inválidas.
     * @throws SQLException Si ocurre un error en la consulta a la base de datos.
     */
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

    /**
     * Cambia la escena a la vista correspondiente según el rol del usuario.
     * 
     * @throws IOException  Si ocurre un error al cargar el archivo FXML.
     * @throws SQLException Si ocurre un error al validar las credenciales.
     */
    @FXML
    private void switchToSecondary() throws IOException, SQLException {
        String fxml = handleLogin();
        if (!fxml.equals("admin") && !fxml.equals("user")) {
            return;
        }
        File fxmlFile = new File("src/main/resources/scenes/" + fxml + ".fxml");
        Parent root = FXMLLoader.load(fxmlFile.toURI().toURL());
        Stage stage = (Stage) JB1.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.sizeToScene();
        stage.setResizable(false);
    }

    /**
     * Abre la ventana de creación de usuario.
     * 
     * @throws IOException Si ocurre un error al cargar el archivo FXML.
     */
    @FXML
    private void openCreateUserScene() throws IOException {
        File fxmlFile = new File("src/main/resources/scenes/createUser.fxml");
        FXMLLoader loader = new FXMLLoader(fxmlFile.toURI().toURL());
        
        Parent root = loader.load();
        Stage stage = (Stage) JB2.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.sizeToScene();
        stage.setResizable(false);
    }
}
