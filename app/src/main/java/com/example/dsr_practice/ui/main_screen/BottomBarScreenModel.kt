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

enum class BottomBarScreenModel(
    val direction: DirectionDestinationSpec,
    val icon: ImageVector,
    @StringRes val label: Int
) {
    LocationScreen(
        direction = LocationScreenDestination,
        label = R.string.location,
        icon = Icons.Default.LocationOn
    ),
    TriggersScreen(
        direction = TriggersScreenDestination,
        label = R.string.triggers,
        icon = Icons.Default.Notifications
    ),
    SettingsScreen(
        direction = SettingsScreenDestination,
        label = R.string.settings,
        icon = Icons.Default.Settings
    )
}
