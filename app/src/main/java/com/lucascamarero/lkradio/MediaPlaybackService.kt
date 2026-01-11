package com.lucascamarero.lkradio

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSessionService

/**
 * Servicio de reproducción de audio.
 * Es un Foreground Service con MediaSession, lo que permite:
 * - Sonido en segundo plano
 * - Controles desde la notificación
 * - Integración con pantalla de bloqueo y Bluetooth
 */
class MediaPlaybackService : MediaSessionService() {

    // ExoPlayer real que reproduce el audio
    private lateinit var player: ExoPlayer

    // MediaSession que conecta el player con Android (lockscreen, notificación, etc.)
    private lateinit var mediaSession: MediaSession

    override fun onCreate() {
        super.onCreate()

        // Creamos el reproductor de audio
        player = ExoPlayer.Builder(this).build()

        // Creamos la sesión de medios que envuelve al reproductor
        mediaSession = MediaSession.Builder(this, player).build()

        // Convertimos el servicio en Foreground Service
        // Si no hacemos esto, Android cortará el audio al apagar la pantalla
        startForeground(1, createNotification())
    }

    /**
     * Crea la notificación persistente que Android exige
     * cuando una app reproduce audio en segundo plano.
     */
    private fun createNotification(): Notification {

        val channelId = "lk_radio_playback"

        // En Android 8+ es obligatorio registrar un canal de notificación
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Reproducción de radio",
                NotificationManager.IMPORTANCE_LOW // No molesta al usuario
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }

        // Construimos la notificación que se mostrará mientras suena la radio
        return NotificationCompat.Builder(this, channelId)
            .setContentTitle("Lk Radio")
            .setContentText("Reproduciendo radio")
            .setSmallIcon(android.R.drawable.ic_media_play)
            .setOngoing(true) // No se puede quitar mientras esté reproduciendo
            .build()
    }

    /**
     * Devuelve la MediaSession cuando Android o un controlador externo
     * (como Bluetooth o la notificación) quiere acceder al reproductor.
     */
    override fun onGetSession(controllerInfo: MediaSession.ControllerInfo): MediaSession {
        return mediaSession
    }

    /**
     * Se llama cuando el servicio se destruye.
     * Liberamos todos los recursos para evitar fugas de memoria.
     */
    override fun onDestroy() {
        mediaSession.release()
        player.release()
        super.onDestroy()
    }
}
