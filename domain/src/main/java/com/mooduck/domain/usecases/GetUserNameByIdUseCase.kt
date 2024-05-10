package com.mooduck.domain.usecases

import com.mooduck.domain.models.Result
import com.mooduck.domain.models.User
import com.mooduck.domain.repository.RemoteUserRepository

class GetUserNameByIdUseCase(private val userRepository: RemoteUserRepository) {
    suspend operator fun invoke(userId: String): String {
        val result = userRepository.getUserById(userId)
        when(result){
            is Result.Error -> return ""
            is Result.Success -> return result.data.userName
        }
    }
}
