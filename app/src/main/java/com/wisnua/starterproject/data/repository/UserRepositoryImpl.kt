package com.wisnua.starterproject.data.repository

import com.wisnua.starterproject.data.local.dao.UserDao
import com.wisnua.starterproject.data.local.model.UserEntity
import com.wisnua.starterproject.data.remote.ApiService
import com.wisnua.starterproject.domain.model.DetailResponse
import com.wisnua.starterproject.domain.model.SearchResponse
import com.wisnua.starterproject.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val userDao: UserDao
) : UserRepository {

    override suspend fun searchUsers(query: String, perPage: Int, page: Int): SearchResponse {
        return apiService.searchUsers(query, perPage, page)
    }

    override suspend fun getUserDetail(username: String): DetailResponse {
        return apiService.getDetail(username)
    }

    override suspend fun saveUserToLocal(user: DetailResponse) {
        val userEntity = UserEntity(
            id = user.id ?: 0,
            username = user.login ?: "",
            avatarUrl = user.avatarUrl ?: "",
            bio = user.bio,
            followers = user.followers ?: 0,
            following = user.following ?:0
        )
        userDao.insertUser(userEntity)
    }

    override suspend fun getUserFromLocal(userId: Int): Flow<DetailResponse?> {
        return userDao.getUserById(userId).map { entity ->
            entity?.let {
                DetailResponse(
                    id = it.id,
                    login = it.username,
                    avatarUrl = it.avatarUrl,
                    bio = it.bio,
                    followers = it.followers,
                    following = it.following
                )
            }
        }
    }

    override suspend fun clearLocalUsers() {
        userDao.deleteAllUsers()
    }
}
