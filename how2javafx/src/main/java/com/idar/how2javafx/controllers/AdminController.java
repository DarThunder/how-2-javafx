package com.idar.how2javafx.controllers;

import com.idar.how2javafx.objets.Planta;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableCell;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javax.swing.JOptionPane;
import lib.SqlLib;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class AdminController implements Initializable {

    @FXML
    private BorderPane BP1;
    @FXML
    private VBox VB1;
    @FXML
    private StackPane SP1;
    @FXML
    private AnchorPane panel1;
    @FXML
    private Button BUsuario;
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
    private TextField nuevoNombre;
    @FXML
    private TextField nuevoNombreC;
    @FXML
    private TextField nuevaFamilia;
    @FXML
    private TextField nuevoHabitat;
    @FXML
    private TextField nuevaDescripcion;
    @FXML
    private TextField nuevaEpocaFloracion;
    @FXML
    private TextField edit;
    @FXML
    private AnchorPane panel3;
    @FXML
    private Button JB2;
    @FXML
    private TableView<Planta> TV1; // Cambia el tipo genérico a Planta

    @FXML
    private TableColumn<Planta, Integer> columnaId;

    @FXML
    private TableColumn<Planta, String> columnaNombre;

    @FXML
    private TableColumn<Planta, String> columnaNombreCientifico;

    @FXML
    private TableColumn<Planta, String> columnaFamilia;

    @FXML
    private TableColumn<Planta, String> columnaEpocaFloracion;

    @FXML
    private TableColumn<Planta, String> columnaHabitat;

    @FXML
    private TableColumn<Planta, String> columnaDescripcion;

    @FXML
    private TableColumn<Planta, Boolean> columnaEstatus; // Cambia el tipo a Boolean

    private SqlLib db;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Vincular las columnas a los atributos de la clase Planta
        columnaId.setCellValueFactory(new PropertyValueFactory<>("idPlanta"));
        columnaNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        columnaNombreCientifico.setCellValueFactory(new PropertyValueFactory<>("nombreCientifico"));
        columnaFamilia.setCellValueFactory(new PropertyValueFactory<>("familia"));
        columnaEpocaFloracion.setCellValueFactory(new PropertyValueFactory<>("epocaFloracion"));
        columnaHabitat.setCellValueFactory(new PropertyValueFactory<>("habitat"));
        columnaDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        columnaEstatus.setCellValueFactory(new PropertyValueFactory<>("isDeleted")); // Nueva columna

        // Formatear el estatus
        columnaEstatus.setCellFactory(column -> {
            return new TableCell<Planta, Boolean>() {
                @Override
                protected void updateItem(Boolean isDeleted, boolean empty) {
                    super.updateItem(isDeleted, empty);
                    if (empty || isDeleted == null) {
                        setText("");
                    } else {
                        setText(isDeleted ? "Eliminada" : "Activa");
                    }
                }
            };
        });

        // Cargar los datos de la tabla de plantas
        cargarDatosPlantas();
    }

    private void cargarDatosPlantas() {
    try {
        // Obtener la instancia Singleton de SqlLib
        db = SqlLib.getInstance("jdbc:mysql://localhost:3306/bd", "TheAl", "contraseña");

        // Cargar los datos desde la base de datos
        List<String[]> plantas = db.cargarDatosDesdeBD();

        // Limpiar la tabla antes de cargar nuevos datos
        TV1.getItems().clear();

        // Convertir los datos en objetos Planta
        for (String[] planta : plantas) {
            int idPlanta = Integer.parseInt(planta[0]);
            String nombre = planta[1];
            String nombreCientifico = planta[2];
            String familia = planta[3];
            String epocaFloracion = planta[4];
            String habitat = planta[5];
            String descripcion = planta[6];

            // Convertir el valor "0" o "1" a boolean
            boolean isDeleted = planta[7].equals("1"); // "1" -> true, "0" -> false

            // Agregar la planta a la tabla
            TV1.getItems().add(new Planta(idPlanta, nombre, nombreCientifico, familia, epocaFloracion, habitat, descripcion, isDeleted));
        }
    } catch (SQLException e) {
        System.err.println("Error al cargar datos: " + e.getMessage());
        }
    }

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
    private boolean verificarCampos() {
        return !plantNameField.getText().trim().isEmpty()
                && !plantScientificNameField.getText().trim().isEmpty()
                && !plantFamilyField.getText().trim().isEmpty()
                && !plantFlowerinSeasonField.getText().trim().isEmpty()
                && !plantHabitatField.getText().trim().isEmpty()
                && !plantDescriptionField.getText().trim().isEmpty();
    }

    @FXML
    private void addPlant() throws SQLException {
        if (!verificarCampos()) {
            JOptionPane.showMessageDialog(null, "Todos los campos deben estar completos", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String name = plantNameField.getText();
        String scientificName = plantScientificNameField.getText();
        String family = plantFamilyField.getText();
        String floweringSeason = plantFlowerinSeasonField.getText();
        String habitat = plantHabitatField.getText();
        String description = plantDescriptionField.getText();
        System.out.println(name + " " + scientificName + " " + family + " " + floweringSeason + " " + habitat + " " + description);

        if (db.addPlant(name, scientificName, family, floweringSeason, habitat, description)) {
            System.out.println("Se agrego planta tu");
            JOptionPane.showMessageDialog(null, "Planta agregada correctamente");
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

    private boolean esNumeroEntero(String texto) {
        try {
            Integer.parseInt(texto);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @FXML
    private void removePlant() {
        String idPlanta = remove.getText().trim();
        if (idPlanta.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Por favor, ingresa un ID de planta para eliminar.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!esNumeroEntero(idPlanta)) {
            JOptionPane.showMessageDialog(null, "El ID de la planta debe ser un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int plantID = Integer.parseInt(idPlanta);
        if (db.removePlant(plantID)) {
            JOptionPane.showMessageDialog(null, "Planta eliminada correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            System.out.println("Se eliminó correctamente.");
            remove.setText("");
        } else {
            JOptionPane.showMessageDialog(null, "No se pudo eliminar la planta. Verifica que el ID sea correcto.", "Error", JOptionPane.ERROR_MESSAGE);
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

    /**
     * Cambia la escena actual a la pantalla de configuración de usuarios.
     *
     * @throws IOException si ocurre un error al cargar el archivo FXML.
     */
    @FXML
    private void switchToAdminUserEdit() throws IOException {
        File fxmlFile = new File("src/main/resources/scenes/adminUserEdit.fxml");
        FXMLLoader loader = new FXMLLoader(fxmlFile.toURI().toURL());
        Parent root = loader.load();
        Stage stage = (Stage) BUsuario.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();
    }

    @FXML
    private void cambiarNombre() {
        String inputText = edit.getText().trim();
        if (inputText.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Por favor, ingresa un ID de planta.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!esNumeroEntero(inputText)) {
            JOptionPane.showMessageDialog(null, "El ID de la planta debe ser un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int plantID = Integer.parseInt(inputText);
        String newNombre = nuevoNombre.getText().trim();
        if (newNombre.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Por favor, ingresa un nuevo nombre para la planta.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (db.setPlantName(plantID, newNombre)) {
            JOptionPane.showMessageDialog(null, "Nombre de la planta actualizado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            nuevoNombre.setText("");
        } else {
            JOptionPane.showMessageDialog(null, "Error al actualizar el nombre de la planta.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @FXML
    private void cambiarNombreC() {
        String inputText = edit.getText().trim();
        if (inputText.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Por favor, ingresa un ID de planta.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!esNumeroEntero(inputText)) {
            JOptionPane.showMessageDialog(null, "El ID de la planta debe ser un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int plantID = Integer.parseInt(inputText);
        String newNombreC = nuevoNombreC.getText().trim();
        if (newNombreC.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Por favor, ingresa un nuevo nombre científico para la planta.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (db.setScientificPlantName(plantID, newNombreC)) {
            JOptionPane.showMessageDialog(null, "Nombre científico de la planta actualizado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            nuevoNombreC.setText("");
        } else {
            JOptionPane.showMessageDialog(null, "Error al actualizar el nombre de la planta.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @FXML
    private void cambiarFamilia() {
        String inputText = edit.getText().trim();
        if (inputText.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Por favor, ingresa un ID de planta.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!esNumeroEntero(inputText)) {
            JOptionPane.showMessageDialog(null, "El ID de la planta debe ser un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int plantID = Integer.parseInt(inputText);
        String newFamily = this.nuevaFamilia.getText().trim();
        if (newFamily.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Por favor, ingresa una nueva familia para la planta.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (db.setPlantFamily(plantID, newFamily)) {
            JOptionPane.showMessageDialog(null, "Familia de la planta actualizado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            nuevaFamilia.setText("");
        } else {
            JOptionPane.showMessageDialog(null, "Error al actualizar la familia de la planta.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @FXML
    private void cambiarEpocaFloracion() {
        String inputText = edit.getText().trim();
        if (inputText.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Por favor, ingresa un ID de planta.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!esNumeroEntero(inputText)) {
            JOptionPane.showMessageDialog(null, "El ID de la planta debe ser un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int plantID = Integer.parseInt(inputText);
        String newFloweringSeason = nuevaEpocaFloracion.getText().trim();
        if (newFloweringSeason.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Por favor, ingresa la época de floración para la planta.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (db.setFloweringSeason(plantID, newFloweringSeason)) {
            JOptionPane.showMessageDialog(null, "Época de floración de la planta actualizado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            nuevaEpocaFloracion.setText("");
        } else {
            JOptionPane.showMessageDialog(null, "Error al actualizar la época de floración de la planta.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @FXML
    private void cambiarHabitat() {
        String inputText = edit.getText().trim();
        if (inputText.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Por favor, ingresa un ID de planta.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!esNumeroEntero(inputText)) {
            JOptionPane.showMessageDialog(null, "El ID de la planta debe ser un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int plantID = Integer.parseInt(inputText);
        String newHabitat = nuevoHabitat.getText().trim();
        if (newHabitat.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Por favor, ingresa el nuevo hábitat para la planta.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (db.setHabitat(plantID, newHabitat)) {
            JOptionPane.showMessageDialog(null, "Hábitat de la planta actualizado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            nuevoHabitat.setText("");
        } else {
            JOptionPane.showMessageDialog(null, "Error al actualizar el hábitat de la planta.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @FXML
    private void cambiarDescripcion() {
        String inputText = edit.getText().trim();
        if (inputText.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Por favor, ingresa un ID de planta.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!esNumeroEntero(inputText)) {
            JOptionPane.showMessageDialog(null, "El ID de la planta debe ser un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int plantID = Integer.parseInt(inputText);
        String newDescription = nuevaDescripcion.getText().trim();
        if (newDescription.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Por favor, ingresa la nueva descripción para la planta.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (db.setDescription(plantID, newDescription)) {
            JOptionPane.showMessageDialog(null, "Descripción de la planta actualizada correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            nuevaDescripcion.setText("");
        } else {
            JOptionPane.showMessageDialog(null, "Error al actualizar la descripción de la planta.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
