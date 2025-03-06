package com.wisnua.starterproject.domain.repository

import com.wisnua.starterproject.domain.model.DetailResponse
import com.wisnua.starterproject.domain.model.SearchResponse
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun searchUsers(query: String, perPage: Int, page: Int): SearchResponse
    suspend fun getUserDetail(username: String): DetailResponse
    suspend fun saveUserToLocal(user: DetailResponse)
    suspend fun getUserFromLocal(userId: Int): Flow<DetailResponse?>
    suspend fun clearLocalUsers()
}
