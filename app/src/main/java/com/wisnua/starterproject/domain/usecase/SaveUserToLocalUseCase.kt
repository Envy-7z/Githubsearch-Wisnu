package com.wisnua.starterproject.domain.usecase

import com.wisnua.starterproject.domain.model.DetailResponse
import com.wisnua.starterproject.domain.repository.UserRepository
import javax.inject.Inject

class SaveUserToLocalUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(user: DetailResponse) {
        repository.saveUserToLocal(user)
    }
}
