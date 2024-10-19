
    plugins {
        id("com.android.application")
        id("kotlin-android")
        id("kotlin-kapt")  // Si usas anotaciones en Kotlin
        id("com.google.gms.google-services") version "4.3.15"  // Asegúrate de que esté aquí
        id("com.google.firebase.crashlytics") version "2.9.6"  // Asegúrate de que esté aquí
    }


android {
    namespace = "com.miproyecto.guatecinema"
    compileSdk = 34  // Actualizado a la última versión estable del SDK

    defaultConfig {
        applicationId = "com.miproyecto.guatecinema"
        minSdk = 21
        targetSdk = 34  // Actualizado para usar la última versión estable
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    compileOptions {
        // Actualizado a Java 11 para usar características modernas
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"  // Asegúrate de que coincida con la versión de Java
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true  // Activa la ofuscación en la versión de producción
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    // Habilitar View Binding
    viewBinding {
        enable = true
    }
}

dependencies {
    // Dependencias esenciales de Android
    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")

    // Retrofit y Gson converter
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")

    // OkHttp y logging interceptor para ver las peticiones y respuestas HTTP
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")

    // LiveData y ViewModel
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.8.6")

    // JWT para autenticación basada en tokens
    implementation("io.jsonwebtoken:jjwt:0.12.6")

    // Google Sign-in para autenticación
    implementation("com.google.android.gms:play-services-auth:21.2.0")

    // Firebase
    implementation("com.google.firebase:firebase-crashlytics:19.2.0")
    implementation("com.google.firebase:firebase-analytics:22.1.2")

    // Glide
    implementation("com.github.bumptech.glide:glide:4.16.0")
    annotationProcessor("com.github.bumptech.glide:compiler:4.16.0")

    // Picasso
    implementation("com.squareup.picasso:picasso:2.71828")

    // Facebook Login para autenticación
    implementation("com.facebook.android:facebook-login:17.0.2")
    implementation("androidx.activity:activity-ktx:1.9.3")

    // Dependencias de pruebas
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
}

