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
    suspend fun insertUsers(users: List<UserEntity>) // Simpan banyak user

    @Query("SELECT * FROM users")
    fun getAllUsers(): Flow<List<UserEntity>> // Ambil semua user dari local DB

    @Query("DELETE FROM users")
    suspend fun deleteAllUsers()
}
