package com.example.dsr_practice.ui.location_screen.view_pager

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import com.example.dsr_practice.R
import com.example.dsr_practice.domain.model.Weather
import com.example.dsr_practice.ui.location_screen.view_pager.all_tab.AllTabScreen
import com.example.dsr_practice.ui.location_screen.view_pager.favorite_tab.FavoriteTabScreen


sealed class LocationsViewPagerTab(@StringRes val title: Int, val content: @Composable () -> Unit) {
    class All(navigateToDetails: (Weather) -> Unit) : LocationsViewPagerTab(
        title = R.string.all,
        content = { AllTabScreen(navigateToDetails = navigateToDetails) })

    class Favorite(navigateToDetails: (Weather) -> Unit) : LocationsViewPagerTab(
        title = R.string.favorite,
        content = { FavoriteTabScreen(navigateToDetails = navigateToDetails) })
}