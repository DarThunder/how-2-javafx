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

    public boolean isValidCredentials(String username, String password) throws SQLException {
        String query = "SELECT Contrasenia FROM Usuario WHERE Nombre = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String storedHash = resultSet.getString("Contrasenia");

                return BCrypt.checkpw(password, storedHash);
            } else {
                return false;
            }
        }
    }

    public boolean createUser(int id, String role, String username, String password) {
        String hashedPassword = generateHash(password);

        String query = "{ CALL AgregarUsuario(?, ?, ?, ?) }";
        try (PreparedStatement statement = connection.prepareCall(query)) {
            statement.setInt(1, id);
            statement.setString(2, role);
            statement.setString(3, username);
            statement.setString(4, hashedPassword);
            statement.execute();

            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean removeUser(int id) {
        String query = "{ CALL EliminarUsuario(?) }";
        
        try (PreparedStatement statement = connection.prepareCall(query)){
            statement.setInt(1, id);
            statement.execute();
            
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
    
    public boolean setUsername(int id, String username) {
        String query = "{ CALL CambiarNombreUsuario(?, ?) }";
        
        try (PreparedStatement statement = connection.prepareCall(query)){
            statement.setInt(1, id);
            statement.setString(2, username);
            statement.execute();
            
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
    
    public boolean setUserPassword(int id, String password) {
        String query = "{ CALL CambiarContraseniaUsuario(?, ?) }";
        
        try (PreparedStatement statement = connection.prepareCall(query)){
            statement.setInt(1, id);
            statement.setString(2, password);
            statement.execute();
            
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public String getRole(String username) throws SQLException {
        String sql = "SELECT Rol FROM Usuario WHERE Nombre = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, username);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String role = rs.getString("Rol");
                return role;
            } else {
                return "nar";
            }
        }
    }

    public boolean addPlant(int id, String plantName, String scientificPlantName, String plantFamily, String floweringSeason, String habitat, String description) throws SQLException {
        String query = "{ CALL AgregarPlanta(?, ?, ?, ?) }";
        try (PreparedStatement statement = connection.prepareCall(query)) {
            statement.setInt(1, id);
            statement.setString(2, plantName);
            statement.setString(3, scientificPlantName);
            statement.setString(4, plantFamily);
            statement.setString(5, floweringSeason);
            statement.setString(6, habitat);
            statement.setString(7, description);
            statement.execute();

            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean removePlant(int id) {
        String query = "{ CALL EliminarPlanta(?) }";
        try (PreparedStatement statement = connection.prepareCall(query)) {
            statement.setInt(1, id);
            statement.execute();

            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean setPlantName(int id, String newPlantName) {
        String query = "{ CALL CambiarNombrePlanta(?, ?) }";
        try (PreparedStatement statement = connection.prepareCall(query)) {
            statement.setInt(1, id);
            statement.setString(2, newPlantName);
            statement.execute();

            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean setScientificPlantName(int id, String newScientificPlantName) {
        String query = "{ CALL CambiarNombreCPlanta(?, ?) }";
        try (PreparedStatement statement = connection.prepareCall(query)) {
            statement.setInt(1, id);
            statement.setString(2, newScientificPlantName);
            statement.execute();

            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean setPlantFamily(int id, String newPlantFamily) {
        String query = "{ CALL CambiarFamiliaPlanta(?, ?) }";
        try (PreparedStatement statement = connection.prepareCall(query)) {
            statement.setInt(1, id);
            statement.setString(2, newPlantFamily);
            statement.execute();

            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean setFloweringSeason(int id, String newFloweringSeason) {
        String query = "{ CALL CambiarFloracionPlanta(?, ?) }";
        try (PreparedStatement statement = connection.prepareCall(query)) {
            statement.setInt(1, id);
            statement.setString(2, newFloweringSeason);
            statement.execute();

            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean setHabitat(int id, String newHabitat) {
        String query = "{ CALL CambiarHabitatPlanta(?, ?) }";
        try (PreparedStatement statement = connection.prepareCall(query)) {
            statement.setInt(1, id);
            statement.setString(2, newHabitat);
            statement.execute();

            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean setDescription(int id, String newDescription) {
        String query = "{ CALL CambiarDescripcionPlanta(?, ?) }";
        try (PreparedStatement statement = connection.prepareCall(query)) {
            statement.setInt(1, id);
            statement.setString(2, newDescription);
            statement.execute();

            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public void close() throws SQLException {
        if (connection != null) {
            connection.close();
            System.out.println("Conexión cerrada.");
        }
    }
}
