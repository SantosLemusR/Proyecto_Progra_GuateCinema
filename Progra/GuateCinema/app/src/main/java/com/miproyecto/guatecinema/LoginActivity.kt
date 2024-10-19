package com.miproyecto.guatecinema

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.miproyecto.guatecinema.model.LoginRequest
import com.miproyecto.guatecinema.model.LoginResponse
import com.miproyecto.guatecinema.network.RetrofitClient
import com.miproyecto.guatecinema.network.ApiService
import retrofit2.Call
import android.util.Log
import retrofit2.Callback
import retrofit2.Response
import android.util.Patterns

class LoginActivity : AppCompatActivity() {

    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        emailEditText = findViewById(R.id.editTextEmailLogin)
        passwordEditText = findViewById(R.id.editTextPasswordLogin)
        val loginButton = findViewById<Button>(R.id.buttonLogin)
        val backButton = findViewById<ImageButton>(R.id.buttonBack) // Cambié a ImageButton
        val googleSignInButton = findViewById<ImageButton>(R.id.buttonGoogleSignIn) // Cambié a ImageButton
        val facebookSignInButton = findViewById<ImageButton>(R.id.buttonFacebookSignIn) // Cambié a ImageButton

        // Funcionalidad del botón de login
        loginButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()
            if (validateInputs(email, password)) {
                loginUser(email, password)
            } else {
                Toast.makeText(this, "Por favor ingrese sus credenciales", Toast.LENGTH_SHORT).show()
            }
        }

        // Funcionalidad del botón de "Regresar"
        backButton.setOnClickListener {
            onBackPressed()  // Retrocede a la actividad anterior
        }

        // Listener para el botón de Google Sign-In
        googleSignInButton.setOnClickListener {
            Toast.makeText(this, "Iniciar sesión con Google", Toast.LENGTH_SHORT).show()
        }

        // Listener para el botón de Facebook Sign-In
        facebookSignInButton.setOnClickListener {
            Toast.makeText(this, "Iniciar sesión con Facebook", Toast.LENGTH_SHORT).show()
        }
    }

    private fun validateInputs(correo: String, contraseña: String): Boolean {
        var isValid = true
        if (correo.isEmpty()) {
            emailEditText.error = "El correo es obligatorio"
            isValid = false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
            emailEditText.error = "Ingrese un correo válido"
            isValid = false
        } else {
            emailEditText.error = null
        }
        if (contraseña.isEmpty()) {
            passwordEditText.error = "La contraseña es obligatoria"
            isValid = false
        } else if (contraseña.length < 6) {
            passwordEditText.error = "La contraseña debe tener al menos 6 caracteres"
            isValid = false
        } else {
            passwordEditText.error = null
        }
        return isValid
    }

    private fun loginUser(correoElectronico: String, contraseña: String) {
        val apiService = RetrofitClient.getClient(this).create(ApiService::class.java)
        val loginRequest = LoginRequest(correoElectronico, contraseña)

        apiService.loginUser(loginRequest).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    val loginResponse = response.body()
                    val token = loginResponse?.token?.trim()
                    val rolAuthority = loginResponse?.usuario?.rol?.authority
                    Log.d("LoginActivity", "Rol del usuario: $rolAuthority")

                    if (!token.isNullOrEmpty() && isValidJwtToken(token)) {
                        storeTokenInSharedPreferences(token)
                        Toast.makeText(this@LoginActivity, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show()

                        when (rolAuthority) {
                            "ADMIN" -> {
                                val intent = Intent(this@LoginActivity, AdministradorActivity::class.java)
                                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                startActivity(intent)
                            }
                            "VENDEDOR" -> {
                                val intent = Intent(this@LoginActivity, VendedorActivity::class.java)
                                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                startActivity(intent)
                            }
                            "USER" -> {
                                val intent = Intent(this@LoginActivity, CarteleraActivity::class.java)
                                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                startActivity(intent)
                            }
                            else -> {
                                Toast.makeText(this@LoginActivity, "Rol no reconocido", Toast.LENGTH_SHORT).show()
                            }
                        }
                    } else {
                        Toast.makeText(this@LoginActivity, "Error: Token no válido o mal formado", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    handleLoginError(response)
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Toast.makeText(this@LoginActivity, "Error en la red: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun isValidJwtToken(token: String): Boolean {
        return try {
            val parts = token.split(".")
            parts.size == 3
        } catch (e: Exception) {
            false
        }
    }

    private fun storeTokenInSharedPreferences(token: String) {
        val sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        sharedPreferences.edit().putString("auth_token", token).apply()
    }

    private fun handleLoginError(response: Response<*>) {
        if (response.code() == 401) {
            Toast.makeText(this, "Credenciales incorrectas. Verifique su correo o contraseña.", Toast.LENGTH_LONG).show()
        } else if (response.code() == 500) {
            Toast.makeText(this, "Error en el servidor: ${response.code()}", Toast.LENGTH_LONG).show()
        } else {
            val errorBody = response.errorBody()?.string()
            Toast.makeText(this, "Error: $errorBody", Toast.LENGTH_SHORT).show()
        }
    }
}
