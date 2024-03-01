package com.example.mooduck.ui.viewmodel

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mooduck.R
import com.example.mooduck.data.remote.Result
import com.example.mooduck.data.remote.auth.AuthResult
import com.example.mooduck.data.repository.LocalUserRepository
import com.example.mooduck.data.repository.AuthRepository
import com.example.mooduck.ui.model.AuthFormState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val localUserRepository: LocalUserRepository,
) : ViewModel() {
    private val _signupForm = MutableLiveData<AuthFormState>()
    val signupFormState: LiveData<AuthFormState> = _signupForm

    private val _signupResult = MutableLiveData<AuthResult>()
    val signupResult: LiveData<AuthResult> = _signupResult


    suspend fun signup(username: String,email: String, password: String) {
        val result = authRepository.signup(email, password,username)

        if(result is Result.Success){
            _signupResult.value = AuthResult(success = result.data)
        }
        else{
            _signupResult.value = AuthResult(error = R.string.error_string)
        }
    }

    fun loginDataChanged(username: String, password: String) {
        if (!isUserNameValid(username)) {
            _signupForm.value = AuthFormState(usernameError = R.string.invalid_username)
        } else if (!isPasswordValid(password)) {
            _signupForm.value = AuthFormState(passwordError = R.string.invalid_password)
        } else {
            _signupForm.value = AuthFormState(isDataValid = true)
        }
    }

    private fun isUserNameValid(username: String): Boolean {
        return if(username.contains('@')){
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else{
            username.isNotBlank()
        }
    }

    private fun isPasswordValid(password: String): Boolean{
        return password.length > 5
    }
}