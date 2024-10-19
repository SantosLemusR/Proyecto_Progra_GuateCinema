package org.example.guatecinemaapi.repository;

import org.example.guatecinemaapi.model.Cine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CineRepository extends JpaRepository<Cine, Integer> {
}
