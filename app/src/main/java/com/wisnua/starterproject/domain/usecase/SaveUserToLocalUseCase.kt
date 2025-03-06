package com.wisnua.starterproject.domain.usecase

import com.wisnua.starterproject.domain.model.UserItem
import com.wisnua.starterproject.domain.repository.UserRepository
import javax.inject.Inject

class SaveUsersToLocalUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(users: List<UserItem>) {
        repository.saveUsersToLocal(users)
    }
}
