package com.example.dsr_practice.ui.triggers_screen

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Divider
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.dsr_practice.R
import com.example.dsr_practice.domain.model.Trigger
import com.example.dsr_practice.ui.location_screen.view_pager.EmptyContent
import com.example.dsr_practice.ui.navigation.graphs.BottomNavGraph
import com.ramcosta.composedestinations.annotation.Destination

@BottomNavGraph
@Destination
@Composable
fun TriggersScreen(
    navigateToDetails: (Trigger) -> Unit,
    navigateToEdit: () -> Unit,
    navigateUp: () -> Unit,
    viewModel: TriggersViewModel = hiltViewModel()
) {
    val triggers by viewModel.triggers.collectAsState()
    val context = LocalContext.current
    TriggersScreenContent(
        list = triggers,
        itemOnClick = { trigger ->
            navigateToDetails(trigger)
        },
        floatingActionBtnOnClick = {
            if (viewModel.checkNotificationsEnabled(context)) navigateToEdit()
            else Toast.makeText(
                context,
                context.getString(R.string.turn_on_notifications),
                Toast.LENGTH_SHORT
            ).show()
        }
    )
    BackHandler {
        navigateUp()
    }
}

@Composable
fun TriggersScreenContent(
    list: List<Trigger>,
    itemOnClick: (Trigger) -> Unit,
    floatingActionBtnOnClick: () -> Unit,
) {
    Scaffold(
        topBar = { TriggersTopBar() },
        floatingActionButton = {
            FloatingActionButton(onClick = floatingActionBtnOnClick) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
            }
        },
        floatingActionButtonPosition = FabPosition.End,
    ) { paddingValues ->
        Crossfade(
            targetState = list.isEmpty(),
            label = "",
            modifier = Modifier.padding(paddingValues)
        ) {
            when (it) {
                true -> EmptyContent(
                    text = stringResource(R.string.you_don_t_have_any_triggers),
                    modifier = Modifier.fillMaxSize()
                )

                false -> TriggersList(
                    list = list,
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxSize(),
                    itemOnClick = itemOnClick,
                )
            }
        }
    }
}

@Composable
fun TriggersList(
    list: List<Trigger>,
    modifier: Modifier = Modifier,
    itemOnClick: (Trigger) -> Unit,
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp),
        contentPadding = PaddingValues(top = 8.dp, bottom = 128.dp)
    ) {
        items(list) { trigger ->
            TriggerListItem(
                name = trigger.name ?: "",
                onClick = { itemOnClick(trigger) },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun TriggerListItem(
    name: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier
        .clip(RoundedCornerShape(8.dp))
        .clickable { onClick() }) {
        Box(
            modifier = Modifier
                .height(48.dp)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = name,
                style = MaterialTheme.typography.bodyMedium.copy(fontSize = 18.sp)
            )
        }
        Divider(
            modifier = Modifier
                .padding(horizontal = 32.dp)
                .padding(top = 4.dp)
                .fillMaxWidth(),
            color = MaterialTheme.colorScheme.outlineVariant
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TriggersTopBar() {
    TopAppBar(
        title = {
            Text(
                text = stringResource(id = R.string.triggers),
                color = MaterialTheme.colorScheme.onPrimary
            )
        }, colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary
        )
    )
}
