package com.orangeink.offlinechatapp.auth.domain

import com.orangeink.offlinechatapp.core.database.entity.User
import com.orangeink.offlinechatapp.core.util.Result
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    suspend fun registerUser(user: User): Flow<Result<User>>

    suspend fun loginUser(user: User): Flow<Result<User>>
}