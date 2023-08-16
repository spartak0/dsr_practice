package com.example.dsr_practice.ui.details_screen

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.dsr_practice.R
import com.example.dsr_practice.data.network.api.RetrofitApi
import com.example.dsr_practice.domain.model.Weather
import com.example.dsr_practice.ui.composables.AppBar
import com.example.dsr_practice.utils.checkUnaryPlus
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun DetailsScreen(
    weather: Weather,
    navigator: DestinationsNavigator,
    viewModel: DetailsScreenViewModel = hiltViewModel()
) {
    var dialog by remember { mutableStateOf(false) }
    DetailsScreenContent(weather = weather,
        navigationIconOnClick = { navigator.navigateUp() },
        actionIconOnClick = {
            dialog = true
        })
    AnimatedVisibility(visible = dialog) {
        DeleteDialog(onDismiss = { dialog = false }, onConfirm = {
            viewModel.deleteWeather(weather.id)
            navigator.navigateUp()
        })
    }
}

@Composable
fun DeleteDialog(onDismiss: () -> Unit, onConfirm: () -> Unit) {
    AlertDialog(onDismissRequest = onDismiss,
        title = { Text(text = stringResource(R.string.delete_location)) },
        text = { Text(text = stringResource(R.string.this_action_cannot_be_undone)) },
        confirmButton = {
            Button(onClick = onConfirm) {
                Text(text = stringResource(R.string.delete))
            }
        },
        dismissButton = {
            OutlinedButton(onClick = onDismiss) {
                Text(text = stringResource(R.string.cancel))
            }
        })
}

@OptIn(ExperimentalGlideComposeApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DetailsScreenContent(
    weather: Weather, navigationIconOnClick: () -> Unit, actionIconOnClick: () -> Unit
) {
    Scaffold(topBar = {
        DetailsAppBar(
            modifier = Modifier.fillMaxWidth(),
            location = weather.name,
            navigationIconOnClick = navigationIconOnClick,
            deleteOnClick = actionIconOnClick,
        )
    }) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.Start
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                GlideImage(
                    model = RetrofitApi.generateIconUrl(weather.conditionIcon),
                    contentDescription = null,
                    modifier = Modifier.size(150.dp)
                )
                Column {
                    Text(
                        text = weather.condition,
                        style = MaterialTheme.typography.displaySmall.copy(fontWeight = FontWeight.W300)
                    )
                    Text(
                        text = "${weather.currentTemp.toInt().checkUnaryPlus()}°C",
                        style = MaterialTheme.typography.displaySmall.copy(fontWeight = FontWeight.W300)
                    )
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                DailyCard(
                    name = stringResource(R.string.morning),
                    iconId = R.drawable.sunrise_icon,
                    temp = weather.daily[0].morn.toInt()
                )
                DailyCard(
                    name = stringResource(R.string.day),
                    iconId = R.drawable.sun_icon,
                    temp = weather.daily[0].day.toInt()
                )
                DailyCard(
                    name = stringResource(R.string.evening),
                    iconId = R.drawable.moon_icon,
                    temp = weather.daily[0].eve.toInt()
                )
                DailyCard(
                    name = stringResource(R.string.night),
                    iconId = R.drawable.full_moon_icon,
                    temp = weather.daily[0].night.toInt()
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            InfoItem(iconId = R.drawable.wind_icon, text = "wind speed ${weather.windSpeed} m/s")
            InfoItem(iconId = R.drawable.humidity_icon, text = "humidity ${weather.humidity}%")
            InfoItem(iconId = R.drawable.pressure_icon, text = "pressure ${weather.pressure} hPa")
        }
    }
}

@Composable
fun DailyCard(name: String, iconId: Int, temp: Int) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = name)
        Icon(
            painter = painterResource(id = iconId),
            contentDescription = null,
            modifier = Modifier.size(48.dp)
        )
        Text(text = temp.checkUnaryPlus())
    }
}

@Composable
fun InfoItem(iconId: Int, text: String, modifier: Modifier = Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier,
    ) {
        Icon(
            painter = painterResource(id = iconId),
            contentDescription = null,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(2.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.W300)
        )
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