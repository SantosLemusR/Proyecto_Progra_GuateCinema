package org.example.guatecinemaapi.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "cine")
// La clase "Cine" representa una entidad en la base de datos que mapea la tabla "cine".
public class Cine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // El campo "id" es la clave primaria, y su valor se genera automáticamente.
    private Integer id;

    // Nombre del cine
    private String nombre;

    // Dirección del cine
    private String direccion;

    // Ubicación geográfica del cine (puede ser una coordenada o un área específica)
    private String ubicacion;

    @OneToMany(mappedBy = "cine", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    // Relación uno a muchos con la entidad "Sala". Un cine puede tener varias salas.
    // "mappedBy" indica que la relación es bidireccional, y "cine" es el campo en la clase Sala que hace referencia a Cine.
    // "cascade = CascadeType.ALL" asegura que todas las operaciones (guardar, borrar, etc.) en "Cine" afecten también a sus salas.
    // "fetch = FetchType.LAZY" significa que las salas se cargan de manera perezosa, solo cuando se accede a ellas explícitamente.
    private List<Sala> salas;

    // Getters y Setters para los atributos privados de la entidad

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public List<Sala> getSalas() {
        return salas;
    }

    public void setSalas(List<Sala> salas) {
        this.salas = salas;
    }
}
