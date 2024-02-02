package com.orangeink.offlinechatapp.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.orangeink.offlinechatapp.core.database.entity.User

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun registerUser(user: User): Long

    @Query("SELECT * FROM user_table WHERE username LIKE :name AND password LIKE :password")
    suspend fun loginUser(name: String, password: String): User?

    @Query("SELECT * FROM user_table")
    suspend fun getAllUsers(): List<User>

    @Query("SELECT * FROM user_table WHERE id LIKE :userId")
    suspend fun getUser(userId: Int): User?
}