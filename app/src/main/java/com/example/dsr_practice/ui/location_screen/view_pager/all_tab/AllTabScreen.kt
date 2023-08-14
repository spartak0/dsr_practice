package com.example.dsr_practice.ui.location_screen.view_pager.all_tab

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshState
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.dsr_practice.domain.model.Weather
import com.example.dsr_practice.ui.location_screen.LocationsScreenViewModel
import com.example.dsr_practice.ui.location_screen.PullRefreshWeatherList
import com.example.dsr_practice.ui.location_screen.view_pager.EmptyContent

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AllTabScreen(
    viewModel: LocationsScreenViewModel = hiltViewModel(),
    navigateToDetails: (Weather) -> Unit,
) {
    val weather by viewModel.weather.collectAsState()
    val isRefreshing by viewModel.isRefreshing.collectAsState()
    val pullRefreshState = rememberPullRefreshState(
        refreshing = isRefreshing,
        onRefresh = viewModel::onRefresh,
    )
    Crossfade(targetState = weather.isEmpty(), label = "") {
        when(it){
            true->EmptyContent(modifier = Modifier.fillMaxSize())
            false-> AllTabContent(
                list = weather,
                pullRefreshState = pullRefreshState,
                isRefreshing = isRefreshing,
                isFavoriteOnClick = { onClickedWeather ->
                    viewModel.updateWeather(
                        onClickedWeather.copy(
                            isFavorite = !onClickedWeather.isFavorite
                        )
                    )
                },
                itemOnClick = { weather -> navigateToDetails(weather) })
        }

    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AllTabContent(
    list: List<Weather>,
    pullRefreshState: PullRefreshState,
    isRefreshing: Boolean,
    isFavoriteOnClick: (Weather) -> Unit,
    itemOnClick: (Weather) -> Unit
) {
    PullRefreshWeatherList(
        list = list,
        pullRefreshState = pullRefreshState,
        isRefreshing = isRefreshing,
        isFavoriteOnClick = isFavoriteOnClick,
        itemOnClick = itemOnClick
    )
}