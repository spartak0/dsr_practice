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
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.dsr_practice.R
import com.example.dsr_practice.data.network.api.WeatherApi
import com.example.dsr_practice.domain.model.settings.Units
import com.example.dsr_practice.domain.model.Weather
import com.example.dsr_practice.ui.composables.AppBar
import com.example.dsr_practice.utils.checkUnaryPlus
import com.example.dsr_practice.utils.secToTime
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import java.util.Locale

@Destination
@Composable
fun DetailsScreen(
    weather: Weather,
    navigator: DestinationsNavigator,
    viewModel: DetailsScreenViewModel = hiltViewModel()
) {
    var dialog by remember { mutableStateOf(false) }
    val units by viewModel.currentUnits.collectAsState()
    DetailsScreenContent(weather = weather,
        navigationIconOnClick = { navigator.navigateUp() },
        units = units,
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

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DetailsScreenContent(
    weather: Weather, units: Units, navigationIconOnClick: () -> Unit, actionIconOnClick: () -> Unit
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
            CurrentContent(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                currentTemp = weather.currentTemp.toInt(),
                condition = weather.condition,
                iconUrl = WeatherApi.generateIconUrl(weather.conditionIcon),
                units = units,
            )
            DailyContent(
                date = weather.daily[0].dt.secToTime(Locale.ENGLISH),
                mornTemp = weather.daily[0].morn.toInt(),
                dayTemp = weather.daily[0].day.toInt(),
                eveTemp = weather.daily[0].eve.toInt(),
                nightTemp = weather.daily[0].night.toInt(),
                windSpeed = weather.daily[0].windSpeed.toInt(),
                humidity = weather.daily[0].humidity.toInt(),
                pressure = weather.daily[0].pressure.toInt(),
                units = units,
            )
            AnimatedVisibility(visible = weather.isSecondDayForecast) {
                DailyContent(
                    modifier = Modifier.padding(top = 8.dp),
                    date = weather.daily[1].dt.secToTime(Locale.ENGLISH),
                    mornTemp = weather.daily[1].morn.toInt(),
                    dayTemp = weather.daily[1].day.toInt(),
                    eveTemp = weather.daily[1].eve.toInt(),
                    nightTemp = weather.daily[1].night.toInt(),
                    windSpeed = weather.daily[1].windSpeed.toInt(),
                    humidity = weather.daily[1].humidity.toInt(),
                    pressure = weather.daily[1].pressure.toInt(),
                    units = units,
                )

            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CurrentContent(
    modifier: Modifier = Modifier,
    condition: String,
    currentTemp: Int,
    iconUrl: String,
    units: Units,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = modifier,
    ) {
        GlideImage(
            model = iconUrl,
            contentDescription = null,
            modifier = Modifier.size(150.dp)
        )
        Column {
            Text(
                text = condition,
                style = MaterialTheme.typography.displaySmall.copy(fontWeight = FontWeight.W300)
            )
            Text(
                text = currentTemp.checkUnaryPlus() + units.tempUnit,
                style = MaterialTheme.typography.displaySmall.copy(fontWeight = FontWeight.W300)
            )
        }
    }
}

@Composable
fun DailyContent(
    modifier: Modifier = Modifier,
    date: String,
    mornTemp: Int,
    dayTemp: Int,
    eveTemp: Int,
    nightTemp: Int,
    windSpeed: Int,
    humidity: Int,
    pressure: Int,
    units: Units,
) {
    Column(modifier = modifier) {
        Text(
            text = date,
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.W300,
                fontSize = 22.sp
            ),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 4.dp)
        )
        DailyForecast(
            modifier = Modifier.fillMaxWidth(),
            mornTemp = mornTemp,
            dayTemp = dayTemp,
            eveTemp = eveTemp,
            nightTemp = nightTemp,
        )
        Spacer(modifier = Modifier.height(16.dp))
        InfoItem(iconId = R.drawable.wind_icon, text = "wind speed $windSpeed ${units.speedUnit}")
        InfoItem(iconId = R.drawable.humidity_icon, text = "humidity $humidity%")
        InfoItem(
            iconId = R.drawable.pressure_icon,
            text = "pressure $pressure ${units.pressureUnit}"
        )
    }
}

@Composable
fun DailyForecast(
    modifier: Modifier = Modifier,
    mornTemp: Int,
    dayTemp: Int,
    eveTemp: Int,
    nightTemp: Int
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        DailyCard(
            name = stringResource(R.string.morning),
            iconId = R.drawable.sunrise_icon,
            temp = mornTemp
        )
        DailyCard(
            name = stringResource(R.string.day),
            iconId = R.drawable.sun_icon,
            temp = dayTemp
        )
        DailyCard(
            name = stringResource(R.string.evening),
            iconId = R.drawable.moon_icon,
            temp = eveTemp
        )
        DailyCard(
            name = stringResource(R.string.night),
            iconId = R.drawable.full_moon_icon,
            temp = nightTemp
        )
    }
}

@Composable
fun DailyCard(name: String, iconId: Int, temp: Int) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = name,
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.W300,
                fontSize = 20.sp
            )
        )
        Icon(
            painter = painterResource(id = iconId),
            contentDescription = null,
            modifier = Modifier.size(48.dp)
        )
        Text(
            text = temp.checkUnaryPlus(),
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.W300,
                fontSize = 18.sp
            )
        )
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
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.W300,
                fontSize = 20.sp
            )
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