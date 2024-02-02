package com.orangeink.offlinechatapp.auth.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import com.orangeink.offlinechatapp.core.database.entity.User
import com.orangeink.offlinechatapp.auth.domain.AuthRepository
import com.orangeink.offlinechatapp.core.database.dao.ConversationDao
import com.orangeink.offlinechatapp.core.database.dao.UserDao
import com.orangeink.offlinechatapp.core.database.entity.Conversation
import com.orangeink.offlinechatapp.core.util.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val userDao: UserDao,
    private val conversationDao: ConversationDao,
    private val userDataStore: DataStore<Preferences>
) : AuthRepository {

    override suspend fun registerUser(user: User): Flow<Result<User>> = flow {
        emit(Result.Loading)
        val id = userDao.registerUser(user)
        if (id == -1L) {
            emit(Result.Error("Username is already taken!"))
        } else {
            userDao.getAllUsers().forEach {
                if (it.id != id.toInt()) {
                    conversationDao.addConversation(
                        Conversation(
                            participantOneId = it.id,
                            participantTwoId = id.toInt()
                        )
                    )
                }
            }
            userDataStore.edit { preferences ->
                preferences[intPreferencesKey("userId")] = id.toInt()
            }
            emit(Result.Success(user))
        }
    }

    override suspend fun loginUser(user: User): Flow<Result<User>> = flow {
        emit(Result.Loading)
        val userResponse = userDao.loginUser(user.username, user.password)
        userResponse?.let {
            userDataStore.edit { preferences ->
                preferences[intPreferencesKey("userId")] = it.id
            }
            emit(Result.Success(it))
        } ?: emit(Result.Error("User doesn't exist!"))
    }
}