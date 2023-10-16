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

package com.propin.properties.di

import com.propin.properties.data.local.LeaseEntity
import com.propin.properties.data.local.PropertiesDatabase
import com.propin.properties.data.local.repository.LocalPropertyDatasource
import com.propin.properties.data.local.repository.LocalPropertyRepository
import com.propin.properties.domain.repository.PropertyDatasource
import com.propin.properties.domain.repository.PropertyRepository
import com.propin.properties.domain.use_case.GetPropertiesUseCase
import com.propin.properties.domain.use_case.GetPropertyUseCase
import com.propin.properties.domain.use_case.PersistPropertyUseCase
import com.squareup.sqldelight.EnumColumnAdapter
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import kotlinx.coroutines.Dispatchers
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module

private const val NAMED_DATABASE = "properties.db"

val propertiesModule = module {
    single {
        PropertiesDatabase(get(), get())
    }

    single<SqlDriver> {
        AndroidSqliteDriver(PropertiesDatabase.Schema, get(), NAMED_DATABASE)
    }

    single { LeaseEntity.Adapter(paymentFrequencyAdapter = EnumColumnAdapter()) }

    factoryOf(::LocalPropertyDatasource) bind PropertyDatasource::class

    factoryOf(::LocalPropertyRepository) bind PropertyRepository::class

    factoryOf(::GetPropertiesUseCase)

    factoryOf(::PersistPropertyUseCase)

    factoryOf(::GetPropertyUseCase)

    factory { Dispatchers.IO }
}