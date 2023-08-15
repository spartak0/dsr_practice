package com.example.dsr_practice.ui.location_screen.view_pager.favorite_tab

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshState
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.dsr_practice.R
import com.example.dsr_practice.domain.model.Weather
import com.example.dsr_practice.ui.location_screen.LocationsScreenViewModel
import com.example.dsr_practice.ui.location_screen.PullRefreshWeatherList
import com.example.dsr_practice.ui.location_screen.view_pager.EmptyContent

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FavoriteTabScreen(
    viewModel: LocationsScreenViewModel = hiltViewModel(),
    navigateToDetails: (Weather) -> Unit
) {
    val weather by viewModel.favoriteWeather.collectAsState()
    val isRefreshing by viewModel.isRefreshing.collectAsState()
    val pullRefreshState = rememberPullRefreshState(
        refreshing = isRefreshing,
        onRefresh = viewModel::onRefresh,
    )

    Crossfade(targetState = weather.isEmpty(), label = "") { isEmpty ->
        when (isEmpty) {
            true -> EmptyContent(
                modifier = Modifier.fillMaxSize(),
                text = stringResource(R.string.empty_favorite)
            )

            false -> FavoriteTabContent(
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
                itemOnClick = { weather -> navigateToDetails(weather) }
            )

        }

    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FavoriteTabContent(
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