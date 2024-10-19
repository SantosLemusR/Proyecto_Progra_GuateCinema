package org.example.guatecinemaapi.controller;

import org.example.guatecinemaapi.model.Pelicula;
import org.example.guatecinemaapi.service.PeliculaService;
import org.example.guatecinemaapi.repository.PeliculaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/peliculas")
public class PeliculaController {

    @Autowired
    private PeliculaService peliculaService;

    @Autowired
    private PeliculaRepository peliculaRepository;  // Repositorio para manejar consultas adicionales

    // Obtener todas las películas
    @GetMapping
    public List<Pelicula> getAllPeliculas() {
        return peliculaService.getAllPeliculas();
    }

    // Obtener película por ID
    @GetMapping("/{id}")
    public ResponseEntity<Pelicula> getPeliculaById(@PathVariable Integer id) {
        Optional<Pelicula> pelicula = peliculaService.getPeliculaById(id);
        return pelicula.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Crear una nueva película
    @PostMapping
    public Pelicula createPelicula(@RequestBody Pelicula pelicula) {
        return peliculaService.createPelicula(pelicula);
    }

    // Actualizar película existente por ID
    @PutMapping("/{id}")
    public ResponseEntity<Pelicula> updatePelicula(@PathVariable Integer id, @RequestBody Pelicula pelicula) {
        try {
            Pelicula updatedPelicula = peliculaService.updatePelicula(id, pelicula);
            return ResponseEntity.ok(updatedPelicula);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Eliminar película por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePelicula(@PathVariable Integer id) {
        peliculaService.deletePelicula(id);
        return ResponseEntity.noContent().build();
    }

    // Obtener películas por cine usando un parámetro "cineId"
    @GetMapping("/por-cine")
    public List<Pelicula> getPeliculasPorCine(@RequestParam("cineId") Integer cineId) {
        // Corregido: ahora se llama al método correcto del repositorio
        return peliculaRepository.findPeliculasPorCine(cineId);
    }
}
