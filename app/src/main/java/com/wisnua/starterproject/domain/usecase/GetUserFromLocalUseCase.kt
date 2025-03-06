package com.wisnua.starterproject.domain.usecase

import com.wisnua.starterproject.domain.model.DetailResponse
import com.wisnua.starterproject.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserFromLocalUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(userId: Int): Flow<DetailResponse?> {
        return repository.getUserFromLocal(userId)
    }
}
