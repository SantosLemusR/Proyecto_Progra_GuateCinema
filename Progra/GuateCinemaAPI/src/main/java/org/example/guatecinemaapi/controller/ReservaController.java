package org.example.guatecinemaapi.controller;

import org.example.guatecinemaapi.model.Reserva;
import org.example.guatecinemaapi.service.ReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/reservas")
public class ReservaController {

    @Autowired
    private ReservaService reservaService;

    // Obtener todas las reservas
    @GetMapping
    public ResponseEntity<List<Reserva>> getAllReservas() {
        List<Reserva> reservas = reservaService.findAllReservas();
        return ResponseEntity.ok(reservas);
    }

    // Crear una nueva reserva
    @PostMapping
    public ResponseEntity<Reserva> createReserva(@RequestBody Reserva reserva) {
        Reserva nuevaReserva = reservaService.saveReserva(reserva);
        return ResponseEntity.ok(nuevaReserva);
    }

    // Buscar reserva por ID
    @GetMapping("/{id}")
    public ResponseEntity<Reserva> getReservaById(@PathVariable Integer id) {
        Optional<Reserva> reserva = reservaService.findById(id);
        return reserva.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Buscar reservas por ID de usuario
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Reserva>> getReservasByUsuarioId(@PathVariable Integer usuarioId) {
        List<Reserva> reservas = reservaService.findByUsuarioId(usuarioId);
        return ResponseEntity.ok(reservas);
    }

    // Buscar reservas por ID de función
    @GetMapping("/funcion/{funcionId}")
    public ResponseEntity<List<Reserva>> getReservasByFuncionId(@PathVariable Integer funcionId) {
        List<Reserva> reservas = reservaService.findByFuncionId(funcionId);
        return ResponseEntity.ok(reservas);
    }

    // Eliminar una reserva
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReserva(@PathVariable Integer id) {
        reservaService.deleteReserva(id);
        return ResponseEntity.noContent().build();
    }
}
