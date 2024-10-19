package org.example.guatecinemaapi.controller;

import org.example.guatecinemaapi.model.Cine;
import org.example.guatecinemaapi.repository.CineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@RestController
@RequestMapping("/api/v1/cines")
// Controlador para manejar las operaciones CRUD relacionadas con los cines.
public class CineController {

    @Autowired
    private CineRepository cineRepository; // Repositorio para acceder a los datos de la entidad Cine.

    // Método para obtener la lista de todos los cines.
    @GetMapping
    public ResponseEntity<List<Cine>> getAllCines() {
        try {
            // Obtiene todos los cines de la base de datos.
            List<Cine> cines = cineRepository.findAll();
            // Retorna una respuesta exitosa con la lista de cines.
            return ResponseEntity.ok(cines);
        } catch (Exception e) {
            // En caso de un error, se registra y se retorna un error interno del servidor.
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null); // No retorna ningún cuerpo en caso de error.
        }
    }

    // Método para obtener un cine específico por su ID.
    @GetMapping("/{id}")
    public ResponseEntity<Cine> getCineById(@PathVariable Integer id) {
        // Busca el cine por su ID en la base de datos.
        Optional<Cine> cine = cineRepository.findById(id);
        // Si se encuentra el cine, se retorna con un estado 200 (OK), de lo contrario, se retorna 404 (Not Found).
        return cine.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Método para crear un nuevo cine.
    @PostMapping
    public ResponseEntity<Cine> createCine(@RequestBody Cine cine) {
        // Guarda el nuevo cine en la base de datos.
        Cine nuevoCine = cineRepository.save(cine);
        // Retorna el cine recién creado con un estado 200 (OK).
        return ResponseEntity.ok(nuevoCine);
    }
}
