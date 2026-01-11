package com.lucascamarero.lkradio

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import com.lucascamarero.lkradio.screens.SplashScreen
import com.lucascamarero.lkradio.ui.theme.LkRadioTheme

/**
 * Actividad principal de la aplicación.
 * Aquí se inicializa Compose, el tema y se decide
 * si se muestra el SplashScreen o la pantalla principal.
 */
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Permite que la app dibuje detrás de la barra de estado
        // y de la barra de navegación (aspecto más moderno).
        enableEdgeToEdge()

        // Inicia el sistema de UI de Jetpack Compose
        setContent {

            // Aplica el tema personalizado de la app
            LkRadioTheme(dynamicColor = false) {

                // Surface es el contenedor base que aplica
                // el color de fondo del tema
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    /**
                     * Estado que controla si se muestra el SplashScreen.
                     * rememberSaveable lo mantiene incluso si la Activity
                     * se recrea (rotación, etc).
                     */
                    var showSplash by rememberSaveable { mutableStateOf(true) }

                    // Según el valor de showSplash, mostramos una pantalla u otra
                    when {
                        showSplash -> {
                            // Pantalla de bienvenida animada
                            SplashScreen(
                                onTimeout = { showSplash = false } // Se ejecuta al terminar el splash
                            )
                        }

                        else -> {
                            // Pantalla principal de la app
                            ScreenManager()
                        }
                    }
                }
            }
        }
    }
}
