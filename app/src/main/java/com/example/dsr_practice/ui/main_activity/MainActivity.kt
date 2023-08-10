package com.example.dsr_practice.ui.main_activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.dsr_practice.ui.navigation.graphs.RootNavHost
import com.example.dsr_practice.ui.theme.DsrPracticeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DsrPracticeTheme {
                RootNavHost()
            }
        }
    }
}