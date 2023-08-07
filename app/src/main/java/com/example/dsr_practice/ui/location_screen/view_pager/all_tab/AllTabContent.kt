package com.example.dsr_practice.ui.location_screen.view_pager.all_tab

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dsr_practice.ui.location_screen.LocationsList

@Composable
fun AllTabContent() {
    LocationsList(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxSize(),
        itemOnClick = {},
    )
}

@Composable
fun WeatherItem(
    city: String,
    currentTemp: Int,
    favoriteOnClick: () -> Unit,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.clip(RoundedCornerShape(8.dp)).clickable { onClick() }) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(Modifier.padding(start=2.dp)) {
                Text(
                    text = city,
                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 18.sp)
                )
                Text(
                    text = "Current temperature: $currentTemp C",
                    style = MaterialTheme.typography.bodySmall.copy(fontSize = 14.sp),
                    color = MaterialTheme.colorScheme.outline
                )
            }
            IconButton(onClick = favoriteOnClick) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = null
                )
            }
        }
        Divider(
            modifier = Modifier
                .padding(horizontal = 32.dp)
                .padding(top = 4.dp)
                .fillMaxWidth(),
            color = MaterialTheme.colorScheme.outlineVariant
        )
    }
}