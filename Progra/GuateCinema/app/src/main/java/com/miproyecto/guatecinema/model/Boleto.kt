package com.miproyecto.guatecinema.model

data class Boleto(
    val id: Int,
    val funcionId: Int,
    val clienteId: Int,
    val asientoId: Int,
    val complementoId: Int,
    val precio: Double,
    val reservaId: Int,
    val usuarioId: Int
)
