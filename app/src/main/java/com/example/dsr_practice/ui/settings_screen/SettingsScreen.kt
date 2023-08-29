package com.example.dsr_practice.ui.settings_screen

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.os.LocaleListCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.dsr_practice.R
import com.example.dsr_practice.domain.model.settings.Locales
import com.example.dsr_practice.domain.model.settings.ThemeState
import com.example.dsr_practice.domain.model.settings.Units
import com.example.dsr_practice.domain.model.settings.Units.Imperial
import com.example.dsr_practice.domain.model.settings.Units.Metric
import com.example.dsr_practice.ui.composables.VerticalRadioButtonGroup
import com.example.dsr_practice.ui.navigation.graphs.BottomNavGraph
import com.ramcosta.composedestinations.annotation.Destination

@SuppressLint("ResourceType")
@Composable
@BottomNavGraph()
@Destination
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel(),
    navigateUp: () -> Unit,
) {
    val unitsOptions = listOf(Metric, Imperial)
    val selectedUnits by viewModel.currentUnits.collectAsState()

    val themeOptions = listOf(ThemeState.Light, ThemeState.Dark, ThemeState.System)
    val selectedTheme by viewModel.useDarkTheme.collectAsState()
    val locales = LocalConfiguration.current.locales
    val currentLanguage = locales[0].toLanguageTag()

    SettingsScreenContent(
        unitsOptions = unitsOptions,
        unitsSelectedOptions = selectedUnits,
        unitsOnOptionSelected = { units ->
            viewModel.setUnits(units)
        },
        themeOptions = themeOptions,
        selectedTheme = selectedTheme,
        onThemeSelected = { themeSettings ->
            viewModel.setThemeSettings(themeSettings)
        },
        currentLanguageTag = currentLanguage,
    )
    BackHandler {
        navigateUp()
    }
}

@Composable
fun SettingsScreenContent(
    unitsOptions: List<Units>,
    unitsSelectedOptions: Units,
    unitsOnOptionSelected: (Units) -> Unit,
    themeOptions: List<ThemeState>,
    selectedTheme: ThemeState,
    onThemeSelected: (ThemeState) -> Unit,
    currentLanguageTag: String,
) {
    Scaffold(topBar = { SettingsScreenTopAppBar() }) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            UnitsSettings(
                modifier = Modifier.fillMaxWidth(),
                unitsOptions = unitsOptions,
                unitsSelectedOptions = unitsSelectedOptions,
                unitsOnOptionSelected = unitsOnOptionSelected
            )
            ThemeSettings(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                themeOptions = themeOptions,
                selectedTheme = selectedTheme,
                onThemeSelected = onThemeSelected
            )
            LocaleSettings(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                locales = Locales.values().toList(),
                currentLanguageTag = currentLanguageTag,
            )
        }
    }
}

@Composable
fun LocaleSettings(
    modifier: Modifier = Modifier,
    locales: List<Locales>,
    currentLanguageTag: String
) {
    Column(
        modifier = modifier,
    ) {
        Text(
            text = stringResource(R.string.language) + ":",
            style = MaterialTheme.typography.titleLarge
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            locales.forEach { localeSettings ->
                Image(
                    painter = painterResource(id = localeSettings.flagId),
                    contentDescription = null,
                    modifier = Modifier
                        .width(70.dp)
                        .border(
                            2.dp,
                            if (currentLanguageTag == localeSettings.tag) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
                        )
                        .clickable {
                            AppCompatDelegate.setApplicationLocales(
                                LocaleListCompat.forLanguageTags(
                                    localeSettings.tag
                                )
                            )
                        })
            }
        }
    }

}

@Composable
fun ThemeSettings(
    modifier: Modifier = Modifier,
    themeOptions: List<ThemeState>,
    selectedTheme: ThemeState,
    onThemeSelected: (ThemeState) -> Unit,
) {
    Column(modifier) {
        Text(
            text = stringResource(R.string.theme) + ":", style = MaterialTheme.typography.titleLarge
        )
        Spacer(modifier = Modifier.height(8.dp))
        IconGroup(
            modifier = Modifier.fillMaxWidth(),
            themeOptions = themeOptions,
            selectedTheme = selectedTheme,
            onThemeSelected = onThemeSelected
        )
    }
}

@Composable
fun IconGroup(
    modifier: Modifier = Modifier,
    themeOptions: List<ThemeState>,
    selectedTheme: ThemeState,
    onThemeSelected: (ThemeState) -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround,
    ) {
        themeOptions.forEach { theme ->
            Image(
                painter = painterResource(id = theme.backgroundId),
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .border(
                        if (selectedTheme == theme) 2.dp else 1.dp,
                        if (selectedTheme == theme) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary,
                        CircleShape
                    )
                    .selectable(
                        selected = (selectedTheme == theme),
                        onClick = { onThemeSelected(theme) }),
                contentDescription = null
            )
        }

    }
}

@Composable
fun UnitsSettings(
    unitsOptions: List<Units>,
    unitsSelectedOptions: Units,
    unitsOnOptionSelected: (Units) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier) {
        Text(
            text = stringResource(R.string.units) + ":", style = MaterialTheme.typography.titleLarge
        )
        VerticalRadioButtonGroup(
            options = unitsOptions,
            optionsTitle = unitsOptions.map { stringResource(id = it.title) },
            selectedOption = unitsSelectedOptions,
            onOptionSelected = unitsOnOptionSelected
        )
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