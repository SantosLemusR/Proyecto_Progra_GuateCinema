package org.example.guatecinemaapi.model;
// Define el paquete al que pertenece esta clase, que organiza las clases relacionadas con los modelos en el proyecto.

public class RegistroRequest {
    // Define la clase pública RegistroRequest. Esta clase actuará como un modelo de datos para manejar solicitudes de registro
    // que contengan un nombre, correo electrónico y contraseña.

    private String nombre;
    // Declara una variable privada de tipo String para almacenar el nombre del usuario.

    private String correoElectronico;
    // Declara una variable privada de tipo String para almacenar el correo electrónico del usuario.

    private String contraseña;
    // Declara una variable privada de tipo String para almacenar la contraseña del usuario.

    // Getters y setters
    // Los métodos getter y setter permiten acceder y modificar los valores de los campos privados desde fuera de la clase.

    public String getNombre() {
        // Método getter para obtener el valor de la variable "nombre".

        return nombre;
        // Retorna el valor actual de la variable "nombre".
    }

    public void setNombre(String nombre) {
        // Método setter para establecer el valor de la variable "nombre".

        this.nombre = nombre;
        // Asigna el valor del parámetro "nombre" a la variable de instancia "nombre" de la clase.
    }

    public String getCorreoElectronico() {
        // Método getter para obtener el valor de la variable "correoElectronico".

        return correoElectronico;
        // Retorna el valor actual de la variable "correoElectronico".
    }

    public void setCorreoElectronico(String correoElectronico) {
        // Método setter para establecer el valor de la variable "correoElectronico".

        this.correoElectronico = correoElectronico;
        // Asigna el valor del parámetro "correoElectronico" a la variable de instancia "correoElectronico".
    }

    public String getContraseña() {
        // Método getter para obtener el valor de la variable "contraseña".

        return contraseña;
        // Retorna el valor actual de la variable "contraseña".
    }

    public void setContraseña(String contraseña) {
        // Método setter para establecer el valor de la variable "contraseña".

        this.contraseña = contraseña;
        // Asigna el valor del parámetro "contraseña" a la variable de instancia "contraseña".
    }
}
