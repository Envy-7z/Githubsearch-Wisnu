package com.wisnua.starterproject.domain.usecase

import com.wisnua.starterproject.domain.model.UserItem
import com.wisnua.starterproject.domain.repository.UserRepository
import javax.inject.Inject

class SearchUserUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(query: String, perPage: Int, page: Int): List<UserItem> {
        return repository.searchUsers(query, perPage, page)
    }
}
