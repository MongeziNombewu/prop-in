/*
 * Copyright (C) 2022. Mongezi Nombewu
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.propin.android.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.propin.android.R
import com.propin.android.ui.composables.PropAppBar
import com.propin.android.ui.composables.PropertyCard

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel,
    onNewPropertyClick: () -> Unit = {},
    onPropertyClick: (Long) -> Unit = {},
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = { PropAppBar(titleRes = R.string.app_name, backPressEnabled = false) },
        floatingActionButton = {
            FloatingActionButton(onClick = onNewPropertyClick) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = stringResource(id = R.string.content_description_add_property)
                )
            }
        }
    ) { padding ->
        val uiState: HomeUiState by homeViewModel.uiState.collectAsStateWithLifecycle()
        //rewrite to separate composables for each state
        when (uiState) {
            is HomeUiState.PropertiesAvailable -> {
                LazyColumn(modifier = Modifier.padding(padding)) {
                    items((uiState as HomeUiState.PropertiesAvailable).properties) { property ->
                        PropertyCard(
                            modifier = Modifier.padding(8.dp),
                            property = property
                        ) { onPropertyClick(property.id) }
                    }
                }
            }

            is HomeUiState.NoPropertiesAvailable -> {
                Column(
                    modifier = Modifier
                        .padding(dimensionResource(id = R.dimen.padding_standard))
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = (uiState as HomeUiState.NoPropertiesAvailable).uiText.asString(LocalContext.current),
                        textAlign = TextAlign.Center
                    )
                }
            }

            is HomeUiState.Error -> TODO()
            HomeUiState.Loading -> Column(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LinearProgressIndicator()
            }
        }
    }
}