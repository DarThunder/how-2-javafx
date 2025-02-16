/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assets;

import java.sql.SQLException;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Screen;
import lib.SqlLib;

public class Login extends Application {
    private SqlLib db;
    private TextField txtUser;
    private PasswordField txtPass;
    
    public void setDb(SqlLib db) {
        this.db = db;
    }

    @Override
    public void start(Stage primaryStage) {
        if (db != null) {
            System.out.println("Conexión a la base de datos establecida.");
        } else {
            System.out.println("No se ha establecido la base de datos.");
            primaryStage.close();
        }
        
        txtUser = new TextField();
        txtUser.setPromptText("Nombre de usuario");

        txtPass = new PasswordField();
        txtPass.setPromptText("Contraseña");

        Button btnLogin = new Button("Iniciar sesión");

        btnLogin.setOnAction(e -> {
            try {
                boolean isAuthenticated = db.isValidCredentials(txtUser.getText(), txtPass.getText());
                if (isAuthenticated) {
                    System.out.println("¡Usuario autenticado!");
                } else {
                    System.out.println("Credenciales incorrectas.");
                }
            } catch (SQLException ex) {
                System.out.println("Error al acceder a la base de datos: " + ex.getMessage());
            }
        });

        VBox vbox = new VBox(10);
        vbox.getChildren().addAll(txtUser, txtPass, btnLogin);

        Scene scene = new Scene(vbox, 400, 300);
        primaryStage.setScene(scene);

        primaryStage.setTitle("Login");
        primaryStage.setHeight(400);
        primaryStage.setWidth(300);

        Screen screen = Screen.getPrimary();
        double screenWidth = screen.getVisualBounds().getWidth();
        double screenHeight = screen.getVisualBounds().getHeight();
        double windowWidth = primaryStage.getWidth();
        double windowHeight = primaryStage.getHeight();
        primaryStage.setX((screenWidth - windowWidth) / 2);
        primaryStage.setY((screenHeight - windowHeight) / 2);

        primaryStage.setOnCloseRequest(event -> {
            System.out.println("La ventana se está cerrando.");
        });

        primaryStage.show();
    }
}