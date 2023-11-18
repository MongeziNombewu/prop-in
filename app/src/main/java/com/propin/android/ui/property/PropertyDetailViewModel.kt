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

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.propin.properties.domain.model.Address
import com.propin.properties.domain.model.PaymentFrequency
import com.propin.properties.domain.model.Property
import com.propin.properties.domain.use_case.GetPropertyUseCase
import com.propin.properties.domain.use_case.PersistPropertyUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PropertyDetailViewModel(
    private val persistPropertyUseCase: PersistPropertyUseCase,
    private val getPropertyUseCase: GetPropertyUseCase,
    private val id: Long = Property.INVALID_ID
) : ViewModel() {
    private val _uiState: MutableStateFlow<PropertyDetailUiState> =
        MutableStateFlow(PropertyDetailUiState.Loading)
    val uiState: StateFlow<PropertyDetailUiState> = _uiState

    private var property: Property? = null

    init {
        viewModelScope.launch {
            if (id == Property.INVALID_ID) {
                _uiState.emit(PropertyDetailUiState.NewProperty)
            } else {
                val resource = getPropertyUseCase(id)
                // TODO: check for no match
                if (resource.isSuccessful()) {
                    resource.data?.let {
                        property = it
                        _uiState.update { _ -> PropertyDetailUiState.ExistingProperty(it) }
                    }
                } else {
                    resource.uiText?.let { errorUiText ->
                        _uiState.update { _ -> PropertyDetailUiState.Error(errorUiText, property) }
                    }
                }
            }
        }
    }

    fun saveProperty(
        description: String,
        address: Address,
        rate: Int,
        paymentFrequency: PaymentFrequency,
    ) {
        viewModelScope.launch {
            val updatedProperty = property?.copy(
                description = description,
                address = address,
                defaultRate = rate,
                defaultPaymentFrequency = paymentFrequency
            ) ?: Property(
                description = description,
                address = address,
                defaultRate = rate,
                defaultPaymentFrequency = paymentFrequency
            )

            val resource = persistPropertyUseCase(updatedProperty)
            if (resource.isSuccessful()) {
                resource.data?.let {
                    property = it
                    _uiState.update { _ -> PropertyDetailUiState.ExistingProperty(it) }
                }
            } else {
                resource.uiText?.let { errorUiText ->
                    _uiState.update { _ -> PropertyDetailUiState.Error(errorUiText, property) }
                }
            }
        }
    }
}