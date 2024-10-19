package com.miproyecto.guatecinema

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso

// La clase SeleccionBoletosActivity maneja la selección de boletos (adulto y niño) y la navegación a la siguiente pantalla
class SeleccionBoletosActivity : AppCompatActivity() {

    // Variables para almacenar la cantidad de boletos y el monto total
    private var adultTicketCount = 0
    private var childTicketCount = 0
    private var totalAmount = 0
    private val ticketPriceAdult = 34  // Precio del boleto para adultos
    private val ticketPriceChild = 25  // Precio del boleto para niños
    private val TAG = "SelBoletosActivity"  // Tag para los logs, ayuda a identificar la actividad

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seleccion_boletos)
        Log.d(TAG, "onCreate: Actividad creada")  // Log para verificar cuándo se crea la actividad

        // Obtener la información de la película seleccionada pasada desde la actividad anterior (Cartelera)
        val movieTitle = intent.getStringExtra("movie_title")
        val movieSynopsis = intent.getStringExtra("movie_synopsis")
        val movieTime = intent.getStringExtra("movie_time")
        val movieRoom = intent.getIntExtra("movie_room", -1) // Obtener el número de sala como entero
        val moviePosterUrl = intent.getStringExtra("movie_image")
        val trailerUrl = intent.getStringExtra("trailer_url")

        // Verificar que los datos de la película no sean nulos o inválidos
        if (movieTitle == null || movieTime == null || movieRoom == -1) {
            Log.e(TAG, "Error: Faltan datos de la película")  // Si falta información, se registra el error
            Toast.makeText(this, "Error al cargar los detalles de la película", Toast.LENGTH_SHORT).show()
            finish()  // Cierra la actividad si faltan datos críticos
            return
        }

        // Log para verificar los datos recibidos
        Log.d(TAG, "Datos recibidos: Título: $movieTitle, Sinopsis: $movieSynopsis, Hora: $movieTime, Sala: $movieRoom, Poster URL: $moviePosterUrl, Trailer URL: $trailerUrl")

        // Referencias a los elementos del layout de la actividad
        val textMovieTitle: TextView = findViewById(R.id.textMovieTitle)
        val textMovieDetails: TextView = findViewById(R.id.textMovieDetails)
        val textMovieSynopsis: TextView = findViewById(R.id.textMovieSinopsis)
        val moviePoster: ImageView = findViewById(R.id.moviePoster)
        val buttonAdultPlus: Button = findViewById(R.id.buttonAdultPlus)
        val buttonAdultMinus: Button = findViewById(R.id.buttonAdultMinus)
        val textAdultCount: TextView = findViewById(R.id.textAdultCount)
        val buttonChildPlus: Button = findViewById(R.id.buttonChildPlus)
        val buttonChildMinus: Button = findViewById(R.id.buttonChildMinus)
        val textChildCount: TextView = findViewById(R.id.textChildCount)
        val textTotal: TextView = findViewById(R.id.textTotal)
        val buttonSiguiente: Button = findViewById(R.id.buttonSiguiente)
        val buttonTrailer: Button = findViewById(R.id.buttonTrailer)

        // Mostrar la información de la película seleccionada en los TextViews correspondientes
        textMovieTitle.text = movieTitle
        textMovieDetails.text = "Hora: $movieTime\nSala: $movieRoom"
        textMovieSynopsis.text = movieSynopsis ?: "Sinopsis no disponible"

        // Cargar el póster de la película usando la biblioteca Picasso, que facilita la carga de imágenes desde URL
        if (!moviePosterUrl.isNullOrEmpty()) {
            Picasso.get()
                .load(moviePosterUrl)
                .placeholder(R.drawable.placeholder)  // Imagen mostrada mientras se carga el póster
                .error(R.drawable.error_image)  // Imagen mostrada si hay un error al cargar el póster
                .into(moviePoster)  // Colocar la imagen cargada en el ImageView
        } else {
            moviePoster.setImageResource(R.drawable.ic_movie_placeholder)  // Imagen predeterminada si no hay URL
        }

        // Función interna para actualizar el total en función de los boletos seleccionados
        fun updateTotal() {
            totalAmount = (adultTicketCount * ticketPriceAdult) + (childTicketCount * ticketPriceChild)  // Calcular el total
            textTotal.text = "Total: Q$totalAmount"  // Mostrar el total en el TextView
            Log.d(TAG, "Total actualizado: $totalAmount")  // Log para verificar el total calculado
        }

        // Configurar los botones para incrementar y decrementar la cantidad de boletos de adulto
        buttonAdultPlus.setOnClickListener {
            adultTicketCount++  // Incrementa la cantidad de boletos de adulto
            textAdultCount.text = adultTicketCount.toString()  // Muestra la cantidad actualizada
            updateTotal()  // Actualiza el total
        }

        buttonAdultMinus.setOnClickListener {
            if (adultTicketCount > 0) {  // Asegurarse de que no se pueda tener una cantidad negativa
                adultTicketCount--  // Decrementa la cantidad de boletos de adulto
                textAdultCount.text = adultTicketCount.toString()  // Actualiza el TextView
                updateTotal()  // Actualiza el total
            }
        }

        // Configurar los botones para incrementar y decrementar la cantidad de boletos de niños
        buttonChildPlus.setOnClickListener {
            childTicketCount++  // Incrementa la cantidad de boletos de niño
            textChildCount.text = childTicketCount.toString()  // Muestra la cantidad actualizada
            updateTotal()  // Actualiza el total
        }

        buttonChildMinus.setOnClickListener {
            if (childTicketCount > 0) {  // Evitar que la cantidad sea negativa
                childTicketCount--  // Decrementa la cantidad de boletos de niño
                textChildCount.text = childTicketCount.toString()  // Actualiza el TextView
                updateTotal()  // Actualiza el total
            }
        }

        // Configurar el botón para validar y pasar a la selección de asientos
        buttonSiguiente.setOnClickListener {
            // Verificar si se seleccionaron boletos antes de continuar
            if (adultTicketCount <= 0 && childTicketCount <= 0) {
                Toast.makeText(this, "Debes seleccionar al menos un boleto para continuar", Toast.LENGTH_SHORT).show()
            } else {
                // Crear un Intent para navegar a la actividad SeleccionAsientosActivity, pasando los datos necesarios
                val intent = Intent(this, SeleccionAsientosActivity::class.java).apply {
                    putExtra("movie_title", movieTitle)
                    putExtra("movie_synopsis", movieSynopsis)
                    putExtra("movie_time", movieTime)
                    putExtra("movie_room", movieRoom)
                    putExtra("movie_image", moviePosterUrl)
                    putExtra("adult_ticket_count", adultTicketCount)
                    putExtra("child_ticket_count", childTicketCount)
                    putExtra("ticket_count", adultTicketCount + childTicketCount)  // Pasar la cantidad total de boletos
                }
                Log.d(TAG, "Navegando a SeleccionAsientosActivity con $adultTicketCount boletos adultos y $childTicketCount boletos niños")
                startActivity(intent)  // Iniciar la nueva actividad
            }
        }

        // Configurar el botón para abrir el tráiler en YouTube o en el navegador
        buttonTrailer.setOnClickListener {
            if (trailerUrl.isNullOrEmpty()) {  // Verificar que exista una URL válida para el tráiler
                Toast.makeText(this, "Tráiler no disponible", Toast.LENGTH_SHORT).show()
            } else {
                try {
                    // Extraer el ID del video de YouTube desde la URL del tráiler
                    val videoId = trailerUrl.substringAfterLast("=")
                    val youtubeIntent = Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:$videoId"))

                    // Verificar si hay una aplicación de YouTube instalada, si no, abrir en el navegador
                    val intentToUse = if (youtubeIntent.resolveActivity(packageManager) != null) {
                        Log.d(TAG, "Abriendo en YouTube: $trailerUrl")
                        youtubeIntent
                    } else {
                        Log.d(TAG, "Abriendo en navegador: $trailerUrl")
                        Intent(Intent.ACTION_VIEW, Uri.parse(trailerUrl))
                    }

                    // Verificar si existe alguna aplicación para manejar el intent
                    if (intentToUse.resolveActivity(packageManager) != null) {
                        startActivity(intentToUse)  // Abrir el tráiler
                    } else {
                        Log.e(TAG, "No hay aplicaciones para abrir el tráiler")
                        Toast.makeText(this, "No hay aplicaciones para abrir el tráiler", Toast.LENGTH_SHORT).show()
                    }

                } catch (e: Exception) {
                    Log.e(TAG, "Error al abrir el tráiler: ${e.message}")
                    Toast.makeText(this, "Error al abrir el tráiler", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
