package com.miproyecto.guatecinema.model

import com.google.gson.annotations.SerializedName

data class Rol(
    @SerializedName("authority") val authority: String
)