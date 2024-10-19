package com.miproyecto.guatecinema.network

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.miproyecto.guatecinema.LoginActivity
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.net.HttpURLConnection
import java.util.concurrent.TimeUnit

object RetrofitClient {

    // URL base de la API, usada como punto de partida para todas las solicitudes.
    private const val BASE_URL = "http://192.168.184.254:8080/api/v1/"
    private var retrofit: Retrofit? = null

    // Proporciona una instancia del servicio API, usando Retrofit para crear las implementaciones de las interfaces.
    val apiService: ApiService
        get() = retrofit!!.create(ApiService::class.java)

    // Devuelve una instancia del cliente Retrofit. Si aún no se ha creado, llama a buildRetrofitClient.
    fun getClient(context: Context): Retrofit {
        if (retrofit == null) {
            buildRetrofitClient(context)
        }
        return retrofit!!
    }

    // Construye y configura el cliente Retrofit junto con los interceptores y el cliente HTTP.
    private fun buildRetrofitClient(context: Context) {
        // Interceptor para registrar los detalles de las solicitudes y respuestas HTTP.
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY // Muestra el cuerpo completo de las solicitudes/respuestas.
        }

        // Interceptor que agrega el token JWT a las solicitudes en el encabezado Authorization.
        val authInterceptor = Interceptor { chain ->
            val request = chain.request() // Obtiene la solicitud original.
            val sharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
            val token = sharedPreferences.getString("auth_token", null) // Recupera el token de SharedPreferences.

            // Si existe un token, lo agrega al encabezado de la solicitud.
            val newRequest = if (!token.isNullOrEmpty()) {
                request.newBuilder()
                    .addHeader("Authorization", "Bearer ${token.trim()}") // Añade el token al encabezado Authorization.
                    .build()
            } else {
                request // Si no hay token, la solicitud original se envía sin modificar.
            }

            chain.proceed(newRequest) // Procede con la nueva solicitud (con o sin token).
        }

        // Interceptor que maneja los errores de autorización (401 o 403).
        val authErrorInterceptor = Interceptor { chain ->
            val response = chain.proceed(chain.request()) // Realiza la solicitud y obtiene la respuesta.
            when (response.code) {
                HttpURLConnection.HTTP_UNAUTHORIZED -> {
                    // Si recibe un código 401, redirige al usuario a la pantalla de login.
                    val intent = Intent(context, LoginActivity::class.java).apply {
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK // Limpia la pila de actividades.
                    }
                    context.startActivity(intent) // Inicia la actividad de login.
                    (context as? Activity)?.runOnUiThread {
                        // Muestra un mensaje indicando que la sesión ha expirado.
                        Toast.makeText(context, "Sesión expirada. Inicie sesión nuevamente.", Toast.LENGTH_LONG).show()
                    }
                }
                HttpURLConnection.HTTP_FORBIDDEN -> {
                    // Si recibe un código 403, indica que el usuario no tiene permisos suficientes.
                    (context as? Activity)?.runOnUiThread {
                        Toast.makeText(context, "Acceso denegado. No tiene permisos suficientes.", Toast.LENGTH_LONG).show()
                    }
                }
            }
            response // Devuelve la respuesta, ya sea con error o sin él.
        }

        // Configura el cliente OkHttp, agregando los interceptores configurados.
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor) // Interceptor para registrar los detalles HTTP.
            .addInterceptor(authInterceptor)  // Interceptor para agregar el token JWT.
            .addInterceptor(authErrorInterceptor) // Interceptor para manejar errores de autorización.
            .connectTimeout(30, TimeUnit.SECONDS) // Establece un tiempo de espera para conexiones.
            .readTimeout(30, TimeUnit.SECONDS) // Establece un tiempo de espera para lecturas.
            .writeTimeout(30, TimeUnit.SECONDS) // Establece un tiempo de espera para escrituras.
            .build()

        // Configura Retrofit con la URL base, el cliente HTTP y el convertidor Gson.
        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL) // Establece la URL base de la API.
            .client(okHttpClient) // Asigna el cliente OkHttp configurado.
            .addConverterFactory(GsonConverterFactory.create()) // Usa Gson para convertir las respuestas JSON en objetos de Kotlin.
            .build()
    }
}
