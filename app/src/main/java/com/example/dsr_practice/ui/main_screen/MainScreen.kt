package com.example.dsr_practice.ui.main_screen

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.dsr_practice.domain.model.Weather
import com.example.dsr_practice.ui.NavGraphs
import com.example.dsr_practice.ui.appCurrentDestinationAsState
import com.example.dsr_practice.utils.SnackbarController
import com.example.dsr_practice.ui.destinations.Destination
import com.example.dsr_practice.ui.destinations.DetailsScreenDestination
import com.example.dsr_practice.ui.navigation.graphs.BottomNavHost
import com.example.dsr_practice.ui.startAppDestination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.navigate


@Composable
@RootNavGraph(start = true)
@com.ramcosta.composedestinations.annotation.Destination
fun MainScreen(
    externalNavController: NavController,
    navigator: DestinationsNavigator,
    snackbarController: SnackbarController,
    viewModel: MainScreenViewModel = hiltViewModel()
) {
    val navController = rememberNavController()
    val startDestination by viewModel.rootGraphStartDestination.collectAsState()
    val weather by viewModel.weather.collectAsState()
    MainScreenContent(
        navController = navController,
        externalNavController = externalNavController,
        bottomBarItems = BottomBarScreenModel.bottomBarScreensList,
        snackbarController = snackbarController,
    )
    LaunchedEffect(key1 = true) {
        if (startDestination == DetailsScreenDestination.route) {
            navigator.navigate(DetailsScreenDestination(weather = weather ?: Weather()))
            viewModel.clearStartDestination()
        }
    }
}

@Composable
fun MainScreenContent(
    snackbarController: SnackbarController,
    navController: NavHostController,
    externalNavController: NavController,
    bottomBarItems: List<BottomBarScreenModel>
) {
    Scaffold(
        bottomBar = { BottomBar(navController = navController, bottomBarItems = bottomBarItems) },
    ) { paddingValues ->
        BottomNavHost(
            navController = navController,
            externalNavController = externalNavController,
            modifier = Modifier.padding(paddingValues),
            snackbarController = snackbarController,
        )
    }
}

@Composable
fun BottomBar(
    navController: NavController,
    bottomBarItems: List<BottomBarScreenModel>
) {
    val currentDestination: Destination = navController.appCurrentDestinationAsState().value
        ?: NavGraphs.bottomNavigation.startAppDestination

    BottomAppBar {
        bottomBarItems.forEach { destination ->
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
                        destination.icon, contentDescription = null
                    )
                },
                label = { Text(stringResource(id = destination.label)) },
            )
        }
    }
}

