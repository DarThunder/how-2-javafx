package com.idar.how2javafx.controllers;

import com.idar.how2javafx.objets.Planta;
import java.awt.event.ActionEvent;
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
import javafx.scene.Node;
import javafx.stage.FileChooser;

/**
 * Controlador para administrar la interfaz gráfica de usuario relacionada con
 * las plantas. Esta clase es responsable de gestionar la interacción entre el
 * usuario y la base de datos, mostrando los datos de las plantas, permitiendo
 * la adición, eliminación y edición de información de las mismas. Implementa la
 * interfaz {@link Initializable} para inicializar los componentes de la
 * interfaz de usuario.
 */
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
    private Button BotonMImagen;

    @FXML
    private Button modificarImagen;

    @FXML
    private Button BAgregar;

    @FXML
    private Button bSeleccionar;

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
    private TableView<Planta> TV1;

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
    private TableColumn<Planta, Boolean> columnaEstatus;

    private String imageUrl;
    private SqlLib db;

    /**
     * Método que inicializa la configuración de las columnas de la tabla de
     * plantas y carga los datos desde la base de datos. Este método se ejecuta
     * automáticamente cuando la vista se carga.
     *
     * @param url URL de la ubicación de la vista FXML.
     * @param resourceBundle Un conjunto de recursos de texto que pueden ser
     * utilizados para traducir las etiquetas o textos de la vista.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        columnaId.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnaNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        columnaNombreCientifico.setCellValueFactory(new PropertyValueFactory<>("nombreCientifico"));
        columnaFamilia.setCellValueFactory(new PropertyValueFactory<>("familia"));
        columnaEpocaFloracion.setCellValueFactory(new PropertyValueFactory<>("epocaFloracion"));
        columnaHabitat.setCellValueFactory(new PropertyValueFactory<>("habitat"));
        columnaDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        columnaEstatus.setCellValueFactory(new PropertyValueFactory<>("eliminada"));

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

        cargarDatosPlantas();
    }

    /**
     * Carga los datos de las plantas desde la base de datos y los muestra en la
     * tabla. Este método obtiene una lista de plantas almacenada en la base de
     * datos, convierte los datos de la base de datos en objetos `Planta`, y los
     * agrega a la vista de la tabla {@link TableView}.
     *
     * En caso de error al cargar los datos, se captura una excepción
     * {@link SQLException}.
     */
    private void cargarDatosPlantas() {
        try {
            // Obtener la instancia Singleton de SqlLib
            db = SqlLib.getInstance("", "", "");

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
                String imagenRuta = planta[7];

                // Convertir el valor "0" o "1" a boolean
                boolean isDeleted = planta[8].equals("1"); // "1" -> true, "0" -> false

                // Agregar la planta a la tabla
                System.out.println(idPlanta);
                TV1.getItems().add(new Planta(idPlanta, nombre, nombreCientifico, familia, epocaFloracion, habitat, descripcion, imagenRuta, isDeleted));
            }
        } catch (SQLException e) {
            System.err.println("Error al cargar datos: " + e.getMessage());
        }
    }

    /**
     * Método de inicialización de la interfaz. Se ejecuta automáticamente
     * cuando la vista es cargada.
     *
     * Establece los estilos de fondo de los paneles y oculta los paneles de la
     * vista inicial. Inicializa la instancia de la base de datos usando el
     * patrón Singleton.
     *
     * @throws SQLException si ocurre un error al intentar acceder a la base de
     * datos.
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
     * Muestra el panel de agregar planta y oculta los otros paneles. Este
     * método se utiliza para mostrar el formulario de adición de planta y
     * ocultar los demás paneles de la interfaz.
     */
    @FXML
    private void mostrarPanelAgregar() {
        panel1.setVisible(true);
        panel1.setStyle("-fx-background-color: #B4BF5E;");
        panel2.setVisible(false);
        panel3.setVisible(false);
    }

    /**
     * Verifica que todos los campos de texto necesarios para agregar una planta
     * no estén vacíos.
     *
     * @return {@code true} si todos los campos están completos, {@code false}
     * si alguno está vacío.
     */
    @FXML
    private boolean verificarCampos() {
        return !plantNameField.getText().trim().isEmpty()
                && !plantScientificNameField.getText().trim().isEmpty()
                && !plantFamilyField.getText().trim().isEmpty()
                && !plantFlowerinSeasonField.getText().trim().isEmpty()
                && !plantHabitatField.getText().trim().isEmpty()
                && !plantDescriptionField.getText().trim().isEmpty();
    }

    /**
     * Agrega una nueva planta a la base de datos. Si alguno de los campos está
     * vacío, se muestra un mensaje de error. De lo contrario, se agrega la
     * planta y se limpian los campos de entrada.
     *
     * @param event
     * @throws SQLException si ocurre un error al intentar insertar los datos en
     * la base de datos.
     */
    @FXML
    private void addPlant() throws SQLException {
        if (!verificarCampos()) {
            JOptionPane.showMessageDialog(null, "Todos los campos deben estar completos", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Obtener los datos de los campos de entrada
        String name = plantNameField.getText();
        String scientificName = plantScientificNameField.getText();
        String family = plantFamilyField.getText();
        String floweringSeason = plantFlowerinSeasonField.getText();
        String habitat = plantHabitatField.getText();
        String description = plantDescriptionField.getText();

        // Comprobar si se ha seleccionado una imagen
        if (imageUrl == null || imageUrl.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No se ha seleccionado una imagen", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Reemplazar espacios en la URL de la imagen
        imageUrl = imageUrl.replace(" ", "%20");

        // Llamar al método para agregar la planta a la base de datos, pasando la URL de la imagen
        if (db.addPlant(name, scientificName, family, floweringSeason, habitat, description, imageUrl)) {
            JOptionPane.showMessageDialog(null, "Planta agregada correctamente");

            plantNameField.setText("");
            plantScientificNameField.setText("");
            plantFamilyField.setText("");
            plantFlowerinSeasonField.setText("");
            plantHabitatField.setText("");
            plantDescriptionField.setText("");
            imageUrl = null;
            cargarDatosPlantas();

        } else {
            JOptionPane.showMessageDialog(null, "Error al agregar la planta", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Muestra el panel para eliminar una planta y oculta los demás paneles.
     * Este método se utiliza para cambiar la interfaz y permitir al usuario
     * eliminar una planta específica.
     */
    @FXML
    private void mostrarPanelEliminar() {
        panel1.setVisible(false);
        panel2.setVisible(true);
        panel2.setStyle("-fx-background-color: #B4BF5E;");
        panel3.setVisible(false);
    }

    /**
     * Verifica si el texto proporcionado puede ser convertido a un número
     * entero.
     *
     * @param texto El texto que se desea verificar.
     * @return {@code true} si el texto es un número entero válido,
     * {@code false} si no lo es.
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
     * Elimina una planta de la base de datos usando su ID. Si el ID ingresado
     * no es válido o el campo está vacío, se muestra un mensaje de error.
     *
     * @throws SQLException si ocurre un error al intentar eliminar la planta de
     * la base de datos.
     */
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
            cargarDatosPlantas();
        } else {
            JOptionPane.showMessageDialog(null, "No se pudo eliminar la planta. Verifica que el ID sea correcto.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Muestra el panel de edición de plantas (panel 3) y oculta los demás. Este
     * método se utiliza para cambiar la interfaz de usuario y permitir la
     * edición de una planta.
     */
    @FXML
    private void mostrarPanelEditar() {
        panel1.setVisible(false);
        panel2.setVisible(false);
        panel3.setVisible(true);
        panel3.setStyle("-fx-background-color: #B4BF5E;");
    }

    /**
     * Cambia la escena actual a la pantalla de inicio de sesión. Este método
     * carga el archivo FXML correspondiente a la pantalla de inicio de sesión y
     * establece esa escena como la activa.
     *
     * @throws IOException si ocurre un error al cargar el archivo FXML
     * correspondiente.
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
     * Cambia la escena actual a la pantalla de configuración de usuarios. Este
     * método carga el archivo FXML correspondiente a la pantalla de
     * administración de usuarios y establece esa escena como la activa.
     *
     * @throws IOException si ocurre un error al cargar el archivo FXML
     * correspondiente.
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

    /**
     * Cambia el nombre de la planta especificada por su ID. Si el ID o el nuevo
     * nombre son inválidos, se muestra un mensaje de error. Si el nombre se
     * actualiza correctamente en la base de datos, se muestra un mensaje de
     * éxito.
     */
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
            cargarDatosPlantas();
        } else {
            JOptionPane.showMessageDialog(null, "Error al actualizar el nombre de la planta.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Cambia el nombre científico de la planta especificada por su ID. Si el ID
     * o el nuevo nombre científico son inválidos, se muestra un mensaje de
     * error. Si el nombre científico se actualiza correctamente en la base de
     * datos, se muestra un mensaje de éxito.
     */
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
            cargarDatosPlantas();
        } else {
            JOptionPane.showMessageDialog(null, "Error al actualizar el nombre de la planta.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Cambia la familia de una planta en la base de datos.
     *
     * El método obtiene el ID de planta ingresado por el usuario, valida si es
     * un número entero, y si la familia de la planta no está vacía, actualiza
     * el campo correspondiente en la base de datos. Si el ID o la familia no
     * son válidos, muestra un mensaje de error.
     *
     * @throws SQLException Si ocurre un error al interactuar con la base de
     * datos.
     */
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
            cargarDatosPlantas();
        } else {
            JOptionPane.showMessageDialog(null, "Error al actualizar la familia de la planta.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Cambia la época de floración de una planta en la base de datos.
     *
     * Este método toma el ID de la planta y la nueva época de floración
     * proporcionada por el usuario, valida que el ID sea un número entero y que
     * la época de floración no esté vacía. Si todo es válido, actualiza el
     * registro en la base de datos.
     *
     * @throws SQLException Si ocurre un error al interactuar con la base de
     * datos.
     */
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
            cargarDatosPlantas();
        } else {
            JOptionPane.showMessageDialog(null, "Error al actualizar la época de floración de la planta.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Cambia el hábitat de una planta en la base de datos.
     *
     * Este método obtiene el ID de la planta y el nuevo hábitat proporcionado
     * por el usuario, valida que el ID sea un número entero y que el hábitat no
     * esté vacío. Si las validaciones son exitosas, se actualiza el hábitat de
     * la planta en la base de datos.
     *
     * @throws SQLException Si ocurre un error al interactuar con la base de
     * datos.
     */
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
            cargarDatosPlantas();
        } else {
            JOptionPane.showMessageDialog(null, "Error al actualizar el hábitat de la planta.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Cambia la descripción de una planta en la base de datos.
     *
     * Este método obtiene el ID de la planta y la nueva descripción
     * proporcionada por el usuario, valida que el ID sea un número entero y que
     * la descripción no esté vacía. Si las validaciones son exitosas, actualiza
     * la descripción de la planta en la base de datos.
     *
     * @throws SQLException Si ocurre un error al interactuar con la base de
     * datos.
     */
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
            cargarDatosPlantas();
        } else {
            JOptionPane.showMessageDialog(null, "Error al actualizar la descripción de la planta.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Actualiza la imagen de una planta en la base de datos.
     *
     * Este método verifica que el ID de la planta sea válido, que se haya
     * seleccionado una nueva imagen, y luego actualiza la imagen de la planta
     * en la base de datos. Muestra un mensaje de éxito si la actualización es
     * correcta, o un mensaje de error si ocurre algún problema.
     *
     * @see db.setImage(int, String) Método que realiza la actualización en la
     * base de datos.
     */
    @FXML
    private void cambiarImagen() {
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

        // Verificar si se ha seleccionado una nueva imagen
        if (imageUrl == null || imageUrl.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Por favor, selecciona una imagen.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Actualizar la imagen de la planta en la base de datos
        if (db.setImage(plantID, imageUrl)) {
            JOptionPane.showMessageDialog(null, "Imagen de la planta actualizada correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            imageUrl = ""; // Limpiar la URL de la imagen seleccionada
        } else {
            JOptionPane.showMessageDialog(null, "Error al actualizar la imagen de la planta.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Permite al usuario seleccionar una imagen y almacena su URL.
     */
    @FXML
    public void addImage() {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Imágenes", "*.png", "*.jpg", "*.jpeg", "*.gif");
        fileChooser.getExtensionFilters().add(extFilter);

        // Obtener la ventana principal
        Stage stage = (Stage) plantNameField.getScene().getWindow();

        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            imageUrl = file.toURI().toString(); // Convertir el archivo a URL
            System.out.println("Imagen seleccionada: " + imageUrl);

        }
    }

}
