package com.orangeink.offlinechatapp.auth.presentation

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import com.orangeink.offlinechatapp.auth.presentation.ui.AuthScreen
import com.orangeink.offlinechatapp.chat.presentation.ChatActivity
import com.orangeink.offlinechatapp.core.design.theme.OfflineChatAppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class AuthActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OfflineChatAppTheme {
                val viewModel: AuthViewModel = hiltViewModel()

                val scrollState = rememberScrollState()
                val snackBarHostState = remember { SnackbarHostState() }

                var isLoading by remember { mutableStateOf(false) }

                Scaffold(
                    snackbarHost = { SnackbarHost(snackBarHostState) },
                    containerColor = Color.White
                ) { contentPadding ->
                    LaunchedEffect(true) {
                        viewModel.eventFlow.collectLatest { event ->
                            when (event) {
                                is AuthViewModel.AuthUIEvent.Loading -> isLoading = true

                                is AuthViewModel.AuthUIEvent.Error -> {
                                    isLoading = false
                                    snackBarHostState.showSnackbar(
                                        message = event.message,
                                        duration = SnackbarDuration.Short
                                    )
                                }

                                is AuthViewModel.AuthUIEvent.NavigateToAllChatScreen -> {
                                    isLoading = false
                                    startActivity(
                                        Intent(this@AuthActivity, ChatActivity::class.java)
                                    )
                                    finish()
                                }
                            }
                        }
                    }

                    AuthScreen(
                        modifier = Modifier
                            .verticalScroll(scrollState)
                            .padding(contentPadding),
                        register = viewModel::registerUser,
                        login = viewModel::loginUser,
                        isLoading = isLoading
                    )
                }
            }
        }
    }
}