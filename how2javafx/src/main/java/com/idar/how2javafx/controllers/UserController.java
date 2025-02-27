package com.idar.how2javafx.controllers;

import com.idar.how2javafx.objets.Planta;
import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lib.SqlLib;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

public class UserController implements Initializable {

    @FXML
    private TableView<Planta> tablaPlantas;

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

    private ObservableList<Planta> plantasObservableList;
    
    @FXML
    private Button BAtras;
    
    @FXML
    private void switchToLogin() {
        // Lógica para cambiar a la escena de inicio de sesión
       try {
            // Cargar la nueva escena
           FXMLLoader loader = new FXMLLoader(getClass().getResource("/scenes/login.fxml"));
           Parent root = loader.load();

            // Obtener la escena actual y cambiarla
           Scene scene = tablaPlantas.getScene();
            scene.setRoot(root);
       } catch (IOException e) {
           e.printStackTrace();
       }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Configurar las columnas de la tabla
        columnaId.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnaNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        columnaNombreCientifico.setCellValueFactory(new PropertyValueFactory<>("nombreCientifico"));
        columnaFamilia.setCellValueFactory(new PropertyValueFactory<>("familia"));
        columnaEpocaFloracion.setCellValueFactory(new PropertyValueFactory<>("epocaFloracion"));
        columnaHabitat.setCellValueFactory(new PropertyValueFactory<>("habitat"));
        columnaDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));

      try {
        // Cargar los datos desde la base de datos
        cargarDatos();
      } catch (SQLException ex) {
        Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
      }
    }

    private void cargarDatos() throws SQLException {
    plantasObservableList = FXCollections.observableArrayList();

    // Obtener la instancia Singleton de SqlLib
    SqlLib sqlLib = SqlLib.getInstance("jdbc:mysql://localhost:3306/db", "root", "contraseña");

    // Cargar los datos desde la base de datos
    List<String[]> plantas = sqlLib.cargarDatosDesdeBD();

    // Convertir los datos en objetos Planta
    for (String[] planta : plantas) {
        int id = Integer.parseInt(planta[0]);
        String nombre = planta[1];
        String nombreCientifico = planta[2];
        String familia = planta[3];
        String epocaFloracion = planta[4];
        String habitat = planta[5];
        String descripcion = planta[6];
        boolean eliminada = Boolean.parseBoolean(planta[7]);

        plantasObservableList.add(new Planta(id, nombre, nombreCientifico, familia, epocaFloracion, habitat, descripcion, eliminada));
    }

    // Asignar los datos a la tabla
    tablaPlantas.setItems(plantasObservableList);
}
}