/*
 * Copyright (C) 2023. Mongezi Nombewu
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

package com.propin.android.ui.property

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.propin.android.R
import com.propin.android.ui.composables.LoadingIndicator
import com.propin.android.ui.composables.PropAppBar
import com.propin.android.ui.theme.PropInTheme
import com.propin.properties.domain.model.Address
import com.propin.properties.domain.model.Property
import kotlinx.coroutines.launch
import timber.log.Timber


@Composable
fun PropertyDetailScreen(
    modifier: Modifier = Modifier,
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    propertyDetailViewModel: PropertyDetailViewModel,
    onBackPress: () -> Unit = {},
    onSave: (Property) -> Unit
) {
    val uiState: PropertyDetailUiState by propertyDetailViewModel.uiState.collectAsState()
    val scope = rememberCoroutineScope()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = { PropAppBar(titleRes = R.string.add_property, onBackPress = onBackPress) },
    ) { paddingValues ->
        when (uiState) {
            is PropertyDetailUiState.Loading -> {
                LoadingIndicator(modifier.padding(paddingValues = paddingValues))
            }

            is PropertyDetailUiState.PropertySaved -> {
                val message = (uiState as PropertyDetailUiState.PropertySaved).savedUiText.asString()
                LaunchedEffect(key1 = uiState) {
                    scope.launch {
                        snackbarHostState.showSnackbar(message)
                    }
                }
                PropertyContent(
                    modifier = modifier.padding(paddingValues = paddingValues),
                    property = uiState.property,
                    onSave = onSave
                )
            }

            else -> {
                PropertyContent(
                    modifier = modifier.padding(paddingValues = paddingValues),
                    property = uiState.property,
                    onSave = onSave
                )
            }
        }
    }
}


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun PropertyContent(
    modifier: Modifier = Modifier,
    property: Property? = null,
    onSave: (Property) -> Unit = {}
) {
    Column(
        modifier = modifier
            .padding(top = 8.dp, bottom = 16.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        val keyboardController = LocalSoftwareKeyboardController.current

        var addressLineOne by rememberSaveable { mutableStateOf(property?.address?.lineOne ?: "") }
        var addressLineTwo by rememberSaveable { mutableStateOf(property?.address?.lineTwo ?: "") }
        var suburb by rememberSaveable { mutableStateOf(property?.address?.suburb ?: "") }
        var city by rememberSaveable { mutableStateOf(property?.address?.city ?: "") }
        var country by rememberSaveable { mutableStateOf(property?.address?.country ?: "") }
        var postalCode by rememberSaveable { mutableStateOf(property?.address?.code ?: "") }
        var description by rememberSaveable { mutableStateOf(property?.description ?: "") }
        var rate by rememberSaveable { mutableStateOf(property?.defaultRate?.toString() ?: "") }

        Timber.d("PropertyContent: $property")
        Timber.d("PropertyContent description 1: ${property?.description} 2: $description")
        Timber.d("PropertyContent addressLineOne 1: ${property?.id} 2: $addressLineOne")
        Timber.d("PropertyContent rate: ${property?.defaultRate} 2: $rate")

        TextField(
            label = { Text(stringResource(id = R.string.property_description)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 16.dp),
            value = description,
            onValueChange = { description = it },
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
        )
        TextField(
            label = { Text(stringResource(id = R.string.address_line_one)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 16.dp),
            value = addressLineOne,
            onValueChange = { addressLineOne = it },
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
        )
        TextField(
            label = { Text(stringResource(id = R.string.address_line_two)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 16.dp),
            value = addressLineTwo,
            onValueChange = { addressLineTwo = it },
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
        )
        TextField(
            label = { Text(stringResource(id = R.string.suburb)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 16.dp),
            value = suburb,
            onValueChange = { suburb = it },
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
        )
        TextField(
            label = { Text(stringResource(id = R.string.city)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 16.dp),
            value = city,
            onValueChange = { city = it },
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
        )
        TextField(
            label = { Text(stringResource(id = R.string.country)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 16.dp),
            value = country,
            onValueChange = { country = it },
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
        )
        TextField(
            label = { Text(stringResource(id = R.string.postal_code)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 16.dp),
            value = postalCode,
            onValueChange = { postalCode = it },
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
        )
        TextField(
            label = { Text(stringResource(id = R.string.monthly_rate)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 16.dp),
            value = rate,
            onValueChange = { rate = it },
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done, keyboardType = KeyboardType.Number),
            keyboardActions = KeyboardActions(
                onDone = {
                    keyboardController?.hide()
                })
        )
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            onClick = {
                val propertyToSave = Property(
                    description = description,
                    address = Address(
                        lineOne = addressLineOne,
                        lineTwo = addressLineTwo,
                        suburb = suburb,
                        city = city,
                        country = country,
                        code = postalCode
                    ),
                    defaultRate = if (rate.isNotBlank()) rate.toLong() else 0L
                )

                onSave.invoke(propertyToSave)
            }
        ) {
            Text(text = stringResource(id = R.string.save))
        }
    }
}

@Composable
@Preview(showBackground = true)
fun DefaultPreview() {
    PropInTheme {
        PropertyContent()
    }
}