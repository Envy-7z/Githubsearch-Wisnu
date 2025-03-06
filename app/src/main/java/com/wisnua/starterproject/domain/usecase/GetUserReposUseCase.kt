package com.wisnua.starterproject.domain.usecase

import com.wisnua.starterproject.domain.model.RepoResponseItem
import com.wisnua.starterproject.domain.repository.UserRepository
import javax.inject.Inject

class GetUserReposUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(username: String): List<RepoResponseItem> {
        return userRepository.getUserRepos(username)
    }
}
