package com.example.dsr_practice.ui.main_activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.dsr_practice.domain.model.settings.ThemeState
import com.example.dsr_practice.ui.navigation.graphs.RootNavHost
import com.example.dsr_practice.ui.theme.DsrPracticeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val viewModel: MainActivityViewModel = hiltViewModel()
            val useDarkTheme by viewModel.useDarkTheme.collectAsState()
            DsrPracticeTheme(
                useDarkTheme = when (useDarkTheme) {
                    ThemeState.Dark -> true
                    ThemeState.Light -> false
                    ThemeState.System -> isSystemInDarkTheme()
                }
            ) {
                RootNavHost()
            }
        }
    }
}