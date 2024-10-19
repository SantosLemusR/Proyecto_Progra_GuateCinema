package org.example.guatecinemaapi.repository;

import org.example.guatecinemaapi.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    // Buscar un usuario por su correo electrónico
    Optional<Usuario> findByCorreoElectronico(String correoElectronico);
}
