package com.example.dsr_practice.ui.details_settings_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.dsr_practice.R
import com.example.dsr_practice.ui.composables.AppBar
import com.example.dsr_practice.ui.destinations.MainScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Destination
@Composable
fun DetailsSettingsScreen(navigator: DestinationsNavigator) {
    val options = RadioButtonOptions.values().map { it.description }.toList()
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(options[0]) }

    DetailsSettingsScreenContent(
        backOnClick = { navigator.navigateUp() },
        options = options,
        selectedOption = selectedOption,
        onOptionSelected = onOptionSelected,
        nextOnClick = {navigator.navigate(MainScreenDestination)}
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DetailsSettingsScreenContent(
    backOnClick: () -> Unit,
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit,
    nextOnClick: () -> Unit,
) {
    Scaffold(topBar = { DetailsSettingsAppBar(backOnClick = backOnClick) }) {
        Box(modifier = Modifier.fillMaxSize()) {
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
                RadioButtonGroup(
                    options = options,
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
fun RadioButtonGroup(
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit
) {
    Column {
        options.forEach { text ->
            Row(
                Modifier
                    .fillMaxWidth()
                    .selectable(
                        selected = (text == selectedOption),
                        onClick = {
                            onOptionSelected(text)
                        }
                    )
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = (text == selectedOption),
                    onClick = { onOptionSelected(text) }
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = text,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}

@Composable
fun DetailsSettingsAppBar(backOnClick: () -> Unit) {
    AppBar(title = stringResource(R.string.weather_details), backOnClick = backOnClick)
}