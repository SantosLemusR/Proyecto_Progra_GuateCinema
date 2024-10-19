package com.miproyecto.guatecinema.network

import com.miproyecto.guatecinema.model.Pelicula
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// La clase MovieRepository es responsable de la lógica de obtención de películas desde el API.
// El apiService pasado como parámetro es una interfaz que define las solicitudes de la API.
class MovieRepository(private val apiService: ApiService) {

    // La función getMovies realiza una llamada asíncrona para obtener una lista de películas.
    // Requiere un token de autenticación y un callback (onMoviesFetched) que será invocado cuando la respuesta sea recibida.
    fun getMovies(token: String, onMoviesFetched: (List<Pelicula>) -> Unit) {
        // Aquí se está configurando la llamada HTTP a la API usando Retrofit.
        // El token de autenticación se pasa en el header usando el formato "Bearer".
        val call = apiService.getMovies("Bearer $token")

        // La función enqueue inicia la solicitud de forma asíncrona.
        // Se usa un objeto Callback para manejar la respuesta y los errores potenciales.
        call.enqueue(object : Callback<List<Pelicula>> {
            // Este método es llamado cuando el servidor devuelve una respuesta exitosa.
            override fun onResponse(call: Call<List<Pelicula>>, response: Response<List<Pelicula>>) {
                // Aquí se verifica si la respuesta fue exitosa, es decir, si el código HTTP está en el rango 200.
                if (response.isSuccessful) {
                    // Si la respuesta contiene un cuerpo (body), se ejecuta el callback onMoviesFetched con la lista de películas.
                    response.body()?.let {
                        onMoviesFetched(it)
                    }
                }
            }

            // Este método se invoca cuando ocurre un fallo en la solicitud (problemas de red, errores del servidor, etc.).
            override fun onFailure(call: Call<List<Pelicula>>, t: Throwable) {
                // Se imprime la traza del error para ayudar en la depuración.
                t.printStackTrace()
            }
        })
    }

    // La función getPeliculasPorCineSuspend realiza una solicitud similar, pero usa corrutinas y suspend.
    // Se utiliza para manejar llamadas de red de manera más fluida en un entorno con corrutinas.
    // Es suspendida, lo que significa que puede ser invocada desde una corrutina o función suspend.
    suspend fun getPeliculasPorCineSuspend(token: String, cineId: Int): Response<List<Pelicula>> {
        // La llamada HTTP se hace de manera síncrona aquí, ya que las corrutinas permiten evitar el uso de callbacks.
        // Devuelve directamente la Response obtenida de la API, que contiene el código HTTP y el cuerpo.
        return apiService.getMoviesByCineSuspend(cineId, "Bearer $token")
    }
}

