package com.wisnua.starterproject.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey val id: Int,
    val username: String,
    val avatarUrl: String,
    val bio: String?,
    val followers: Int,
    val following: Int
)
