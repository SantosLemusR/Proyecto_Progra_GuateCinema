package org.example.guatecinemaapi.config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.security.Keys;

// Clase JwtTokenVerifier: encargada de verificar y validar tokens JWT (JSON Web Tokens)
public class JwtTokenVerifier {

    // Método que verifica la validez de un token JWT usando una clave secreta
    public static void verifyJwtToken(String token, String secretKey) {
        try {
            // Crear un parser para el token JWT, utilizando la clave secreta
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(secretKey.getBytes()) // Usar la clave secreta convertida a bytes
                    .build()
                    .parseClaimsJws(token) // Parsear y verificar el token con la clave
                    .getBody(); // Obtener el contenido (claims) del token

            // Imprimir el encabezado del token
            System.out.println("Encabezado del token: " + Jwts.parserBuilder()
                    .setSigningKey(secretKey.getBytes()) // Usar nuevamente la clave secreta
                    .build()
                    .parseClaimsJws(token).getHeader()); // Obtener y mostrar el encabezado del token

            // Imprimir el contenido (claims) del token
            System.out.println("Contenido (Payload): " + claims);

            // Mensaje que indica que el token fue verificado correctamente
            System.out.println("Token verificado correctamente.");
        } catch (Exception e) {
            // Manejo de excepciones si el token es inválido o ha expirado
            System.out.println("Error verificando el token: " + e.getMessage());
        }
    }

    // Método principal para probar la verificación del token
    public static void main(String[] args) {
        // Token y clave secreta de ejemplo
        String token = "tuTokenAqui"; // Reemplaza con el token JWT a verificar
        String secretKey = "claveSecreta1234567890"; // Reemplaza con la clave secreta usada para firmar el token

        // Llamar al método para verificar el token
        verifyJwtToken(token, secretKey);
    }
}
