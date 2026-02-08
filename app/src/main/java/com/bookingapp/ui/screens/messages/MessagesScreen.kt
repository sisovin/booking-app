@file:OptIn(ExperimentalMaterial3Api::class)

package com.bookingapp.ui.screens.messages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ClearAll
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.ui.tooling.preview.Preview
import com.bookingapp.data.remote.ChatMessage
import com.bookingapp.ui.theme.GeminiPrimary
import com.bookingapp.ui.theme.BookingAppTheme

@Composable
fun MessagesScreen(
    viewModel: MessagesViewModel = hiltViewModel(),
    onBack: () -> Unit = {}
) {
    val messages by viewModel.messages.collectAsState()
    val isTyping by viewModel.isAssistantTyping.collectAsState()

    var input by remember { mutableStateOf(TextFieldValue()) }
    val focusManager = LocalFocusManager.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Assistant") },
                actions = {
                    IconButton(onClick = { viewModel.clearConversation() }) {
                        Icon(Icons.Filled.ClearAll, contentDescription = "Clear conversation")
                    }
                }
            )
        }
    ) { padding ->
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(padding)) {

            // Conversation list
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(messages) { msg ->
                    MessageBubble(msg)
                }

                if (isTyping) {
                    item { TypingIndicator() }
                }
            }

            // Input area
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                TextField(
                    value = input,
                    onValueChange = { input = it },
                    modifier = Modifier.weight(1f),
                    placeholder = { Text(text = "Ask the assistant...") },
                    singleLine = false,
                    maxLines = 4
                )
                Spacer(modifier = Modifier.width(8.dp))
                IconButton(onClick = {
                    val text = input.text.trim()
                    if (text.isNotEmpty()) {
                        viewModel.sendMessage(text)
                        input = TextFieldValue()
                        focusManager.clearFocus()
                    }
                }) {
                    Icon(Icons.Filled.Send, contentDescription = "Send", tint = GeminiPrimary)
                }
            }
        }
    }
}

@Composable
fun MessageBubble(message: ChatMessage) {
    val bg = if (message.isUser) MaterialTheme.colorScheme.primary else Color(0xFFF1F3F4)
    val txtColor = if (message.isUser) MaterialTheme.colorScheme.onPrimary else Color.Black

    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = if (message.isUser) Arrangement.End else Arrangement.Start) {
        Box(modifier = Modifier
            .widthIn(max = 280.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(bg)
            .padding(12.dp)) {
            Text(text = message.text, color = txtColor)
        }
    }
}

@Composable
fun TypingIndicator() {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start) {
        Surface(shape = RoundedCornerShape(12.dp), color = Color(0xFFECEFF1)) {
            Text(text = "Assistant is typing...", modifier = Modifier.padding(8.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MessagesPreview() {
    BookingAppTheme {
        val sample = listOf(
            ChatMessage("Hi there! How can I help you today?", isUser = false),
            ChatMessage("Show me beach villas in Bali", isUser = true),
            ChatMessage("Here are some top picks for Bali...", isUser = false)
        )
        Column(modifier = Modifier.fillMaxSize()) {
            LazyColumn(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(sample) { msg -> MessageBubble(msg) }
            }
            TypingIndicator()
        }
    }
}
