package com.bookingapp.data.remote

import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GeminiAgentManager @Inject constructor(
    private val generativeModel: GenerativeModel
) {
    /**
     * Sends a natural language query to Gemini and returns a stream of responses.
     */
    fun searchProperties(query: String): Flow<String> = flow {
        val prompt = """
            You are a helpful travel assistant for a booking app. 
            The user is searching for properties with the following query: "$query"
            Extract the following information if available: location, property type, price range, and special features.
            Format your response as a friendly conversational message, and include a summary of the filters you've identified.
            Also, provide a few recommendations based on the data provided in the context of the app.
        """.trimIndent()

        val response = generativeModel.generateContent(prompt)
        response.text?.let { emit(it) } ?: emit("I'm sorry, I couldn't process that request.")
    }

    /**
     * Handles multi-turn chat interactions.
     */
    suspend fun getChatResponse(userMessage: String, history: List<ChatMessage>): String {
        val chat = generativeModel.startChat(history.map { 
            content(role = if (it.isUser) "user" else "model") { text(it.text) }
        })
        val response = chat.sendMessage(userMessage)
        return response.text ?: "I'm having trouble thinking right now."
    }
}

data class ChatMessage(
    val text: String,
    val isUser: Boolean,
    val timestamp: Long = System.currentTimeMillis()
)
