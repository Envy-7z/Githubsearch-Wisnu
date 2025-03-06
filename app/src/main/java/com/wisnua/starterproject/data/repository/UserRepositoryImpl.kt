package com.wisnua.starterproject.data.repository

import com.wisnua.starterproject.data.local.dao.UserDao
import com.wisnua.starterproject.data.local.model.UserEntity
import com.wisnua.starterproject.data.remote.ApiService
import com.wisnua.starterproject.domain.model.DetailResponse
import com.wisnua.starterproject.domain.model.RepoResponseItem
import com.wisnua.starterproject.domain.model.UserItem
import com.wisnua.starterproject.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val userDao: UserDao
) : UserRepository {

    override suspend fun searchUsers(query: String, perPage: Int, page: Int): List<UserItem> {
        val users = apiService.searchUsers(query, perPage, page).items.orEmpty().map { userResponse ->
            UserItem(
                id = userResponse?.id ?: 0,
                login = userResponse?.login ?: "",
                avatarUrl = userResponse?.avatarUrl ?: ""
            )
        }

        saveUsersToLocal(users)

        return users
    }

    override suspend fun getUserDetail(username: String): DetailResponse {
        return apiService.getDetail(username)
    }

    override suspend fun getUserRepos(username: String): List<RepoResponseItem> {
        return apiService.getUserRepos(username)
    }

    override fun getUsersFromLocal(): Flow<List<UserItem>> {
        return userDao.getAllUsers().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun saveUsersToLocal(users: List<UserItem>) {
        val userEntities = users.map { user ->
            UserEntity(
                id = user.id ?: 0,
                username = user.login ?:"",
                avatarUrl = user.avatarUrl?:"",
                bio = null,
                followers = 0,
                following = 0
            )
        }
        userDao.insertUsers(userEntities)
    }
}
