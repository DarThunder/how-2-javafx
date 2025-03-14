/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.idar.how2javafx;

import java.io.FileInputStream;
import java.io.IOException;
import lib.SqlLib;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Clase principal que inicia la aplicación y gestiona la conexión con la base de datos.
 */
public class How2javafx {
    private static SqlLib db; // Instancia de la conexión a la base de datos

    /**
     * Método principal que inicia la aplicación y establece la conexión con la base de datos.
     *
     * @param args Argumentos de la línea de comandos.
     * @throws java.sql.SQLException
     */
    public static void main(String[] args) throws SQLException {
        String[] credentials = getDBCredentials(); // Obtener credenciales de la base de datos
        if (credentials == null) {
            System.out.println("Error: No se pudieron cargar las credenciales de la base de datos.");
            return;
        }

        try {
            // Inicializar la conexión con la base de datos
            db = SqlLib.getInstance(credentials[0], credentials[1], credentials[2]);
        } catch (SQLException e) {
            System.out.println("Url: " + credentials[0] + "\nUsuario: " + credentials[1]);
            System.out.println("Error: No se pudo conectar a la base de datos.");
            e.printStackTrace();
            return;
        }

        // Iniciar la aplicación JavaFX
        App.main(args);
    }

    /**
     * Carga las credenciales de la base de datos desde un archivo de propiedades.
     *
     * @return Un array de Strings con la URL, usuario y contraseña de la base de datos.
     */
    private static String[] getDBCredentials() {
        Properties properties = new Properties();
        String[] credentials = new String[3];

        try (FileInputStream fis = new FileInputStream("src/main/java/var/credentials.properties")) {
            properties.load(fis);

            // Obtener los valores de las propiedades
            credentials[0] = properties.getProperty("db.url");
            credentials[1] = properties.getProperty("db.user");
            credentials[2] = properties.getProperty("db.password");

        } catch (IOException e) {
            System.out.println("Error: No se pudo leer el archivo de credenciales.");
            e.printStackTrace();
            return null;
        }
        return credentials;
    }
}
