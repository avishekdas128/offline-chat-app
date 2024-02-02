package com.orangeink.offlinechatapp.auth.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.orangeink.offlinechatapp.core.database.entity.User
import com.orangeink.offlinechatapp.auth.domain.AuthRepository
import com.orangeink.offlinechatapp.core.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    sealed class AuthUIEvent {
        data class Error(val message: String) : AuthUIEvent()
        data object Loading : AuthUIEvent()
        data object NavigateToAllChatScreen : AuthUIEvent()
    }

    private val _eventFlow = MutableSharedFlow<AuthUIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun registerUser(username: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            if (validate(username, password)) {
                authRepository.registerUser(User(username, password)).onEach { result ->
                    when (result) {
                        Result.Loading -> _eventFlow.emit(AuthUIEvent.Loading)
                        is Result.Error -> _eventFlow.emit(
                            AuthUIEvent.Error(result.message ?: "Something went wrong!")
                        )

                        is Result.Success -> _eventFlow.emit(AuthUIEvent.NavigateToAllChatScreen)
                    }
                }.launchIn(this)
            }
        }
    }

    fun loginUser(username: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            if (validate(username, password)) {
                authRepository.loginUser(User(username, password)).onEach { result ->
                    when (result) {
                        Result.Loading -> _eventFlow.emit(AuthUIEvent.Loading)
                        is Result.Error -> _eventFlow.emit(
                            AuthUIEvent.Error(result.message ?: "Something went wrong!")
                        )

                        is Result.Success -> _eventFlow.emit(AuthUIEvent.NavigateToAllChatScreen)
                    }
                }.launchIn(this)
            }
        }
    }

    private suspend fun validate(username: String, password: String): Boolean {
        if (username.isEmpty()) {
            _eventFlow.emit(AuthUIEvent.Error("Username cannot be empty!"))
            return false
        }
        if (password.isEmpty()) {
            _eventFlow.emit(AuthUIEvent.Error("Password cannot be empty!"))
            return false
        }
        return true
    }
}