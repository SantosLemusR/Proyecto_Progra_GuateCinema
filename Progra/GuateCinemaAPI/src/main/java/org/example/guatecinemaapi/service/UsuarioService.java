package org.example.guatecinemaapi.service;

import org.example.guatecinemaapi.model.Usuario;
import org.example.guatecinemaapi.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String correoElectronico) throws UsernameNotFoundException {
        return usuarioRepository.findByCorreoElectronico(correoElectronico)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con el correo: " + correoElectronico));
    }

    // Buscar usuario por correo electrónico
    public Optional<Usuario> findByCorreoElectronico(String correoElectronico) {
        if (correoElectronico == null || correoElectronico.isEmpty()) {
            throw new IllegalArgumentException("El correo electrónico no puede estar vacío.");
        }
        return usuarioRepository.findByCorreoElectronico(correoElectronico);
    }

    // Guardar o actualizar un usuario
    public Usuario saveUsuario(Usuario usuario) {
        if (usuario == null || usuario.getCorreoElectronico().isEmpty()) {
            throw new IllegalArgumentException("El usuario y su correo electrónico no pueden estar vacíos.");
        }

        // Validar si el correo ya está registrado
        if (usuarioRepository.findByCorreoElectronico(usuario.getCorreoElectronico()).isPresent()) {
            throw new IllegalArgumentException("El correo electrónico ya está registrado.");
        }

        // Guardar el usuario
        return usuarioRepository.save(usuario);  // Se asegura de que el `return` esté al final
    }

    // Buscar usuario por ID
    public Usuario findById(Integer id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con el ID: " + id));
    }

    // Eliminar usuario por ID
    public void eliminarUsuario(Integer id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con el ID: " + id));

        usuarioRepository.delete(usuario);
    }
}
