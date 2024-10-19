package org.example.guatecinemaapi.repository;

import org.example.guatecinemaapi.model.Complemento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComplementoRepository extends JpaRepository<Complemento, Integer> {}
