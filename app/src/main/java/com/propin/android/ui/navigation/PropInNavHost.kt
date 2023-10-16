package com.propin.android.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.propin.android.ui.home.HomeScreen
import com.propin.android.ui.login.LoginScreen
import com.propin.android.ui.property.PropertyDetailScreen
import com.propin.android.ui.property.PropertyDetailViewModel
import com.propin.properties.domain.model.Property
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun PropInNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    NavHost(navController = navController, startDestination = Home.route, modifier = modifier) {
        composable(Login.route) {
            LoginScreen(
                viewModel = koinViewModel(),
                onNavigateToHome = { navController.navigate(Home.route) }
            )
        }
        composable(Home.route) {
            HomeScreen(
                homeViewModel = koinViewModel(),
                onNewPropertyClick = { navController.navigateToPropertyDetail(Property.INVALID_ID) }
            )
        }
        composable(
            route = PropertyDetail.routeWithArgs,
            arguments = PropertyDetail.arguments
        ) { navBackStackEntry ->
            val propertyId = navBackStackEntry.arguments?.getLong(
                PropertyDetail.propertyIdArg,
                Property.INVALID_ID
            )
            val viewModel = koinViewModel<PropertyDetailViewModel> { parametersOf(propertyId) }
            PropertyDetailScreen(
                navController = navController,
                propertyDetailViewModel = viewModel
            )
        }
    }
}

private fun NavHostController.navigateToPropertyDetail(propertyId: Long) {
    this.navigate("${PropertyDetail.route}/${propertyId}")
}
