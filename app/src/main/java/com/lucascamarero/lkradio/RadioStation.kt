package com.lucascamarero.lkradio

/**
 * Modelo de datos que representa una emisora de radio.
 *
 * name → nombre que se muestra en la interfaz
 * url  → dirección del stream de audio
 */
data class RadioStation(
    val name: String,
    val url: String
)
