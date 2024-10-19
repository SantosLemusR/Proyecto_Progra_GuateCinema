package com.miproyecto.guatecinema

import android.os.Bundle
import android.view.ViewGroup
import android.widget.Button
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.squareup.picasso.Picasso

class SeleccionAsientosActivity : AppCompatActivity() {

    // Variable para almacenar el monto total de la compra
    private var totalAmount = 0
    // Lista que almacena los botones correspondientes a los asientos seleccionados
    private val selectedSeats = mutableListOf<Button>()
    // Almacena el número de boletos que el usuario ha seleccionado
    private var ticketCount = 0
    // Precio fijo de cada boleto
    private val ticketPrice = 34

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seleccion_asientos)

        // Se obtiene la referencia de las vistas en el layout: póster, título y detalles de la película
        val moviePoster: ImageView = findViewById(R.id.moviePoster)
        val movieTitleTextView: TextView = findViewById(R.id.movieTitle)
        val movieDetailsTextView: TextView = findViewById(R.id.movieDetails)

        // Se recuperan los datos pasados a esta actividad, como el título, horario, póster y cantidad de boletos
        val movieTitle = intent.getStringExtra("movie_title")
        val movieTime = intent.getStringExtra("movie_time")
        val moviePosterUrl = intent.getStringExtra("movie_image")
        ticketCount = intent.getIntExtra("ticket_count", 0)

        // Se asignan los datos de la película a las vistas correspondientes en la interfaz
        movieTitleTextView.text = movieTitle
        movieDetailsTextView.text = "Hora: $movieTime"

        // Se utiliza Picasso para cargar la imagen del póster en la vista ImageView
        if (!moviePosterUrl.isNullOrEmpty()) {
            Picasso.get()
                .load(moviePosterUrl)
                .placeholder(R.drawable.placeholder) // Imagen mostrada mientras se carga el póster
                .error(R.drawable.error_image) // Imagen que se muestra en caso de error
                .into(moviePoster)
        } else {
            moviePoster.setImageResource(R.drawable.ic_movie_placeholder) // Si no hay URL, se usa un marcador de posición
        }

        // Se crea dinámicamente el diseño de los asientos dentro del GridLayout
        val gridLayoutAsientos: GridLayout = findViewById(R.id.gridLayoutAsientos)
        createSeats(gridLayoutAsientos) // Llamada para generar los asientos

        // Se inicializa el texto del total a pagar, mostrando el monto actual
        val textViewTotal: TextView = findViewById(R.id.textViewTotal)
        textViewTotal.text = "Total (IVA incluido): Q$totalAmount"
    }

    // Función que genera los asientos de manera dinámica sin conectarse a una base de datos
    private fun createSeats(gridLayout: GridLayout) {
        val seatCount = 20 // Número total de asientos
        val rows = 5 // Número de filas de asientos
        val columns = 4 // Número de columnas de asientos

        gridLayout.rowCount = rows
        gridLayout.columnCount = columns

        // Bucle para crear un botón por cada asiento
        for (i in 1..seatCount) {
            val button = Button(this@SeleccionAsientosActivity)

            // Se define el diseño del botón dentro del GridLayout
            val params = GridLayout.LayoutParams().apply {
                width = 0 // El ancho es proporcional al espacio disponible
                height = ViewGroup.LayoutParams.WRAP_CONTENT
                columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f) // Especifica las columnas
                rowSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f) // Especifica las filas
                setMargins(8, 8, 8, 8) // Se agregan márgenes alrededor de cada asiento
            }

            // Se asigna el número del asiento como texto en el botón
            button.layoutParams = params
            button.text = "$i"
            button.setTextColor(ContextCompat.getColor(this, android.R.color.white)) // El texto es de color blanco
            button.setBackgroundResource(R.drawable.selector_seat_background) // Se asigna un fondo al botón

            // Inicialmente, todos los asientos están disponibles (color por defecto)
            button.setBackgroundColor(ContextCompat.getColor(this@SeleccionAsientosActivity, R.color.seat_default_color))

            // Se asigna una acción de clic para cada asiento
            button.setOnClickListener {
                toggleSeatSelection(button) // Llamada para manejar la selección del asiento
            }

            // Se agrega el botón (asiento) al GridLayout
            gridLayout.addView(button)
        }
    }

    // Función que alterna la selección y deselección de un asiento
    private fun toggleSeatSelection(button: Button) {
        if (selectedSeats.contains(button)) {
            // Si el asiento ya estaba seleccionado, se deselecciona
            button.setBackgroundColor(ContextCompat.getColor(this, R.color.seat_default_color)) // Vuelve al color original
            selectedSeats.remove(button) // Se elimina de la lista de asientos seleccionados
            totalAmount -= ticketPrice // Se resta el precio del boleto al total
        } else {
            // Verifica que no se seleccionen más asientos que los boletos comprados
            if (selectedSeats.size < ticketCount) {
                // Si el asiento no estaba seleccionado y aún hay espacio, se selecciona
                button.setBackgroundColor(ContextCompat.getColor(this, R.color.seat_selected_color)) // Cambia al color seleccionado
                selectedSeats.add(button) // Se añade a la lista de seleccionados
                totalAmount += ticketPrice // Se suma el precio del boleto al total
            } else {
                // Muestra un mensaje indicando que no se pueden seleccionar más asientos
                Toast.makeText(this, "Has alcanzado el límite de boletos seleccionados.", Toast.LENGTH_SHORT).show()
            }
        }

        // Actualiza el texto del total en la vista TextView
        val textViewTotal: TextView = findViewById(R.id.textViewTotal)
        textViewTotal.text = "Total (IVA incluido): Q$totalAmount"
    }
}
