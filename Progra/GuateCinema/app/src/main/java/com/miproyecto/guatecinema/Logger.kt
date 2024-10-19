package com.miproyecto.guatecinema
import android.content.Context
import android.util.Log
import java.io.File
import java.io.FileWriter
import java.io.IOException

class Logger(private val context: Context) {

    fun logError(tag: String, message: String, throwable: Throwable) {
        Log.e(tag, message, throwable)
        saveErrorToFile("$tag: $message\n${throwable.localizedMessage}")
    }

    private fun saveErrorToFile(logMessage: String) {
        try {
            val file = File(context.filesDir, "app_logs.txt")
            val writer = FileWriter(file, true)
            writer.append(logMessage).append("\n")
            writer.flush()
            writer.close()
        } catch (e: IOException) {
            Log.e("Logger", "No se pudo guardar el error", e)
        }
    }
}
