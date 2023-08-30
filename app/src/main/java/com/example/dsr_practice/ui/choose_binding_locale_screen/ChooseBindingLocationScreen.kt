package com.example.dsr_practice.ui.choose_binding_locale_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.dsr_practice.R
import com.example.dsr_practice.domain.model.Trigger
import com.example.dsr_practice.domain.model.Weather
import com.example.dsr_practice.ui.components.AppBar
import com.example.dsr_practice.ui.destinations.EditTriggersScreenDestination
import com.example.dsr_practice.ui.location_screen.view_pager.EmptyContent
import com.example.dsr_practice.ui.triggers_screen.TriggerListItem
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.popUpTo


@Destination
@Composable
fun ChooseBindingLocaleScreen(
    trigger: Trigger,
    navigator: DestinationsNavigator,
    fromRouteCopy: String,
    viewModel: ChooseBindingLocationViewModel = hiltViewModel()
) {
    val weather by viewModel.weather.collectAsState()
    ChooseBindingLocationContent(
        list = weather,
        itemOnClick = { location ->
            navigator.navigate(
                EditTriggersScreenDestination(
                    trigger = trigger.copy(locationId = location.id, locationName = location.name),
                    fromRoute = fromRouteCopy,
                )
            ) {
                popUpTo(EditTriggersScreenDestination) {
                    inclusive = true
                }
            }
        },
        navigationIconOnClick = {
            navigator.navigateUp()
        }
    )
}

@Composable
fun ChooseBindingLocationContent(
    list: List<Weather>,
    itemOnClick: (Weather) -> Unit,
    navigationIconOnClick: () -> Unit
) {
    Scaffold(
        topBar = {
            ChooseBindingLocationAppBar(
                modifier = Modifier.fillMaxWidth(),
                navigationIconOnClick = navigationIconOnClick
            )
        }
    ) { paddingValues ->
        if (list.isEmpty()) EmptyContent(
            text = stringResource(id = R.string.empty_locations),
            modifier = Modifier.fillMaxSize(),
        )
        else ChooseBindingLocationList(
            list = list,
            itemOnClick = itemOnClick,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        )
    }

}

@Composable
fun ChooseBindingLocationList(
    list: List<Weather>, modifier: Modifier = Modifier, itemOnClick: (Weather) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp),
        contentPadding = PaddingValues(top = 8.dp, bottom = 128.dp)
    ) {
        items(list) { weather ->
            TriggerListItem(
                name = weather.name,
                onClick = { itemOnClick(weather) },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun ChooseBindingLocationAppBar(
    modifier: Modifier = Modifier,
    navigationIconOnClick: () -> Unit,
) {
    AppBar(
        title = stringResource(R.string.choose_location),
        navigationIcon = Icons.Default.ArrowBack,
        navigationIconOnClick = navigationIconOnClick,
        modifier = modifier,
    )
}
