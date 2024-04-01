package com.example.mooduck.ui.viewmodel


import android.util.Log
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mooduck.R
import com.mooduck.data.remote.auth.AuthResult
import com.mooduck.data.repository.AuthRepositoryIml
import com.example.mooduck.ui.model.AuthFormState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepositoryIml,
    private val localUserRepository: AuthRepositoryIml,
) : ViewModel() {
    private val _loginFormState = MutableLiveData<AuthFormState>()
    val loginFormState: LiveData<AuthFormState> = _loginFormState

    private val _loginResult = MutableLiveData<AuthResult>()
    val loginResult: LiveData<AuthResult> = _loginResult

    suspend fun login(email: String, password: String) {
        val result = authRepository.login(email, password)

        if (result is com.mooduck.domain.models.Result.Success) {
            _loginResult.value = AuthResult(success = result.data)
            Log.d("TAG", "access token${result.data.accessToken}")
        } else {
            _loginResult.value = AuthResult(error = R.string.error_string)
        }
    }

    fun loginDataChanged(username: String, password: String) {
        if (!isUserNameValid(username)) {
            _loginFormState.value = AuthFormState(usernameError = R.string.invalid_username)
        } else if (!isPasswordValid(password)) {
            _loginFormState.value = AuthFormState(passwordError = R.string.invalid_password)
        } else {
            _loginFormState.value = AuthFormState(isDataValid = true)
        }
    }

    private fun isUserNameValid(username: String): Boolean {
        return if (username.contains('@')) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            username.isNotBlank()
        }
    }

    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }
}