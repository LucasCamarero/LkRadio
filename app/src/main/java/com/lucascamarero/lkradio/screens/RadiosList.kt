package com.lucascamarero.lkradio.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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

@Composable
fun RadiosList(navController: NavController) {

    // Emisoras locales
    val radiosLocales = listOf(
        RadioStation(
            "Radio Nervión",
            "https://stream.radionervion.com/listen/radio-nervion/radionervion.mp3"
        ),
        RadioStation(
            "Radio Popular",
            "https://stream.mediasector.es/listen/radio_popular/radiopopular.mp3"
        )
    )

    // Emisoras nacionales
    val radiosNacionales = listOf(
        RadioStation(
            "Cadena SER",
            "https://playerservices.streamtheworld.com/api/livestream-redirect/CADENASER.mp3"
        ),
        RadioStation(
            "Cope",
            "https://madrid-cope-rrcast.flumotion.com/cope/madrid.mp3"
        ),
        RadioStation(
            "Radio Marca",
            "https://29103.live.streamtheworld.com/RADIOMARCA_NACIONAL.mp3"
        ),
        RadioStation(
            "Rock FM",
            "https://rockfm-cope-rrcast.flumotion.com/cope/rockfm-low.mp3"
        ),
        RadioStation(
            "Los 40 Classic",
            "https://28033.live.streamtheworld.com/LOS40_CLASSIC_SC"
        ),
        RadioStation(
            "Cadena 100",
            "https://cadena100-cope-rrcast.flumotion.com/cope/cadena100-low.mp3"
        ),
        RadioStation(
            "Cadena Dial",
            "https://23553.live.streamtheworld.com/CADENADIAL_SC"
        )
    )

    // Emisoras internacionales
    val radiosInternacionales = listOf(
        RadioStation(
            "CNN",
            "https://tunein.cdnstream1.com/3517_96.mp3"
        )
    )

    LazyColumn(
        modifier = Modifier.padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {

        item { Spacer(modifier = Modifier.height(25.dp)) }

        item { CreateTitle("Locales:") }

        items(radiosLocales) { radio ->
            CreateItem(radio.name, radio.url)
        }

        item { Spacer(modifier = Modifier.height(30.dp)) }

        item { CreateTitle("Nacionales:") }

        items(radiosNacionales) { radio ->
            CreateItem(radio.name, radio.url)
        }

        item { Spacer(modifier = Modifier.height(30.dp)) }

        item { CreateTitle("Internacionales:") }

        items(radiosInternacionales) { radio ->
            CreateItem(radio.name, radio.url)
        }
    }
}

// Crea un título
@Composable
fun CreateTitle(title: String) {
    Text(
        title,
        style = MaterialTheme.typography.bodyLarge,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.tertiary
    )
}

// Item de emisora
@Composable
fun CreateItem(emisora: String, url: String) {
    val context = LocalContext.current
    val isActive = RadioPlayer.currentStation.value == emisora

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp, horizontal = 15.dp),
        onClick = {
            RadioPlayer.play(context, emisora, url)
        },
        colors = CardDefaults.cardColors(
            containerColor = if (isActive)
                MaterialTheme.colorScheme.primaryContainer
            else
                MaterialTheme.colorScheme.tertiaryContainer
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = emisora,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium,
                color = if (isActive)
                    MaterialTheme.colorScheme.onPrimaryContainer
                else
                    MaterialTheme.colorScheme.onTertiaryContainer,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f)
            )
        }
    }
}
