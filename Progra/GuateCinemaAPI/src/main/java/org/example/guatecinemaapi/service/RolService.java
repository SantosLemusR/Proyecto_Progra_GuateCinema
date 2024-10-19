package org.example.guatecinemaapi.service;

import org.example.guatecinemaapi.model.Rol;
import org.example.guatecinemaapi.repository.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RolService {

    @Autowired
    private RolRepository rolRepository;

    // Método para encontrar un rol por nombre
    public Optional<Rol> findByNombre(String nombre) {
        return rolRepository.findByNombre(nombre);
    }

    // Método para obtener todos los roles
    public List<Rol> getAllRoles() {
        return rolRepository.findAll();
    }

    // Método para crear un nuevo rol
    public Rol createRol(Rol rol) {
        return rolRepository.save(rol);
    }
}
