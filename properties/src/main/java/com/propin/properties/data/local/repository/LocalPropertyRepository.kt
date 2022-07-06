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

import com.propin.core.Resource
import com.propin.properties.data.local.repository.mapper.toProperty
import com.propin.properties.domain.model.Property
import com.propin.properties.domain.repository.PropertyRepository

class LocalPropertyRepository(private val datasource: LocalPropertyDatasource) : PropertyRepository {
    override suspend fun getAllProperties(): Resource<List<Property>> = try {
        Resource.Success(datasource.getAllProperties().map { propertyDto ->
            propertyDto.toProperty()
        })
    } catch (ex: Exception) {
        Resource.Error(ex)
    }

    override suspend fun getProperty(id: Int): Resource<Property> = try {
        val property = datasource.getProperty(id).toProperty()
        Resource.Success(property)
    } catch (ex: Exception) {
        Resource.Error(ex)
    }
}