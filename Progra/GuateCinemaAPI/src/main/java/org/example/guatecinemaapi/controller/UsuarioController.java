package org.example.guatecinemaapi.controller;

import org.example.guatecinemaapi.model.RegistroRequest;
import org.example.guatecinemaapi.model.Rol;
import org.example.guatecinemaapi.model.Usuario;
import org.example.guatecinemaapi.repository.RolRepository;
import org.example.guatecinemaapi.repository.UsuarioRepository;
import org.example.guatecinemaapi.service.RolService;
import org.example.guatecinemaapi.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private RolService rolService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    // Obtener todos los usuarios
    @GetMapping
    public List<Usuario> getAllUsuarios() {
        return usuarioRepository.findAll();
    }

    // Registro de usuario con validación de correo y contraseña, asignación automática del rol "USER", y encriptación de contraseña
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegistroRequest registroRequest) {
        try {
            // Verificar si el correo ya está registrado
            if (usuarioRepository.findByCorreoElectronico(registroRequest.getCorreoElectronico()).isPresent()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Map.of("error", "El correo electrónico ya está en uso."));
            }

            // Validación de contraseña
            if (registroRequest.getContraseña() == null || registroRequest.getContraseña().isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of("error", "La contraseña no puede ser nula o vacía."));
            }

            // Crear el nuevo usuario
            Usuario usuario = new Usuario();
            usuario.setNombre(registroRequest.getNombre());
            usuario.setCorreoElectronico(registroRequest.getCorreoElectronico());

            // Encriptar la contraseña usando BCryptPasswordEncoder
            String passwordEncriptada = passwordEncoder.encode(registroRequest.getContraseña());
            usuario.setContraseña(passwordEncriptada);

            // Asignar automáticamente el rol "USER" al nuevo usuario
            Rol rolUsuario = rolService.findByNombre("USER")
                    .orElseThrow(() -> new RuntimeException("Rol USER no encontrado"));
            usuario.setRol(rolUsuario);

            // Guardar el usuario en la base de datos usando el servicio de Usuario
            Usuario nuevoUsuario = usuarioService.saveUsuario(usuario);

            return ResponseEntity.ok(Map.of("message", "Usuario registrado exitosamente con el rol USER.", "usuario", nuevoUsuario));
        } catch (DataIntegrityViolationException e) {
            // Manejar errores de base de datos, como correos electrónicos duplicados
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("error", "El correo electrónico ya está registrado."));
        } catch (Exception e) {
            // Manejar otros errores y devolver una respuesta de error genérico
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error interno del servidor."));
        }
    }
}

