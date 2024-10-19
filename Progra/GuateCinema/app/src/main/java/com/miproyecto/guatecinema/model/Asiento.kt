package com.miproyecto.guatecinema.model

data class Asiento(
    val id: Int,
    val salaId: Int,
    val numero: Int,
    val tipo: String // Puede ser 'Normal' o 'VIP'
)
