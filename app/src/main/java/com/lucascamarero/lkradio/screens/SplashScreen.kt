package com.lucascamarero.lkradio.screens

import android.view.animation.LinearInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import kotlinx.coroutines.delay
import com.lucascamarero.lkradio.R
import kotlinx.coroutines.launch

@Composable
fun SplashScreen(onTimeout: () -> Unit) {
    val scale = remember { Animatable(0.1f) }
    val alpha = remember { Animatable(0f) }

    LaunchedEffect(Unit) {

        // Fade in lento
        launch {
            alpha.animateTo(
                1f,
                animationSpec = tween(
                    durationMillis = 1200
                )
            )
        }

        // Pequeño → grande (LENTO)
        scale.animateTo(
            5.2f,
            animationSpec = tween(
                durationMillis = 2800,
                //easing = FastOutSlowInEasing
                easing = LinearOutSlowInEasing
            )
        )

        // Pausa breve para que se note el tamaño grande
        delay(400)

        // Grande → normal (MUY SUAVE)
        scale.animateTo(
            2.5f,
            animationSpec = tween(
                durationMillis = 1700,
                easing = LinearOutSlowInEasing
            )
        )

        delay(2300)
        onTimeout()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primaryContainer),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(R.drawable.logo),
            contentDescription = "Logo",
            modifier = Modifier
                .scale(scale.value)
                .alpha(alpha.value)
        )
    }
}
