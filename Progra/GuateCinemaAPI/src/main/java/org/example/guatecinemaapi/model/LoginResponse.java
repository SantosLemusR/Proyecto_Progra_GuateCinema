package org.example.guatecinemaapi.model;

public class LoginResponse {
    private String token;
    private String message; // Mensaje opcional de éxito o error
    private Usuario usuario; // Información del usuario (asegúrate de no incluir datos sensibles como la contraseña)

    // Constructor con todos los campos
    public LoginResponse(String token, String message, Usuario usuario) {
        this.token = token;
        this.message = message;
        this.usuario = usuario;
    }

    // Constructor con solo el token (si no se necesita más información)
    public LoginResponse(String token) {
        this.token = token;
    }

    // Getters y Setters
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
