package org.example.guatecinemaapi.service;

import org.example.guatecinemaapi.model.Funcion;
import org.example.guatecinemaapi.repository.FuncionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FuncionService {

    @Autowired
    private FuncionRepository funcionRepository;

    // Guardar una función
    public Funcion saveFuncion(Funcion funcion) {
        return funcionRepository.save(funcion);
    }

    // Obtener todas las funciones
    public List<Funcion> findAllFunciones() {
        return funcionRepository.findAll();
    }

    // Buscar función por ID
    public Optional<Funcion> findById(Integer id) {
        return funcionRepository.findById(id);
    }

    // Buscar funciones por sala
    public List<Funcion> findBySalaId(Integer salaId) {
        return funcionRepository.findBySalaId(salaId);
    }

    // Buscar funciones por película
    public List<Funcion> findByPeliculaId(Integer peliculaId) {
        return funcionRepository.findByPeliculaId(peliculaId);
    }

    // Eliminar función
    public void deleteFuncion(Integer id) {
        funcionRepository.deleteById(id);
    }
}
