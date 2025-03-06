package com.wisnua.starterproject.domain.repository

import com.wisnua.starterproject.domain.model.DetailResponse
import com.wisnua.starterproject.domain.model.RepoResponseItem
import com.wisnua.starterproject.domain.model.UserItem
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun searchUsers(query: String, perPage: Int, page: Int): List<UserItem>
    suspend fun getUserDetail(username: String): DetailResponse
    suspend fun getUserRepos(username: String): List<RepoResponseItem>
    suspend fun getUserFromLocal(userId: Int): Flow<DetailResponse?>
    suspend fun saveUserToLocal(user: DetailResponse)
}

