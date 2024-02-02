package com.orangeink.offlinechatapp.chat.presentation.messages

import com.orangeink.offlinechatapp.core.database.entity.User

data class MessagesState(
    val isLoading: Boolean = true,
    val messages: List<MessagesItem> = emptyList(),
    val toUser: User? = null
)

data class MessagesItem(
    val body: String? = null,
    val mediaPath: String? = null,
    val createdAt: String,
    val type: MessagesType,
)

sealed interface MessagesType {
    data object Sent : MessagesType
    data object Received : MessagesType
}