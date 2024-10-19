package com.miproyecto.guatecinema.model

import com.google.gson.annotations.SerializedName

data class Usuario(
    val id: Int,
    val nombre: String,
    @SerializedName("correoElectronico") val email: String,
    @SerializedName("contraseña") val password: String,
    @SerializedName("rol") val rol: Rol,  // Aquí mapeamos el objeto Rol
    val token: String? = null // Opcional, para almacenar el token JWT si lo necesitas
)