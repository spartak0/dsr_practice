package com.example.dsr_practice.ui.details_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.dsr_practice.domain.model.Weather
import com.example.dsr_practice.ui.composables.AppBar
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun DetailsScreen(
    weather: Weather,
    navigator: DestinationsNavigator,
    viewModel: DetailsScreenViewModel = hiltViewModel()
) {
    DetailsScreenContent(
        weather = weather,
        navigationIconOnClick = { navigator.navigateUp() },
        actionIconOnClick = {
            viewModel.deleteWeather(weather.id)
            navigator.navigateUp()
        }
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DetailsScreenContent(
    weather: Weather,
    navigationIconOnClick: () -> Unit,
    actionIconOnClick: () -> Unit
) {
    Scaffold(topBar = {
        DetailsAppBar(
            modifier = Modifier.fillMaxWidth(),
            location = weather.name,
            navigationIconOnClick = navigationIconOnClick,
            deleteOnClick = actionIconOnClick,
        )
    }) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            Text(text = "Current temp:${weather.currentTemp}")
            Text(text = "Condition:${weather.condition}")
            Text(text = "WindSpeed:${weather.windSpeed}")
            Text(text = "Humidity:${weather.humidity}")
            Text(text = "Pressure:${weather.pressure}")
        }
    }
}

@Composable
fun DetailsAppBar(
    modifier: Modifier,
    location: String,
    navigationIconOnClick: () -> Unit,
    deleteOnClick: () -> Unit
) {
    AppBar(
        title = location,
        navigationIcon = Icons.Default.ArrowBack,
        navigationIconOnClick = navigationIconOnClick,
        actionIcon = Icons.Default.Delete,
        actionOnClick = deleteOnClick,
        modifier = modifier,
    )
}