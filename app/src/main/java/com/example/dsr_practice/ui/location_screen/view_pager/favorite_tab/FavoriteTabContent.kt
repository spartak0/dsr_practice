package com.example.dsr_practice.ui.location_screen.view_pager.favorite_tab

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.dsr_practice.ui.location_screen.LocationsList

@Composable
fun FavoriteTabContent() {
    LocationsList(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxSize(),
        itemOnClick = {}
    )
}