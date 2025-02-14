/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lib;

/**
 *
 * @author dard
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.mindrot.jbcrypt.BCrypt;

public class SqlLib {

    private Connection connection;
    private final String url;
    private String username;
    private String password;

    public SqlLib(String url, String username, String password) throws ClassNotFoundException, SQLException {
        this.url = String.format(url, username, password);
        connect();
    }

    private void connect() throws ClassNotFoundException, SQLException {
        try {
            this.connection = DriverManager.getConnection(url, username, password);
            System.out.println("Conexión establecida exitosamente.");
        } catch (SQLException e) {
            System.err.println("Error al conectar a la base de datos: " + e.getMessage());
            close();
        }
    }

    public String generateHash(String password) {
        String salt = BCrypt.gensalt(12);
        return BCrypt.hashpw(password, salt);
    }

    public void createUser(String username, String password) throws SQLException {
        String hashedPassword = generateHash(password);

        String query = "INSERT INTO sudoers (username, user_password) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            statement.setString(2, hashedPassword);
            statement.executeUpdate();
        }
    }

    public boolean isValidCredentials(String username, String password) throws SQLException {
        String query = "SELECT user_password FROM sudoers WHERE username = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String storedHash = resultSet.getString("user_password");

                return BCrypt.checkpw(password, storedHash);
            } else {
                return false;
            }
        }
    }
    
    public String getRole(String username) throws SQLException {
        String sql = "SELECT role FROM users WHERE username = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, username);
            
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                String role = rs.getString("role");
                return role;
            } else {
                return "nar";
            }
        }
    }

    public void close() throws SQLException {
        if (connection != null) {
            connection.close();
            System.out.println("Conexión cerrada.");
        }
    }
}
