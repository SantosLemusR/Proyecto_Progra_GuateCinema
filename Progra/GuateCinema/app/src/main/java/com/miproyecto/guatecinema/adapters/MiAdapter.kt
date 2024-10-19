package com.miproyecto.guatecinema.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.miproyecto.guatecinema.R
import com.miproyecto.guatecinema.databinding.ItemPeliculaBinding
import com.miproyecto.guatecinema.model.Pelicula
import com.squareup.picasso.Picasso

class MiAdapter(
    private var peliculas: List<Pelicula>,
    private val onItemClick: (Pelicula) -> Unit  // Callback para manejar los clics en las películas
) : RecyclerView.Adapter<MiAdapter.ViewHolder>() {

    // Función para actualizar los datos de la lista
    fun updateData(nuevasPeliculas: List<Pelicula>) {
        Log.d("MiAdapter", "updateData: Se actualizaron ${nuevasPeliculas.size} películas")
        peliculas = nuevasPeliculas
        notifyDataSetChanged()  // Refresca el RecyclerView
    }

    // Implementar correctamente el ViewHolder
    class ViewHolder(private val binding: ItemPeliculaBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(pelicula: Pelicula, onItemClick: (Pelicula) -> Unit) {
            // Vincular los datos de la película al diseño
            binding.tituloTextView.text = pelicula.titulo
            binding.generoTextView.text = pelicula.genero
            binding.clasificacionTextView.text = pelicula.clasificacion
            binding.sinopsisTextView.text = pelicula.sinopsis
            binding.horaTextView.text = pelicula.hora
            binding.salaTextView.text = pelicula.sala
            binding.repartoTextView.text = pelicula.reparto

            // Cargar la imagen del póster usando Picasso
            if (pelicula.posterUrl.isNullOrEmpty()) {
                binding.posterImageView.setImageResource(R.drawable.placeholder)  // Imagen de reemplazo si no hay URL
                Log.d("MiAdapter", "Poster URL vacía para la película: ${pelicula.titulo}")
            } else {
                Picasso.get()
                    .load(pelicula.posterUrl)
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.error_image)
                    .into(binding.posterImageView)

                Log.d("MiAdapter", "Cargando imagen de URL: ${pelicula.posterUrl} para la película: ${pelicula.titulo}")
            }

            // Manejar el clic en el elemento
            binding.root.setOnClickListener {
                onItemClick(pelicula)  // Invocar el callback con la película seleccionada
            }
        }
    }

    // Crear el ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        Log.d("MiAdapter", "onCreateViewHolder: Se creó un nuevo ViewHolder")
        val binding = ItemPeliculaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    // Asignar los datos a las vistas en el ViewHolder
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pelicula = peliculas[position]
        Log.d("MiAdapter", "onBindViewHolder: Vinculando la película ${pelicula.titulo} en la posición $position")
        holder.bind(pelicula, onItemClick)  // Pasar el callback a la función bind
    }

    // Indicar cuántos elementos tiene la lista
    override fun getItemCount(): Int {
        Log.d("MiAdapter", "getItemCount: Hay ${peliculas.size} películas en la lista")
        return peliculas.size
    }
}
