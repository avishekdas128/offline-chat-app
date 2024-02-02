package com.orangeink.offlinechatapp.chat.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.intPreferencesKey
import com.orangeink.offlinechatapp.chat.domain.ChatRepository
import com.orangeink.offlinechatapp.chat.presentation.messages.MessagesItem
import com.orangeink.offlinechatapp.chat.presentation.messages.MessagesType
import com.orangeink.offlinechatapp.core.database.dao.ConversationDao
import com.orangeink.offlinechatapp.core.database.dao.MessageDao
import com.orangeink.offlinechatapp.core.database.dao.UserDao
import com.orangeink.offlinechatapp.core.database.entity.Message
import com.orangeink.offlinechatapp.core.database.entity.User
import com.orangeink.offlinechatapp.core.database.model.ChatHistory
import com.orangeink.offlinechatapp.core.util.Result
import com.orangeink.offlinechatapp.core.util.parseForSort
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(
    private val userDao: UserDao,
    private val conversationDao: ConversationDao,
    private val messageDao: MessageDao,
    private val dataStore: DataStore<Preferences>
) : ChatRepository {

    override suspend fun getChatHistory(userId: Int?): Flow<Result<List<ChatHistory>>> =
        channelFlow {
            send(Result.Loading)
            userId?.let { userId ->
                conversationDao.getAllConversations(userId).collectLatest { chatHistory ->
                    send(
                        Result.Success(
                            chatHistory.sortedWith(
                                compareByDescending(nullsLast()) {
                                    it.messages.lastOrNull()?.createdAt?.parseForSort()
                                }
                            )
                        )
                    )
                }
            } ?: send(Result.Error("Something went wrong!"))
        }

    override suspend fun getMessagesHistory(conversationId: Int?): Flow<Result<List<MessagesItem>>> =
        channelFlow {
            send(Result.Loading)
            val userId = dataStore.data.first()[intPreferencesKey("userId")]
            conversationId?.let {
                messageDao.getAllMessages(it).collectLatest { messageHistory ->
                    val messages = messageHistory.map { item ->
                        MessagesItem(
                            body = item.message.body,
                            mediaPath = item.message.mediaPath,
                            createdAt = item.message.createdAt,
                            type = if (item.message.fromId == userId)
                                MessagesType.Sent
                            else
                                MessagesType.Received
                        )
                    }
                    send(Result.Success(messages))
                }
            } ?: send(Result.Error("Something went wrong!"))
        }

    override suspend fun getUser(userId: Int?): Flow<Result<User>> = flow {
        emit(Result.Loading)
        userId?.let { id ->
            val toUser = userDao.getUser(id)
            toUser?.let {
                emit(Result.Success(it))
            } ?: emit(Result.Error())
        } ?: emit(Result.Error("Something went wrong!"))
    }

    override suspend fun sendMessage(message: Message) {
        messageDao.addMessage(
            message.body,
            message.mediaPath,
            message.conversationId,
            message.toId,
            message.fromId
        )
    }
}