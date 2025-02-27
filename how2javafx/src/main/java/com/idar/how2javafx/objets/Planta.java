/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.idar.how2javafx.objets;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Planta {
    private final IntegerProperty id;
    private final StringProperty nombre;
    private final StringProperty nombreCientifico;
    private final StringProperty familia;
    private final StringProperty epocaFloracion;
    private final StringProperty habitat;
    private final StringProperty descripcion;
    private final BooleanProperty eliminada;

    public Planta(int id, String nombre, String nombreCientifico, String familia, String epocaFloracion, String habitat, String descripcion, boolean eliminada) {
        this.id = new SimpleIntegerProperty(id);
        this.nombre = new SimpleStringProperty(nombre);
        this.nombreCientifico = new SimpleStringProperty(nombreCientifico);
        this.familia = new SimpleStringProperty(familia);
        this.epocaFloracion = new SimpleStringProperty(epocaFloracion);
        this.habitat = new SimpleStringProperty(habitat);
        this.descripcion = new SimpleStringProperty(descripcion);
        this.eliminada = new SimpleBooleanProperty(eliminada);
    }

    // Getters y propiedades para JavaFX
    public int getId() { return id.get(); }
    public IntegerProperty idProperty() { return id; }

    public String getNombre() { return nombre.get(); }
    public StringProperty nombreProperty() { return nombre; }

    public String getNombreCientifico() { return nombreCientifico.get(); }
    public StringProperty nombreCientificoProperty() { return nombreCientifico; }

    public String getFamilia() { return familia.get(); }
    public StringProperty familiaProperty() { return familia; }

    public String getEpocaFloracion() { return epocaFloracion.get(); }
    public StringProperty epocaFloracionProperty() { return epocaFloracion; }

    public String getHabitat() { return habitat.get(); }
    public StringProperty habitatProperty() { return habitat; }

    public String getDescripcion() { return descripcion.get(); }
    public StringProperty descripcionProperty() { return descripcion; }

    public boolean isEliminada() { return eliminada.get(); } // Getter para eliminada
    public BooleanProperty eliminadaProperty() { return eliminada; } // Propiedad para eliminada
}