package com.example.dsr_practice.ui.location_name_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.dsr_practice.R
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun LocationNameScreen(navigator: DestinationsNavigator) {
    var name by remember { mutableStateOf("") }

    LocationNameScreenContent(
        name = name,
        nameOnChange = { name = it },
        nextOnClick = {},
        backOnClick = { navigator.navigateUp() })
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun LocationNameScreenContent(
    name: String,
    nameOnChange: (String) -> Unit,
    nextOnClick: () -> Unit,
    backOnClick: () -> Unit
) {
    Scaffold(topBar = { LocationNameAppBar(backOnClick = backOnClick) }) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            OutlinedTextField(
                value = name,
                onValueChange = nameOnChange,
                singleLine = true,
                label = { Text(text = stringResource(R.string.location_name)) },
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
                    .align(
                        Alignment.Center
                    )
            )
            Button(
                onClick = nextOnClick,
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
                    .height(48.dp)
                    .align(Alignment.BottomCenter),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(text = stringResource(R.string.next))
            }
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationNameAppBar(backOnClick: () -> Unit) {
    TopAppBar(
        title = {
            Text(
                text = stringResource(R.string.location_name),
                color = MaterialTheme.colorScheme.onPrimary
            )
        },
        navigationIcon = {
            IconButton(onClick = backOnClick) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary
        )
    )
}