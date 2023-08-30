package com.example.dsr_practice.ui.location_screen

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.dsr_practice.R
import com.example.dsr_practice.domain.model.Weather
import com.example.dsr_practice.ui.location_screen.view_pager.LocationsViewPager
import com.example.dsr_practice.ui.location_screen.view_pager.LocationsViewPagerTab
import com.example.dsr_practice.ui.navigation.graphs.BottomNavGraph
import com.example.dsr_practice.utils.SnackbarController
import com.ramcosta.composedestinations.annotation.Destination
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
@BottomNavGraph(start = true)
@Destination
fun LocationScreen(
    navigateToMap: () -> Unit,
    navigateToDetails: (Weather) -> Unit,
    snackbarController: SnackbarController,
    viewModel: LocationsScreenViewModel = hiltViewModel()
) {
    val tabs = listOf(
        LocationsViewPagerTab.All(navigateToDetails),
        LocationsViewPagerTab.Favorite(navigateToDetails)
    )
    val pagerState = rememberPagerState { tabs.size }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    LocationScreenContent(
        tabs = tabs,
        pagerState = pagerState,
        tabOnClick = { tabIndex ->
            scope.launch {
                pagerState.animateScrollToPage(tabIndex)
            }
        },
        navigateToMap = {
            if (viewModel.checkInternetConnection()) navigateToMap()
            else snackbarController.showSnackbar(
                context.getString(R.string.map_without_internet_connection),
                withDismissAction = true
            )
        },
    )
    BackHandler {
        (context as? Activity)?.finish()
    }

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LocationScreenContent(
    tabs: List<LocationsViewPagerTab>,
    pagerState: PagerState,
    tabOnClick: (Int) -> Unit,
    navigateToMap: () -> Unit,
) {
    Scaffold(
        topBar = {
            LocationsScreenTopAppBar()
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { navigateToMap() }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
            }
        },
        floatingActionButtonPosition = FabPosition.End,
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            LocationsViewPager(
                modifier = Modifier.fillMaxSize(),
                tabs = tabs,
                tabOnClick = tabOnClick,
                pagerState = pagerState,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationsScreenTopAppBar() {
    TopAppBar(
        title = {
            Text(
                text = stringResource(R.string.locations),
                color = MaterialTheme.colorScheme.onPrimary
            )
        }, colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary
        )
    )
}