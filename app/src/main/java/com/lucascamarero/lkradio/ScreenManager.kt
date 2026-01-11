package com.lucascamarero.lkradio

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.lucascamarero.lkradio.player.RadioPlayer
import com.lucascamarero.lkradio.screens.RadiosList
import com.lucascamarero.lkradio.ui.theme.Typography2

@Composable
fun ScreenManager() {

    val navController = rememberNavController()

    Scaffold(
        topBar = { BarraSuperior() },
        bottomBar = { BottomPlayerBar() }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.background),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            NavHost(
                navController = navController,
                startDestination = "list"
            ) {
                composable("list") {
                    RadiosList(navController)
                }
            }
        }
    }
}

// Barra superior
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

// Barra inferior
@Composable
fun BottomPlayerBar() {

    val station = RadioPlayer.currentStation.value
    val isPlaying = RadioPlayer.isPlayingState.value

    BottomAppBar(
        containerColor = MaterialTheme.colorScheme.background
    ) {

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

        // PLAY / PAUSE grande
        BigPlayerButton(
            icon = if (isPlaying) Icons.Filled.Pause else Icons.Filled.PlayArrow
        ) {
            if (isPlaying) RadioPlayer.pause() else RadioPlayer.resume()
        }

        // STOP grande
        BigPlayerButton(
            icon = Icons.Filled.Stop
        ) {
            RadioPlayer.stop()
        }
    }
}

// Creación de botones
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