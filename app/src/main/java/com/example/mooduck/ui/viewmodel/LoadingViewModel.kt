package com.example.mooduck.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mooduck.R
import com.example.mooduck.common.RetrofitClient
import com.example.mooduck.data.remote.Result
import com.example.mooduck.data.remote.auth.AuthApi
import com.example.mooduck.data.remote.auth.AuthResponse
import com.example.mooduck.data.remote.auth.AuthResult
import com.example.mooduck.data.repository.RemoteAuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import javax.inject.Inject

@HiltViewModel
class LoadingViewModel @Inject constructor(
    private  val remoteAuthRepository: RemoteAuthRepository,
): ViewModel() {
    private val _refreshResult = MutableLiveData<AuthResult>()
    val refreshResult: LiveData<AuthResult> = _refreshResult

    suspend fun getTokens(){
        val result = remoteAuthRepository.refresh()

        if(result is Result.Success){
            _refreshResult.value = AuthResult(success = result.data)
            Log.d("TAG", "access token${result.data.accessToken}")
        }
        else{
            _refreshResult.value = AuthResult(error = R.string.error_string)
        }
    }
    fun setRetrofitToken(accessToken: String, refreshToken:String){
        if (accessToken == null || refreshToken == null) {
            return
        }
        RetrofitClient.setAccessAndRefreshTokens(accessToken,refreshToken)
        Log.d("log", "token :${accessToken} and ${refreshToken}")
    }
}