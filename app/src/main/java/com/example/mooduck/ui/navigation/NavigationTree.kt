package com.example.mooduck.ui.navigation


sealed class NavigationTree( val route: String) {
    object Auth : NavigationTree("auth")
    object Main : NavigationTree("main")
}

sealed class MainNavigationTree( val route: String) {
    object Home : NavigationTree("home")
    object Search : NavigationTree("search")
    object Account : NavigationTree("account")

    object DetailBook : NavigationTree("detail-book")
}