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

public class UserController implements Initializable {

    @FXML
    private ListView<String> listaPlantas; // Lista de nombres de plantas

    @FXML
    private Label nombrePlanta; // Label para el nombre de la planta seleccionada

    @FXML
    private ImageView imagenPlanta; // ImageView para la imagen de la planta

    @FXML
    private Label descripcionPlanta; // Label para la descripción de la planta

    @FXML
    private Button BAtras; // Botón para volver atrás

    private ObservableList<Planta> plantasObservableList; // Lista observable de plantas

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            // Cargar los datos desde la base de datos
            cargarDatos();
        } catch (SQLException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Configurar el listener para la selección de la lista
        listaPlantas.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                mostrarDetallePlanta(newValue);
            }
        });
    }

    private void cargarDatos() throws SQLException {
        plantasObservableList = FXCollections.observableArrayList();

        // Obtener la instancia Singleton de SqlLib
        SqlLib sqlLib = SqlLib.getInstance("jdbc:mysql://localhost:3306/db", "root", "contraseña");

        // Cargar los datos desde la base de datos
        List<String[]> plantas = sqlLib.cargarDatosDesdeBD();

        // Convertir los datos en objetos Planta y agregar los nombres a la lista
        for (String[] planta : plantas) {
            int id = Integer.parseInt(planta[0]);
            String nombre = planta[1];
            String nombreCientifico = planta[2];
            String familia = planta[3];
            String epocaFloracion = planta[4];
            String habitat = planta[5];
            String descripcion = planta[6];
            String imagenRuta = planta [7];
            boolean eliminada = Boolean.parseBoolean(planta[7]);

            plantasObservableList.add(new Planta(id, nombre, nombreCientifico, familia, epocaFloracion, habitat, descripcion, imagenRuta, eliminada));
            listaPlantas.getItems().add(nombre); // Agregar el nombre de la planta a la lista
        }
    }

    private void mostrarDetallePlanta(String nombrePlantaSeleccionada) {
        // Buscar la planta seleccionada en la lista observable
        Planta plantaSeleccionada = plantasObservableList.stream()
                .filter(planta -> planta.getNombre().equals(nombrePlantaSeleccionada))
                .findFirst()
                .orElse(null);

        if (plantaSeleccionada != null) {
            // Actualizar la información detallada
            nombrePlanta.setText(plantaSeleccionada.getNombre());
            descripcionPlanta.setText(plantaSeleccionada.getDescripcion());

            // Cargar la imagen de la planta (ajusta la ruta según tu estructura de archivos)
            String imagenUrl = getClass().getResource("/images/" + plantaSeleccionada.getNombre().toLowerCase() + ".jpg").toExternalForm();
            imagenPlanta.setImage(new Image(imagenUrl));
        }
    }

    @FXML
    private void switchToLogin() {
        // Lógica para cambiar a la escena de inicio de sesión
        try {
            // Cargar la nueva escena
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/scenes/login.fxml"));
            Parent root = loader.load();

            // Obtener la escena actual y cambiarla
            Scene scene = listaPlantas.getScene();
            scene.setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}