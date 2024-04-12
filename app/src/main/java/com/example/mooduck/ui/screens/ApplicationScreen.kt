package com.example.mooduck.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mooduck.ui.navigation.NavigationTree
import com.example.mooduck.ui.screens.auth.AuthScreen
import com.example.mooduck.ui.screens.auth.AuthViewModel
import com.example.mooduck.ui.screens.splash.SplashScreen

@Composable
fun ApplicationScreen(){
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = NavigationTree.Splash.route){

//        composable(NavigationTree.Splash.route){ SplashScreen(navController) }
//        composable(NavigationTree.Login.route) {
//            val loginViewModel = hiltViewModel<AuthViewModel>()
//            AuthScreen(
//                modifier = Modifier
//                    .padding(horizontal = 44.dp)
//                    .fillMaxSize(),
//                loginViewModel = loginViewModel,
//                navController = navController,
//            )
//        }


    }
}