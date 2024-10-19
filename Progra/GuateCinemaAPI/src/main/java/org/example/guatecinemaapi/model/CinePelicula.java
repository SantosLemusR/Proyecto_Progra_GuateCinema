package org.example.guatecinemaapi.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
@Entity
@Table(name = "cine_pelicula")
// La clase "CinePelicula" representa la relación entre la entidad "Cine" y "Pelicula", almacenando información sobre qué cines muestran qué películas.
@IdClass(CinePeliculaId.class)
// Indica que esta entidad usa una clave compuesta, y la clase "CinePeliculaId" define cómo manejar esta clave compuesta.
public class CinePelicula {

    @Id
    @Column(name = "cine_id")
    // El campo "cineId" es parte de la clave compuesta, referenciando a la entidad "Cine".
    private Integer cineId;

    @Id
    @Column(name = "pelicula_id")
    // El campo "peliculaId" es la otra parte de la clave compuesta, referenciando a la entidad "Pelicula".
    private Integer peliculaId;

    // Relación con la entidad "Cine"
    @ManyToOne
    @JoinColumn(name = "cine_id", insertable = false, updatable = false)
    // Define la relación de muchos a uno con la entidad "Cine". "insertable = false" y "updatable = false" evitan que este campo se modifique directamente.
    @JsonBackReference
    // Evita la serialización cíclica de la entidad "Cine" cuando se convierte a JSON.
    private Cine cine;

    // Relación con la entidad "Pelicula"
    @ManyToOne
    @JoinColumn(name = "pelicula_id", insertable = false, updatable = false)
    // Define la relación de muchos a uno con la entidad "Pelicula".
    @JsonBackReference
    // Evita la serialización cíclica de la entidad "Pelicula" cuando se convierte a JSON.
    private Pelicula pelicula;

    // Constructor vacío
    public CinePelicula() {}

    // Constructor con parámetros para inicializar los campos "cineId" y "peliculaId"
    public CinePelicula(Integer cineId, Integer peliculaId) {
        this.cineId = cineId;
        this.peliculaId = peliculaId;
    }

    // Getters y Setters para acceder y modificar los campos de la clase

    public Integer getCineId() {
        return cineId;
    }

    public void setCineId(Integer cineId) {
        this.cineId = cineId;
    }

    public Integer getPeliculaId() {
        return peliculaId;
    }

    public void setPeliculaId(Integer peliculaId) {
        this.peliculaId = peliculaId;
    }

    public Cine getCine() {
        return cine;
    }

    public void setCine(Cine cine) {
        this.cine = cine;
    }

    public Pelicula getPelicula() {
        return pelicula;
    }

    public void setPelicula(Pelicula pelicula) {
        this.pelicula = pelicula;
    }
}
