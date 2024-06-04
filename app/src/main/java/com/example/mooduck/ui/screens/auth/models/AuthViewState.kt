package com.example.mooduck.ui.screens.auth.models



enum class AuthSubState{
    SignIn, SignUp,
}
data class AuthViewState (
    val authSubState: AuthSubState = AuthSubState.SignIn,
    val usernameValue: String = "",
    val emailValue: String = "",
    val passwordValue: String = "",
    val repeatPasswordValue: String = "",
    val fullNameValue: String = "",
    val isProgress: Boolean = false,
)