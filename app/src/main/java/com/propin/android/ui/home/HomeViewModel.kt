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

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.propin.android.R
import com.propin.core.UIText
import com.propin.properties.domain.use_case.GetPropertiesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber

class HomeViewModel(private val getPropertiesUseCase: GetPropertiesUseCase) : ViewModel() {

    var uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
        private set

    init {
        viewModelScope.launch {
            getPropertiesUseCase()
                .catch { exception ->
                    Timber.e(exception)
                }
                .collect {
                    if (it.isSuccessful()) {
                        if (it.data.isNullOrEmpty()) {
                            uiState.update { _ -> HomeUiState.NoPropertiesAvailable(UIText.ResourceString(R.string.home_no_properties)) }
                        } else {
                            uiState.update { _ -> HomeUiState.PropertiesAvailable(it.data.orEmpty()) }
                        }
                    } else {
                        it.error?.uiText?.let { uiText -> uiState.update { HomeUiState.Error(uiText) } }
                    }
                }
        }
    }
}