package com.example.mooduck.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.mooduck.data.local.User
import com.example.mooduck.data.repository.LocalUserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userManager: LocalUserRepository
) : ViewModel() {

    private val _user = MutableLiveData<User?>()
    val user: LiveData<User?> get() = _user

    init {
        viewModelScope.launch {
            userManager.userFlow.collect { user ->
                _user.value = user
            }
        }
    }

    fun saveUser(user: User) {
        userManager.saveUser(user)
    }

    fun deleteUser() {
        userManager.clearUserData()
    }
}