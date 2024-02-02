package com.orangeink.offlinechatapp.chat.presentation

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.orangeink.offlinechatapp.auth.presentation.AuthActivity
import com.orangeink.offlinechatapp.chat.presentation.chatHistory.ChatHistoryViewModel
import com.orangeink.offlinechatapp.chat.presentation.chatHistory.ui.ChatHistoryScreen
import com.orangeink.offlinechatapp.chat.presentation.messages.MessagesViewModel
import com.orangeink.offlinechatapp.chat.presentation.messages.ui.MessagesScreen
import com.orangeink.offlinechatapp.chat.presentation.navigation.ChatNavigation
import com.orangeink.offlinechatapp.core.design.theme.OfflineChatAppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import javax.inject.Inject

@AndroidEntryPoint
class ChatActivity : ComponentActivity() {

    @Inject
    lateinit var dataStore: DataStore<Preferences>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OfflineChatAppTheme {
                val navController = rememberNavController()
                var loggedInUserId: Int? = 0

                LaunchedEffect(true) {
                    loggedInUserId = dataStore.data.first()[intPreferencesKey("userId")]
                }

                NavHost(
                    navController = navController,
                    startDestination = ChatNavigation.ChatHistory.route
                ) {
                    composable(ChatNavigation.ChatHistory.route) {
                        val viewModel: ChatHistoryViewModel = hiltViewModel()
                        val chatHistoryState by viewModel.state.collectAsState()

                        ChatHistoryScreen(navController, chatHistoryState, loggedInUserId) {
                            viewModel.logout()
                            startActivity(Intent(this@ChatActivity, AuthActivity::class.java))
                            finish()
                        }
                    }
                    composable(
                        "${ChatNavigation.Messages.route}/{conversationId}&{toUserId}",
                        arguments = listOf(
                            navArgument("conversationId") {
                                type = NavType.IntType
                            },
                            navArgument("toUserId") {
                                type = NavType.IntType
                            }
                        )
                    ) { backStackEntry ->
                        val conversationId = backStackEntry.arguments?.getInt("conversationId")
                        val toUserId = backStackEntry.arguments?.getInt("toUserId")

                        val viewModel: MessagesViewModel = hiltViewModel()
                        viewModel.getAllMessages(conversationId, toUserId)
                        val messagesState by viewModel.state.collectAsState()

                        MessagesScreen(
                            navController,
                            messagesState,
                            viewModel::echoMessage,
                            viewModel::sendMessage
                        )
                    }
                }
            }
        }
    }
}
