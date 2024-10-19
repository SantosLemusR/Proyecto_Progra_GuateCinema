package org.example.guatecinemaapi.config;

import org.example.guatecinemaapi.service.JwtTokenProvider;
import org.example.guatecinemaapi.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // Inyectar el servicio de usuarios (para autenticación)
    @Autowired
    private UsuarioService usuarioService;

    // Inyectar el proveedor de tokens JWT (para manejar la autenticación basada en JWT)
    @Autowired
    private JwtTokenProvider jwtTokenProvider; // Filtro JWT

    // Definir un bean para codificar contraseñas usando BCrypt
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Usa BCrypt para codificar las contraseñas
    }

    // Bean para gestionar la autenticación
    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        // Construir un AuthenticationManager configurando el servicio de detalles de usuario (UsuarioService) y el codificador de contraseñas (BCrypt)
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(usuarioService) // Usa UsuarioService para autenticación
                .passwordEncoder(passwordEncoder()) // Codificador de contraseñas
                .and()
                .build(); // Devuelve el AuthenticationManager configurado
    }

    // Configuración de seguridad para la aplicación
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Deshabilitar CSRF ya que se utiliza JWT (el cual no requiere protección CSRF)
                .csrf().disable()

                // Configurar autorizaciones para las diferentes rutas
                .authorizeRequests()

                // Permitir acceso público (sin autenticación) a las rutas de autenticación (como login y registro)
                .antMatchers("/api/v1/auth/**").permitAll()

                // Solo los usuarios con rol ADMIN pueden acceder a la gestión de usuarios
                .antMatchers("/api/v1/usuarios").hasRole("ADMIN")

                // Solo los usuarios con rol ADMIN pueden gestionar roles
                .antMatchers("/api/v1/roles/**").hasRole("ADMIN")

                // Cualquier usuario puede ver la información sobre cines
                .antMatchers("/api/v1/cines/**").permitAll()

                // Cualquier usuario puede acceder a la lista de películas
                .antMatchers("/api/v1/peliculas/**").permitAll()

                // Solo ADMIN puede ver reportes y estadísticas
                .antMatchers("/api/v1/reportes/**").hasRole("ADMIN")

                // Solo ADMIN y VENDEDOR pueden acceder a facturas y clientes
                .antMatchers("/api/facturas/**").hasAnyRole("ADMIN", "VENDEDOR")
                .antMatchers("/api/clientes/**").hasAnyRole("ADMIN", "VENDEDOR")

                // Cualquier otra solicitud necesita autenticación
                .anyRequest().authenticated()
                .and()

                // Configurar la política de sesión: No se crean sesiones en el servidor (JWT es stateless)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()

                // Agregar el filtro de JWT antes del filtro estándar de autenticación
                .addFilterBefore(new JwtTokenFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);

        return http.build(); // Construir y devolver el SecurityFilterChain
    }
}
