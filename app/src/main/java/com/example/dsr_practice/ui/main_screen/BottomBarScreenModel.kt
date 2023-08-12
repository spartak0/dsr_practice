package com.example.dsr_practice.ui.main_screen

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.dsr_practice.R
import com.example.dsr_practice.ui.destinations.LocationScreenDestination
import com.example.dsr_practice.ui.destinations.SettingsScreenDestination
import com.example.dsr_practice.ui.destinations.TriggersScreenDestination
import com.ramcosta.composedestinations.spec.DirectionDestinationSpec

sealed class BottomBarScreenModel(
    val direction: DirectionDestinationSpec,
    val icon: ImageVector,
    @StringRes val label: Int
) {
    object LocationScreen : BottomBarScreenModel(
        direction = LocationScreenDestination,
        label = R.string.location,
        icon = Icons.Default.LocationOn
    )

    object TriggersScreen : BottomBarScreenModel(
        direction = TriggersScreenDestination,
        label = R.string.triggers,
        icon = Icons.Default.Notifications
    )

    object SettingsScreen : BottomBarScreenModel(
        direction = SettingsScreenDestination,
        label = R.string.settings,
        icon = Icons.Default.Settings
    )

    companion object {
        val bottomBarScreensList = listOf(
            LocationScreen,
            TriggersScreen,
            SettingsScreen
        )
    }
}
