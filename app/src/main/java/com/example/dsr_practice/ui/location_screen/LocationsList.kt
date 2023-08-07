package com.example.dsr_practice.ui.location_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.dsr_practice.ui.location_screen.view_pager.all_tab.WeatherItem

@Composable
fun LocationsList(modifier: Modifier = Modifier, itemOnClick: () -> Unit) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp),
        contentPadding = PaddingValues(top = 8.dp, bottom = 128.dp)
    ) {
        //tmp impl
        items(20) {
            WeatherItem(
                city = "Voronezh",
                currentTemp = 15,
                favoriteOnClick = {},
                onClick = itemOnClick,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    }
}