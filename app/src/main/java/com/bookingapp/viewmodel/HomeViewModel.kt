package com.bookingapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bookingapp.data.model.Listing
import com.bookingapp.data.repository.ListingRepository
import com.bookingapp.data.remote.GeminiAgentManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val listingRepository: ListingRepository,
    private val geminiAgentManager: GeminiAgentManager
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<Listing>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<Listing>>> = _uiState.asStateFlow()

    private val _geminiState = MutableStateFlow<UiState<String>>(UiState.Idle)
    val geminiState: StateFlow<UiState<String>> = _geminiState.asStateFlow()

    init {
        fetchListings()
    }

    fun fetchListings() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            listingRepository.getListings()
                .catch { e -> _uiState.value = UiState.Error(e.message ?: "Unknown error") }
                .collect { listings ->
                    _uiState.value = UiState.Success(listings)
                }
        }
    }

    fun searchWithGemini(query: String) {
        viewModelScope.launch {
            _geminiState.value = UiState.Loading
            geminiAgentManager.searchProperties(query)
                .catch { e -> _geminiState.value = UiState.Error(e.message ?: "AI error") }
                .collect { response ->
                    _geminiState.value = UiState.Success(response)
                    // Optionally filter listings based on AI response
                    // In a real app, Gemini would return structured data to filter the repository
                }
        }
    }
}
