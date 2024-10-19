package com.miproyecto.guatecinema.model

data class RegistroRequest(
    val nombre: String,
    val correoElectronico: String,
    val contraseña: String
)
