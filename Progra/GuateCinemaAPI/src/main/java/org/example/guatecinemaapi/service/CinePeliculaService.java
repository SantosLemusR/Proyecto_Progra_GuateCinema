package org.example.guatecinemaapi.service;

import org.example.guatecinemaapi.model.CinePelicula;
import org.example.guatecinemaapi.repository.CinePeliculaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CinePeliculaService {

    @Autowired
    private CinePeliculaRepository cinePeliculaRepository;

    // Obtener todas las relaciones CinePelicula
    public List<CinePelicula> getAllCinePeliculas() {
        return cinePeliculaRepository.findAll();
    }

    // Obtener una relación CinePelicula por ID
    public CinePelicula getCinePeliculaById(Long id) {
        Optional<CinePelicula> cinePelicula = cinePeliculaRepository.findById(id);
        return cinePelicula.orElse(null);  // Retorna null si no encuentra la relación
    }

    // Crear una nueva relación CinePelicula
    public CinePelicula createCinePelicula(CinePelicula cinePelicula) {
        return cinePeliculaRepository.save(cinePelicula);
    }

    // Cambio en el método updateCinePelicula
    public CinePelicula updateCinePelicula(Long id, CinePelicula cinePeliculaDetails) {
        CinePelicula cinePelicula = cinePeliculaRepository.findById(id).orElseThrow(() -> new RuntimeException("No encontrado"));

        cinePelicula.setPelicula(cinePeliculaDetails.getPelicula());  // Cambia getPeliculas() por getPelicula()
        cinePelicula.setCine(cinePeliculaDetails.getCine());          // Cambia getCines() por getCine()

        return cinePeliculaRepository.save(cinePelicula);
    }


    // Eliminar una relación CinePelicula
    public void deleteCinePelicula(Long id) {
        cinePeliculaRepository.deleteById(id);
    }
}
