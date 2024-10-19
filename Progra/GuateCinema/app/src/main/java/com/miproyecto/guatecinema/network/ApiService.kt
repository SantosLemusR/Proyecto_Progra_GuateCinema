package com.miproyecto.guatecinema.network

import com.miproyecto.guatecinema.model.*
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import retrofit2.http.Header
import retrofit2.http.Path

interface ApiService {

    // Valida el token JWT proporcionado.
    @GET("auth/validate")
    fun validateToken(
        @Header("Authorization") token: String  // Token JWT
    ): Call<Void>

    // Inicia sesión para un usuario.
    @POST("auth/login")
    fun loginUser(@Body loginRequest: LoginRequest): Call<LoginResponse>

    // Registra un nuevo usuario.
    @POST("auth/register")
    fun registerUser(@Body registroRequest: RegistroRequest): Call<Usuario>

    // Obtiene la lista de películas.
    @GET("peliculas")
    fun getMovies(
        @Header("Authorization") token: String  // Token JWT
    ): Call<List<Pelicula>>

    // Obtiene la lista de cines.
    @GET("cines")
    fun getCines(
        @Header("Authorization") token: String  // Token JWT
    ): Call<List<Cine>>

    // Obtiene los detalles de una función específica.
    @GET("funciones/{id}")
    fun getFuncionDetails(@Path("id") funcionId: Int): Call<Funcion>

    // Obtiene la lista de asientos ocupados en una sala específica.
    @GET("asientos/sala/{salaId}")
    fun getAsientosPorSala(@Path("salaId") salaId: Int): Call<List<Asiento>>

    // Crea un nuevo boleto.
    @POST("boletos")
    fun createBoleto(@Body boleto: Boleto): Call<Boleto>

    // Obtiene la lista de reservas.
    @GET("reservas")
    fun getReservas(): Call<List<Reserva>>

    // Crea una nueva factura.
    @POST("facturas")
    fun createFactura(@Body factura: Factura): Call<Factura>

    // Obtiene películas filtradas por cine (usando corrutinas)
    @GET("peliculas")
    suspend fun getMoviesByCineSuspend(
        @Query("cineId") cineId: Int,
        @Header("Authorization") token: String  // Token JWT
    ): Response<List<Pelicula>>

    // Obtiene películas filtradas por cine (versión sin corrutinas)
    @GET("peliculas")
    fun getMoviesByCine(
        @Query("cineId") cineId: Int,
        @Header("Authorization") token: String  // Token JWT
    ): Call<List<Pelicula>>
}
