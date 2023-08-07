package com.example.dsr_practice.ui.triggers_screen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.dsr_practice.ui.navigation.graphs.BottomNavGraph
import com.ramcosta.composedestinations.annotation.Destination

@BottomNavGraph
@Destination
@Composable
fun TriggersScreen() {
    Text(text = "Triggers")
}