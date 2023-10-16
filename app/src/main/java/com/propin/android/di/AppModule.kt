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

package com.propin.android.di

import com.propin.android.ui.home.HomeViewModel
import com.propin.android.ui.login.LoginViewModel
import com.propin.android.ui.property.PropertyDetailViewModel
import com.propin.properties.di.propertiesModule
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    includes(
        propertiesModule
    )
    viewModelOf(::LoginViewModel)

    viewModel { HomeViewModel(get()) }


    viewModel { params ->
        PropertyDetailViewModel(
            persistPropertyUseCase = get(),
            getPropertyUseCase = get(),
            id = params.get()
        )
    }
}