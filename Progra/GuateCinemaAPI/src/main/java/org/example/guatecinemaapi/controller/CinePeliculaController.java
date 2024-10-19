package org.example.guatecinemaapi.controller;

import org.example.guatecinemaapi.model.CinePelicula;
import org.example.guatecinemaapi.service.CinePeliculaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cinepeliculas")
public class CinePeliculaController {

    @Autowired
    private CinePeliculaService cinePeliculaService;

    // Obtener todas las relaciones CinePelicula
    @GetMapping
    public ResponseEntity<List<CinePelicula>> getAllCinePeliculas() {
        List<CinePelicula> cinePeliculas = cinePeliculaService.getAllCinePeliculas();
        return ResponseEntity.ok(cinePeliculas);
    }

    // Obtener una relación CinePelicula por ID
    @GetMapping("/{id}")
    public ResponseEntity<CinePelicula> getCinePeliculaById(@PathVariable Long id) {
        CinePelicula cinePelicula = cinePeliculaService.getCinePeliculaById(id);
        return ResponseEntity.ok(cinePelicula);
    }

    // Crear una nueva relación CinePelicula
    @PostMapping
    public ResponseEntity<CinePelicula> createCinePelicula(@RequestBody CinePelicula cinePelicula) {
        CinePelicula newCinePelicula = cinePeliculaService.createCinePelicula(cinePelicula);
        return ResponseEntity.ok(newCinePelicula);
    }

    // Actualizar una relación CinePelicula existente
    @PutMapping("/{id}")
    public ResponseEntity<CinePelicula> updateCinePelicula(@PathVariable Long id, @RequestBody CinePelicula cinePeliculaDetails) {
        CinePelicula updatedCinePelicula = cinePeliculaService.updateCinePelicula(id, cinePeliculaDetails);
        return ResponseEntity.ok(updatedCinePelicula);
    }

    // Eliminar una relación CinePelicula
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCinePelicula(@PathVariable Long id) {
        cinePeliculaService.deleteCinePelicula(id);
        return ResponseEntity.noContent().build();
    }
}
