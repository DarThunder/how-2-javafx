/**
 * La clase Planta representa una entidad que contiene información sobre una planta.
 * Utiliza propiedades de JavaFX para permitir el enlace de datos (data binding) en aplicaciones JavaFX.
 * 
 * Atributos:
 * - id: Identificador único de la planta.
 * - nombre: Nombre común de la planta.
 * - nombreCientifico: Nombre científico de la planta.
 * - familia: Familia a la que pertenece la planta.
 * - epocaFloracion: Época del año en la que la planta florece.
 * - habitat: Hábitat natural de la planta.
 * - descripcion: Descripción general de la planta.
 * - imagenRuta: Ruta de la imagen asociada a la planta.
 * - eliminada: Indica si la planta ha sido marcada como eliminada.
 * 
 * Métodos:
 * - Constructor: Inicializa una nueva instancia de la clase Planta con los valores proporcionados.
 * - Getters: Métodos para obtener el valor de cada atributo.
 * - Properties: Métodos que devuelven las propiedades de JavaFX para cada atributo, permitiendo el enlace de datos.
 */
package com.idar.how2javafx.objets;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Planta {
    private final IntegerProperty idPlanta;
    private final StringProperty nombre;
    private final StringProperty nombreCientifico;
    private final StringProperty familia;
    private final StringProperty epocaFloracion;
    private final StringProperty habitat;
    private final StringProperty descripcion;
    private final StringProperty imagenRuta;
    private final BooleanProperty eliminada;

    /**
     * Constructor de la clase Planta.
     * 
     * @param idPlanta Identificador único de la planta.
     * @param nombre Nombre común de la planta.
     * @param nombreCientifico Nombre científico de la planta.
     * @param familia Familia a la que pertenece la planta.
     * @param epocaFloracion Época del año en la que la planta florece.
     * @param habitat Hábitat natural de la planta.
     * @param descripcion Descripción general de la planta.
     * @param imagenRuta Ruta de la imagen asociada a la planta.
     * @param eliminada Indica si la planta ha sido marcada como eliminada.
     */
    public Planta(int idPlanta, String nombre, String nombreCientifico, String familia, String epocaFloracion, String habitat, String descripcion, String imagenRuta, boolean eliminada) {
        this.idPlanta = new SimpleIntegerProperty(idPlanta);
        this.nombre = new SimpleStringProperty(nombre);
        this.nombreCientifico = new SimpleStringProperty(nombreCientifico);
        this.familia = new SimpleStringProperty(familia);
        this.epocaFloracion = new SimpleStringProperty(epocaFloracion);
        this.habitat = new SimpleStringProperty(habitat);
        this.descripcion = new SimpleStringProperty(descripcion);
        this.imagenRuta = new SimpleStringProperty(imagenRuta);
        this.eliminada = new SimpleBooleanProperty(eliminada);
    }

    // Getters y propiedades para JavaFX

    /**
     * Obtiene el identificador de la planta.
     * @return El identificador de la planta.
     */
    public Integer getId() { return idPlanta.get(); }

    /**
     * Devuelve la propiedad del identificador para enlace de datos.
     * @return La propiedad del identificador.
     */
    public IntegerProperty idProperty() { return idPlanta; }

    /**
     * Obtiene el nombre común de la planta.
     * @return El nombre común de la planta.
     */
    public String getNombre() { return nombre.get(); }

    /**
     * Devuelve la propiedad del nombre común para enlace de datos.
     * @return La propiedad del nombre común.
     */
    public StringProperty nombreProperty() { return nombre; }

    /**
     * Obtiene el nombre científico de la planta.
     * @return El nombre científico de la planta.
     */
    public String getNombreCientifico() { return nombreCientifico.get(); }

    /**
     * Devuelve la propiedad del nombre científico para enlace de datos.
     * @return La propiedad del nombre científico.
     */
    public StringProperty nombreCientificoProperty() { return nombreCientifico; }

    /**
     * Obtiene la familia de la planta.
     * @return La familia de la planta.
     */
    public String getFamilia() { return familia.get(); }

    /**
     * Devuelve la propiedad de la familia para enlace de datos.
     * @return La propiedad de la familia.
     */
    public StringProperty familiaProperty() { return familia; }

    /**
     * Obtiene la época de floración de la planta.
     * @return La época de floración de la planta.
     */
    public String getEpocaFloracion() { return epocaFloracion.get(); }

    /**
     * Devuelve la propiedad de la época de floración para enlace de datos.
     * @return La propiedad de la época de floración.
     */
    public StringProperty epocaFloracionProperty() { return epocaFloracion; }

    /**
     * Obtiene el hábitat de la planta.
     * @return El hábitat de la planta.
     */
    public String getHabitat() { return habitat.get(); }

    /**
     * Devuelve la propiedad del hábitat para enlace de datos.
     * @return La propiedad del hábitat.
     */
    public StringProperty habitatProperty() { return habitat; }

    /**
     * Obtiene la descripción de la planta.
     * @return La descripción de la planta.
     */
    public String getDescripcion() { return descripcion.get(); }

    /**
     * Devuelve la propiedad de la descripción para enlace de datos.
     * @return La propiedad de la descripción.
     */
    public StringProperty descripcionProperty() { return descripcion; }

    /**
     * Obtiene la ruta de la imagen de la planta.
     * @return La ruta de la imagen de la planta.
     */
    public String getImagenRuta() { return imagenRuta.get(); }

    /**
     * Devuelve la propiedad de la ruta de la imagen para enlace de datos.
     * @return La propiedad de la ruta de la imagen.
     */
    public StringProperty imagenRutaProperty() { return imagenRuta; }

    /**
     * Indica si la planta ha sido marcada como eliminada.
     * @return true si la planta está marcada como eliminada, false en caso contrario.
     */
    public boolean isEliminada() { return eliminada.get(); }

    /**
     * Devuelve la propiedad de eliminada para enlace de datos.
     * @return La propiedad de eliminada.
     */
    public BooleanProperty eliminadaProperty() { return eliminada; }

}