package org.example.guatecinemaapi.repository;

import org.example.guatecinemaapi.model.CinePelicula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CinePeliculaRepository extends JpaRepository<CinePelicula, Long> {
}
