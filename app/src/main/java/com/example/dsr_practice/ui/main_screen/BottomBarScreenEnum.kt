package com.example.dsr_practice.ui.main_screen

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.dsr_practice.ui.destinations.LocationScreenDestination
import com.example.dsr_practice.ui.destinations.SettingsScreenDestination
import com.example.dsr_practice.ui.destinations.TriggersScreenDestination
import com.ramcosta.composedestinations.spec.DirectionDestinationSpec

enum class BottomBarScreenEnum(
    val direction: DirectionDestinationSpec,
    val icon: ImageVector,
    val label: String
) {
    LocationScreen(
        direction = LocationScreenDestination,
        label = "Location",
        icon = Icons.Default.LocationOn
    ),
    TriggersScreen(
        direction = TriggersScreenDestination,
        label = "Triggers",
        icon = Icons.Default.Notifications
    ),
    SettingsScreen(
        direction = SettingsScreenDestination,
        label = "Settings",
        icon = Icons.Default.Settings
    )
}
