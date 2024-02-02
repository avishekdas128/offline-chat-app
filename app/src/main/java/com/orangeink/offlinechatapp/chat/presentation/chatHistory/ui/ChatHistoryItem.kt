package com.orangeink.offlinechatapp.chat.presentation.chatHistory.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.orangeink.offlinechatapp.core.database.entity.Conversation
import com.orangeink.offlinechatapp.core.database.entity.Message
import com.orangeink.offlinechatapp.core.database.entity.User
import com.orangeink.offlinechatapp.core.database.model.ChatHistory
import com.orangeink.offlinechatapp.core.design.theme.GreyDark
import com.orangeink.offlinechatapp.core.design.theme.Primary
import com.orangeink.offlinechatapp.core.util.formatCreatedAt

@Composable
fun ChatHistoryItem(
    chatHistory: ChatHistory,
    loggedInUserId: Int? = 0,
    onItemClick: () -> Unit
) {
    val name by remember {
        mutableStateOf(
            if (loggedInUserId == chatHistory.conversation.participantOneId)
                chatHistory.participantTwoUser.username
            else
                chatHistory.participantOneUser.username
        )
    }
    val lastMessage by remember { mutableStateOf(chatHistory.messages.lastOrNull()) }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onItemClick)
            .padding(16.dp)
    ) {
        Box(Modifier.size(35.dp), contentAlignment = Alignment.Center) {
            Canvas(modifier = Modifier.fillMaxSize()) { drawCircle(SolidColor(Primary)) }
            Text(
                text = name.take(1).uppercase(),
                style = TextStyle(
                    fontSize = 18.sp,
                    platformStyle = PlatformTextStyle(includeFontPadding = false)
                ),
                color = Color.White
            )
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(
                text = name,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = lastMessage?.body ?: if (lastMessage?.mediaPath != null)
                    if (lastMessage?.fromId == loggedInUserId) "Sent a media" else "Received a media"
                else
                    "Start chatting now!",
                fontSize = 11.sp,
                color = if (lastMessage?.body != null || lastMessage?.mediaPath != null)
                    GreyDark
                else
                    Primary
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        lastMessage?.createdAt?.let {
            Text(text = it.formatCreatedAt(), fontSize = 10.sp)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ChatHistoryItemPreview() {
    ChatHistoryItem(
        ChatHistory(
            Conversation(0, 1),
            User("Robert", "1234", 0),
            User("Andrew", "1234", 1),
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
    ) { }
}
