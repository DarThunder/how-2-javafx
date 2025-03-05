/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.idar.how2javafx.controllers;

import com.idar.how2javafx.objets.Usuario;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.UUID;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import lib.SqlLib;

/**
 * Controlador de la vista de administración de usuarios. Esta clase maneja la
 * creación, modificación, eliminación y visualización de usuarios en el
 * sistema. Además, proporciona la interacción con la base de datos para
 * almacenar y recuperar la información.
 */
public class AdminUserEditController implements Initializable {

    @FXML
    private AnchorPane APane1;
    @FXML
    private AnchorPane AP1;
    @FXML
    private AnchorPane AP2;
    @FXML
    private AnchorPane AP3;
    @FXML
    private Label JL1;
    @FXML
    private Label LNombre;
    @FXML
    private Label LContraseña;
    @FXML
    private Label LRol;
    @FXML
    private TextField TFN;
    @FXML
    private TextField TFC;
    @FXML
    private TextField TFEliminar;
    @FXML
    private TextField TFID;
    @FXML
    private TextField TFNuevoNombre;
    @FXML
    private TextField TFNuevaContraseña;
    @FXML
    private Button BEliminarUser;
    @FXML
    private ComboBox CB1;
    @FXML
    private ListView LV1;
    @FXML
    private Pane JP1;
    @FXML
    private StackPane SP1;
    @FXML
    private Button BVolver;
    @FXML
    private Button BNombre;
    @FXML
    private Button BContraseña;
    @FXML
    private Button BAgregar;
    @FXML
    private Button BEliminar;
    @FXML
    private Button BEditar;
    @FXML
    private Button BCrear;
    @FXML
    private TableView TV1;

    @FXML
    private TableColumn<Usuario, Integer> columnaId;
    @FXML
    private TableColumn<Usuario, String> columnaNombre;
    @FXML
    private TableColumn<Usuario, String> columnaContraseña;
    @FXML
    private TableColumn<Usuario, String> columnaRol;

    private SqlLib db;

    /**
     * Inicializa los componentes de la interfaz y la conexión a la base de
     * datos. Configura las columnas de la tabla para mostrar los datos de los
     * usuarios y carga los datos de la base de datos.
     *
     * @param url La URL de la vista cargada.
     * @param resourceBundle El conjunto de recursos para internacionalización.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            setDB(); 
        } catch (SQLException e) {
            System.err.println("Error al conectar a la base de datos: " + e.getMessage());
        }
        columnaId.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnaNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        columnaContraseña.setCellValueFactory(new PropertyValueFactory<>("contraseña"));
        columnaRol.setCellValueFactory(new PropertyValueFactory<>("rol"));
        cargarDatosUsuarios();
        CB1.getItems().addAll("admin", "user");
    }

    /**
     * Establece la conexión a la base de datos utilizando las credenciales
     * proporcionadas.
     *
     * @throws SQLException Si ocurre un error al intentar conectar a la base de
     * datos.
     */
    public void setDB() throws SQLException {
        this.db = SqlLib.getInstance("", "", "");
    }

    /**
     * Carga los datos de los usuarios desde la base de datos y los muestra en
     * la tabla. Convierte los datos de la base de datos en objetos Usuario y
     * los agrega a la vista.
     */
    private void cargarDatosUsuarios() {
        try {
            // Cargar los datos desde la base de datos
            List<String[]> usuarios = db.cargarUsuariosDesdeBD();

            // Limpiar la tabla antes de cargar nuevos datos
            TV1.getItems().clear();

            // Convertir los datos en objetos Usuario
            for (String[] usuario : usuarios) {
                int id = Integer.parseInt(usuario[0]);
                String nombre = usuario[1];
                String contraseña = usuario[2];
                String rol = usuario[3];

                // Agregar el usuario a la tabla
                TV1.getItems().add(new Usuario(id, nombre, contraseña, rol));
            }
        } catch (SQLException e) {
            System.err.println("Error al cargar datos: " + e.getMessage());
        }
    }

    /**
     * Inicializa la vista para mostrar las opciones disponibles al
     * administrador.
     */
    @FXML
    private void initialize() throws SQLException {
        SP1.setVisible(true);
        AP1.setVisible(false);
        AP2.setVisible(false);
        AP3.setVisible(false);
        setDB();
        CB1.getItems().addAll("admin", "user");
    }

    /**
     * Crea un nuevo usuario en la base de datos con los valores proporcionados
     * en los campos de texto.
     *
     * @throws IOException Si ocurre un error al crear el usuario.
     */
    @FXML
    public void crearUsuario() throws IOException {
        Object selectedValue = CB1.getValue();
        if (selectedValue == null) {
            JOptionPane.showMessageDialog(null, "Por favor, selecciona un valor en el ComboBox");
            return;
        }

        String newRole = selectedValue.toString();
        String newUsername = TFN.getText();
        String newPassword = TFC.getText();

        if (newUsername.isEmpty() || newPassword.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Por favor, llena todos los campos");
            return;
        }

        if (db.createUser((int) UUID.randomUUID().getLeastSignificantBits(), newRole, newUsername, newPassword)) {
            System.out.println("Si se pudo w");
            JOptionPane.showMessageDialog(null, "Usuario creado exitosamente");
            TFN.setText("");
            TFC.setText("");
            cargarDatosUsuarios();
        } else {
            System.out.println("No se pudo w");
        }
    }

