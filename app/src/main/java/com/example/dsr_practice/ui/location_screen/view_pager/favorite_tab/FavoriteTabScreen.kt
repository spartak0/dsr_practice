package com.example.dsr_practice.ui.location_screen.view_pager.favorite_tab

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshState
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.dsr_practice.domain.model.Weather
import com.example.dsr_practice.ui.location_screen.PullRefreshWeatherList

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FavoriteTabContent(viewModel: FavoriteTabViewModel = hiltViewModel()) {
    val weather by viewModel.weather.collectAsState()
    val isRefreshing by viewModel.isRefreshing.collectAsState()
    val pullRefreshState = rememberPullRefreshState(
        refreshing = isRefreshing,
        onRefresh = viewModel::onRefresh,
    )
    FavoriteTabContent(
        list = weather,
        pullRefreshState = pullRefreshState,
        isRefreshing = isRefreshing,
        isFavoriteOnClick = { onClickWeather ->
            viewModel.updateWeather(
                onClickWeather.copy(
                    isFavorite = !onClickWeather.isFavorite
                )
            )
        },
        itemOnClick = {}
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FavoriteTabContent(
    list: List<Weather>,
    pullRefreshState: PullRefreshState,
    isRefreshing: Boolean,
    isFavoriteOnClick: (Weather) -> Unit,
    itemOnClick: () -> Unit
) {
    PullRefreshWeatherList(
        list = list,
        pullRefreshState = pullRefreshState,
        isRefreshing = isRefreshing,
        isFavoriteOnClick = isFavoriteOnClick,
        itemOnClick = itemOnClick
    )
}