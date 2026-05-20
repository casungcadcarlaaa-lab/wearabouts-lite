package com.wearabouts.lite.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.wearabouts.lite.ui.screens.splash.SplashScreen
import com.wearabouts.lite.ui.screens.auth.LoginScreen
import com.wearabouts.lite.ui.screens.auth.SignUpScreen
import com.wearabouts.lite.ui.screens.auth.ForgotPasswordScreen
import com.wearabouts.lite.ui.screens.home.HomeDashboard
import com.wearabouts.lite.ui.screens.additem.AddItemScreen
import com.wearabouts.lite.ui.screens.itemdetail.ItemDetailScreen
import com.wearabouts.lite.ui.screens.edititem.EditItemScreen
import com.wearabouts.lite.ui.screens.search.SearchScreen
import com.wearabouts.lite.ui.screens.history.HistoryScreen
import com.wearabouts.lite.ui.screens.profile.ProfileScreen
import com.wearabouts.lite.ui.screens.settings.AppSettingsScreen
import com.wearabouts.lite.viewmodel.ClothingViewModel

@Composable
fun NavGraph(
    navController: NavHostController = rememberNavController(),
    viewModel: ClothingViewModel
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        composable(Screen.Splash.route) {
            SplashScreen(onNavigateToLogin = {
                navController.navigate(Screen.Login.route) {
                    popUpTo(Screen.Splash.route) { inclusive = true }
                }
            })
        }
        composable(Screen.Login.route) {
            LoginScreen(
                viewModel = viewModel,
                onLoginSuccess = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
                onNavigateToSignUp = { navController.navigate(Screen.SignUp.route) },
                onNavigateToForgotPassword = { navController.navigate(Screen.ForgotPassword.route) }
            )
        }
        composable(Screen.SignUp.route) {
            SignUpScreen(
                viewModel = viewModel,
                onSignUpSuccess = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.SignUp.route) { inclusive = true }
                    }
                },
                onNavigateToLogin = { navController.popBackStack() }
            )
        }
        composable(Screen.ForgotPassword.route) {
            ForgotPasswordScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(Screen.Home.route) {
            HomeDashboard(viewModel = viewModel, navController = navController)
        }
        composable(Screen.AddItem.route) {
            AddItemScreen(viewModel = viewModel, onNavigateBack = { navController.popBackStack() })
        }
        composable(
            route = Screen.ItemDetail.route,
            arguments = listOf(navArgument("itemId") { type = NavType.StringType })
        ) { backStackEntry ->
            val itemId = backStackEntry.arguments?.getString("itemId") ?: return@composable
            ItemDetailScreen(
                itemId = itemId,
                viewModel = viewModel,
                onNavigateBack = { navController.popBackStack() },
                onNavigateToEdit = { id -> navController.navigate(Screen.EditItem.createRoute(id)) }
            )
        }
        composable(
            route = Screen.EditItem.route,
            arguments = listOf(navArgument("itemId") { type = NavType.StringType })
        ) { backStackEntry ->
            val itemId = backStackEntry.arguments?.getString("itemId") ?: return@composable
            EditItemScreen(
                itemId = itemId,
                viewModel = viewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }
        composable(Screen.Search.route) {
            SearchScreen(viewModel = viewModel, navController = navController)
        }
        composable(Screen.History.route) {
            HistoryScreen(viewModel = viewModel, navController = navController)
        }
        composable(Screen.Profile.route) {
            ProfileScreen(
                viewModel = viewModel,
                navController = navController,
                onNavigateBack = { navController.popBackStack() },
                onSignOut = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Home.route) { inclusive = true }
                    }
                },
                onNavigateToSettings = { navController.navigate(Screen.AppSettings.route) }
            )
        }
        composable(Screen.AppSettings.route) {
            AppSettingsScreen(
                viewModel = viewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}
