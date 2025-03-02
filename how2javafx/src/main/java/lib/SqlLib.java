/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lib;

/**
 * Clase que proporciona métodos para interactuar con una base de datos SQL.
 * Permite realizar operaciones como la conexión a la base de datos, la gestión
 * de usuarios, la gestión de plantas, y la verificación de credenciales.
 *
 * @author dard
 */
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import org.mindrot.jbcrypt.BCrypt;
import java.util.ArrayList;
import java.util.List;

public class SqlLib {

    private static SqlLib instance;
    private Connection connection;
    private final String url;
    private String username;
    private String password;

    /**
     * Constructor de la clase SqlLib.
     *
     * @param url La URL de la base de datos.
     * @param username El nombre de usuario para la conexión a la base de datos.
     * @param password La contraseña para la conexión a la base de datos.
     * @throws SQLException Si ocurre un error al establecer la conexión.
     */
    public SqlLib(String url, String username, String password) throws SQLException {
        this.url = String.format(url, username, password);
        connect();
    }

    /**
     * Establece la conexión con la base de datos.
     *
     * @throws ClassNotFoundException Si no se encuentra el driver de la base de
     * datos.
     * @throws SQLException Si ocurre un error al establecer la conexión.
     */
    private void connect() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();

            this.connection = DriverManager.getConnection(url, username, password);
            System.out.println("Conexión establecida exitosamente.");
        } catch (SQLException e) {
            System.err.println("Error de SQL: " + e.getMessage());

            close();
        } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException e) {
            System.err.println("Error de reflexión: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Argumento ilegal: " + e.getMessage());
        } catch (InvocationTargetException e) {
        }
    }

    public static SqlLib getInstance(String url, String username, String password) throws SQLException {
        if (instance == null) {
            instance = new SqlLib(url, username, password);
        }
        return instance;
    }

    /**
     * Genera un hash de la contraseña proporcionada utilizando BCrypt.
     *
     * @param password La contraseña a hashear.
     * @return El hash de la contraseña.
     */
    /**
     * Genera un hash de la contraseña proporcionada utilizando BCrypt.
     *
     * @param password La contraseña a hashear.
     * @return El hash de la contraseña.
     */
    public String generateHash(String password) {
        String salt = BCrypt.gensalt(12);
        return BCrypt.hashpw(password, salt);
    }

    /**
     * Verifica si las credenciales proporcionadas son válidas.
     *
     * @param username El nombre de usuario a verificar.
     * @param password La contraseña a verificar.
     * @return true si las credenciales son válidas, false en caso contrario.
     * @throws SQLException Si ocurre un error al ejecutar la consulta.
     */
    public boolean isValidCredentials(String username, String password) throws SQLException {
        String query = "SELECT contrasena FROM usuario WHERE nombre = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String storedHash = resultSet.getString("contrasena");
                if (BCrypt.checkpw(password, storedHash)) {
                    return true;
                } else {
                    JOptionPane.showMessageDialog(null, "Contraseña incorrecta.", "Error", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
            } else {
                JOptionPane.showMessageDialog(null, "Usuario no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
    }

    /**
     * Crea un nuevo usuario en la base de datos.
     *
     * @param role El rol del usuario.
     * @param username El nombre de usuario.
     * @param password La contraseña del usuario.
     * @return true si el usuario se creó correctamente, false en caso
     * contrario.
     */
    public boolean createUser(int par, String role, String username, String password) {
        String hashedPassword = generateHash(password);
        System.out.println(hashedPassword);

        String query = "{ CALL AgregarUsuario(?, ?, ?) }";
        try (PreparedStatement statement = connection.prepareCall(query)) {
            statement.setString(1, role);
            statement.setString(2, username);
            statement.setString(3, hashedPassword);
            statement.execute();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     * Elimina un usuario de la base de datos.
     *
     * @param id El ID del usuario a eliminar.
     * @return true si el usuario se eliminó correctamente, false en caso
     * contrario.
     */
    public boolean removeUser(int id) {
        String query = "{ CALL EliminarUsuario(?) }";

        try (PreparedStatement statement = connection.prepareCall(query)) {
            statement.setInt(1, id);
            statement.execute();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    /**
     * Cambia el nombre de usuario de un usuario existente.
     *
     * @param id El ID del usuario.
     * @param username El nuevo nombre de usuario.
     * @return true si el nombre de usuario se cambió correctamente, false en
     * caso contrario.
     */
    public boolean setUsername(int id, String username) {
        String query = "{ CALL CambiarNombreUsuario(?, ?) }";

        try (PreparedStatement statement = connection.prepareCall(query)) {
            statement.setInt(1, id);
            statement.setString(2, username);
            statement.execute();

            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    /**
     * Cambia la contraseña de un usuario existente.
     *
     * @param id El ID del usuario.
     * @param password La nueva contraseña.
     * @return true si la contraseña se cambió correctamente, false en caso
     * contrario.
     */
    public boolean setUserPassword(int id, String password) {
        String hashedPassword = generateHash(password);
        String query = "{ CALL CambiarContraseniaUsuario(?, ?) }";
        try (PreparedStatement statement = connection.prepareCall(query)) {
            statement.setInt(1, id);
            statement.setString(2, hashedPassword);
            statement.execute();  
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    /**
     * Obtiene el rol de un usuario.
     *
     * @param username El nombre de usuario.
     * @return El rol del usuario, o "nar" si no se encuentra el usuario.
     * @throws SQLException Si ocurre un error al ejecutar la consulta.
     */
    public String getRole(String username) throws SQLException {
        String sql = "SELECT rol FROM usuario WHERE nombre = ?";
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

    /**
     * Agrega una nueva planta a la base de datos.
     *
     * @param id El ID de la planta.
     * @param plantName El nombre de la planta.
     * @param scientificPlantName El nombre científico de la planta.
     * @param plantFamily La familia de la planta.
     * @param floweringSeason La temporada de floración de la planta.
     * @param habitat El hábitat de la planta.
     * @param description La descripción de la planta.
     * @return true si la planta se agregó correctamente, false en caso
     * contrario.
     * @throws SQLException Si ocurre un error al ejecutar la consulta.
     */
    public boolean addPlant(String plantName, String scientificPlantName, String plantFamily, String floweringSeason, String habitat, String description) throws SQLException {
        String query = "{ CALL AgregarPlanta(?, ?, ?, ?, ?, ?) }";
        try (PreparedStatement statement = connection.prepareCall(query)) {
            statement.setString(1, plantName);
            statement.setString(2, scientificPlantName);
            statement.setString(3, plantFamily);
            statement.setString(4, floweringSeason);
            statement.setString(5, habitat);
            statement.setString(6, description);
            statement.execute();

            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     * Elimina una planta de la base de datos.
     *
     * @param id El ID de la planta a eliminar.
     * @return true si la planta se eliminó correctamente, false en caso
     * contrario.
     */
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

    /**
     * Cambia el nombre de una planta existente.
     *
     * @param id El ID de la planta.
     * @param newPlantName El nuevo nombre de la planta.
     * @return true si el nombre se cambió correctamente, false en caso
     * contrario.
     */
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

    /**
     * Cambia el nombre científico de una planta existente.
     *
     * @param id El ID de la planta.
     * @param newScientificPlantName El nuevo nombre científico de la planta.
     * @return true si el nombre científico se cambió correctamente, false en
     * caso contrario.
     */
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

    /**
     * Cambia la familia de una planta existente.
     *
     * @param id El ID de la planta.
     * @param newPlantFamily La nueva familia de la planta.
     * @return true si la familia se cambió correctamente, false en caso
     * contrario.
     */
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

    /**
     * Cambia la temporada de floración de una planta existente.
     *
     * @param id El ID de la planta.
     * @param newFloweringSeason La nueva temporada de floración de la planta.
     * @return true si la temporada de floración se cambió correctamente, false
     * en caso contrario.
     */
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

    /**
     * Cambia el hábitat de una planta existente.
     *
     * @param id El ID de la planta.
     * @param newHabitat El nuevo hábitat de la planta.
     * @return true si el hábitat se cambió correctamente, false en caso
     * contrario.
     */
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

    /**
     * Cambia la descripción de una planta existente.
     *
     * @param id El ID de la planta.
     * @param newDescription La nueva descripción de la planta.
     * @return true si la descripción se cambió correctamente, false en caso
     * contrario.
     */
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

    /**
     * Cierra la conexión con la base de datos.
     *
     * @throws SQLException Si ocurre un error al cerrar la conexión.
     */
    public void close() throws SQLException {
        if (connection != null) {
            connection.close();
            System.out.println("Conexión cerrada.");
        }
    }
    
    public List<String[]> cargarUsuariosDesdeBD() throws SQLException {
        List<String[]> usuarios = new ArrayList<>();
        String query = "SELECT id_usuario, nombre, contrasena, rol FROM usuario WHERE is_deleted = 0";

        // Ejemplo de conexión y consulta a la base de datos
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet rs = statement.executeQuery())
             {

            while (rs.next()) {
                String[] usuario = new String[4];
                usuario[0] = rs.getString("id_usuario");
                usuario[1] = rs.getString("nombre");
                usuario[2] = rs.getString("contrasena");
                usuario[3] = rs.getString("rol");
                usuarios.add(usuario);
            }
        }

        return usuarios;
    }

    /**
     * Carga los datos de las plantas desde la base de datos.
     * Este método ejecuta una consulta SQL para obtener todas las plantas que no han sido marcadas como eliminadas
     * (es decir, donde `is_deleted = 0`). Luego, almacena los datos de cada planta en un arreglo de Strings
     * y los agrega a una lista.
     *
     * @return Una lista de arreglos de Strings, donde cada arreglo representa una planta con los siguientes campos:
     *         - id_planta: Identificador único de la planta.
     *         - nombre: Nombre común de la planta.
     *         - nombre_cientifico: Nombre científico de la planta.
     *         - familia: Familia a la que pertenece la planta.
     *         - epoca_floracion: Época de floración de la planta.
     *         - habitat: Hábitat natural de la planta.
     *         - descripcion: Descripción de la planta.
     *         - imagen_ruta: Ruta de la imagen asociada a la planta.
     *         - is_deleted: Indica si la planta ha sido marcada como eliminada (0 = no eliminada, 1 = eliminada).
     */
    public List<String[]> cargarDatosDesdeBD() {
        List<String[]> plantas = new ArrayList<>();
        String query = "SELECT id_planta, nombre, nombre_cientifico, familia, epoca_floracion, habitat, descripcion, imagen_ruta, is_deleted FROM planta WHERE is_deleted = 0";

        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            // Recorre cada fila del resultado de la consulta
            while (resultSet.next()) {
                String[] planta = new String[9];
                planta[0] = resultSet.getString("id_planta"); // ID de la planta
                planta[1] = resultSet.getString("nombre"); // Nombre común
                planta[2] = resultSet.getString("nombre_cientifico"); // Nombre científico
                planta[3] = resultSet.getString("familia"); // Familia
                planta[4] = resultSet.getString("epoca_floracion"); // Época de floración
                planta[5] = resultSet.getString("habitat"); // Hábitat
                planta[6] = resultSet.getString("descripcion"); // Descripción
                planta[7] = resultSet.getString("imagen_ruta"); // Ruta de la imagen
                planta[8] = resultSet.getString("is_deleted"); // Estado de eliminación

                // Agrega la planta a la lista
                plantas.add(planta);
            }
        } catch (SQLException e) {
            // Registra el error en la consola
            System.err.println("Error al cargar datos: " + e.getMessage());
        }

        // Retorna la lista de plantas
        return plantas;
    }

}
