package org.example.guatecinemaapi.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Component
public class JwtTokenProvider {

    // Llave secreta para firmar los tokens JWT
    // Esta clave debe ser más segura y se recomienda almacenarla en un lugar seguro (como una variable de entorno)
    private final String secretKey = "MjIxNjQ2NjU3MDg5NDc1MjM1NjAxMjA2NDg5MjM0NTY2NzIwMzI=";

    // Validez del token en milisegundos (1 hora)
    private final long validityInMilliseconds = 3600000; // 1 hora

    // Servicio para cargar detalles del usuario a partir del nombre de usuario
    private final UserDetailsService userDetailsService;

    // Constructor que inyecta el UserDetailsService
    public JwtTokenProvider(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    // Método para generar un token JWT a partir de la autenticación
    public String generateToken(Authentication authentication) {
        // Obtener el nombre de usuario (email)
        String username = authentication.getName();
        Date now = new Date(); // Fecha actual
        // Fecha de expiración del token (actual + validez en milisegundos)
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        // Crear y firmar el token JWT
        return Jwts.builder()
                .setSubject(username) // Definir el email como sujeto del token
                .setIssuedAt(now) // Fecha de emisión del token
                .setExpiration(validity) // Fecha de expiración del token
                .signWith(SignatureAlgorithm.HS256, secretKey) // Firmar con HS256 y la llave secreta
                .compact(); // Generar el token
    }

    // Método para validar un token JWT
    public boolean validarToken(String token) {
        try {
            // Parsear el token y verificar que es válido con la clave secreta
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true; // Token válido
        } catch (Exception e) {
            return false; // Token no válido
        }
    }

    // Método para obtener el nombre de usuario (email) del token
    public String getUserEmailFromToken(String token) {
        // Extraer los claims (información contenida en el token)
        Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
        return claims.getSubject(); // El "subject" es el email del usuario
    }

    // Método para obtener la autenticación a partir del token y los detalles del usuario
    public Authentication getAuthentication(String token, UserDetails userDetails) {
        // Crear un objeto de autenticación con los detalles del usuario y sus roles
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // Método para cargar los detalles del usuario a partir del nombre de usuario (email)
    public UserDetails loadUserByUsername(String username) {
        // Usa el UserDetailsService para cargar los detalles del usuario
        return userDetailsService.loadUserByUsername(username);
    }
}
