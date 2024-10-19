package com.miproyecto.guatecinema.model

data class Cine(
    val id: Int,  // Cambiado de Long a Int
    val nombre: String,
    val direccion: String,
    val ubicacion: String
) {
    // Sobrescribir toString para mostrar el nombre del cine en el Spinner
    override fun toString(): String {
        return nombre
    }
}
