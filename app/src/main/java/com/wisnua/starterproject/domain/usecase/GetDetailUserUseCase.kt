package com.wisnua.starterproject.domain.usecase

import com.wisnua.starterproject.domain.model.DetailResponse
import com.wisnua.starterproject.domain.repository.UserRepository
import javax.inject.Inject

class GetUserDetailUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(username: String): DetailResponse {
        return repository.getUserDetail(username)
    }
}
