package com.example.dsr_practice.ui.location_name_screen

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.dsr_practice.R
import com.example.dsr_practice.domain.model.Weather
import com.example.dsr_practice.ui.composables.AppBar
import com.example.dsr_practice.ui.destinations.DetailsSettingsScreenDestination
import com.google.maps.model.LatLng
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun LocationNameScreen(
    navigator: DestinationsNavigator,
    weatherData: Weather,
    viewModel: LocationNameViewModel = hiltViewModel()
) {
    val name by viewModel.name.collectAsState()
    val context = LocalContext.current


    LocationNameScreenContent(
        name = name,
        nameOnChange = { viewModel.setName(it) },
        nextOnClick = {
            if (!viewModel.validTest(name))
                Toast.makeText(
                    context,
                    context.getText(R.string.invalid_name), Toast.LENGTH_SHORT
                ).show()
            else navigator.navigate(
                DetailsSettingsScreenDestination(
                    weatherData = weatherData.copy(
                        name = name
                    )
                )
            )
        },
        backOnClick = { navigator.navigateUp() })
    LaunchedEffect(true) {
        viewModel.fetchPlaceName(LatLng(weatherData.lat, weatherData.lon))
    }

}

@Composable
fun LocationNameScreenContent(
    name: String,
    nameOnChange: (String) -> Unit,
    nextOnClick: () -> Unit,
    backOnClick: () -> Unit
) {
    Scaffold(topBar = { LocationNameAppBar(backOnClick = backOnClick) }) { paddingValue ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValue)
        ) {
            OutlinedTextField(
                value = name,
                onValueChange = nameOnChange,
                singleLine = true,
                label = { Text(text = stringResource(R.string.location_name)) },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(
                    onNext = { nextOnClick() }
                ),
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
                    .align(
                        Alignment.Center
                    )
            )
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
fun LocationNameAppBar(backOnClick: () -> Unit) {
    AppBar(
        title = stringResource(id = R.string.location_name),
        navigationIcon = Icons.Default.ArrowBack,
        navigationIconOnClick = backOnClick
    )
}