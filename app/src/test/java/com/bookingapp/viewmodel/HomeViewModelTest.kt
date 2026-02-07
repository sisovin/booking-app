package com.bookingapp.viewmodel

import com.bookingapp.data.model.Listing
import com.bookingapp.data.repository.ListingRepository
import com.bookingapp.data.remote.GeminiAgentManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    @Mock
    private lateinit var listingRepository: ListingRepository
    @Mock
    private lateinit var geminiAgentManager: GeminiAgentManager

    private lateinit var viewModel: HomeViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
        
        `when`(listingRepository.getListings()).thenReturn(flowOf(emptyList()))
        viewModel = HomeViewModel(listingRepository, geminiAgentManager)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `searchWithGemini should update geminiState with success`() = runTest {
        val query = "Beach villa in Bali"
        val expectedResponse = "I found several beach villas in Bali for you."
        
        `when`(geminiAgentManager.searchProperties(query)).thenReturn(flowOf(expectedResponse))
        
        viewModel.searchWithGemini(query)
        testDispatcher.scheduler.advanceUntilIdle()
        
        val state = viewModel.geminiState.value
        assertTrue(state is UiState.Success && state.data == expectedResponse)
    }

    @Test
    fun `fetchListings should update uiState with listings`() = runTest {
        val mockListings = listOf(
            Listing("1", "Test Villa", "Bali", 100.0, 5.0, 10, emptyList(), "Beach", "Host", "Desc")
        )
        `when`(listingRepository.getListings()).thenReturn(flowOf(mockListings))
        
        viewModel.fetchListings()
        testDispatcher.scheduler.advanceUntilIdle()
        
        val state = viewModel.uiState.value
        assertTrue(state is UiState.Success && state.data == mockListings)
    }
}
