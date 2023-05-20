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

package com.propin.android

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.propin.android.ui.Screens
import com.propin.android.ui.home.HomeScreen
import com.propin.android.ui.login.LoginScreen
import com.propin.android.ui.property.PropertyDetailScreen
import org.koin.androidx.compose.getViewModel

@Composable
fun MainApp(navController: NavHostController = rememberNavController()) {
// TODO: handle high-level app scenarios i.e internet connectivity

    NavHost(navController = navController, startDestination = Screens.HOME.name) {
        composable(Screens.LOGIN.name) {
            LoginScreen(
                viewModel = getViewModel(),
                onNavigateToHome = { navController.navigate(Screens.HOME.name) }
            )
        }
        composable(Screens.HOME.name) { HomeScreen(navController = navController) }
        composable(Screens.PROPERTY_DETAIL.name) {
            PropertyDetailScreen(
                navController = navController,
                propertyDetailViewModel = getViewModel()
            )
        }
    }
}