package com.example.dsr_practice.ui.navigation.graphs

import com.ramcosta.composedestinations.annotation.NavGraph
import com.ramcosta.composedestinations.annotation.RootNavGraph

@RootNavGraph
@NavGraph(route = "bottom_navigation")
annotation class BottomNavGraph(val start: Boolean = false)