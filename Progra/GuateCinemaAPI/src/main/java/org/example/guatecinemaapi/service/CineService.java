package org.example.guatecinemaapi.service;

import org.example.guatecinemaapi.model.Cine;
import org.example.guatecinemaapi.repository.CineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CineService {

    private final CineRepository cineRepository;

    @Autowired
    public CineService(CineRepository cineRepository) {
        this.cineRepository = cineRepository;
    }

    // Obtener todos los cines
    public List<Cine> getAllCines() {
        return cineRepository.findAll();
    }

    // Obtener un cine por su ID
    public Optional<Cine> getCineById(Integer id) {  // Cambiado de Long a Integer
        return cineRepository.findById(id);
    }

    // Guardar un nuevo cine
    public Cine addCine(Cine cine) {
        return cineRepository.save(cine);
    }

    // Actualizar un cine existente
    public Cine updateCine(Integer id, Cine cineDetails) {  // Cambiado de Long a Integer
        Optional<Cine> cine = cineRepository.findById(id);
        if (cine.isPresent()) {
            Cine existingCine = cine.get();
            existingCine.setNombre(cineDetails.getNombre());
            existingCine.setDireccion(cineDetails.getDireccion());
            existingCine.setUbicacion(cineDetails.getUbicacion());
            return cineRepository.save(existingCine);
        } else {
            throw new RuntimeException("Cine no encontrado con ID: " + id);
        }
    }

    // Eliminar un cine
    public void deleteCine(Integer id) {  // Cambiado de Long a Integer
        cineRepository.deleteById(id);
    }
}
