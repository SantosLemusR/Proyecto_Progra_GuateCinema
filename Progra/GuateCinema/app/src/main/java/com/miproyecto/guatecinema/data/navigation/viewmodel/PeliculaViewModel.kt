package com.miproyecto.guatecinema.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.miproyecto.guatecinema.model.Pelicula
import com.miproyecto.guatecinema.network.ApiService
import com.miproyecto.guatecinema.network.MovieRepository
import com.miproyecto.guatecinema.network.RetrofitClient
import kotlinx.coroutines.launch

class PeliculaViewModel(application: Application) : AndroidViewModel(application) {

    private val movieRepository: MovieRepository = MovieRepository(
        RetrofitClient.getClient(getApplication()).create(ApiService::class.java)
    )

    private val _movies = MutableLiveData<List<Pelicula>>()
    val movies: LiveData<List<Pelicula>> get() = _movies

    // Función para obtener películas usando un token JWT
    fun fetchPeliculas(token: String) {
        movieRepository.getMovies(token) { peliculas ->
            _movies.postValue(peliculas)  // Actualiza el LiveData con la lista de películas
        }
    }

    // Nueva función para obtener películas filtradas por cine
    fun fetchPeliculasPorCine(token: String, cineId: Int) {
        viewModelScope.launch {
            try {
                val peliculasResponse = movieRepository.getPeliculasPorCineSuspend(token, cineId)
                if (peliculasResponse.isSuccessful) {
                    val peliculas = peliculasResponse.body()
                    if (peliculas != null) {
                        _movies.postValue(peliculas)  // Actualiza el LiveData con las películas filtradas por cine
                    } else {
                        Log.e("PeliculaViewModel", "Error: respuesta nula")
                    }
                } else {
                    Log.e("PeliculaViewModel", "Error: ${peliculasResponse.code()}")
                }
            } catch (e: Exception) {
                Log.e("PeliculaViewModel", "Error al obtener películas por cine", e)
            }
        }
    }
}
