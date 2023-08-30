package com.example.dsr_practice.ui.main_activity

import android.os.Build
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.dsr_practice.domain.model.settings.ThemeState
import com.example.dsr_practice.utils.SnackbarController
import com.example.dsr_practice.ui.navigation.graphs.RootNavHost
import com.example.dsr_practice.ui.theme.DsrPracticeTheme
import com.ramcosta.composedestinations.navigation.dependency
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val viewModel: MainActivityViewModel = hiltViewModel()
            val useDarkTheme by viewModel.useDarkTheme.collectAsState()
            val scope = rememberCoroutineScope()
            val snackbarHostState = remember { SnackbarHostState() }
            val snackbarController = remember {
                SnackbarController(
                    scope = scope,
                    snackbarHostState = snackbarHostState
                )
            }

            DsrPracticeTheme(
                useDarkTheme = when (useDarkTheme) {
                    ThemeState.Dark -> true
                    ThemeState.Light -> false
                    ThemeState.System -> isSystemInDarkTheme()
                }
            ) {
                Scaffold(snackbarHost = { SnackbarHost(snackbarHostState) }) { paddingValues ->
                    RootNavHost(modifier = Modifier.padding(paddingValues),
                        dependenciesContainerBuilder = {
                            dependency(snackbarController)
                        })
                }
            }
        }
    }
}