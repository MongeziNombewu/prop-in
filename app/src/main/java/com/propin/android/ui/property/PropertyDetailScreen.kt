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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.propin.android.R
import com.propin.android.ui.theme.PropInTheme
import org.koin.androidx.compose.getViewModel

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun PropertyDetailScreen(
    navController: NavController = rememberNavController(),
    propertyDetailViewModel: PropertyDetailViewModel = getViewModel()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val keyboardController = LocalSoftwareKeyboardController.current
        val uiState: PropertyDetailUiState by propertyDetailViewModel.uiState.collectAsState()
        var addressLineOne by rememberSaveable { mutableStateOf("") }
        var addressLineTwo by rememberSaveable { mutableStateOf("") }
        var suburb by rememberSaveable { mutableStateOf("") }
        var city by rememberSaveable { mutableStateOf("") }
        var country by rememberSaveable { mutableStateOf("") }
        var postalCode by rememberSaveable { mutableStateOf("") }

        when (uiState) {
            is PropertyDetailUiState.Error -> TODO()
            is PropertyDetailUiState.ExistingProperty -> TODO()
            PropertyDetailUiState.Loading -> TODO()
            PropertyDetailUiState.NewProperty -> TODO()
            is PropertyDetailUiState.PropertySaved -> TODO()
        }

        TopAppBar(title = { Text(stringResource(id = R.string.add_property)) },
            navigationIcon = {
                IconButton(onClick = { navController.navigateUp() }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(id = R.string.content_description_navigate_back)
                    )
                }
            })
        TextField(
            label = { Text(stringResource(id = R.string.address_line_one)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.padding_medium)),
            value = addressLineOne,
            onValueChange = { addressLineOne = it },
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
        )
        TextField(
            label = { Text(stringResource(id = R.string.address_line_two)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.padding_medium)),
            value = addressLineTwo,
            onValueChange = { addressLineTwo = it },
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
        )
        TextField(
            label = { Text(stringResource(id = R.string.suburb)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.padding_medium)),
            value = suburb,
            onValueChange = { suburb = it },
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
        )
        TextField(
            label = { Text(stringResource(id = R.string.city)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.padding_medium)),
            value = city,
            onValueChange = { city = it },
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
        )
        TextField(
            label = { Text(stringResource(id = R.string.country)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.padding_medium)),
            value = country,
            onValueChange = { country = it },
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
        )
        TextField(
            label = { Text(stringResource(id = R.string.postal_code)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.padding_medium)),
            value = postalCode,
            onValueChange = { postalCode = it },
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onDone = {
                    keyboardController?.hide()
                    //onSave.invoke()
                })
        )
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.padding_medium)),
            onClick = {/*NO-OP*/ }
        ) {
            Text(text = stringResource(id = R.string.save))
        }
    }
}

@Composable
@Preview(showBackground = true)
fun DefaultPreview() {
    PropInTheme {
        PropertyDetailScreen()
    }
}