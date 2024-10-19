package com.miproyecto.guatecinema.model

data class Funcion(
    val id: Int,
    val peliculaId: Int,
    val salaId: Int,
    val horario: String // Formato de hora
)
