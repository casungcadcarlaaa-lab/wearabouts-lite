package com.wearabouts.lite.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Login : Screen("login")
    object SignUp : Screen("signup")
    object ForgotPassword : Screen("forgot_password")
    object Home : Screen("home")
    object AddItem : Screen("add_item")
    object ItemDetail : Screen("item_detail/{itemId}") {
        fun createRoute(id: String) = "item_detail/$id"
    }
    object EditItem : Screen("edit_item/{itemId}") {
        fun createRoute(id: String) = "edit_item/$id"
    }
    object Search : Screen("search")
    object History : Screen("history")
    object Profile : Screen("profile")
    object AppSettings : Screen("app_settings")
}
