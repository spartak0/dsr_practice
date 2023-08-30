package com.example.dsr_practice.ui.navigation.graphs

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.dsr_practice.domain.model.Trigger
import com.example.dsr_practice.ui.NavGraphs
import com.example.dsr_practice.ui.destinations.DetailsScreenDestination
import com.example.dsr_practice.ui.destinations.EditTriggersScreenDestination
import com.example.dsr_practice.ui.destinations.LocationScreenDestination
import com.example.dsr_practice.ui.destinations.MapScreenDestination
import com.example.dsr_practice.ui.destinations.SettingsScreenDestination
import com.example.dsr_practice.ui.destinations.TriggerDetailsScreenDestination
import com.example.dsr_practice.ui.destinations.TriggersScreenDestination
import com.example.dsr_practice.ui.location_screen.LocationScreen
import com.example.dsr_practice.ui.settings_screen.SettingsScreen
import com.example.dsr_practice.ui.triggers_screen.TriggersScreen
import com.example.dsr_practice.utils.SnackbarController
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.manualcomposablecalls.composable
import com.ramcosta.composedestinations.navigation.dependency
import com.ramcosta.composedestinations.navigation.navigate
import com.ramcosta.composedestinations.navigation.popUpTo

@Composable
fun BottomNavHost(
    modifier: Modifier = Modifier,
    externalNavController: NavController,
    snackbarController: SnackbarController,
    navController: NavHostController = rememberNavController(),
) {
    DestinationsNavHost(
        navGraph = NavGraphs.bottomNavigation,
        navController = navController,
        modifier = modifier,
        dependenciesContainerBuilder = { dependency(snackbarController) }
    ) {
        composable(LocationScreenDestination) {
            LocationScreen(
                navigateToMap = { externalNavController.navigate(MapScreenDestination) },
                navigateToDetails = { weather ->
                    externalNavController.navigate(
                        DetailsScreenDestination(
                            weather = weather
                        )
                    )
                })
        }
        composable(TriggersScreenDestination) {
            TriggersScreen(
                navigateToDetails = { trigger ->
                    externalNavController.navigate(
                        TriggerDetailsScreenDestination(
                            trigger = trigger,
                        )
                    )
                },
                navigateUp = {
                    navController.navigate(LocationScreenDestination) {
                        popUpTo(LocationScreenDestination) {
                            inclusive = false
                        }
                    }
                },
                navigateToEdit = {
                    externalNavController.navigate(
                        EditTriggersScreenDestination(
                            trigger = Trigger(),
                            fromRoute = TriggersScreenDestination.route
                        )
                    )
                },
                snackbarController = snackbarController
            )
        }
        composable(SettingsScreenDestination) {
            SettingsScreen(
                navigateUp = {
                    navController.navigate(LocationScreenDestination) {
                        popUpTo(LocationScreenDestination) {
                            inclusive = false
                        }
                    }
                },
                snackbarController = snackbarController,
            )
        }
    }
}