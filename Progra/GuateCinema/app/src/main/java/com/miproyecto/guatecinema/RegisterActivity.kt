package com.miproyecto.guatecinema

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.miproyecto.guatecinema.model.RegistroRequest
import com.miproyecto.guatecinema.model.Usuario
import com.miproyecto.guatecinema.network.ApiService
import com.miproyecto.guatecinema.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.util.Patterns

class RegisterActivity : AppCompatActivity() {

    private lateinit var etName: EditText
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnRegister: Button
    private lateinit var backButton: ImageButton // Botón de regreso

    private val apiService: ApiService by lazy {
        RetrofitClient.getClient(this).create(ApiService::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        etName = findViewById(R.id.etName)
        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        btnRegister = findViewById(R.id.btnRegister)
        backButton = findViewById(R.id.buttonBack) // Inicializar el botón de regreso

        // Listener para el botón de regresar
        backButton.setOnClickListener {
            finish() // Regresa a la actividad anterior
        }

        // Listener para el botón de registrar
        btnRegister.setOnClickListener {
            try {
                val name = etName.text.toString().trim()
                val email = etEmail.text.toString().trim()
                val password = etPassword.text.toString().trim()

                // Validar campos
                if (validateInputs(name, email, password)) {
                    registerUser(name, email, password)
                }
            } catch (e: Exception) {
                Log.e("RegisterActivity", "Error durante el proceso de registro", e)
            }
        }
    }

    // Método para validar entradas actualizado con la nueva lógica
    private fun validateInputs(name: String, email: String, password: String): Boolean {
        var isValid = true

        // Validación de nombre
        if (name.isBlank()) {
            etName.error = "El nombre no puede estar vacío"
            isValid = false
        } else {
            etName.error = null // Limpia el error si es válido
        }

        // Validación de email
        if (email.isBlank()) {
            etEmail.error = "El correo electrónico no puede estar vacío"
            isValid = false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.error = "Ingrese un correo electrónico válido"
            isValid = false
        } else {
            etEmail.error = null // Limpia el error si es válido
        }

        // Validación de contraseña
        if (password.isBlank()) {
            etPassword.error = "La contraseña no puede estar vacía"
            isValid = false
        } else if (password.length < 6) {
            etPassword.error = "La contraseña debe tener al menos 6 caracteres"
            isValid = false
        } else {
            etPassword.error = null // Limpia el error si es válido
        }

        return isValid
    }

    // Método para registrar al usuario
    private fun registerUser(name: String, email: String, password: String) {
        val registroRequest = RegistroRequest(
            nombre = name,
            correoElectronico = email,
            contraseña = password
        )

        Log.d("RegisterActivity", "Datos enviados: Nombre = $name, Email = $email, Contraseña = $password")

        try {
            apiService.registerUser(registroRequest).enqueue(object : Callback<Usuario> {
                override fun onResponse(call: Call<Usuario>, response: Response<Usuario>) {
                    if (response.isSuccessful) {
                        val registeredUser = response.body()
                        val token = registeredUser?.token

                        // Almacenamiento del token en SharedPreferences al registrar al usuario
                        if (token != null) {
                            val sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
                            sharedPreferences.edit().putString("auth_token", token).apply()

                            Toast.makeText(this@RegisterActivity, "Usuario registrado y token guardado", Toast.LENGTH_SHORT).show()

                            // Redirigir a la actividad principal
                            val intent = Intent(this@RegisterActivity, CarteleraActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            startActivity(intent)
                        } else {
                            Toast.makeText(this@RegisterActivity, "Usuario registrado pero no se recibió token", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        handleRegistrationError(response)
                    }
                }

                override fun onFailure(call: Call<Usuario>, t: Throwable) {
                    Log.e("RegisterActivity", "Error en la red: ${t.message}")
                    Toast.makeText(this@RegisterActivity, "Error en la red: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        } catch (e: Exception) {
            Log.e("RegisterActivity", "Error durante el proceso de registro", e)
        }
    }

    // Manejo de errores durante el registro
    private fun handleRegistrationError(response: Response<Usuario>) {
        if (response.code() == 400) {
            val errorBody = response.errorBody()?.string()
            if (errorBody?.contains("correo electrónico ya está en uso") == true) {
                Toast.makeText(this, "El correo electrónico ya está registrado. Intente con otro correo.", Toast.LENGTH_LONG).show()
            } else {
                Log.e("RegisterActivity", "Error 400: $errorBody")
                Toast.makeText(this, "Error en el registro: $errorBody", Toast.LENGTH_SHORT).show()
            }
        } else {
            val responseBody = response.errorBody()?.string()
            Log.e("RegisterActivity", "Error en el registro: ${response.code()} - $responseBody")
            Toast.makeText(this, "Error en el registro: ${response.code()}", Toast.LENGTH_SHORT).show()
        }
    }
}
