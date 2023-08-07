package com.example.dsr_practice.ui.navigation.graphs

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.dsr_practice.ui.NavGraphs
import com.example.dsr_practice.ui.destinations.LocationScreenDestination
import com.example.dsr_practice.ui.destinations.MapScreenDestination
import com.example.dsr_practice.ui.location_screen.LocationScreen
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.manualcomposablecalls.composable
import com.ramcosta.composedestinations.navigation.navigate

@Composable
fun BottomNavHost(
    modifier: Modifier = Modifier,
    externalNavController: NavController,
    navController: NavHostController = rememberNavController(),
) {
    DestinationsNavHost(
        navGraph = NavGraphs.bottomNavigation,
        navController = navController,
        modifier = modifier,
    ) {
        composable(LocationScreenDestination) {
            LocationScreen(navigateToMap = { externalNavController.navigate(MapScreenDestination) })
        }
    }
}