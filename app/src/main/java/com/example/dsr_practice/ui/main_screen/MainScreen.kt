package com.example.dsr_practice.ui.main_screen

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.dsr_practice.ui.NavGraphs
import com.example.dsr_practice.ui.appCurrentDestinationAsState
import com.example.dsr_practice.ui.destinations.Destination
import com.example.dsr_practice.ui.navigation.graphs.BottomNavHost
import com.example.dsr_practice.ui.startAppDestination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.navigate


@Composable
@RootNavGraph(start = true)
@com.ramcosta.composedestinations.annotation.Destination
fun MainScreen(externalNavController: NavController) {
    val navController = rememberNavController()
    MainScreenContent(
        navController = navController,
        externalNavController = externalNavController,
    )
}

@Composable
fun MainScreenContent(navController: NavHostController, externalNavController: NavController) {
    Scaffold(
        bottomBar = { BottomBar(navController = navController) },
    ) { paddingValues ->
        BottomNavHost(
            navController = navController,
            externalNavController = externalNavController,
            modifier = Modifier.padding(paddingValues)
        )
    }
}

@Composable
fun BottomBar(
    navController: NavController
) {
    val currentDestination: Destination = navController.appCurrentDestinationAsState().value
        ?: NavGraphs.bottomNavigation.startAppDestination

    BottomAppBar {
        BottomBarScreenEnum.values().forEach { destination ->
            NavigationBarItem(
                selected = currentDestination == destination.direction,
                onClick = {
                    navController.navigate(
                        destination.direction
                    ) {
                        launchSingleTop = true
                    }
                },
                icon = {
                    Icon(
                        destination.icon, contentDescription = destination.label
                    )
                },
                label = { Text(destination.label) },
            )
        }
    }
}

