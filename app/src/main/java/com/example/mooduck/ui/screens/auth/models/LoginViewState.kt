package com.example.mooduck.ui.screens.auth.models



enum class LoginSubState{
    SignIn, SignUp, Forgot
}
data class LoginViewState (
    val loginSubState: LoginSubState = LoginSubState.SignIn,
    val usernameValue: String = "",
    val emailValue: String = "",
    val passwordValue: String = "",
    val fullNameValue: String = "",
    val isProgress: Boolean = false,
)