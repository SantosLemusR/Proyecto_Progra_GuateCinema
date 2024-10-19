package org.example.guatecinemaapi.repository;

import org.example.guatecinemaapi.model.Pelicula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PeliculaRepository extends JpaRepository<Pelicula, Integer> {

    // Consulta para obtener películas por cine
    @Query("SELECT p FROM Pelicula p JOIN p.cinePeliculas cp WHERE cp.cine.id = :cineId")
    List<Pelicula> findPeliculasPorCine(@Param("cineId") Integer cineId);
}
