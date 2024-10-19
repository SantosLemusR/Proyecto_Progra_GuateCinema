package com.miproyecto.guatecinema.model

data class Complemento(
    val id: Int,
    val nombre: String,
    val precio: Double,
    val peliculaId: Int, // Relación con la película
    val promocionId: Int? // Relación opcional con la promoción
)
