package com.miproyecto.guatecinema.storage

import android.content.Context
import android.content.SharedPreferences

object LocalStorageManager {

    private const val PREF_NAME = "user_prefs"
    private const val KEY_AUTH_TOKEN = "auth_token"

    // Obtener una instancia de SharedPreferences
    private fun getPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    // Guardar el token en SharedPreferences
    fun saveToken(context: Context, token: String?) {
        if (!token.isNullOrEmpty()) {
            val sharedPreferences = getPreferences(context)
            sharedPreferences.edit().putString(KEY_AUTH_TOKEN, token.trim()).apply() // Guardar el token sin espacios extra
        }
    }

    // Recuperar el token de SharedPreferences
    fun getToken(context: Context): String? {
        val sharedPreferences = getPreferences(context)
        return sharedPreferences.getString(KEY_AUTH_TOKEN, null)?.trim() // Retornar el token sin espacios extra
    }

    // Eliminar el token de SharedPreferences
    fun clearToken(context: Context) {
        val sharedPreferences = getPreferences(context)
        sharedPreferences.edit().remove(KEY_AUTH_TOKEN).apply() // Eliminar el token
    }

    // Verificar si el token está guardado
    fun isTokenSaved(context: Context): Boolean {
        val sharedPreferences = getPreferences(context)
        return !sharedPreferences.getString(KEY_AUTH_TOKEN, null).isNullOrEmpty() // Verificar si el token no es nulo o vacío
    }
}
