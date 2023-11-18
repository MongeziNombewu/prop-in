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

import com.propin.properties.data.local.PropertiesDatabase
import com.propin.properties.data.local.PropertyEntity
import com.propin.properties.domain.model.Property
import com.propin.properties.domain.repository.PropertyDatasource
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import kotlinx.coroutines.flow.Flow

class LocalPropertyDatasource(private val database: PropertiesDatabase) : PropertyDatasource {
    override suspend fun getAllProperties(): Flow<List<PropertyEntity>> {
        return database.propertyEntityQueries.getAllProperties().asFlow().mapToList()
    }

    override suspend fun getProperty(id: Long): PropertyEntity? {
        return database.propertyEntityQueries.getPropertyById(id).executeAsOneOrNull()
    }

    override suspend fun persistProperty(property: Property): Long {
        return database.propertyEntityQueries.transactionWithResult {
            var id: Long? = if (property.id == Property.INVALID_ID) null else property.id
            database.propertyEntityQueries.updateProperty(
                id = id,
                description = property.description,
                addressLineOne = property.address.lineOne,
                addressLineTwo = property.address.lineTwo,
                suburb = property.address.suburb,
                city = property.address.city,
                country = property.address.country,
                code = property.address.code
            )

            // Get last inserted ID, if not updating
            if (id == null) {
                id = database.propertyEntityQueries.getLastInsertRowId().executeAsOne()
            }

            id
        }
    }
}