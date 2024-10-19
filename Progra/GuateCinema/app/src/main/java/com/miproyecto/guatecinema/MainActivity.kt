package com.miproyecto.guatecinema

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity

// Actividad principal de la aplicación
class MainActivity : AppCompatActivity() {

    // Declaración de los botones para el inicio de sesión, registro, y la barra de progreso.
    private lateinit var loginButton: Button
    private lateinit var registerButton: Button
    private lateinit var progressBar: ProgressBar

    // Método onCreate, ejecutado cuando la actividad es creada.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicializar los botones y la barra de progreso obteniendo los elementos del layout.
        loginButton = findViewById(R.id.buttonToLogin)
        registerButton = findViewById(R.id.buttonRegister)
        progressBar = findViewById(R.id.progressBar)

        // Ocultar la barra de progreso inicialmente.
        progressBar.visibility = ProgressBar.GONE

        // Listener para el botón de iniciar sesión, que redirige a LoginActivity
        loginButton.setOnClickListener {
            navigateToActivity(LoginActivity::class.java)
        }

        // Listener para el botón de registro, que redirige a RegisterActivity
        registerButton.setOnClickListener {
            navigateToActivity(RegisterActivity::class.java)
        }
    }

    // Método para manejar la navegación a otra actividad y mostrar la barra de progreso
    private fun navigateToActivity(activityClass: Class<*>) {
        progressBar.visibility = ProgressBar.VISIBLE // Mostrar barra de progreso
        val intent = Intent(this, activityClass)
        startActivity(intent) // Inicia la actividad correspondiente
        progressBar.visibility = ProgressBar.GONE // Ocultar la barra de progreso después de iniciar la actividad
    }
}
