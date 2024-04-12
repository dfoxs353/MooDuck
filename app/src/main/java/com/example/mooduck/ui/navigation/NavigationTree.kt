package com.example.mooduck.ui.navigation


sealed class NavigationTree( val route: String) {
    object Splash : NavigationTree("splash")
    object Auth : NavigationTree("auth")
    object Main : NavigationTree("main")
}