package com.example.dsr_practice.ui.location_screen.view_pager

import androidx.compose.runtime.Composable
import com.example.dsr_practice.ui.location_screen.view_pager.all_tab.AllTabContent
import com.example.dsr_practice.ui.location_screen.view_pager.favorite_tab.FavoriteTabContent


enum class LocationsViewPagerTab(val title: String, val content: @Composable () -> Unit) {
    All(title = "All", content = { AllTabContent() }),
    Favorite(title = "Favorite", content = { FavoriteTabContent() }),
}