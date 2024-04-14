package com.example.mooduck.ui.screens.auth.models


sealed class AuthEvent {
    object ActionSwitch: AuthEvent()
    object SignInClicked : AuthEvent()
    object SignUpClicked : AuthEvent()
    data class UserNameChanged(val value: String) : AuthEvent()
    data class EmailChanged(val value: String) : AuthEvent()
    data class PasswordChanged(val value: String): AuthEvent()
}