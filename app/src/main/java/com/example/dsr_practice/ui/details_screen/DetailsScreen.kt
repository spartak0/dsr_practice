package com.example.dsr_practice.ui.details_screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.dsr_practice.R
import com.example.dsr_practice.domain.model.Weather
import com.example.dsr_practice.domain.model.settings.Units
import com.example.dsr_practice.ui.components.AppBar
import com.example.dsr_practice.ui.components.DeleteDialog
import com.example.dsr_practice.ui.destinations.MainScreenDestination
import com.example.dsr_practice.utils.generateIconUrl
import com.example.dsr_practice.utils.secToTime
import com.example.dsr_practice.utils.toTempString
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
    val currentLocale = LocalConfiguration.current.locales[0]
    DetailsScreenContent(weather = weather,
        navigationIconOnClick = { navigator.navigate(MainScreenDestination.route){
            popUpTo(MainScreenDestination.route){
                inclusive=true
            }
        } },
        units = units,
        locale = currentLocale,
        actionIconOnClick = {
            dialog = true
        })
    AnimatedVisibility(visible = dialog) {
        DeleteDialog(
            title = stringResource(R.string.delete_location),
            onDismiss = { dialog = false },
            onConfirm = {
                viewModel.deleteWeather(weather.id)
                navigator.navigateUp()
            })
    }
}

@Composable
fun DetailsScreenContent(
    weather: Weather,
    units: Units,
    navigationIconOnClick: () -> Unit,
    actionIconOnClick: () -> Unit,
    locale: Locale,
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
                iconUrl = generateIconUrl(weather.conditionIcon),
                units = units,
            )
            DailyContent(
                date = weather.daily.component1().dt.secToTime(locale),
                mornTemp = weather.daily.component1().morn.toInt(),
                dayTemp = weather.daily.component1().day.toInt(),
                eveTemp = weather.daily.component1().eve.toInt(),
                nightTemp = weather.daily.component1().night.toInt(),
                windSpeed = weather.daily.component1().windSpeed.toInt(),
                humidity = weather.daily.component1().humidity.toInt(),
                pressure = weather.daily.component1().pressure.toInt(),
                units = units,
            )
            AnimatedVisibility(visible = weather.isSecondDayForecast) {
                DailyContent(
                    modifier = Modifier.padding(top = 32.dp),
                    date = weather.daily.component2().dt.secToTime(locale),
                    mornTemp = weather.daily.component2().morn.toInt(),
                    dayTemp = weather.daily.component2().day.toInt(),
                    eveTemp = weather.daily.component2().eve.toInt(),
                    nightTemp = weather.daily.component2().night.toInt(),
                    windSpeed = weather.daily.component2().windSpeed.toInt(),
                    humidity = weather.daily.component2().humidity.toInt(),
                    pressure = weather.daily.component2().pressure.toInt(),
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
        horizontalArrangement = Arrangement.Center,
        modifier = modifier,
    ) {
        GlideImage(
            model = iconUrl,
            contentDescription = null,
            modifier = Modifier.size(150.dp)
        )
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = condition,
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.W300),
                textAlign = TextAlign.Center,
                modifier = Modifier.width(IntrinsicSize.Min)
            )
            Text(
                text = currentTemp.toTempString() + units.tempUnit,
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
        InfoItem(
            iconId = R.drawable.wind_icon,
            text = stringResource(R.string.wind_speed) + " $windSpeed ${units.speedUnit}"
        )
        InfoItem(
            iconId = R.drawable.humidity_icon,
            text = stringResource(R.string.humidity) + " $humidity%"
        )
        InfoItem(
            iconId = R.drawable.pressure_icon,
            text = stringResource(R.string.pressure) + " $pressure ${units.pressureUnit}"
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
            text = temp.toTempString(),
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