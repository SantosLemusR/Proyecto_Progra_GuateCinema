package org.example.guatecinemaapi.repository;

import org.example.guatecinemaapi.model.Funcion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FuncionRepository extends JpaRepository<Funcion, Integer> {
    List<Funcion> findBySalaId(Integer salaId);

    List<Funcion> findByPeliculaId(Integer peliculaId);
}
