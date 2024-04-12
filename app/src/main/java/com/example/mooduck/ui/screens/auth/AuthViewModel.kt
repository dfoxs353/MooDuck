package com.example.mooduck.ui.screens.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mooduck.common.EventHandler
import com.example.mooduck.ui.screens.auth.models.LoginEvent
import com.example.mooduck.ui.screens.auth.models.LoginSubState
import com.example.mooduck.ui.screens.auth.models.LoginViewState
import com.mooduck.domain.models.User
import com.mooduck.domain.models.Result
import com.mooduck.domain.repository.AuthRepository
import com.mooduck.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository,
) : ViewModel(), EventHandler<LoginEvent> {

    private val _viewState = MutableLiveData(LoginViewState())
    val viewState: LiveData<LoginViewState> = _viewState

    private val resultChannel = Channel<Result<User>>()
    val authResults = resultChannel.receiveAsFlow()


    override fun obtainEvent(event: LoginEvent) {
        when(event){
            LoginEvent.SignInClicked -> signIn()
            is LoginEvent.SignUpClicked -> signUp()
            is LoginEvent.UserNameChanged -> usernameChanged(event.value)
            is LoginEvent.PasswordChanged -> passwordChanged(event.value)
            is LoginEvent.ActionSwitch -> actionSwitch()
        }
    }

    private fun actionSwitch() {
        when(_viewState.value!!.loginSubState){
            LoginSubState.SignUp -> fetchSignIn()
            LoginSubState.SignIn -> fetchSignUp()
            LoginSubState.Forgot -> fetchForgot()
        }
    }


    private fun signIn() {
        viewModelScope.launch(Dispatchers.IO) {
            _viewState.postValue(
                _viewState.value?.copy(isProgress = true)
            )

            val result = authRepository.login(
                email = _viewState.value!!.usernameValue,
                password = _viewState.value!!.passwordValue
            )

            when(result){
                is Result.Error -> userRepository.clearUser()
                is Result.Success -> with(result.data){
                    userRepository.saveUser(this)
                }
            }

            resultChannel.send(result)

            _viewState.postValue(
                _viewState.value?.copy(isProgress = false)
            )
        }
    }

    private fun signUp() {
        viewModelScope.launch(Dispatchers.IO) {
            _viewState.postValue(
                _viewState.value?.copy(isProgress = true)
            )

            val result = authRepository.signup(
                email = _viewState.value!!.emailValue,
                username = _viewState.value!!.usernameValue,
                password = _viewState.value!!.passwordValue
            )


            when(result){
                is Result.Error -> userRepository.clearUser()
                is Result.Success -> with(result.data){
                    userRepository.saveUser(this)
                }
            }

            resultChannel.send(result)

            _viewState.postValue(
                _viewState.value?.copy(isProgress = false)
            )
        }
    }
    private fun passwordChanged(value: String) {
        _viewState.postValue(
            _viewState.value?.copy(passwordValue = value)
        )
    }

    private fun usernameChanged(value: String) {
        _viewState.postValue(
            _viewState.value?.copy(usernameValue = value)
        )
    }

    private fun fetchForgot(){
        _viewState.postValue(
            _viewState.value?.copy(loginSubState = LoginSubState.Forgot)
        )
    }

    private fun fetchSignIn(){
        _viewState.postValue(
            _viewState.value?.copy(loginSubState = LoginSubState.SignIn)
        )
    }

    private fun fetchSignUp(){
        _viewState.postValue(
            _viewState.value?.copy(loginSubState = LoginSubState.SignUp)
        )
    }
}