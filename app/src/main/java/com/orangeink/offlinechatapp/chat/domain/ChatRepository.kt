package com.orangeink.offlinechatapp.chat.domain

import com.orangeink.offlinechatapp.chat.presentation.messages.MessagesItem
import com.orangeink.offlinechatapp.core.database.entity.Message
import com.orangeink.offlinechatapp.core.database.entity.User
import com.orangeink.offlinechatapp.core.database.model.ChatHistory
import com.orangeink.offlinechatapp.core.util.Result
import kotlinx.coroutines.flow.Flow

interface ChatRepository {

    suspend fun getChatHistory(userId: Int?): Flow<Result<List<ChatHistory>>>

    suspend fun getMessagesHistory(conversationId: Int?): Flow<Result<List<MessagesItem>>>

    suspend fun getUser(userId: Int?): Flow<Result<User>>

    suspend fun sendMessage(message: Message)
}