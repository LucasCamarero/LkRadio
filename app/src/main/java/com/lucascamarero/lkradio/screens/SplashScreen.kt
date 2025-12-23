package com.lucascamarero.lkradio.screens

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import com.lucascamarero.lkradio.R
import com.lucascamarero.lkradio.ui.theme.Typography2
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
            2.1f,
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
            0.9f,
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
            .background(MaterialTheme.colorScheme.tertiary),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(R.drawable.android),
                contentDescription = "Android",
                modifier = Modifier
                    .size(45.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            Image(
                painter = painterResource(R.drawable.logo),
                contentDescription = "Logo",
                modifier = Modifier
                    .scale(scale.value)   // SOLO el logo se anima
                    .alpha(alpha.value)
            )

            Spacer(modifier = Modifier.height(25.dp))

            Text(
                text = "Lucas Camarero",
                style = Typography2.bodyLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onTertiary
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Multiplatform Developer",
                style = Typography2.bodySmall,
                color = MaterialTheme.colorScheme.onTertiary
            )
        }
    }
}
