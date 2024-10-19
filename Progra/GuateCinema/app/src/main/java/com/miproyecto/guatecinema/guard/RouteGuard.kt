package com.miproyecto.guatecinema.guard

import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.miproyecto.guatecinema.LoginActivity
import android.content.SharedPreferences
import java.util.regex.Pattern

class AuthGuard {

    companion object {
        // Función para verificar si el usuario está autenticado y si el token tiene formato válido
        fun isUserAuthenticated(context: Context): Boolean {
            val sharedPreferences: SharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
            val token = sharedPreferences.getString("auth_token", null)

            // Verifica si el token está presente y sigue el formato básico de JWT (tres partes separadas por '.')
            return !token.isNullOrEmpty() && isValidTokenFormat(token)
        }

        // Función para manejar el acceso a una ruta protegida
        fun guardRoute(context: Context, onSuccess: () -> Unit) {
            if (isUserAuthenticated(context)) {
                // Si el usuario está autenticado y el token tiene un formato válido, ejecuta la lógica de éxito
                onSuccess()
            } else {
                // Si no está autenticado, redirigir al login
                redirectToLogin(context)
            }
        }

        // Función para redirigir al usuario al login
        private fun redirectToLogin(context: Context) {
            val intent = Intent(context, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            context.startActivity(intent)
            Toast.makeText(context, "Por favor, inicia sesión para acceder a esta página", Toast.LENGTH_SHORT).show()
        }

        // Función para validar el formato básico del token JWT
        private fun isValidTokenFormat(token: String): Boolean {
            // Un token JWT debe tener tres segmentos separados por puntos
            val jwtPattern = Pattern.compile("^[A-Za-z0-9-_=]+\\.[A-Za-z0-9-_=]+\\.[A-Za-z0-9-_=]+$")
            return jwtPattern.matcher(token).matches()
        }
    }
}
