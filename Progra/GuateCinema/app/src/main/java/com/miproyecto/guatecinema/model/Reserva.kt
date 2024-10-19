package com.miproyecto.guatecinema.model

data class Reserva(
    val id: Int,
    val funcionId: Int,
    val clienteId: Int,
    val asientoId: Int,
    val estado: String, // Ejemplo: 'Confirmada', 'Cancelada'
    val fechaReserva: String,
    val boletoId: Int,
    val tipoReserva: String // Ejemplo: 'Normal', 'App'
)
