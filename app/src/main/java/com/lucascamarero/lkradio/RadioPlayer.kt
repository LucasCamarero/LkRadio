package com.lucascamarero.lkradio.player

import android.content.ComponentName
import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.media3.common.MediaItem
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import com.lucascamarero.lkradio.MediaPlaybackService

object RadioPlayer {

    private var controller: MediaController? = null
    private var connecting = false

    val currentStation = mutableStateOf<String?>(null)
    val isPlayingState = mutableStateOf(false)

    private fun connect(context: Context, onReady: () -> Unit) {
        if (controller != null) {
            onReady()
            return
        }

        if (connecting) return
        connecting = true

        val token = SessionToken(
            context,
            ComponentName(context, MediaPlaybackService::class.java)
        )

        val future = MediaController.Builder(context, token).buildAsync()

        future.addListener(
            {
                controller = future.get()
                connecting = false
                onReady()
            },
            context.mainExecutor
        )
    }

    fun play(context: Context, name: String, url: String) {
        connect(context) {
            currentStation.value = name
            controller?.setMediaItem(MediaItem.fromUri(url))
            controller?.prepare()
            controller?.play()
            isPlayingState.value = true
        }
    }

    fun pause() {
        controller?.pause()
        isPlayingState.value = false
    }

    fun resume() {
        controller?.play()
        isPlayingState.value = true
    }

    fun stop() {
        controller?.stop()
        currentStation.value = null
        isPlayingState.value = false
    }
}
