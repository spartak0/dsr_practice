package com.example.dsr_practice.ui.edit_trigger_screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.dsr_practice.R
import com.example.dsr_practice.domain.model.Trigger
import com.example.dsr_practice.domain.model.settings.Units
import com.example.dsr_practice.ui.components.AppBar
import com.example.dsr_practice.ui.components.DeleteDialog
import com.example.dsr_practice.utils.SnackbarController
import com.example.dsr_practice.ui.destinations.ChooseBindingLocaleScreenDestination
import com.example.dsr_practice.ui.destinations.MainScreenDestination
import com.example.dsr_practice.ui.destinations.TriggerDetailsScreenDestination
import com.example.dsr_practice.ui.settings_screen.UnitsSettings
import com.example.dsr_practice.utils.toIntString
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Destination
@Composable
fun EditTriggersScreen(
    trigger: Trigger,
    navigator: DestinationsNavigator,
    fromRoute: String,
    snackbarController: SnackbarController,
    viewModel: EditTriggersViewModel = hiltViewModel(),
) {
    var name by remember { mutableStateOf(trigger.name ?: "") }
    var temp by remember { mutableStateOf(trigger.temp?.toIntString() ?: "") }
    var windSpeed by remember { mutableStateOf(trigger.windSpeed?.toIntString() ?: "") }
    var humidity by remember { mutableStateOf(trigger.humidity?.toIntString() ?: "") }
    var pressure by remember { mutableStateOf(trigger.pressure?.toIntString() ?: "") }
    var dialog by remember { mutableStateOf(false) }
    val binding = trigger.locationName ?: ""
    val unitsOptions = listOf(Units.Metric, Units.Imperial)
    var unitsSelectedOptions by remember { mutableStateOf(trigger.units ?: Units.Metric) }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val navigateFromDetails = fromRoute == TriggerDetailsScreenDestination.route
    EditTriggersContent(name = name,
        nameOnChange = { name = it },
        temp = temp,
        tempOnChange = { temp = it },
        windSpeed = windSpeed,
        windSpeedOnChange = { windSpeed = it },
        humidity = humidity,
        humidityOnChange = { humidity = it },
        pressure = pressure,
        pressureOnChange = { pressure = it },
        deleteOnClick = { dialog = true },
        navigationIconOnClick = { navigator.navigateUp() },
        titleAppBar = when (fromRoute == TriggerDetailsScreenDestination.route) {
            true -> stringResource(id = R.string.edit_trigger)
            false -> stringResource(R.string.add_trigger)
        },
        bindingValue = binding,
        bindingOnClick = {
            navigator.navigate(
                ChooseBindingLocaleScreenDestination(
                    trigger = trigger.copy(
                        name = name,
                        humidity = viewModel.parseStringToDouble(humidity),
                        temp = viewModel.parseStringToDouble(temp),
                        windSpeed = viewModel.parseStringToDouble(windSpeed),
                        units = unitsSelectedOptions,
                    ),
                    fromRouteCopy = fromRoute,
                )
            )
        },
        unitsOptions = unitsOptions,
        unitsSelectedOptions = unitsSelectedOptions,
        unitsOnOptionSelected = { unitsSelectedOptions = it },
        doneOnClick = {
            val verifyResult: VerifyResult =
                viewModel.verify(binding, name, temp, windSpeed, humidity, pressure)
            val changedTrigger = trigger.copy(
                name = name,
                humidity = viewModel.parseStringToDouble(humidity),
                temp = viewModel.parseStringToDouble(temp),
                windSpeed = viewModel.parseStringToDouble(windSpeed),
                units = unitsSelectedOptions,
            )
            when {
                verifyResult is VerifyResult.Error -> scope.launch {
                    snackbarController.showSnackbar(
                        message = context.getString(verifyResult.messageId!!),
                        withDismissAction = true,
                        duration = SnackbarDuration.Short,
                    )
                }

                (verifyResult is VerifyResult.Success) && navigateFromDetails -> {
                    viewModel.updateTrigger(changedTrigger)
                    navigator.navigate(TriggerDetailsScreenDestination(trigger = changedTrigger)) {
                        popUpTo(TriggerDetailsScreenDestination.route) {
                            inclusive = true
                        }
                    }
                }

                (verifyResult is VerifyResult.Success) && !navigateFromDetails -> {
                    viewModel.addTrigger(changedTrigger)
                    navigator.navigate(TriggerDetailsScreenDestination(trigger = changedTrigger)) {
                        popUpTo(MainScreenDestination.route) {
                            inclusive = false
                        }
                    }
                }
            }
        })

    AnimatedVisibility(visible = dialog) {
        DeleteDialog(title = stringResource(R.string.delete_trigger),
            onDismiss = { dialog = false },
            onConfirm = {
                viewModel.deleteTrigger(trigger.id)
                navigator.popBackStack(MainScreenDestination.route, false)
            })
    }
}

