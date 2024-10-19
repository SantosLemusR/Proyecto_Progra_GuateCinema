package org.example.guatecinemaapi.service;

import org.example.guatecinemaapi.model.Reserva;
import org.example.guatecinemaapi.repository.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReservaService {

    @Autowired
    private ReservaRepository reservaRepository;

    // Guardar una reserva
    public Reserva saveReserva(Reserva reserva) {
        return reservaRepository.save(reserva);
    }

    // Obtener todas las reservas
    public List<Reserva> findAllReservas() {
        return reservaRepository.findAll();
    }

    // Buscar reserva por ID
    public Optional<Reserva> findById(Integer id) {
        return reservaRepository.findById(id);
    }

    // Buscar reservas por ID de usuario
    public List<Reserva> findByUsuarioId(Integer usuarioId) {
        return reservaRepository.findByUsuarioId(usuarioId);
    }

    // Buscar reservas por ID de función
    public List<Reserva> findByFuncionId(Integer funcionId) {
        return reservaRepository.findByFuncionId(funcionId);
    }

    // Eliminar reserva
    public void deleteReserva(Integer id) {
        reservaRepository.deleteById(id);
    }
}
