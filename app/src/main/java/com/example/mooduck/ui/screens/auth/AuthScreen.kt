package com.example.mooduck.ui.screens.auth

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.example.mooduck.R
import com.example.mooduck.ui.navigation.NavigationTree
import com.mooduck.domain.models.Result
import kotlinx.coroutines.delay

@Composable
fun AuthScreen(
    loginViewModel: LoginViewModel,
    navController: NavController,
) {

    val viewState = loginViewModel.viewState.observeAsState()

    val context = LocalContext.current
    val welcomeText = stringResource(id = R.string.welcome)
    val errorText = stringResource(id = R.string.error_auth)

    LaunchedEffect(loginViewModel, context) {
        loginViewModel.authResults.collect{result ->
            when(result){
                is Result.Error -> {
                    Toast.makeText(
                        context,
                        "$errorText",
                        Toast.LENGTH_LONG
                    ).show()
                }
                is Result.Success -> {

                    Toast.makeText(
                        context,
                        "$welcomeText ${result.data.userName}",
                        Toast.LENGTH_LONG
                    ).show()
                    delay(3000L)
                    navController.navigate(NavigationTree.Main.route)
                }
            }
        }
    }

}