@Composable
fun EditTriggersContent(
    navigationIconOnClick: () -> Unit,
    deleteOnClick: () -> Unit,
    name: String,
    nameOnChange: (String) -> Unit,
    temp: String,
    tempOnChange: (String) -> Unit,
    windSpeed: String,
    windSpeedOnChange: (String) -> Unit,
    humidity: String,
    humidityOnChange: (String) -> Unit,
    pressure: String,
    pressureOnChange: (String) -> Unit,
    doneOnClick: () -> Unit,
    titleAppBar: String,
    bindingValue: String,
    bindingOnClick: () -> Unit,
    unitsOptions: List<Units>,
    unitsSelectedOptions: Units,
    unitsOnOptionSelected: (Units) -> Unit,
) {
    val localeFocusManager = LocalFocusManager.current
    Scaffold(
        topBar = {
            EditTriggersAppBar(
                title = titleAppBar,
                modifier = Modifier.fillMaxWidth(),
                navigationIconOnClick = navigationIconOnClick,
                deleteOnClick = deleteOnClick,
            )
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            CurrentBinding(
                value = bindingValue,
                modifier = Modifier.fillMaxWidth(),
                onClick = bindingOnClick
            )
            EditItem(
                value = name,
                onValueChange = nameOnChange,
                placeholder = { Text(text = stringResource(R.string.name)) },
                keyboardType = KeyboardType.Text,
                onDone = { localeFocusManager.moveFocus(FocusDirection.Down) },
                modifier = Modifier
                    .padding(top = 12.dp)
                    .fillMaxWidth(),
            )
            EditItem(
                value = temp,
                onValueChange = tempOnChange,
                placeholder = { Text(text = stringResource(id = R.string.temperature)) },
                supportingText = { Text(text = stringResource(R.string.optional)) },
                onDone = { localeFocusManager.moveFocus(FocusDirection.Down) },
                modifier = Modifier
                    .padding(top = 12.dp)
                    .fillMaxWidth(),
            )

            EditItem(
                value = windSpeed,
                onValueChange = windSpeedOnChange,
                placeholder = { Text(text = stringResource(id = R.string.wind_speed)) },
                supportingText = { Text(text = stringResource(R.string.optional)) },
                onDone = { localeFocusManager.moveFocus(FocusDirection.Down) },
                modifier = Modifier
                    .padding(top = 12.dp)
                    .fillMaxWidth()
            )
            EditItem(
                value = humidity,
                onValueChange = humidityOnChange,
                placeholder = { Text(text = stringResource(id = R.string.humidity)) },
                supportingText = { Text(text = stringResource(R.string.optional)) },
                onDone = { localeFocusManager.moveFocus(FocusDirection.Down) },
                modifier = Modifier
                    .padding(top = 12.dp)
                    .fillMaxWidth()
            )
            EditItem(
                value = pressure,
                onValueChange = pressureOnChange,
                placeholder = { Text(text = stringResource(id = R.string.pressure)) },
                supportingText = { Text(text = stringResource(R.string.optional)) },
                onDone = { localeFocusManager.clearFocus() },
                modifier = Modifier
                    .padding(top = 12.dp)
                    .fillMaxWidth()
            )
            UnitsSettings(
                modifier = Modifier
                    .padding(top = 12.dp)
                    .fillMaxWidth(),
                unitsOptions = unitsOptions,
                unitsSelectedOptions = unitsSelectedOptions,
                unitsOnOptionSelected = unitsOnOptionSelected
            )
            Button(
                onClick = doneOnClick, modifier = Modifier
                    .padding(top = 12.dp)
                    .height(48.dp)
            ) {
                Text(text = stringResource(R.string.apply))
            }
            Spacer(modifier = Modifier.height(64.dp))
        }
    }

}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CurrentBinding(value: String, modifier: Modifier = Modifier, onClick: () -> Unit) {
    val keyboardController = LocalSoftwareKeyboardController.current
    OutlinedTextField(
        value = value,
        onValueChange = {},
        label = { Text(text = stringResource(R.string.binding)) },
        trailingIcon = {
            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = null
            )
        },
        modifier = modifier.onFocusChanged {
            if (it.isFocused) {
                keyboardController?.hide()
                onClick()
            }
        },
    )
}

@Composable
fun EditItem(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: @Composable (() -> Unit)? = null,
    keyboardType: KeyboardType = KeyboardType.Number,
    supportingText: @Composable (() -> Unit)? = null,
    onDone: (() -> Unit)? = null,
) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = placeholder,
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = keyboardType,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    if (onDone != null) {
                        onDone()
                    }
                }
            ),
            modifier = Modifier.weight(1f),
            supportingText = supportingText,
        )
    }
}

@Composable
fun EditTriggersAppBar(
    title: String,
    modifier: Modifier = Modifier,
    navigationIconOnClick: () -> Unit,
    deleteOnClick: () -> Unit,
) {
    AppBar(
        title = title,
        navigationIcon = Icons.Default.ArrowBack,
        navigationIconOnClick = navigationIconOnClick,
        actionIcon = Icons.Default.Delete,
        actionOnClick = deleteOnClick,
        modifier = modifier,
    )
}