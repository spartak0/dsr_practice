package com.example.dsr_practice.ui.location_screen.view_pager.favorite_tab

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.dsr_practice.domain.model.Weather
import com.example.dsr_practice.ui.location_screen.LocationsList
import com.example.dsr_practice.ui.location_screen.view_pager.all_tab.AllTabContent

@Composable
fun FavoriteTabContent(viewModel: FavoriteTabViewModel = hiltViewModel()) {
    val weather by viewModel.weather.collectAsState()
    AllTabContent(
        list = weather,
        isFavoriteOnClick = { onClickWeather ->
            viewModel.updateWeather(
                onClickWeather.copy(
                    isFavorite =!onClickWeather.isFavorite
                )
            )
        },
        itemOnClick = {}
    )
}

@Composable
fun FavoriteTabContent(
    list: List<Weather>, isFavoriteOnClick: (Weather) -> Unit, itemOnClick: () -> Unit
) {
    LocationsList(
        list = list,
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxSize(),
        itemOnClick = itemOnClick,
        isFavoriteOnClick = isFavoriteOnClick,
    )
}