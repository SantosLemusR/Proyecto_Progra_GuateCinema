package org.example.guatecinemaapi.model;


import java.io.Serializable;
import java.util.Objects;

public class CinePeliculaId implements Serializable {
    private Integer cineId;
    private Integer peliculaId;

    // Constructor vacío
    public CinePeliculaId() {}

    // Constructor con parámetros
    public CinePeliculaId(Integer cineId, Integer peliculaId) {
        this.cineId = cineId;
        this.peliculaId = peliculaId;
    }

    // Getters y Setters
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

    // Método equals para comparar objetos
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CinePeliculaId that = (CinePeliculaId) o;
        return Objects.equals(cineId, that.cineId) &&
                Objects.equals(peliculaId, that.peliculaId);
    }

    // Método hashCode para generación de hash
    @Override
    public int hashCode() {
        return Objects.hash(cineId, peliculaId);
    }
}
