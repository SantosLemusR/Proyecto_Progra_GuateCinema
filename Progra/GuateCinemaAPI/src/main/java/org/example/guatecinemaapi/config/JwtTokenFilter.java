package org.example.guatecinemaapi.config;

import org.example.guatecinemaapi.service.JwtTokenProvider;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    public JwtTokenFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String token = getTokenFromRequest(request);

        try {
            if (token != null && jwtTokenProvider.validarToken(token)) {
                // Extraer detalles del usuario a partir del token
                String userEmail = jwtTokenProvider.getUserEmailFromToken(token);
                UserDetails userDetails = jwtTokenProvider.loadUserByUsername(userEmail);

                // Crear autenticación basada en los detalles del usuario
                Authentication authentication = jwtTokenProvider.getAuthentication(token, userDetails);

                // Establecer el contexto de seguridad
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception ex) {
            logger.error("Error al autenticar al usuario: " + ex.getMessage());
            // En caso de error, limpiar el contexto de seguridad
            SecurityContextHolder.clearContext();
        }

        // Continuar con el siguiente filtro
        filterChain.doFilter(request, response);
    }

    // Método para extraer el token del encabezado de autorización
    private String getTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);  // Extraer el token sin "Bearer "
        }
        return null;
    }
}
