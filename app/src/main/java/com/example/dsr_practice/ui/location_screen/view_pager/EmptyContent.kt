package com.example.dsr_practice.ui.location_screen.view_pager

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun EmptyContent(modifier: Modifier = Modifier, text: String) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Text(
            text = text,
            modifier = Modifier
                .padding(16.dp)
        )
    }
}