package com.lucascamarero.lkradio

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.lucascamarero.lkradio.screens.CreateRadio
import com.lucascamarero.lkradio.screens.RadiosList
import com.lucascamarero.lkradio.ui.theme.Typography2

@Composable
fun ScreenManager(){

    var navController = rememberNavController()

    Scaffold(
        // barra superior
        topBar = { BarraSuperior() },

        // floating action button
        floatingActionButton = { FAB(navController) }

        // cuerpo central
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.background),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            // definición de rutas de pantallas
            NavHost(
                navController = navController,
                startDestination = "list"
            ) {
                composable("list") {
                    RadiosList(navController)
                }
                composable("create") {
                    CreateRadio(navController)
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
            Text(
                "Lk Radio",
                style = Typography2.titleSmall
            )
        },

        actions = {
            IconButton(onClick = {
                activity?.finish()
            }) {
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

// Floating Action Button
@Composable
fun FAB(navController: NavController) {

    FloatingActionButton(
        onClick = {
            navController.navigate("create")
        },
        containerColor = MaterialTheme.colorScheme.tertiaryContainer
    ) {
        Icon(
            Icons.Filled.Add,
            contentDescription = "Add",
            tint = MaterialTheme.colorScheme.onTertiaryContainer,
            modifier = Modifier.size(30.dp)
        )
    }
}