package com.example.dsr_practice.ui.location_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.dsr_practice.domain.model.Weather

@Composable
fun LocationsList(
    list: List<Weather>,
    modifier: Modifier = Modifier,
    itemOnClick: (Weather) -> Unit,
    isFavoriteOnClick: (Weather) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp),
        contentPadding = PaddingValues(top = 8.dp, bottom = 128.dp)
    ) {
        items(list) { weather ->
            WeatherListItem(
                city = weather.name,
                currentTemp = weather.currentTemp.toInt(),
                isFavoriteOnClick = { isFavoriteOnClick(weather) },
                onClick = { itemOnClick(weather) },
                isFavorite = weather.isFavorite,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    }
}