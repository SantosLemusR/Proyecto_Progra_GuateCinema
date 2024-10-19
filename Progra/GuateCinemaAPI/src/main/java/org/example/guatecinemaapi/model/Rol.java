package org.example.guatecinemaapi.model;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Entity
@Table(name = "rol")
public class Rol implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nombre;

    @Override
    public String getAuthority() {
        return nombre;  // El nombre del rol (por ejemplo, "ROLE_USER")
    }

    // Getters y setters para el campo nombre...
}
