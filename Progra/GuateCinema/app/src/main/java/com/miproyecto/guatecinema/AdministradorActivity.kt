package com.miproyecto.guatecinema

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class AdministradorActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_administrador)

        // Botones de las distintas gestiones
        val btnVerUsuarios: Button = findViewById(R.id.btnVerUsuarios)
        val btnEditarRoles: Button = findViewById(R.id.btnEditarRoles)
        val btnEliminarUsuarios: Button = findViewById(R.id.btnEliminarUsuarios)
        val btnAgregarCine: Button = findViewById(R.id.btnAgregarCine)
        val btnEditarCine: Button = findViewById(R.id.btnEditarCine)
        val btnEliminarCine: Button = findViewById(R.id.btnEliminarCine)
        val btnAgregarPelicula: Button = findViewById(R.id.btnAgregarPelicula)
        val btnEditarPelicula: Button = findViewById(R.id.btnEditarPelicula)
        val btnEliminarPelicula: Button = findViewById(R.id.btnEliminarPelicula)
        val btnVerReportes: Button = findViewById(R.id.btnVerReportes)
        val btnVerEstadisticas: Button = findViewById(R.id.btnVerEstadisticas)
        val btnCerrarSesion: Button = findViewById(R.id.btnCerrarSesion)

        // Expandables para las secciones
        val expandableUsuarios: LinearLayout = findViewById(R.id.expandableGestionUsuarios)
        val expandableCines: LinearLayout = findViewById(R.id.expandableGestionCines)
        val expandablePeliculas: LinearLayout = findViewById(R.id.expandableGestionPeliculas)
        val expandableReportes: LinearLayout = findViewById(R.id.expandableGestionReportes)

        // Títulos de las secciones
        val titleUsuarios: TextView = findViewById(R.id.titleGestionUsuarios)
        val titleCines: TextView = findViewById(R.id.titleGestionCines)
        val titlePeliculas: TextView = findViewById(R.id.titleGestionPeliculas)
        val titleReportes: TextView = findViewById(R.id.titleGestionReportes)

        // Configuración de los títulos para expandir/colapsar las secciones
        titleUsuarios.setOnClickListener {
            toggleVisibility(expandableUsuarios)
        }

        titleCines.setOnClickListener {
            toggleVisibility(expandableCines)
        }

        titlePeliculas.setOnClickListener {
            toggleVisibility(expandablePeliculas)
        }

        titleReportes.setOnClickListener {
            toggleVisibility(expandableReportes)
        }

        // Lógica para los botones
        btnVerUsuarios.setOnClickListener {
            Toast.makeText(this, "Ver Usuarios", Toast.LENGTH_SHORT).show()
        }

        btnEditarRoles.setOnClickListener {
            Toast.makeText(this, "Editar Roles", Toast.LENGTH_SHORT).show()
        }

        btnEliminarUsuarios.setOnClickListener {
            Toast.makeText(this, "Eliminar Usuarios", Toast.LENGTH_SHORT).show()
        }

        btnAgregarCine.setOnClickListener {
            Toast.makeText(this, "Agregar Cine", Toast.LENGTH_SHORT).show()
        }

        btnEditarCine.setOnClickListener {
            Toast.makeText(this, "Editar Cine", Toast.LENGTH_SHORT).show()
        }

        btnEliminarCine.setOnClickListener {
            Toast.makeText(this, "Eliminar Cine", Toast.LENGTH_SHORT).show()
        }

        btnAgregarPelicula.setOnClickListener {
            Toast.makeText(this, "Agregar Película", Toast.LENGTH_SHORT).show()
        }

        btnEditarPelicula.setOnClickListener {
            Toast.makeText(this, "Editar Película", Toast.LENGTH_SHORT).show()
        }

        btnEliminarPelicula.setOnClickListener {
            Toast.makeText(this, "Eliminar Película", Toast.LENGTH_SHORT).show()
        }

        btnVerReportes.setOnClickListener {
            Toast.makeText(this, "Ver Reportes", Toast.LENGTH_SHORT).show()
        }

        btnVerEstadisticas.setOnClickListener {
            Toast.makeText(this, "Ver Estadísticas", Toast.LENGTH_SHORT).show()
        }

        // Funcionalidad del botón de cerrar sesión
        btnCerrarSesion.setOnClickListener {
            // Borrar el token almacenado en las SharedPreferences (opcional, si lo estás guardando)
            val sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE)
            sharedPreferences.edit().remove("auth_token").apply()

            // Redirigir a la actividad de Login
            val intent = Intent(this@AdministradorActivity, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        // Configurar los insets para los systemBars
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    // Función para mostrar/ocultar las secciones
    private fun toggleVisibility(view: LinearLayout) {
        if (view.visibility == View.VISIBLE) {
            view.visibility = View.GONE
        } else {
            view.visibility = View.VISIBLE
        }
    }
}
