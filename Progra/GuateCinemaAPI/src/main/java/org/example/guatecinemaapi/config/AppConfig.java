package org.example.guatecinemaapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

// La clase AppConfig es una clase de configuración que permite definir beans reutilizables
@Configuration
public class AppConfig {

    // Define un bean de BCryptPasswordEncoder, que es un componente que Spring podrá inyectar en otros lugares
    @Bean
    public BCryptPasswordEncoder customPasswordEncoder() {  // Cambia el nombre del bean aquí para mayor claridad
        // BCryptPasswordEncoder es utilizado para codificar contraseñas de manera segura.
        // Genera un hash que incluye un "salt" (valor aleatorio) para proteger las contraseñas.
        return new BCryptPasswordEncoder();
    }
}
