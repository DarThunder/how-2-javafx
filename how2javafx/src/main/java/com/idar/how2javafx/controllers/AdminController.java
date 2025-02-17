package com.idar.how2javafx.controllers;

import java.io.File;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Controlador para la interfaz de administrador en una aplicación JavaFX.
 */
public class AdminController {

    @FXML
    private BorderPane BP1;
    @FXML
    private VBox VB1;
    @FXML
    private StackPane SP1;
    @FXML
    private AnchorPane panel1;
    @FXML
    private AnchorPane panel2;
    @FXML
    private AnchorPane panel3;
    @FXML
    private Button JB2;
    @FXML
    private TableView<?> TV1;

    /**
     * Método de inicialización de la interfaz.
     * Se ejecuta automáticamente cuando la vista es cargada.
     */
    @FXML
    private void initialize() {
        VB1.setStyle("-fx-background-color: #34401a;");
        BP1.setStyle("-fx-background-color: #64732F;");
        SP1.setStyle("-fx-background-color: #64732F;");
        panel1.setVisible(false);
        panel2.setVisible(false);
        panel3.setVisible(false);
    }

    /**
     * Muestra el panel 1 y oculta los demás.
     */
    @FXML
    private void showPanel1() {
        panel1.setVisible(true);
        panel1.setStyle("-fx-background-color: #B4BF5E;");
        panel2.setVisible(false);
        panel3.setVisible(false);
    }

    /**
     * Muestra el panel 2 y oculta los demás.
     */
    @FXML
    private void showPanel2() {
        panel1.setVisible(false);
        panel2.setVisible(true);
        panel2.setStyle("-fx-background-color: #B4BF5E;");
        panel3.setVisible(false);
    }

    /**
     * Muestra el panel 3 y oculta los demás.
     */
    @FXML
    private void showPanel3() {
        panel1.setVisible(false);
        panel2.setVisible(false);
        panel3.setVisible(true);
        panel3.setStyle("-fx-background-color: #B4BF5E;");
    }

    /**
     * Método para modificar el nombre (sin implementación actual).
     */
    @FXML
    private void modificarNombre() {
        // Implementar funcionalidad si es necesario
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
        Stage stage = (Stage) JB2.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();
    }
}
