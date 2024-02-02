package com.orangeink.offlinechatapp.chat.presentation.chatHistory

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.orangeink.offlinechatapp.chat.domain.ChatRepository
import com.orangeink.offlinechatapp.core.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatHistoryViewModel @Inject constructor(
    private val chatRepository: ChatRepository,
    private val dataStore: DataStore<Preferences>
) : ViewModel() {

    private val _state = MutableStateFlow(ChatHistoryState())
    val state = _state.asStateFlow()

    init {
        getChatHistory()
    }

    private fun getChatHistory() {
        viewModelScope.launch(Dispatchers.IO) {
            val userId = dataStore.data.first()[intPreferencesKey("userId")]
            chatRepository.getChatHistory(userId).onEach { result ->
                when (result) {
                    Result.Loading -> _state.update { it.copy(isLoading = true) }
                    is Result.Error -> Unit
                    is Result.Success -> _state.update {
                        it.copy(
                            isLoading = false,
                            chatHistory = result.data
                        )
                    }
                }
            }.launchIn(this)
        }
    }

    fun logout() {
        viewModelScope.launch {
            dataStore.edit { it.clear() }
        }
    }

}