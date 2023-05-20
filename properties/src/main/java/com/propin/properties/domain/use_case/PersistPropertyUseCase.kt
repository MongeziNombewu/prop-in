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

package com.propin.properties.domain.use_case

import com.propin.core.Resource
import com.propin.properties.domain.model.Property
import com.propin.properties.domain.repository.PropertyRepository

class PersistPropertyUseCase(
    private val localPropertyRepository: PropertyRepository
) {
    suspend operator fun invoke(property: Property): Resource<Property> {
        return localPropertyRepository.persistProperty(property)
    }
}