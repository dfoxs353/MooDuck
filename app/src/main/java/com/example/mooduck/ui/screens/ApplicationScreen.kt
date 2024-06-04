package com.example.mooduck.ui.screens

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mooduck.ui.navigation.NavigationTree
import com.example.mooduck.ui.screens.auth.AuthScreen
import com.example.mooduck.ui.screens.auth.AuthViewModel
import com.example.mooduck.ui.screens.main.MainScreen

@Composable
fun ApplicationScreen(){
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = NavigationTree.Auth.route){

        composable(NavigationTree.Auth.route) {
            val authViewModel = hiltViewModel<AuthViewModel>()
            AuthScreen(
                authViewModel = authViewModel,
                navController = navController,
            )
        }

        composable(NavigationTree.Main.route){
            MainScreen(
                navController = navController,
                )
        }

    }
}