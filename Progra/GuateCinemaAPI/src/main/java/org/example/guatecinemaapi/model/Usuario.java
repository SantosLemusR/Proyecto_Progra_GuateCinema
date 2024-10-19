package org.example.guatecinemaapi.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;

// La clase Usuario es una entidad de JPA que implementa la interfaz UserDetails para ser usada en la autenticación con Spring Security
@Entity
@Table(name = "usuario")
public class Usuario implements UserDetails {

    // Identificador único para cada usuario
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // El correo electrónico del usuario debe ser único y no puede ser nulo
    @Column(nullable = false, unique = true)
    private String correoElectronico;

    // La contraseña del usuario no puede ser nula
    @Column(nullable = false)
    private String contraseña;

    // El nombre del usuario
    @Column(nullable = false)
    private String nombre;

    // Relación con la entidad Rol. Cada usuario tiene un rol, y ese rol debe existir en la base de datos
    @ManyToOne
    @JoinColumn(name = "rol_id", nullable = false)
    private Rol rol;  // Aquí se asume que la clase Rol implementa GrantedAuthority

    // Getters y Setters para los atributos del usuario
    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    // Implementación de los métodos requeridos por UserDetails para la autenticación con Spring Security

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Devolvemos una lista con el rol del usuario, ya que se utiliza como autoridad
        return Collections.singletonList(rol);
    }

    @Override
    public String getPassword() {
        // Devolvemos la contraseña del usuario (encriptada) cuando se llama a este método
        return contraseña;
    }

    @Override
    public String getUsername() {
        // En este caso, el nombre de usuario es el correo electrónico
        return correoElectronico;
    }

    @Override
    public boolean isAccountNonExpired() {
        // Aquí se puede agregar lógica para verificar si la cuenta ha expirado, pero retornamos true para indicar que no ha expirado
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // Verificamos si la cuenta está bloqueada, pero retornamos true para indicar que no está bloqueada
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // Se puede verificar si las credenciales han expirado, pero retornamos true para indicar que no han expirado
        return true;
    }

    @Override
    public boolean isEnabled() {
        // Aquí se puede agregar lógica para habilitar/deshabilitar usuarios, pero retornamos true para indicar que el usuario está habilitado
        return true;
    }
}
