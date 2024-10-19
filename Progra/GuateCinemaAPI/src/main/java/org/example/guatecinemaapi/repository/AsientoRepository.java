package org.example.guatecinemaapi.repository;

import org.example.guatecinemaapi.model.Asiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AsientoRepository extends JpaRepository<Asiento, Integer> {
}
