package com.orangeink.offlinechatapp.chat.presentation.navigation

enum class ChatScreen {
    CHAT_HISTORY, MESSAGES
}

sealed class ChatNavigation(val route: String) {
    data object ChatHistory: ChatNavigation(ChatScreen.CHAT_HISTORY.name)
    data object Messages: ChatNavigation(ChatScreen.MESSAGES.name)
}