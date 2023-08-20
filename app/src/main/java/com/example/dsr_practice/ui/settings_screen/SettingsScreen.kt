package com.example.dsr_practice.ui.settings_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.dsr_practice.R
import com.example.dsr_practice.domain.model.Units
import com.example.dsr_practice.domain.model.Units.Imperial
import com.example.dsr_practice.domain.model.Units.Metric
import com.example.dsr_practice.ui.composables.VerticalRadioButtonGroup
import com.example.dsr_practice.ui.navigation.graphs.BottomNavGraph
import com.ramcosta.composedestinations.annotation.Destination

@Composable
@BottomNavGraph()
@Destination
fun SettingsScreen(viewModel: SettingsViewModel = hiltViewModel()) {
    val options = listOf(Metric, Imperial)
    val selectedOption by viewModel.currentUnits.collectAsState()
    SettingsScreenContent(
        options = options,
        selectedOptions = selectedOption,
        onOptionSelected = { units ->
            viewModel.setUnits(units)
            viewModel.fetchCurrentUnits()
        }
    )
}

@Composable
fun SettingsScreenContent(
    options: List<Units>,
    selectedOptions: Units,
    onOptionSelected: (Units) -> Unit
) {
    Scaffold(topBar = { SettingsScreenTopAppBar() }) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Text(text = "Units:", style = MaterialTheme.typography.titleLarge)
            VerticalRadioButtonGroup(
                options = options,
                optionsTitle = options.map { it.title },
                selectedOption = selectedOptions,
                onOptionSelected = onOptionSelected
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreenTopAppBar() {
    TopAppBar(
        title = {
            Text(
                text = stringResource(R.string.settings),
                color = MaterialTheme.colorScheme.onPrimary
            )
        }, colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary
        )
    )
}