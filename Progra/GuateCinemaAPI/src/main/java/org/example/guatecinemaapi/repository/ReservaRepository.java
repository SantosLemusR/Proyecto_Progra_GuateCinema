package org.example.guatecinemaapi.repository;

import org.example.guatecinemaapi.model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

// Anotación @Repository indica que esta interfaz es un componente de acceso a datos
@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Integer> {

    // Método para encontrar todas las reservas hechas por un usuario específico usando su ID
    List<Reserva> findByUsuarioId(Integer usuarioId);

    // Método para encontrar todas las reservas asociadas a una función específica usando su ID
    List<Reserva> findByFuncionId(Integer funcionId);
}
