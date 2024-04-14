package com.example.mooduck.ui.screens.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mooduck.common.EventHandler
import com.example.mooduck.ui.screens.auth.models.AuthEvent
import com.example.mooduck.ui.screens.auth.models.AuthSubState
import com.example.mooduck.ui.screens.auth.models.AuthViewState
import com.mooduck.domain.models.User
import com.mooduck.domain.models.Result
import com.mooduck.domain.repository.AuthRepository
import com.mooduck.domain.repository.LocalUserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val localUserRepository: LocalUserRepository,
) : ViewModel(), EventHandler<AuthEvent> {

    private val _viewState = MutableLiveData(AuthViewState())
    val viewState: LiveData<AuthViewState> = _viewState

    private val resultChannel = Channel<Result<User>>()
    val authResults = resultChannel.receiveAsFlow()


    override fun obtainEvent(event: AuthEvent) {
        when(event){
            AuthEvent.SignInClicked -> signIn()
            is AuthEvent.SignUpClicked -> signUp()
            is AuthEvent.UserNameChanged -> usernameChanged(event.value)
            is AuthEvent.PasswordChanged -> passwordChanged(event.value)
            is AuthEvent.ActionSwitch -> actionSwitch()
            is AuthEvent.EmailChanged -> emailChanged(event.value)
        }
    }

    private fun actionSwitch() {
        when(_viewState.value!!.authSubState){
            AuthSubState.SignUp -> fetchSignIn()
            AuthSubState.SignIn -> fetchSignUp()
        }
    }


    private fun signIn() {
        viewModelScope.launch(Dispatchers.IO) {
            _viewState.postValue(
                _viewState.value?.copy(isProgress = true)
            )

            val result = authRepository.login(
                email = _viewState.value!!.emailValue,
                password = _viewState.value!!.passwordValue
            )

            when(result){
                is Result.Error -> localUserRepository.clearUser()
                is Result.Success -> with(result.data){
                    localUserRepository.saveUser(this)
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
                is Result.Error -> localUserRepository.clearUser()
                is Result.Success -> with(result.data){
                    localUserRepository.saveUser(this)
                }
            }

            resultChannel.send(result)

            _viewState.postValue(
                _viewState.value?.copy(isProgress = false)
            )
        }
    }

    private fun emailChanged(value: String) {
        _viewState.postValue(
            _viewState.value?.copy(emailValue = value)
        )
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

    private fun fetchSignIn(){
        _viewState.postValue(
            _viewState.value?.copy(authSubState = AuthSubState.SignIn)
        )
    }

    private fun fetchSignUp(){
        _viewState.postValue(
            _viewState.value?.copy(authSubState = AuthSubState.SignUp)
        )
    }
}