package com.example.dsr_practice.ui.main_activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.dsr_practice.ui.NavGraphs
import com.example.dsr_practice.ui.theme.DsrPracticeTheme
import com.ramcosta.composedestinations.DestinationsNavHost

@Suppress("INLINE_FROM_HIGHER_PLATFORM")
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DsrPracticeTheme {
               DestinationsNavHost(navGraph = NavGraphs.root)
            }
        }
    }
}