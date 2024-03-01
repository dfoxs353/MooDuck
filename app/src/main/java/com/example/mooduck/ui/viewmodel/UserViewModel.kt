package com.example.mooduck.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mooduck.domain.models.User
import dagger.hilt.android.lifecycle.HiltViewModel
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