package com.example.dsr_practice.ui.location_screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.PullRefreshState
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.dsr_practice.domain.model.Weather

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PullRefreshWeatherList(
    list: List<Weather>,
    pullRefreshState: PullRefreshState,
    isRefreshing: Boolean,
    isFavoriteOnClick: (Weather) -> Unit,
    itemOnClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .pullRefresh(pullRefreshState)
    ) {
        LocationsList(
            list = list,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxSize(),
            itemOnClick = itemOnClick,
            isFavoriteOnClick = isFavoriteOnClick,
        )
        PullRefreshIndicator(
            refreshing = isRefreshing,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }

}