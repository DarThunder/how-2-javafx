package com.idar.how2javafx.controllers;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import lib.SqlLib;

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
    private TextField plantNameField;
    @FXML
    private TextField plantScientificNameField;
    @FXML
    private TextField plantFamilyField;
    @FXML
    private TextField plantFlowerinSeasonField;
    @FXML
    private TextField plantHabitatField;
    @FXML
    private TextField plantDescriptionField;

    @FXML
    private AnchorPane panel2;
    @FXML
    private TextField remove;
    
    @FXML
    private AnchorPane panel3;
    @FXML
    private Button JB2;
    @FXML
    private TableView<?> TV1;
    
    private SqlLib db;

    /**
     * Método de inicialización de la interfaz. Se ejecuta automáticamente
     * cuando la vista es cargada.
     */
    @FXML
    private void initialize() throws SQLException {
        VB1.setStyle("-fx-background-color: #34401a;");
        BP1.setStyle("-fx-background-color: #64732F;");
        SP1.setStyle("-fx-background-color: #64732F;");
        panel1.setVisible(false);
        panel2.setVisible(false);
        panel3.setVisible(false);
        
        this.db = SqlLib.getInstance("", "", "");
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

    @FXML
    private void addPlant() throws SQLException {
        String name = plantNameField.getText();
        String scientificName = plantScientificNameField.getText();
        String family = plantFamilyField.getText();
        String floweringSeason = plantFlowerinSeasonField.getText();
        String habitat = plantHabitatField.getText();
        String description = plantDescriptionField.getText();
        System.out.println(name + " " + scientificName + " " + family + " " + floweringSeason + " " + habitat + " " + description);
        
        if(db.addPlant(name, scientificName, family, floweringSeason, habitat, description)) {
            System.out.println("Se agrego planta tu");
            plantNameField.setText("");
            plantScientificNameField.setText("");
            plantFamilyField.setText("");
            plantFlowerinSeasonField.setText("");
            plantHabitatField.setText("");
            plantDescriptionField.setText("");
        } else {
            System.out.println("Ni pedo");
        }
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
    
    @FXML
    private void removePlant() {
        try {
            int plantID = Integer.parseInt(remove.getText());
            
            if (db.removePlant(plantID)) {
                System.out.println("Si se pudo w");
            } else {
                throw new Exception("Pendejo");
            }
            
        } catch (Exception e) {
            System.out.println("No se pudo papa ff");
            System.out.println(e.getMessage());
        }
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
