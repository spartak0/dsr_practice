package com.example.dsr_practice.ui.location_screen.view_pager

import androidx.compose.runtime.Composable
import com.example.dsr_practice.domain.model.Weather
import com.example.dsr_practice.ui.location_screen.view_pager.all_tab.AllTabScreen
import com.example.dsr_practice.ui.location_screen.view_pager.favorite_tab.FavoriteTabScreen


sealed class LocationsViewPagerTab(val title: String, val content: @Composable () -> Unit) {
    class All(navigateToDetails: (Weather) -> Unit) : LocationsViewPagerTab(
        title = "All",
        content = { AllTabScreen(navigateToDetails = navigateToDetails) })

    class Favorite(navigateToDetails: (Weather) -> Unit) : LocationsViewPagerTab(
        title = "Favorite",
        content = { FavoriteTabScreen(navigateToDetails = navigateToDetails) })
}