package com.example.dsr_practice.ui.location_screen.view_pager

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LocationsViewPager(
    pagerState: PagerState,
    tabs: List<LocationsViewPagerTab>,
    tabOnClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        IndicatorLocationsViewPager(
            tabs = tabs,
            selectedTabIndex = pagerState.currentPage,
            tabOnClick = tabOnClick,
            modifier = Modifier
                .padding(top = 4.dp)
                .padding(horizontal = 32.dp)
                .fillMaxWidth()
        )
        ContentLocationsViewPager(
            pagerState = pagerState,
            tabs = tabs,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
private fun IndicatorLocationsViewPager(
    tabs: List<LocationsViewPagerTab>,
    selectedTabIndex: Int,
    tabOnClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    TabRow(
        selectedTabIndex = selectedTabIndex,
        modifier = modifier,
    ) {
        tabs.forEachIndexed { index, tab ->
            Tab(
                selected = index == selectedTabIndex,
                onClick = { tabOnClick(index) },
            ) {
                Text(
                    text = tab.title,
                    style = MaterialTheme.typography.bodyLarge.copy(fontSize = 18.sp),
                    modifier = Modifier.padding(vertical = 4.dp)
                )

            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ContentLocationsViewPager(
    pagerState: PagerState,
    tabs: List<LocationsViewPagerTab>,
    modifier: Modifier = Modifier
) {
    HorizontalPager(
        state = pagerState,
        modifier = modifier,
    ) { tabIndex ->
        tabs[tabIndex].content()
    }
}