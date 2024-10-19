package com.miproyecto.guatecinema.model

data class Pelicula(
    val id: Int,
    val titulo: String,
    val genero: String,
    val clasificacion: String,
    val sinopsis: String,
    val trailer: String,
    val reparto: String,
    val hora: String,
    val sala: String,
    val posterUrl: String,
    val complementos: List<Complemento>?,
    val cinePeliculas: List<CinePelicula> // Relación con cines
)
