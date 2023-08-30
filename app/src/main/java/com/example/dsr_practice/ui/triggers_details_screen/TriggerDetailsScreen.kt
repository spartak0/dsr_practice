package com.example.dsr_practice.ui.triggers_details_screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import com.example.dsr_practice.R
import com.example.dsr_practice.domain.model.Trigger
import com.example.dsr_practice.domain.model.settings.Units
import com.example.dsr_practice.ui.components.AppBar
import com.example.dsr_practice.ui.components.DeleteDialog
import com.example.dsr_practice.ui.destinations.EditTriggersScreenDestination
import com.example.dsr_practice.ui.destinations.TriggerDetailsScreenDestination
import com.example.dsr_practice.utils.toIntString
import com.example.dsr_practice.utils.toTempString
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun TriggerDetailsScreen(
    trigger: Trigger,
    navigator: DestinationsNavigator,
    viewModel: TriggersDetailsViewModel = hiltViewModel(),
) {
    val units = trigger.units?:Units.Metric
    var dialog by remember { mutableStateOf(false) }

    TriggerDetailsContent(
        deleteOnClick = {
            dialog = true
        },
        navigationIconOnClick = { navigator.navigateUp() },
        editIconOnClick = {
            navigator.navigate(
                EditTriggersScreenDestination(
                    trigger = trigger,
                    fromRoute = TriggerDetailsScreenDestination.route
                )
            )
        },
        triggerName = trigger.name ?: "",
        temp = trigger.temp?.toTempString() ?: "",
        windSpeed = trigger.windSpeed?.toIntString() ?: "",
        humidity = trigger.humidity?.toIntString() ?: "",
        pressure = trigger.pressure?.toIntString() ?: "",
        units = units,
        nameLocation = trigger.locationName ?: "",

        )
    AnimatedVisibility(visible = dialog) {
        DeleteDialog(
            title = stringResource(R.string.delete_trigger),
            onDismiss = { dialog = false },
            onConfirm = {
                viewModel.deleteTrigger(trigger.id)
                navigator.navigateUp()
            })
    }
}

@Composable
fun TriggerDetailsContent(
    deleteOnClick: () -> Unit,
    navigationIconOnClick: () -> Unit,
    editIconOnClick: () -> Unit,
    triggerName: String,
    temp: String,
    windSpeed: String,
    humidity: String,
    pressure: String,
    units: Units,
    nameLocation: String
) {
    Scaffold(
        topBar = {
            TriggerDetailsAppBar(
                triggerName = triggerName,
                navigationIconOnClick = navigationIconOnClick,
                deleteOnClick = deleteOnClick,
                editIconOnClick = editIconOnClick,
            )
        }
    ) { paddingValue ->
        Box(
            modifier = Modifier
                .padding(paddingValue)
                .padding(16.dp)
                .fillMaxSize(),
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top
            ) {
                Text(
                    text = nameLocation,
                    style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.W300),
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(bottom = 16.dp)
                )
                if (temp.isNotBlank()) InfoRow(
                    iconId = R.drawable.temp,
                    text = stringResource(R.string.temperature) + " $temp ${units.tempUnit}"
                )
                if (windSpeed.isNotBlank()) InfoRow(
                    iconId = R.drawable.wind_icon,
                    text = stringResource(R.string.wind_speed) + " $windSpeed ${units.speedUnit}"
                )
                if (humidity.isNotBlank()) InfoRow(
                    iconId = R.drawable.humidity_icon,
                    text = stringResource(R.string.humidity) + " $humidity%"
                )
                if (pressure.isNotBlank()) InfoRow(
                    iconId = R.drawable.pressure_icon,
                    text = stringResource(R.string.pressure) + " $pressure ${units.pressureUnit}"
                )
            }
        }

    }
}


@Composable
fun InfoRow(iconId: Int, text: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            painter = painterResource(id = iconId),
            modifier = Modifier.size(28.dp),
            contentDescription = null
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.W300)
        )
    }
}

@Composable
fun TriggerDetailsAppBar(
    modifier: Modifier = Modifier,
    triggerName: String,
    navigationIconOnClick: () -> Unit,
    editIconOnClick: () -> Unit,
    deleteOnClick: () -> Unit,
) {
    AppBar(
        title = triggerName,
        navigationIcon = Icons.Default.ArrowBack,
        navigationIconOnClick = navigationIconOnClick,
        actions = {
            IconButton(onClick = editIconOnClick) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
            IconButton(onClick = deleteOnClick) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        },
        modifier = modifier,
    )
}