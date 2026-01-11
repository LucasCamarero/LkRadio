plugins {
    // Plugin de aplicación Android
    alias(libs.plugins.android.application)

    // Soporte para Kotlin en Android
    alias(libs.plugins.kotlin.android)

    // Soporte para Jetpack Compose
    alias(libs.plugins.kotlin.compose)
}

android {
    // Namespace base de la app
    namespace = "com.lucascamarero.lkradio"

    // SDK con el que se compila la app
    compileSdk = 36

    defaultConfig {
        applicationId = "com.lucascamarero.lkradio"

        // Mínimo Android soportado (Android 7.0)
        minSdk = 24

        // Versión objetivo (Android 14+)
        targetSdk = 36

        // Versión interna de la app
        versionCode = 1

        // Versión visible al usuario
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            // No se ofusca el código (más fácil de depurar)
            isMinifyEnabled = false

            // Archivos Proguard por defecto
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    // Configuración del compilador Java
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    // Configuración del compilador Kotlin
    kotlinOptions {
        jvmTarget = "11"
    }

    // Activamos Jetpack Compose
    buildFeatures {
        compose = true
    }
}

dependencies {

    // ------------------------
    // CORE ANDROID
    // ------------------------
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)

    // ------------------------
    // JETPACK COMPOSE (UI)
    // ------------------------
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)

    // Iconos extendidos (Play, Pause, Stop, etc.)
    implementation("androidx.compose.material:material-icons-extended")

    // ------------------------
    // NAVEGACIÓN ENTRE PANTALLAS
    // ------------------------
    implementation(libs.androidx.navigation.compose)

    // ------------------------
    // MEDIA3 / EXOPLAYER
    // ------------------------

    // Motor principal de audio
    implementation("androidx.media3:media3-exoplayer:1.2.0")

    // Componentes de UI de Media3 (notificaciones, etc.)
    implementation("androidx.media3:media3-ui:1.2.0")

    // Soporte para streams HLS (BBC, RNE, etc.)
    implementation("androidx.media3:media3-exoplayer-hls:1.2.0")

    // MediaSession y MediaSessionService
    // 🔥 Es lo que permite audio en segundo plano real
    implementation("androidx.media3:media3-session:1.2.0")

    // ------------------------
    // TESTING
    // ------------------------
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)

    // Herramientas de depuración de Compose
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}

