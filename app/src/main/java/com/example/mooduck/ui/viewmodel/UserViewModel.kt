package com.example.mooduck.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
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
): ViewModel() {

    val user = MutableLiveData<User?>()

    init {
        user.value = userManager.getUser()
    }

    fun saveUser(user: User) {
        this.user.value = user
        with(user){
            userManager.saveUser(userid, userPassword, accessToken, refreshToken)
        }
    }

    fun deleteUser() {
        user.value = null
        userManager.clearUserData()
    }
}