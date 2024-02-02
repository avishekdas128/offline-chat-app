package com.orangeink.offlinechatapp.chat.presentation.messages.ui

import android.view.ViewTreeObserver
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.orangeink.offlinechatapp.chat.presentation.messages.MessagesItem
import com.orangeink.offlinechatapp.chat.presentation.messages.MessagesState
import com.orangeink.offlinechatapp.chat.presentation.messages.MessagesType
import com.orangeink.offlinechatapp.chat.presentation.messages.ui.components.MessagesBottomBar
import com.orangeink.offlinechatapp.chat.presentation.messages.ui.components.MessagesDateItem
import com.orangeink.offlinechatapp.chat.presentation.messages.ui.components.MessagesItem
import com.orangeink.offlinechatapp.chat.presentation.messages.ui.components.MessagesTopBar
import com.orangeink.offlinechatapp.core.database.entity.User
import com.orangeink.offlinechatapp.core.design.components.EmptyContent
import com.orangeink.offlinechatapp.core.util.formatChatDate
import com.orangeink.offlinechatapp.core.util.getKeyboardHeight

@Composable
fun MessagesScreen(
    navController: NavController,
    messagesState: MessagesState,
    onEchoMessage: () -> Unit,
    onSendMessage: (body: String?, mediaPath: String?) -> Unit
) {
    val lazyColumnState = rememberLazyListState()
    val isKeyboardOpen by rememberIsKeyboardOpen()

    Scaffold(
        topBar = {
            MessagesTopBar(
                navController,
                messagesState.toUser?.username ?: "",
                onEchoMessage
            )
        },
        bottomBar = { MessagesBottomBar(onSendMessage) }
    ) {
        if (messagesState.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            if (messagesState.messages.isEmpty()) {
                EmptyContent(modifier = Modifier.padding(it), desc = "Start the conversation!")
            } else {
                LaunchedEffect(messagesState.messages.size) {
                    lazyColumnState.animateScrollToItem(messagesState.messages.size - 1)
                }
                LaunchedEffect(isKeyboardOpen) {
                    lazyColumnState.scrollToItem(messagesState.messages.size - 1)
                }
                LazyColumn(
                    state = lazyColumnState,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(it),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
                    verticalArrangement = Arrangement.Bottom
                ) {
                    items(
                        count = messagesState.messages.size,
                        key = { index -> messagesState.messages[index].createdAt }
                    ) { i ->
                        val messageItem = messagesState.messages[i]
                        val chatDate = messageItem.createdAt.formatChatDate()
                        if (i == 0) {
                            MessagesDateItem(date = chatDate)
                        } else {
                            val previousMessageItem = messagesState.messages[i - 1]
                            val previousChatDate = previousMessageItem.createdAt.formatChatDate()

                            if (chatDate != previousChatDate) {
                                MessagesDateItem(date = chatDate)
                            }
                        }
                        MessagesItem(messageItem)
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }
    }
}

/**
 * Hacky solution for LazyColumn not scrolling up when paired with a TextField and keyboard opens.
 * Reference: https://github.com/litoco/Docs/tree/main/AndroidDocs/jetpack-compose/lazy-column/encountered-problems/lazy-column-items-hidden-by-keyboard
 */
@Composable
fun rememberIsKeyboardOpen(): State<Boolean> {
    val view = LocalView.current
    return produceState(initialValue = view.getKeyboardHeight()) {
        val viewTreeObserver = view.viewTreeObserver
        val listener = ViewTreeObserver.OnGlobalLayoutListener { value = view.getKeyboardHeight() }
        viewTreeObserver.addOnGlobalLayoutListener(listener)

        awaitDispose { viewTreeObserver.removeOnGlobalLayoutListener(listener) }
    }
}

@Preview(showBackground = true)
@Composable
fun MessagesScreenDataPreview() {
    MessagesScreen(
        rememberNavController(),
        MessagesState(
            isLoading = false,
            messages = listOf(
                MessagesItem(
                    body = "hey, hello? hey, hello? hey, hello?",
                    createdAt = "2024-02-01 17:30:24",
                    type = MessagesType.Received
                ),
                MessagesItem(
                    body = "hey, hello? hey, hello? hey, hello? hey, , hello? hey, hello?",
                    createdAt = "2024-02-01 17:30:25",
                    type = MessagesType.Sent
                ),
                MessagesItem(
                    body = "hey, hello? hey, hello? hey, hello?",
                    createdAt = "2024-02-01 17:30:26",
                    type = MessagesType.Sent
                ),
                MessagesItem(
                    body = "lorem ipsum text",
                    createdAt = "2024-02-01 17:30:27",
                    type = MessagesType.Received
                ),
            ),
            toUser = User("Robert", "1234")
        ), {}, { _, _ -> }
    )
}

@Preview(showBackground = true)
@Composable
fun MessagesScreenNoDataPreview() {
    MessagesScreen(
        rememberNavController(),
        MessagesState(
            isLoading = false,
            messages = emptyList(),
            toUser = User("Robert", "1234")
        ), {}, { _, _ -> }
    )
}