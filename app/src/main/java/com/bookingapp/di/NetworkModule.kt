package com.bookingapp.di

import com.google.ai.client.generativeai.GenerativeModel
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
        // In a real app, the API key would be fetched from local.properties or BuildConfig
        return GenerativeModel(
            modelName = "gemini-pro",
            apiKey = "YOUR_API_KEY_HERE"
        )
    }
}
