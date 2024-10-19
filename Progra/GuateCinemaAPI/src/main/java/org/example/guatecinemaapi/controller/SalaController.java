package org.example.guatecinemaapi.controller;

import org.example.guatecinemaapi.model.Sala;
import org.example.guatecinemaapi.repository.SalaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/salas")
public class SalaController {

    @Autowired
    private SalaRepository salaRepository;

    // Método para obtener todas las salas
    @GetMapping
    public List<Sala> getAllSalas() {
        // Recupera todas las salas de la base de datos
        return salaRepository.findAll();
    }

    // Método para crear una nueva sala
    @PostMapping
    public ResponseEntity<Sala> createSala(@RequestBody Sala sala) {
        // Guarda la nueva sala en la base de datos
        Sala nuevaSala = salaRepository.save(sala);
        // Retorna la sala creada junto con un código de estado HTTP 200 (OK)
        return ResponseEntity.ok(nuevaSala);
    }
}
