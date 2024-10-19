package com.miproyecto.guatecinema.model

data class DetalleFactura(
    val id: Int,
    val facturaId: Int,
    val boletoId: Int,
    val clienteId: Int,
    val precio: Double
)
