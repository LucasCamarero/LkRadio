package com.lucascamarero.lkradio.player

import android.content.ComponentName
import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.media3.common.MediaItem
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import com.lucascamarero.lkradio.MediaPlaybackService

/**
 * RadioPlayer es el controlador global de reproducción.
 * No reproduce audio directamente:
 * se conecta al MediaPlaybackService mediante MediaController.
 *
 * Esto permite:
 * - audio en segundo plano
 * - controles desde la notificación
 * - integración con Android (lockscreen, Bluetooth, etc.)
 */
object RadioPlayer {

    // Controlador que habla con el servicio de reproducción
    private var controller: MediaController? = null

    // Evita que intentemos conectar varias veces a la vez
    private var connecting = false

    // Nombre de la emisora que está sonando (observable por Compose)
    val currentStation = mutableStateOf<String?>(null)

    // Estado de reproducción (play / pause) observable por Compose
    val isPlayingState = mutableStateOf(false)

    /**
     * Conecta la app con el MediaPlaybackService.
     * Solo se ejecuta la primera vez; luego reutiliza la conexión.
     */
    private fun connect(context: Context, onReady: () -> Unit) {

        // Si ya tenemos controlador, no hay que volver a conectar
        if (controller != null) {
            onReady()
            return
        }

        // Si ya estamos conectando, evitamos duplicados
        if (connecting) return
        connecting = true

        // Token que identifica el servicio de reproducción
        val token = SessionToken(
            context,
            ComponentName(context, MediaPlaybackService::class.java)
        )

        // Creamos el MediaController de forma asíncrona
        val future = MediaController.Builder(context, token).buildAsync()

        future.addListener(
            {
                // Cuando el controlador está listo, lo guardamos
                controller = future.get()
                connecting = false

                // Ejecutamos la acción pendiente (por ejemplo, play)
                onReady()
            },
            context.mainExecutor
        )
    }

    /**
     * Inicia la reproducción de una emisora.
     * Llama al servicio de reproducción a través del MediaController.
     */
    fun play(context: Context, name: String, url: String) {
        connect(context) {
            currentStation.value = name
            controller?.setMediaItem(MediaItem.fromUri(url))
            controller?.prepare()
            controller?.play()
            isPlayingState.value = true
        }
    }

    /**
     * Pausa la reproducción.
     */
    fun pause() {
        controller?.pause()
        isPlayingState.value = false
    }

    /**
     * Reanuda la reproducción si estaba en pausa.
     */
    fun resume() {
        controller?.play()
        isPlayingState.value = true
    }

    /**
     * Detiene completamente la reproducción.
     */
    fun stop() {
        controller?.stop()
        currentStation.value = null
        isPlayingState.value = false
    }
}
