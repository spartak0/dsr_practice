package com.example.dsr_practice.ui.composables

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import com.example.dsr_practice.domain.model.Place

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    shape: Shape,
    value: String,
    onValueChange: (String) -> Unit,
    autocompleteList: List<Place> = listOf(),
    itemOnClick: (Place) -> Unit
) {
    var focus by remember { mutableStateOf(false) }
    Column(
        modifier = modifier
            .background(MaterialTheme.colorScheme.surfaceVariant, shape)
            .animateContentSize()
    ) {
        TextField(
            value = value,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                )
            },
            onValueChange = onValueChange,
            singleLine = true,
            shape = RectangleShape,
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent,
            ),
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged {
                    focus = it.isFocused
                },
        )
        if (autocompleteList.isNotEmpty() && focus && value.isNotEmpty()) {
            autocompleteList.forEach { place ->
                SearchBarItem(
                    text = place.address,
                    onClick = { itemOnClick(place) },
                    modifier = Modifier
                        .height(48.dp)
                        .fillMaxWidth(),
                )
            }
        }
    }
}

@Composable
fun SearchBarItem(text: String, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Box(
        modifier = modifier.clickable(onClick = onClick),
        contentAlignment = Alignment.CenterStart
    ) {
        Text(text = text, modifier = Modifier.padding(horizontal = 16.dp))
    }
}