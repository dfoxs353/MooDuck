package com.example.mooduck.ui.screens.home.models

sealed class HomeEvent {
    object Refresh : HomeEvent()
    data class AddedToFavorites(val value: String): HomeEvent()
    data class DeleteFromFavorites(val value: String): HomeEvent()
}
