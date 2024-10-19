package com.miproyecto.guatecinema.model

data class Factura(
    val id: Int,
    val clienteId: Int,
    val usuarioId: Int,
    val fecha: String, // Formato de fecha
    val estado: String // Ejemplo: 'Pagado', 'Pendiente'
)
