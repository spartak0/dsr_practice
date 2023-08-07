package com.example.dsr_practice.ui.settings_screen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.dsr_practice.ui.navigation.graphs.BottomNavGraph
import com.ramcosta.composedestinations.annotation.Destination

@Composable
@BottomNavGraph()
@Destination
fun SettingsScreen() {
    Text(text = "Settings")
}