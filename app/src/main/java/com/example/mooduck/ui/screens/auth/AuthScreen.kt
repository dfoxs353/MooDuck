package com.example.mooduck.ui.screens.auth

import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.example.mooduck.R
import com.example.mooduck.ui.navigation.NavigationTree
import com.example.mooduck.ui.screens.auth.models.AuthEvent
import com.example.mooduck.ui.screens.auth.models.AuthSubState
import com.example.mooduck.ui.screens.auth.views.SignInView
import com.example.mooduck.ui.screens.auth.views.SignUpView
import com.mooduck.domain.models.Result
import kotlinx.coroutines.delay

@Composable
fun AuthScreen(
    authViewModel: AuthViewModel,
    navController: NavController,
) {

    val viewState = authViewModel.viewState.observeAsState()

    val context = LocalContext.current
    val welcomeText = stringResource(id = R.string.welcome)
    val signInErrorText = stringResource(id = R.string.error_sign_in)
    val signUpErrorText = stringResource(id = R.string.error_sign_up)

    LaunchedEffect(authViewModel, context) {
        authViewModel.authResults.collect{result ->
            val  errorText =
                    if (authViewModel.viewState.value!!.authSubState == AuthSubState.SignIn){
                        signInErrorText
                    }
                    else{
                        signUpErrorText
                    }

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

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { pv ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(pv),
        ) {
            with(viewState.value!!){
                when(authSubState){
                    AuthSubState.SignIn -> {
                        SignInView(
                            emailValue = emailValue,
                            passwordValue = passwordValue,
                            onEmailValueChanged = {authViewModel.obtainEvent(AuthEvent.EmailChanged(it))},
                            onPasswordValueChanged = {authViewModel.obtainEvent(AuthEvent.PasswordChanged(it))},
                            forgotPasswordClick = { /*TODO*/ },
                            signInClick = { authViewModel.obtainEvent(AuthEvent.SignInClicked) },
                            signUpCLick = { authViewModel.obtainEvent(AuthEvent.ActionSwitch)},
                            isProgress = isProgress,
                        )
                    }
                    AuthSubState.SignUp -> {
                        SignUpView(
                            emailValue = emailValue,
                            userNameValue = usernameValue,
                            passwordValue = passwordValue,
                            repeatPasswordValue = repeatPasswordValue,
                            onUserNameValueChanged = {},
                            onRepeatPasswordValueChanged = {},
                            onEmailValueChanged = {authViewModel.obtainEvent(AuthEvent.EmailChanged(it))},
                            onPasswordValueChanged = {authViewModel.obtainEvent(AuthEvent.PasswordChanged(it))},
                            signInClick = { authViewModel.obtainEvent(AuthEvent.ActionSwitch) },
                            signUpCLick = { authViewModel.obtainEvent(AuthEvent.SignUpClicked)},
                            isProgress = isProgress,
                        )
                    }
                }
            }
        }
    }

}