package com.lucascamarero.lkradio

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.*
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.*
import com.lucascamarero.lkradio.player.RadioPlayer
import com.lucascamarero.lkradio.screens.RadiosList
import com.lucascamarero.lkradio.ui.theme.Typography2

/**
 * ScreenManager es el layout principal de la aplicación.
 * Define:
 * - Barra superior (TopAppBar)
 * - Contenido central (lista de radios)
 * - Barra inferior (controles de reproducción)
 */
@Composable
fun ScreenManager() {

    // Controlador de navegación (aunque ahora solo hay una pantalla)
    val navController = rememberNavController()

    Scaffold(
        topBar = { BarraSuperior() },      // Barra superior
        bottomBar = { BottomPlayerBar() }  // Controles de reproducción
    ) { innerPadding ->

        // Contenedor principal donde se muestra la pantalla activa
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.background),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            // Sistema de navegación entre pantallas
            NavHost(
                navController = navController,
                startDestination = "list"
            ) {
                composable("list") {
                    // Pantalla con la lista de emisoras
                    RadiosList(navController)
                }
            }
        }
    }
}

/**
 * Barra superior de la app.
 * Muestra el nombre y un botón para salir.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BarraSuperior() {

    val context = LocalContext.current
    val activity = context as? Activity

    TopAppBar(
        colors = topAppBarColors(
            containerColor = MaterialTheme.colorScheme.tertiaryContainer,
            titleContentColor = MaterialTheme.colorScheme.onTertiaryContainer,
        ),
        title = {
            Text("Lk Radio", style = Typography2.titleSmall)
        },
        actions = {
            // Botón para cerrar la app
            IconButton(onClick = { activity?.finish() }) {
                Icon(
                    imageVector = Icons.Filled.ExitToApp,
                    contentDescription = "Salir",
                    tint = MaterialTheme.colorScheme.onTertiaryContainer,
                    modifier = Modifier.size(30.dp)
                )
            }
        }
    )
}

/**
 * Barra inferior con el reproductor.
 * Muestra:
 * - nombre de la emisora
 * - botón play / pause
 * - botón stop
 */
@Composable
fun BottomPlayerBar() {

    // Nombre de la emisora activa
    val station = RadioPlayer.currentStation.value

    // Estado de reproducción (reproduciendo o en pausa)
    val isPlaying = RadioPlayer.isPlayingState.value

    BottomAppBar(
        containerColor = MaterialTheme.colorScheme.background
    ) {

        // Nombre de la emisora que está sonando
        Text(
            text = station ?: "",
            modifier = Modifier
                .weight(1f)
                .padding(start = 26.dp),
            color = MaterialTheme.colorScheme.primaryContainer,
            maxLines = 1,
            fontWeight = FontWeight.Bold,
            fontSize = 28.sp
        )

        // Botón Play / Pause
        BigPlayerButton(
            icon = if (isPlaying) Icons.Filled.Pause else Icons.Filled.PlayArrow
        ) {
            if (isPlaying) RadioPlayer.pause() else RadioPlayer.resume()
        }

        // Botón Stop
        BigPlayerButton(
            icon = Icons.Filled.Stop
        ) {
            RadioPlayer.stop()
        }
    }
}

/**
 * Botón grande reutilizable para Play, Pause y Stop.
 * Es un Box con fondo redondeado y un icono centrado.
 */
@Composable
fun BigPlayerButton(
    icon: ImageVector,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .padding(end = 12.dp)
            .size(64.dp)
            .padding(6.dp)
            .background(
                MaterialTheme.colorScheme.onTertiaryContainer,
                shape = MaterialTheme.shapes.large
            )
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primaryContainer,
            modifier = Modifier.size(32.dp)
        )
    }
}
