package com.wisnua.starterproject.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.wisnua.starterproject.data.local.model.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity)

    @Query("SELECT * FROM users WHERE id = :userId")
    fun getUserById(userId: Int): Flow<UserEntity?>

    @Query("DELETE FROM users")
    suspend fun deleteAllUsers()
}
