package com.miproyecto.guatecinema.model

data class LoginResponse(
    val token: String,
    val usuario: Usuario
)
