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

package com.propin.properties.data.local.repository

import com.propin.core.PropError
import com.propin.core.Resource
import com.propin.core.toPropError
import com.propin.properties.data.local.repository.mapper.toProperty
import com.propin.properties.domain.model.Property
import com.propin.properties.domain.repository.PropertyDatasource
import com.propin.properties.domain.repository.PropertyRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import timber.log.Timber

class LocalPropertyRepository(
    private val datasource: PropertyDatasource,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : PropertyRepository {

    override fun getAllProperties(): Flow<Resource<List<Property>>> = datasource.getAllProperties()
        .catch { ex ->
            Timber.e(ex)
            Resource.Error<Resource<List<Property>>>(ex.toPropError())
        }
        .map { entities ->
            Timber.d("Entities: $entities")
            val mappedList = entities.map { entity -> entity.toProperty() }
            Resource.Success(mappedList)
        }

    override suspend fun getProperty(id: Long): Resource<Property> = withContext(dispatcher) {
        try {
            val property = datasource.getProperty(id)
            if (property == null) {
                Resource.Error(PropError.NotFoundError())
            } else {
                Resource.Success(property.toProperty())
            }
        } catch (ex: Exception) {
            Resource.Error(ex.toPropError())
        }
    }

    override suspend fun persistProperty(property: Property): Resource<Property> = try {
        val id = datasource.persistProperty(property)
        if (property.id == Property.INVALID_ID) {
            Resource.Success(
                property.copy(id = id)
            )
        } else {
            Resource.Success(property)
        }
    } catch (exception: Exception) {
        Resource.Error(exception.toPropError())
    }
}