    /**
     * Verifica si el texto proporcionado puede convertirse en un número entero.
     *
     * @param texto El texto a verificar.
     * @return true si el texto es un número entero, false de lo contrario.
     */
    private boolean esNumeroEntero(String texto) {
        try {
            Integer.parseInt(texto);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Elimina un usuario de la base de datos según el ID proporcionado en el
     * campo de texto.
     *
     * @throws Exception Si ocurre un error al eliminar el usuario.
     */
    @FXML
    private void eliminarUsuario() throws Exception {
        String idUser = TFEliminar.getText().trim();
        if (idUser.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Ingresa el ID del usuario");
            return;
        }
        if (!esNumeroEntero(idUser)) {
            JOptionPane.showMessageDialog(null, "Ingresa un ID válido");
            return;
        }
        int userID = Integer.parseInt(idUser);
        if (db.removeUser(userID)) {
            JOptionPane.showMessageDialog(null, "Usuario eliminado correctamente");
            TFEliminar.setText("");
            cargarDatosUsuarios();
        } else {
            JOptionPane.showMessageDialog(null, "No se pudo eliminar el usuario. Verifica que el ID sea correcto");
        }
    }

    /**
     * Modifica el nombre de un usuario de acuerdo con el ID y el nuevo nombre
     * proporcionado.
     */
    @FXML
    private void modificarNombre() {
        String idUsuario = TFID.getText().trim();
        if (idUsuario.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Por favor, ingresa el ID del usuario.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!esNumeroEntero(idUsuario)) {
            JOptionPane.showMessageDialog(null, "El ID del usuario debe ser un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int userID = Integer.parseInt(idUsuario);
        String newNombre = TFNuevoNombre.getText().trim();
        if (newNombre.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Por favor, ingresa un nuevo nombre para el usuario.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (db.setUsername(userID, newNombre)) {
            JOptionPane.showMessageDialog(null, "Nombre del usuario actualizado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            TFNuevoNombre.setText("");
            cargarDatosUsuarios();
        } else {
            JOptionPane.showMessageDialog(null, "Error al actualizar el nombre del usuario.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Modifica la contraseña de un usuario de acuerdo con el ID y la nueva
     * contraseña proporcionada.
     */
    @FXML
    private void modificarContraseña() {
        String idUsuario = TFID.getText().trim();
        if (idUsuario.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Por favor, ingresa el ID del usuario.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!esNumeroEntero(idUsuario)) {
            JOptionPane.showMessageDialog(null, "El ID del usuario debe ser un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int userID = Integer.parseInt(idUsuario);
        String nuevaContraseña = TFNuevaContraseña.getText().trim();
        if (nuevaContraseña.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Por favor, ingresa una nueva contraseña para el usuario.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (db.setUserPassword(userID, nuevaContraseña)) {
            JOptionPane.showMessageDialog(null, "Contraseña del usuario actualizado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            TFNuevaContraseña.setText("");
            cargarDatosUsuarios();
        } else {
            JOptionPane.showMessageDialog(null, "Error al actualizar la contraseña del usuario.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Cambia la vista de la aplicación a la vista de administración.
     *
     * @throws IOException Si ocurre un error al cambiar la vista.
     */
    @FXML
    private void cambiarAdminView() throws IOException {
        File fxmlFile = new File("src/main/resources/scenes/admin.fxml");
        FXMLLoader loader = new FXMLLoader(fxmlFile.toURI().toURL());
        Parent root = loader.load();
        Stage stage = (Stage) BVolver.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();
    }

    /**
     * Muestra la vista para agregar un nuevo usuario.
     */
    @FXML
    private void cambiarAgregar() {
        AP1.setVisible(false);
        AP2.setVisible(false);
        AP3.setVisible(true);
        AP3.setStyle("-fx-background-color: #B4BF5E;");
    }

    /**
     * Muestra la vista para eliminar un usuario.
     */
    @FXML
    private void cambiarEliminar() {
        AP1.setVisible(false);
        AP3.setVisible(false);
        AP2.setVisible(true);
        AP2.setStyle("-fx-background-color: #B4BF5E;");
    }

    /**
     * Muestra la vista para editar los datos de un usuario.
     */
    @FXML
    private void cambiarEditar() {
        AP2.setVisible(false);
        AP3.setVisible(false);
        AP1.setVisible(true);
        AP1.setStyle("-fx-background-color: #B4BF5E;");
    }
}
