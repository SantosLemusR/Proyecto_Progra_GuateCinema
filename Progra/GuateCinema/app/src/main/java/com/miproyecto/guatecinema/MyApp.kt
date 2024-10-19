package com.miproyecto.guatecinema

import android.app.Application
import android.util.Log
import com.google.firebase.crashlytics.FirebaseCrashlytics

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        // Captura global de errores
        Thread.setDefaultUncaughtExceptionHandler { thread, throwable ->
            Log.e("MiApp", "Error no capturado: ", throwable)

            // Inicializar Firebase Crashlytics
            FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(true)
        }
    }
}
