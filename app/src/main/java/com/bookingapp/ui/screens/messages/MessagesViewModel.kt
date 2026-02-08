package com.bookingapp.ui.screens.messages

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bookingapp.data.remote.ChatMessage
import com.bookingapp.data.remote.GeminiAgentManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MessagesViewModel @Inject constructor(
    private val geminiAgentManager: GeminiAgentManager
) : ViewModel() {

    private val _messages = MutableStateFlow<List<ChatMessage>>(emptyList())
    val messages: StateFlow<List<ChatMessage>> = _messages.asStateFlow()

    private val _isAssistantTyping = MutableStateFlow(false)
    val isAssistantTyping: StateFlow<Boolean> = _isAssistantTyping.asStateFlow()

    fun sendMessage(text: String) {
        if (text.isBlank()) return

        val userMsg = ChatMessage(text = text, isUser = true)
        _messages.value = _messages.value + userMsg

        viewModelScope.launch {
            _isAssistantTyping.value = true
            try {
                val response = geminiAgentManager.getChatResponse(text, _messages.value)
                val assistantMsg = ChatMessage(text = response, isUser = false)
                _messages.value = _messages.value + assistantMsg
            } catch (e: Exception) {
                val errMsg = ChatMessage(text = "Sorry, I couldn't get a response. ${e.message}", isUser = false)
                _messages.value = _messages.value + errMsg
            } finally {
                _isAssistantTyping.value = false
            }
        }
    }

    fun clearConversation() {
        _messages.value = emptyList()
    }
}

