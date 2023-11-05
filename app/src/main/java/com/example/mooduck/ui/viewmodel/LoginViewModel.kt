package com.example.mooduck.ui.viewmodel

import android.app.Application
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mooduck.R
import com.example.mooduck.common.RetrofitClient
import com.example.mooduck.data.remote.Result
import com.example.mooduck.data.remote.auth.AuthApi
import com.example.mooduck.data.remote.auth.AuthResult
import com.example.mooduck.data.repository.LocalUserRepository
import com.example.mooduck.data.repository.RemoteUserRepository
import com.example.mooduck.ui.model.AuthFormState
import kotlinx.coroutines.Dispatchers

class LoginViewModel(application: Application) : ViewModel() {
    private val _loginForm = MutableLiveData<AuthFormState>()
    val loginFormState: LiveData<AuthFormState> = _loginForm

    private val _loginResult = MutableLiveData<AuthResult>()
    val loginResult: LiveData<AuthResult> = _loginResult

    private val retrofit = RetrofitClient.instance
    private val userApi = retrofit.create(AuthApi::class.java)

    private val remoteUserRepository: RemoteUserRepository = RemoteUserRepository(userApi, Dispatchers.IO)
    private val localUserRepository:LocalUserRepository = LocalUserRepository(application)
    suspend fun login(email: String, password: String) {
        val result = remoteUserRepository.login(email, password)

        if(result is Result.Success){
            _loginResult.value = AuthResult(success = result.data)
            saveRefreshToken(result.data.refreshToken,result.data.accessToken)
        }
        else{
            _loginResult.value = AuthResult(error = R.string.error_string)
        }
    }

    fun loginDataChanged(username: String, password: String) {
        if (!isUserNameValid(username)) {
            _loginForm.value = AuthFormState(usernameError = R.string.invalid_username)
        } else if (!isPasswordValid(password)) {
            _loginForm.value = AuthFormState(passwordError = R.string.invalid_password)
        } else {
            _loginForm.value = AuthFormState(isDataValid = true)
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

    fun saveRefreshToken(refreshToken: String, accessToken: String) {
        localUserRepository.saveJWToken(refreshToken,accessToken)
    }
}