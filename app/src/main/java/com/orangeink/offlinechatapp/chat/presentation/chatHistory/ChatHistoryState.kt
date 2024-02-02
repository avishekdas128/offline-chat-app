package com.orangeink.offlinechatapp.chat.presentation.chatHistory

import com.orangeink.offlinechatapp.core.database.model.ChatHistory

data class ChatHistoryState(
    val chatHistory: List<ChatHistory> = emptyList(),
    val isLoading: Boolean = true,
)