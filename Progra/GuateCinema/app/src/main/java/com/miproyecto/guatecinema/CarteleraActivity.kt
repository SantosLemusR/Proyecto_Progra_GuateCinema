package com.miproyecto.guatecinema

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.miproyecto.guatecinema.adapters.MiAdapter
import com.miproyecto.guatecinema.databinding.ActivityCarteleraBinding
import com.miproyecto.guatecinema.model.Cine
import com.miproyecto.guatecinema.model.Pelicula
import com.miproyecto.guatecinema.network.ApiService
import com.miproyecto.guatecinema.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CarteleraActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCarteleraBinding
    private lateinit var miAdapter: MiAdapter
    private lateinit var apiService: ApiService
    private var selectedCineId: Int? = null

    // Tag para los logs
    private val TAG = "CarteleraActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCarteleraBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configurar Retrofit y ApiService
        apiService = RetrofitClient.getClient(this).create(ApiService::class.java)

        // Botón de "Atrás"
        // Botón de "Atrás"
        val backButton = findViewById<ImageButton>(R.id.buttonBack)
        backButton.setOnClickListener {
            val intent = Intent(this@CarteleraActivity, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        // Verificar si el usuario está autenticado
        val token = getToken()
        if (token.isNullOrEmpty()) {
            redirectToLogin()
            return
        }

        // Configurar RecyclerView
        binding.recyclerViewCartelera.layoutManager = LinearLayoutManager(this)
        miAdapter = MiAdapter(emptyList()) { pelicula -> onPeliculaSelected(pelicula) }
        binding.recyclerViewCartelera.adapter = miAdapter

        // Configurar el spinner
        val cineSpinner: Spinner = binding.cineSpinner
        cargarCines(cineSpinner, token)

        // Configurar el listener del spinner
        cineSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                val cine = parent.getItemAtPosition(position) as Cine
                selectedCineId = cine.id
                Log.d(TAG, "Cine seleccionado: $selectedCineId") // Verificar ID seleccionado
                cargarPeliculasPorCine(selectedCineId, token) // Cargar películas según el cine seleccionado
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // No hacer nada o mostrar un mensaje si es necesario
            }
        }
    }

    // Función para obtener el token JWT
    private fun getToken(): String? {
        val sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        return sharedPreferences.getString("auth_token", null)
    }

    // Redirigir al usuario a la pantalla de inicio de sesión si no está autenticado
    private fun redirectToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish() // Cerrar la CarteleraActivity
    }

    // Cargar cines desde la API
    private fun cargarCines(cineSpinner: Spinner, token: String) {
        apiService.getCines("Bearer $token").enqueue(object : Callback<List<Cine>> {
            override fun onResponse(call: Call<List<Cine>>, response: Response<List<Cine>>) {
                if (response.isSuccessful) {
                    val cines = response.body() ?: emptyList()
                    val cineAdapter = ArrayAdapter(
                        this@CarteleraActivity,
                        android.R.layout.simple_spinner_item,
                        cines
                    )
                    cineAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    cineSpinner.adapter = cineAdapter
                } else {
                    // Manejo de errores detallado
                    Log.e(TAG, "Error al obtener cines: ${response.errorBody()?.string()}")
                    Toast.makeText(this@CarteleraActivity, "Error al cargar cines", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Cine>>, t: Throwable) {
                Log.e(TAG, "Error al conectar con la API de cines: ${t.message}")
                Toast.makeText(this@CarteleraActivity, "Error de conexión: ${t.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    // Cargar películas filtradas por cine desde la API
    private fun cargarPeliculasPorCine(cineId: Int?, token: String) {
        Log.d(TAG, "Cargando películas para el cine ID: $cineId")
        if (cineId != null) { // Verificar que cineId no sea nulo antes de hacer la llamada
            apiService.getMoviesByCine(cineId, "Bearer $token")
                .enqueue(object : Callback<List<Pelicula>> {
                    override fun onResponse(call: Call<List<Pelicula>>, response: Response<List<Pelicula>>) {
                        if (response.isSuccessful) {
                            val peliculas = response.body() ?: emptyList()
                            Log.d(TAG, "Películas recibidas: ${peliculas.size}")

                            // Filtrar las películas que pertenecen al cine seleccionado
                            val peliculasFiltradas = peliculas.filter { pelicula ->
                                pelicula.cinePeliculas.any { cinePelicula -> cinePelicula.cineId == cineId }
                            }

                            Log.d(TAG, "Películas después de filtrar: ${peliculasFiltradas.size}")
                            miAdapter.updateData(peliculasFiltradas) // Actualizar el adaptador
                        } else {
                            Log.e(TAG, "Error al obtener películas: ${response.errorBody()?.string()}")
                            Toast.makeText(this@CarteleraActivity, "Error al cargar películas", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<List<Pelicula>>, t: Throwable) {
                        Log.e(TAG, "Error al conectar con la API de películas: ${t.message}")
                        Toast.makeText(this@CarteleraActivity, "Error de conexión", Toast.LENGTH_SHORT).show()
                    }
                })
        } else {
            Log.e(TAG, "cineId es nulo, no se pueden cargar las películas")
            Toast.makeText(this, "Seleccione un cine válido", Toast.LENGTH_SHORT).show()
        }
    }

    // Lógica cuando se selecciona una película
    private fun onPeliculaSelected(pelicula: Pelicula) {
        // Verificar que la película tenga una sala válida
        val salaString = pelicula.sala?.replace("Sala ", "") // Eliminar el texto "Sala " de la cadena
        val salaNumero = salaString?.toIntOrNull()

        // Log para verificar los detalles de la película seleccionada
        Log.d(TAG, "Pelicula seleccionada: ${pelicula.titulo}, Trailer URL: ${pelicula.trailer}")

        // Verificar que todos los datos importantes de la película estén presentes
        if (salaNumero != null && !pelicula.titulo.isNullOrEmpty() && !pelicula.hora.isNullOrEmpty() && !pelicula.trailer.isNullOrEmpty()) {
            // Crear el intent para pasar a SeleccionBoletosActivity
            val intent = Intent(this, SeleccionBoletosActivity::class.java).apply {
                putExtra("movie_title", pelicula.titulo)
                putExtra("movie_synopsis", pelicula.sinopsis)
                putExtra("movie_time", pelicula.hora)
                putExtra("movie_room", salaNumero)  // Usar el número de sala como entero
                putExtra("movie_image", pelicula.posterUrl)
                putExtra("trailer_url", pelicula.trailer)  // Usar "trailer" correctamente
                putExtra("cine_id", selectedCineId)  // Pasar el cine seleccionado
            }
            Log.d(TAG, "Iniciando SeleccionBoletosActivity para la película: ${pelicula.titulo}")
            // Iniciar la actividad de selección de boletos
            startActivity(intent)
        } else {
            // Manejar caso de error con un mensaje específico si faltan datos
            Toast.makeText(this, "Datos de película incompletos o inválidos", Toast.LENGTH_SHORT).show()
        }
    }
}
