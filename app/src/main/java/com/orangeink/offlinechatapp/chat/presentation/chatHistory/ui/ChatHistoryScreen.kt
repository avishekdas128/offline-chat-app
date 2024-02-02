package com.orangeink.offlinechatapp.chat.presentation.chatHistory.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.orangeink.offlinechatapp.R
import com.orangeink.offlinechatapp.chat.presentation.chatHistory.ChatHistoryState
import com.orangeink.offlinechatapp.chat.presentation.navigation.ChatNavigation
import com.orangeink.offlinechatapp.core.database.entity.Conversation
import com.orangeink.offlinechatapp.core.database.entity.Message
import com.orangeink.offlinechatapp.core.database.entity.User
import com.orangeink.offlinechatapp.core.database.model.ChatHistory
import com.orangeink.offlinechatapp.core.design.components.EmptyContent
import com.orangeink.offlinechatapp.core.design.theme.GreyLight
import com.orangeink.offlinechatapp.core.design.theme.Secondary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatHistoryScreen(
    navController: NavController,
    chatHistoryState: ChatHistoryState,
    loggedInUserId: Int? = 0,
    onLogout: () -> Unit
) {
    val lazyColumnState = rememberLazyListState()
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "All Chats",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Secondary,
                    titleContentColor = Color.White,
                    actionIconContentColor = Color.White
                ),
                actions = {
                    IconButton(onClick = onLogout) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_logout),
                            contentDescription = "logout"
                        )
                    }
                }
            )
        }
    ) {
        if (chatHistoryState.isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            if (chatHistoryState.chatHistory.isEmpty()) {
                EmptyContent(modifier = Modifier.padding(it), desc = "No other members yet!")
            } else {
                LazyColumn(
                    state = lazyColumnState, modifier = Modifier
                        .padding(it)
                        .fillMaxSize()
                ) {
                    items(
                        count = chatHistoryState.chatHistory.size,
                        key = { index -> chatHistoryState.chatHistory[index].conversation.id }
                    ) { i ->
                        val chatHistoryItem = chatHistoryState.chatHistory[i]
                        ChatHistoryItem(chatHistoryState.chatHistory[i], loggedInUserId) {
                            val conversationId = chatHistoryItem.conversation.id
                            val toUserId =
                                if (loggedInUserId == chatHistoryItem.conversation.participantOneId)
                                    chatHistoryItem.conversation.participantTwoId
                                else
                                    chatHistoryItem.conversation.participantOneId
                            navController.navigate(
                                "${ChatNavigation.Messages.route}/$conversationId&${toUserId}"
                            )
                        }
                        if (i != chatHistoryState.chatHistory.size - 1)
                            Divider(thickness = 1.dp, color = GreyLight)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ChatHistoryScreenDataPreview() {
    ChatHistoryScreen(
        rememberNavController(),
        ChatHistoryState(
            listOf(
                ChatHistory(
                    Conversation(0, 1, 0),
                    User("Andrew", "1234", 0),
                    User("Robert", "1234", 1),
                    emptyList()
                ),
                ChatHistory(
                    Conversation(0, 2, 1),
                    User("Andrew", "1234", 0),
                    User("John", "1234", 2),
                    listOf(
                        Message(
                            body = "hey, hello!",
                            conversationId = 0,
                            toId = 0,
                            fromId = 0,
                            createdAt = "2024-02-01 17:30:28"
                        )
                    )
                )
            ),
            isLoading = false
        ),
    ) {}
}

@Preview(showBackground = true)
@Composable
fun ChatHistoryScreenNoDataPreview() {
    ChatHistoryScreen(
        rememberNavController(),
        ChatHistoryState(
            emptyList(),
            isLoading = false
        ),
    ) {}
}