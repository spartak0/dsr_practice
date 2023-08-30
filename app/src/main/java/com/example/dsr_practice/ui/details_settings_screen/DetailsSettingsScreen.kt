package com.example.dsr_practice.ui.details_settings_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.dsr_practice.R
import com.example.dsr_practice.domain.model.Weather
import com.example.dsr_practice.ui.components.AppBar
import com.example.dsr_practice.ui.components.VerticalRadioButtonGroup
import com.example.dsr_practice.ui.destinations.MainScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun DetailsSettingsScreen(
    navigator: DestinationsNavigator,
    weatherData: Weather,
    viewModel: DetailsSettingsViewModel = hiltViewModel()
) {
    val options = ForecastRadioButtonOptions.values().toList()
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(options[0]) }

    DetailsSettingsScreenContent(
        backOnClick = { navigator.navigateUp() },
        options = options,
        selectedOption = selectedOption,
        onOptionSelected = onOptionSelected,
        nextOnClick = {
            val newWeather = weatherData.copy(
                isSecondDayForecast = when (selectedOption) {
                    ForecastRadioButtonOptions.OneDay -> false
                    ForecastRadioButtonOptions.TwoDay -> true
                }
            )
            viewModel.addWeatherInDatabase(newWeather)
            navigator.navigate(
                MainScreenDestination()
            ) {
                popUpTo(MainScreenDestination.route) {
                    inclusive = true
                }
            }
        }
    )
}

@Composable
fun DetailsSettingsScreenContent(
    backOnClick: () -> Unit,
    options: List<ForecastRadioButtonOptions>,
    selectedOption: ForecastRadioButtonOptions,
    onOptionSelected: (ForecastRadioButtonOptions) -> Unit,
    nextOnClick: () -> Unit,
) {
    Scaffold(topBar = { DetailsSettingsAppBar(backOnClick = backOnClick) }) { paddingValue ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValue)
        ) {
            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .padding(start = 16.dp)
                    .align(Alignment.Center)
            ) {
                Text(
                    text = stringResource(R.string.show_location_weather),
                    style = MaterialTheme.typography.headlineSmall
                )
                VerticalRadioButtonGroup(
                    options = options,
                    optionsTitle = options.map { stringResource(id = it.description) },
                    selectedOption = selectedOption,
                    onOptionSelected = onOptionSelected,
                )
            }
            Button(
                onClick = nextOnClick,
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
                    .height(48.dp)
                    .align(Alignment.BottomCenter),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(text = stringResource(R.string.next))
            }
        }


    }
}

@Composable
fun DetailsSettingsAppBar(backOnClick: () -> Unit) {
    AppBar(
        title = stringResource(R.string.weather_details),
        navigationIcon = Icons.Default.ArrowBack,
        navigationIconOnClick = backOnClick
    )
}