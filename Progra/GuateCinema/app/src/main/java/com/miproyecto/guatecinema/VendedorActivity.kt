package com.miproyecto.guatecinema

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class VendedorActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vendedor)

        // Inicializamos las secciones
        setupExpandableSection(R.id.titleVerificarBoletos, R.id.expandableVerificarBoletos)
        setupExpandableSection(R.id.titleGestionReservas, R.id.expandableGestionReservas)
        setupExpandableSection(R.id.titleAtencionCliente, R.id.expandableAtencionCliente)

        // Ejemplo de lógica para los botones
        val btnScanQR: Button = findViewById(R.id.btnScanQR)
        btnScanQR.setOnClickListener {
            Toast.makeText(this, "Escanear QR", Toast.LENGTH_SHORT).show()
        }

        val btnCerrarSesion: Button = findViewById(R.id.btnCerrarSesionVendedor)
        btnCerrarSesion.setOnClickListener {
            cerrarSesion() // Método para cerrar sesión e ir a LoginActivity
        }
    }

    // Método para configurar las secciones expandibles
    private fun setupExpandableSection(titleResId: Int, layoutResId: Int) {
        val titleView: TextView = findViewById(titleResId)
        val expandableLayout: LinearLayout = findViewById(layoutResId)

        titleView.setOnClickListener {
            if (expandableLayout.visibility == View.GONE) {
                expandableLayout.visibility = View.VISIBLE
            } else {
                expandableLayout.visibility = View.GONE
            }
        }
    }

    // Método para cerrar sesión
    private fun cerrarSesion() {
        // Limpiar cualquier dato de sesión guardado, si es necesario

        // Redirigir a la pantalla de inicio de sesión (LoginActivity)
        val intent = Intent(this@VendedorActivity, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish() // Cerrar la actividad actual
    }
}
