package com.bookingapp.di

import com.google.ai.client.generativeai.GenerativeModel
import com.bookingapp.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideGenerativeModel(): GenerativeModel {
        // Use GEMINI_API_KEY from BuildConfig which reads the value from local.properties at build time
        val apiKey = if (BuildConfig.GEMINI_API_KEY.isNotBlank()) BuildConfig.GEMINI_API_KEY else "YOUR_API_KEY_HERE"
        return GenerativeModel(
            modelName = "gemini-pro",
            apiKey = apiKey
        )
    }
}
