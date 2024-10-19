package org.example.guatecinemaapi.controller;

import org.example.guatecinemaapi.model.RegistroRequest;
import org.example.guatecinemaapi.model.Rol;
import org.example.guatecinemaapi.model.LoginResponse;
import org.example.guatecinemaapi.model.Usuario;
import org.example.guatecinemaapi.model.LoginRequest;
import org.example.guatecinemaapi.service.RolService;
import org.example.guatecinemaapi.service.UsuarioService;
import org.example.guatecinemaapi.service.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin(origins = "*")
// Controlador de autenticación que maneja el registro, inicio de sesión y validación de JWT.
public class AuthController {

    @Autowired
    private UsuarioService usuarioService; // Servicio para manejar usuarios

    @Autowired
    private RolService rolService; // Servicio para manejar roles

    @Autowired
    private BCryptPasswordEncoder passwordEncoder; // Codificador de contraseñas

    @Autowired
    private AuthenticationManager authenticationManager; // Manejador de autenticación

    @Autowired
    private JwtTokenProvider jwtTokenProvider; // Proveedor de tokens JWT

    // Método para registrar un nuevo usuario
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegistroRequest registroRequest) {
        try {
            // Verifica si el correo electrónico ya está en uso
            if (usuarioService.findByCorreoElectronico(registroRequest.getCorreoElectronico()).isPresent()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Map.of("error", "El correo electrónico ya está en uso."));
            }

            // Crear un nuevo usuario con los datos del registro
            Usuario usuario = new Usuario();
            usuario.setNombre(registroRequest.getNombre());
            usuario.setCorreoElectronico(registroRequest.getCorreoElectronico());
            usuario.setContraseña(passwordEncoder.encode(registroRequest.getContraseña())); // Codifica la contraseña

            // Asignar el rol predeterminado de 'USER' al usuario
            Rol rolUsuario = rolService.findByNombre("USER")
                    .orElseThrow(() -> new RuntimeException("Rol 'USER' no encontrado."));
            usuario.setRol(rolUsuario);

            // Guardar el nuevo usuario en la base de datos
            Usuario nuevoUsuario = usuarioService.saveUsuario(usuario);

            // Respuesta de éxito al registrar el usuario
            return ResponseEntity.ok(Map.of("message", "Usuario registrado exitosamente.", "usuario", nuevoUsuario));

        } catch (DataIntegrityViolationException e) {
            // Maneja la excepción si el correo electrónico ya está registrado
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("error", "El correo electrónico ya está registrado."));
        } catch (Exception e) {
            // Maneja cualquier otro error interno del servidor
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error interno del servidor."));
        }
    }

    // Método para iniciar sesión de un usuario
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) {
        try {
            // Autentica al usuario con el correo y la contraseña proporcionados
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getCorreoElectronico(),
                            loginRequest.getContraseña()
                    )
            );
            // Establece la autenticación en el contexto de seguridad de la aplicación
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Generar el token JWT para el usuario autenticado
            String jwtToken = jwtTokenProvider.generateToken(authentication);
            Usuario usuario = (Usuario) authentication.getPrincipal(); // Obtiene los detalles del usuario autenticado

            // Retorna una respuesta con el token JWT y los datos del usuario
            return ResponseEntity.ok(new LoginResponse(jwtToken, "Login exitoso", usuario));

        } catch (BadCredentialsException e) {
            // Maneja el error si las credenciales son incorrectas
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Credenciales incorrectas."));
        } catch (Exception e) {
            // Maneja cualquier otro error interno del servidor
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error en el servidor."));
        }
    }

    // Método para validar un token JWT
    @GetMapping("/validate")
    public ResponseEntity<?> validateToken(@RequestHeader("Authorization") String token) {
        try {
            // Verifica si el token se envió en el encabezado de autorización
            if (token != null && token.startsWith("Bearer ")) {
                String tokenJwt = token.substring(7); // Remueve el prefijo "Bearer "
                if (jwtTokenProvider.validarToken(tokenJwt)) {
                    // Si el token es válido, retorna una respuesta exitosa
                    return ResponseEntity.ok().build();
                } else {
                    // Si el token es inválido o ha expirado
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido o expirado.");
                }
            } else {
                // Si el formato del encabezado de autorización no es correcto
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Encabezado de autorización inválido.");
            }
        } catch (Exception e) {
            // Maneja cualquier otro error interno del servidor
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error en la validación del token."));
        }
    }
}
