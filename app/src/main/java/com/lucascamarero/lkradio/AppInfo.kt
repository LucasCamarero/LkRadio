package com.lucascamarero.lkradio

import android.content.Context

/**
 * Devuelve la versión de la aplicación (versionName) definida en build.gradle.
 * Por ejemplo: "1.0", "1.1", "2.0", etc.
 *
 * Esta función pregunta al sistema Android cuál es la versión instalada
 * de esta app en el dispositivo.
 */
fun getAppVersion(context: Context): String {
    return try {
        // Pedimos al PackageManager la información del paquete (la app actual)
        val pInfo = context.packageManager.getPackageInfo(context.packageName, 0)

        // Devolvemos el nombre de versión (versionName)
        // Si por algún motivo fuera null, devolvemos una cadena vacía
        pInfo.versionName ?: ""
    } catch (e: Exception) {
        // Si ocurre cualquier error (algo muy raro),
        // evitamos que la app se rompa y devolvemos una cadena vacía
        ""
    }
}
