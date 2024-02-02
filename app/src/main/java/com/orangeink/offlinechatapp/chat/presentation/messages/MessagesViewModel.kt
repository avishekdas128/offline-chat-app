package com.orangeink.offlinechatapp.chat.presentation.messages

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.orangeink.offlinechatapp.chat.domain.ChatRepository
import com.orangeink.offlinechatapp.core.database.entity.Message
import com.orangeink.offlinechatapp.core.database.entity.User
import com.orangeink.offlinechatapp.core.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MessagesViewModel @Inject constructor(
    private val chatRepository: ChatRepository,
    private val dataStore: DataStore<Preferences>
) : ViewModel() {

    private val _state = MutableStateFlow(MessagesState())
    val state = _state.asStateFlow()

    private var conversationId: Int? = null
    private var toUser: User? = null

    private var lastMessage: MessagesItem? = null

    fun getAllMessages(conversationId: Int?, toUserId: Int?) {
        viewModelScope.launch(Dispatchers.IO) {
            this@MessagesViewModel.conversationId = conversationId
            chatRepository.getMessagesHistory(conversationId)
                .combine(chatRepository.getUser(toUserId)) { messagesResult, userResult ->
                    if (messagesResult is Result.Success && userResult is Result.Success) {
                        toUser = userResult.data
                        lastMessage = messagesResult.data.lastOrNull()
                        _state.update {
                            it.copy(
                                isLoading = false,
                                messages = messagesResult.data,
                                toUser = userResult.data
                            )
                        }
                    } else if (messagesResult is Result.Loading || userResult is Result.Loading) {
                        _state.update { it.copy(isLoading = true) }
                    } else {
                        // Handle Error
                        Unit
                    }
                }.launchIn(this)
        }
    }

    fun sendMessage(body: String?, mediaPath: String?) {
        viewModelScope.launch(Dispatchers.IO) {
            val fromUserId = dataStore.data.first()[intPreferencesKey("userId")]
            val toUserId = toUser?.id
            if (toUserId != null && fromUserId != null && conversationId != null) {
                chatRepository.sendMessage(
                    Message(
                        body = body,
                        mediaPath = mediaPath,
                        conversationId = conversationId!!,
                        toId = toUserId,
                        fromId = fromUserId,
                        createdAt = ""
                    )
                )
            }
        }
    }

    fun echoMessage() {
        viewModelScope.launch(Dispatchers.IO) {
            delay(2000)
            val fromUserId = dataStore.data.first()[intPreferencesKey("userId")]
            val toUserId = toUser?.id
            if (toUserId != null && fromUserId != null && conversationId != null) {
                chatRepository.sendMessage(
                    Message(
                        body = lastMessage?.body,
                        mediaPath = lastMessage?.mediaPath,
                        conversationId = conversationId!!,
                        toId = fromUserId,
                        fromId = toUserId,
                        createdAt = ""
                    )
                )
            }
        }
    }
}