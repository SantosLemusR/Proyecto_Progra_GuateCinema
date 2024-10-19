package com.miproyecto.guatecinema.model

data class Sala(
    val id: Int,
    val cineId: Int,
    val nombre: String,
    val capacidad: Int,
    val tipo: String // Ejemplo: 'Normal', 'VIP'
)
