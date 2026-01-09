package com.lucascamarero.lkradio.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.lucascamarero.lkradio.RadioStation
import com.lucascamarero.lkradio.player.RadioPlayer
import androidx.compose.foundation.lazy.items


@Composable
fun RadiosList(navController: NavController){

    val radiosLocales = listOf(
        RadioStation(
            "Cadena SER",
            "https://playerservices.streamtheworld.com/api/livestream-redirect/CADENASER.mp3"
        ),
        RadioStation(
            "RNE",
            "https://playerservices.streamtheworld.com/api/livestream-redirect/SER_ASO_VIGOAAC.aac"
        )
    )

    LazyColumn(
        modifier = Modifier
            .padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {

        item {
            Spacer(modifier = Modifier.height(25.dp))
        }

        item {
            CreateTitle("Locales:")
        }

        items(radiosLocales) { radio ->
            CreateItem(
                emisora = radio.name,
                url = radio.url
            )
        }

        item {
            Spacer(modifier = Modifier.height(5.dp))
        }

        item {
            CreateTitle("Nacionales:")
        }

        item {
            Spacer(modifier = Modifier.height(25.dp))
        }

        item {
            CreateTitle("Internacionales:")
        }
    }
}

// Crea un título: locales, nacionales e Internacionales
@Composable
fun CreateTitle(title: String) {
    Text(title,
        style = MaterialTheme.typography.bodyLarge,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.tertiary
    )
}

// Crea un item de emisora
@Composable
fun CreateItem(
    emisora: String,
    url: String
) {
    val context = LocalContext.current

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = emisora,
            style = MaterialTheme.typography.bodyMedium,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(1f)
        )

        // Bloque de iconos (ancho fijo)
        Row {
            IconButton(onClick = {
                RadioPlayer.play(
                    context,
                    url
                )
            }) {
                Icon(
                    imageVector = Icons.Filled.PlayArrow,
                    contentDescription = "Play",
                    tint = MaterialTheme.colorScheme.onTertiaryContainer,
                    modifier = Modifier.size(32.dp)
                )
            }

            IconButton(onClick = {
                RadioPlayer.stop()
            }) {
                Icon(
                    imageVector = Icons.Filled.Stop,
                    contentDescription = "Stop",
                    tint = MaterialTheme.colorScheme.onTertiaryContainer,
                    modifier = Modifier.size(30.dp)
                )
            }
        }
    }
}