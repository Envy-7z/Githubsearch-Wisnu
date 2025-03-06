package com.wisnua.starterproject.domain.usecase

import com.wisnua.starterproject.domain.model.UserItem
import com.wisnua.starterproject.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUsersFromLocalUseCase @Inject constructor(
    private val repository: UserRepository
) {
    operator fun invoke(): Flow<List<UserItem>> {
        return repository.getUsersFromLocal()
    }
}
