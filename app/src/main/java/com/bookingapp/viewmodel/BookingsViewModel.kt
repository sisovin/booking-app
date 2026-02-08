package com.bookingapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bookingapp.data.model.Listing
import com.bookingapp.data.repository.ListingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookingsViewModel @Inject constructor(
    private val repository: ListingRepository
) : ViewModel() {

    private val _bookings = MutableStateFlow<List<Listing>>(emptyList())
    val bookings: StateFlow<List<Listing>> = _bookings

    init {
        viewModelScope.launch {
            repository.getListings().collect { list ->
                _bookings.value = list
            }
        }
    }
}

