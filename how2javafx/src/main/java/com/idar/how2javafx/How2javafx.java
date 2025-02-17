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
 *
 * @author dard
 */
public class How2javafx {
    private static SqlLib db;

    public static void main(String[] args) {
        String[] credentials = getDBCredentials();
        if (credentials == null) {
            System.out.println("Error: No se pudieron cargar las credenciales de la base de datos.");
            return;
        }

        try {
            db = new SqlLib(credentials[0], credentials[1], credentials[2]);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Error: No se pudo conectar a la base de datos.");
            e.printStackTrace();
            return;
        }

        App.main(args);
    }

    private static String[] getDBCredentials() {
        Properties properties = new Properties();
        String[] credentials = new String[3];

        try (FileInputStream fis = new FileInputStream("src/main/java/var/credentials.properties")) {
            properties.load(fis);

            String dbUrl = properties.getProperty("db.url");
            String dbUser = properties.getProperty("db.user");
            String dbPassword = properties.getProperty("db.password");
            credentials[0] = dbUrl;
            credentials[1] = dbUser;
            credentials[2] = dbPassword;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return credentials;
    }
}
