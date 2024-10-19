package org.example.guatecinemaapi.controller;

import org.example.guatecinemaapi.model.Funcion;
import org.example.guatecinemaapi.service.FuncionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/funciones")
public class FuncionController {

    @Autowired
    private FuncionService funcionService;

    // Obtener todas las funciones
    @GetMapping
    public ResponseEntity<List<Funcion>> getAllFunciones() {
        List<Funcion> funciones = funcionService.findAllFunciones();
        return ResponseEntity.ok(funciones);
    }

    // Crear una nueva función
    @PostMapping
    public ResponseEntity<Funcion> createFuncion(@RequestBody Funcion funcion) {
        Funcion nuevaFuncion = funcionService.saveFuncion(funcion);
        return ResponseEntity.ok(nuevaFuncion);
    }

    // Buscar función por ID
    @GetMapping("/{id}")
    public ResponseEntity<Funcion> getFuncionById(@PathVariable Integer id) {
        Optional<Funcion> funcion = funcionService.findById(id);
        return funcion.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Buscar funciones por sala
    @GetMapping("/sala/{salaId}")
    public ResponseEntity<List<Funcion>> getFuncionesBySalaId(@PathVariable Integer salaId) {
        List<Funcion> funciones = funcionService.findBySalaId(salaId);
        return ResponseEntity.ok(funciones);
    }

    // Buscar funciones por película
    @GetMapping("/pelicula/{peliculaId}")
    public ResponseEntity<List<Funcion>> getFuncionesByPeliculaId(@PathVariable Integer peliculaId) {
        List<Funcion> funciones = funcionService.findByPeliculaId(peliculaId);
        return ResponseEntity.ok(funciones);
    }

    // Eliminar una función
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFuncion(@PathVariable Integer id) {
        funcionService.deleteFuncion(id);
        return ResponseEntity.noContent().build();
    }
}
