/**
 * Controlador para la vista de usuario en la aplicación.
 * Este controlador maneja la interacción del usuario con la lista de plantas,
 * mostrando los detalles de la planta seleccionada y permitiendo la navegación
 * de regreso a la pantalla de inicio de sesión.
 * 
 * @author dard
 */
package com.idar.how2javafx.controllers;

import com.idar.how2javafx.objets.Planta;
import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import lib.SqlLib;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class UserController implements Initializable {

    @FXML
    private ListView<String> listaPlantas;

    @FXML
    private Label nombrePlanta;

    @FXML
    private TextFlow nombreCientificoPlanta;

    @FXML
    private TextFlow familiaPlanta;

    @FXML
    private TextFlow epocaFloracionPlanta;

    @FXML
    private TextFlow habitatPlanta;

    @FXML
    private TextFlow descripcionPlanta;

    @FXML
    private ImageView imagenPlanta;

    @FXML
    private TextArea descripcionCompletaPlanta;

    @FXML
    private Button BAtras;

    private ObservableList<Planta> plantasObservableList;

    /**
     * Inicializa el controlador después de que se haya cargado su elemento raíz.
     * 
     * @param url La ubicación utilizada para resolver rutas relativas para el objeto raíz.
     * @param resourceBundle Los recursos utilizados para localizar el objeto raíz.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            cargarDatos();
        } catch (SQLException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }

        listaPlantas.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                mostrarDetallePlanta(newValue);
            }
        });
    }

    /**
     * Carga los datos de las plantas desde la base de datos y los muestra en la lista.
     * 
     * @throws SQLException Si ocurre un error al acceder a la base de datos.
     */
    private void cargarDatos() throws SQLException {
        plantasObservableList = FXCollections.observableArrayList();

        SqlLib sqlLib = SqlLib.getInstance("", "", "");
        List<String[]> plantas = sqlLib.cargarDatosDesdeBD();

        for (String[] planta : plantas) {
            int id = Integer.parseInt(planta[0]);
            String nombre = planta[1];
            String nombreCientifico = planta[2];
            String familia = planta[3];
            String epocaFloracion = planta[4];
            String habitat = planta[5];
            String descripcion = planta[6];
            String imagenRuta = planta[7];
            boolean eliminada = Boolean.parseBoolean(planta[8]);

            plantasObservableList.add(new Planta(id, nombre, nombreCientifico, familia, epocaFloracion, habitat, descripcion, imagenRuta, eliminada));
            listaPlantas.getItems().add(nombre);
        }
    }

    /**
     * Muestra los detalles de la planta seleccionada en la interfaz de usuario.
     * 
     * @param nombrePlantaSeleccionada El nombre de la planta seleccionada.
     */
    private void mostrarDetallePlanta(String nombrePlantaSeleccionada) {
        Planta plantaSeleccionada = plantasObservableList.stream()
                .filter(planta -> planta.getNombre().equals(nombrePlantaSeleccionada))
                .findFirst()
                .orElse(null);

        if (plantaSeleccionada != null) {
            nombrePlanta.setText(plantaSeleccionada.getNombre());

            // Configurar el TextFlow para "Nombre científico"
            Text nombreCientificoLabel = new Text("Nombre científico: ");
            nombreCientificoLabel.setFont(Font.font("System", FontWeight.BOLD, 12));
            Text nombreCientificoValue = new Text(plantaSeleccionada.getNombreCientifico());
            nombreCientificoPlanta.getChildren().setAll(nombreCientificoLabel, nombreCientificoValue);

            // Configurar el TextFlow para "Familia"
            Text familiaLabel = new Text("Familia: ");
            familiaLabel.setFont(Font.font("System", FontWeight.BOLD, 12));
            Text familiaValue = new Text(plantaSeleccionada.getFamilia());
            familiaPlanta.getChildren().setAll(familiaLabel, familiaValue);

            // Configurar el TextFlow para "Época de floración"
            Text epocaFloracionLabel = new Text("Época de floración: ");
            epocaFloracionLabel.setFont(Font.font("System", FontWeight.BOLD, 12));
            Text epocaFloracionValue = new Text(plantaSeleccionada.getEpocaFloracion());
            epocaFloracionPlanta.getChildren().setAll(epocaFloracionLabel, epocaFloracionValue);

            // Configurar el TextFlow para "Hábitat"
            Text habitatLabel = new Text("Hábitat: ");
            habitatLabel.setFont(Font.font("System", FontWeight.BOLD, 12));
            Text habitatValue = new Text(plantaSeleccionada.getHabitat());
            habitatPlanta.getChildren().setAll(habitatLabel, habitatValue);

            // Configurar el TextFlow para "Descripción"
            Text descripcionLabel = new Text("Descripción: ");
            descripcionLabel.setFont(Font.font("System", FontWeight.BOLD, 12));
            descripcionPlanta.getChildren().setAll(descripcionLabel);

            // Cargar la imagen de la planta desde la ruta relativa
            String imagenRuta = plantaSeleccionada.getImagenRuta();
            if (imagenRuta != null && !imagenRuta.isEmpty()) {
                try {
                    Image imagen = new Image(getClass().getResourceAsStream(imagenRuta));
                    imagenPlanta.setImage(imagen);
                } catch (Exception e) {
                    System.err.println("Error al cargar la imagen: " + e.getMessage());
                    imagenPlanta.setImage(null);
                }
            } else {
                imagenPlanta.setImage(null);
            }

            // Mostrar la descripción completa en el TextArea
            descripcionCompletaPlanta.setText(plantaSeleccionada.getDescripcion());
        }
    }

    /**
     * Cambia la escena actual a la pantalla de inicio de sesión.
     */
    @FXML
    private void switchToLogin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/scenes/login.fxml"));
            Parent root = loader.load();
            Scene scene = listaPlantas.getScene();
            scene.setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}