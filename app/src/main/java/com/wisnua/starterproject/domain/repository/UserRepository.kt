package com.wisnua.starterproject.domain.repository

import com.wisnua.starterproject.domain.model.DetailResponse
import com.wisnua.starterproject.domain.model.RepoResponseItem
import com.wisnua.starterproject.domain.model.UserItem
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun searchUsers(query: String, perPage: Int, page: Int): List<UserItem>
    suspend fun getUserDetail(username: String): DetailResponse
    suspend fun getUserRepos(username: String): List<RepoResponseItem>
    fun getUsersFromLocal(): Flow<List<UserItem>>
    suspend fun saveUsersToLocal(users: List<UserItem>)
}

