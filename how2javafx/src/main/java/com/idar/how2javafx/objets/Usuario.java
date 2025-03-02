/**
 * La clase Usuario representa una entidad que contiene información sobre un usuario.
 * Esta clase incluye atributos como el identificador, nombre, contraseña y rol del usuario.
 * 
 * Atributos:
 * - id: Identificador único del usuario.
 * - nombre: Nombre del usuario.
 * - contraseña: Contraseña del usuario.
 * - rol: Rol o tipo de usuario (por ejemplo, administrador, usuario normal, etc.).
 * 
 * Métodos:
 * - Constructor: Inicializa una nueva instancia de la clase Usuario con los valores proporcionados.
 * - Getters: Métodos para obtener el valor de cada atributo.
 * - Setters: Métodos para modificar el valor de cada atributo (opcional, dependiendo de la lógica de la aplicación).
 */
package com.idar.how2javafx.objets;

public class Usuario {
    private int id;
    private String nombre;
    private String contraseña;
    private String rol;

    /**
     * Constructor de la clase Usuario.
     * 
     * @param id Identificador único del usuario.
     * @param nombre Nombre del usuario.
     * @param contraseña Contraseña del usuario.
     * @param rol Rol o tipo de usuario.
     */
    public Usuario(int id, String nombre, String contraseña, String rol) {
        this.id = id;
        this.nombre = nombre;
        this.contraseña = contraseña;
        this.rol = rol;
    }

    // Getters

    /**
     * Obtiene el identificador del usuario.
     * @return El identificador del usuario.
     */
    public int getId() {
        return id;
    }

    /**
     * Obtiene el nombre del usuario.
     * @return El nombre del usuario.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Obtiene la contraseña del usuario.
     * @return La contraseña del usuario.
     */
    public String getContraseña() {
        return contraseña;
    }

    /**
     * Obtiene el rol del usuario.
     * @return El rol del usuario.
     */
    public String getRol() {
        return rol;
    }

    // Setters (opcional, dependiendo de tu lógica)

    /**
     * Establece el identificador del usuario.
     * @param id El nuevo identificador del usuario.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Establece el nombre del usuario.
     * @param nombre El nuevo nombre del usuario.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Establece la contraseña del usuario.
     * @param contraseña La nueva contraseña del usuario.
     */
    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    /**
     * Establece el rol del usuario.
     * @param rol El nuevo rol del usuario.
     */
    public void setRol(String rol) {
        this.rol = rol;
    }
}