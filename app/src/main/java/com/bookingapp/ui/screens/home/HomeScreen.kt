@file:OptIn(ExperimentalMaterial3Api::class)

package com.bookingapp.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.bookingapp.R
import com.bookingapp.data.model.Listing
import com.bookingapp.ui.components.ConversationalSearchBar
import com.bookingapp.ui.components.ListingCard
import com.bookingapp.viewmodel.HomeViewModel
import com.bookingapp.viewmodel.UiState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.ui.tooling.preview.Preview
import com.bookingapp.ui.theme.BookingAppTheme
import com.bookingapp.ui.components.BottomNavBar
import com.bookingapp.ui.screens.listing.ListingDetailScreen

@Composable
fun TopBar(
    modifier: Modifier = Modifier,
    onBellClick: () -> Unit = {}
) {
    TopAppBar(
        modifier = modifier,
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.ic_logo),
                    contentDescription = stringResource(id = R.string.logo_description),
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.size(40.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("BookingApp", style = MaterialTheme.typography.headlineSmall)
            }
        },
        actions = {
            IconButton(onClick = onBellClick) {
                Icon(Icons.Filled.Notifications, contentDescription = stringResource(id = R.string.logo_description))
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    )
}

@Composable
fun GeminiResponseSection(state: UiState<String>) {
    when (state) {
        is UiState.Loading -> {
            Card(
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
            ) {
                Text(
                    text = "Gemini is thinking...",
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
        is UiState.Success -> {
            Card(
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "🤖 Gemini Assistant",
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.secondary
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = state.data,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
        else -> {}
    }
}

@Composable
fun HomeScreenContent(
    uiState: UiState<List<Listing>>,
    geminiState: UiState<String>,
    onSearch: (String) -> Unit,
    onBellClick: () -> Unit = {}
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedNav by remember { mutableStateOf("home") }

    // Selected listing state for showing detail overlay
    var selectedListing by remember { mutableStateOf<Listing?>(null) }

    Scaffold(
        topBar = {
            TopBar(onBellClick = onBellClick)
        },
        bottomBar = {
            BottomNavBar(selectedItemId = selectedNav, onItemSelected = { id ->
                selectedNav = id
                // TODO: wire navigation actions based on id
            })
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (selectedListing != null) {
                // Show detail screen overlay
                ListingDetailScreen(
                    listing = selectedListing!!,
                    onBack = { selectedListing = null },
                    onBookNow = { /* TODO: booking flow */ }
                )
            } else {
                when (uiState) {
                    is UiState.Loading -> {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    }
                    is UiState.Success -> {
                        val listings = uiState.data
                        LazyColumn(
                            contentPadding = PaddingValues(4.dp),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            item {
                                ConversationalSearchBar(
                                    query = searchQuery,
                                    onQueryChange = { searchQuery = it },
                                    onSearch = { query ->
                                        onSearch(query)
                                    }
                                )
                            }
                            item {
                                // Gemini Response Area (if active)
                                GeminiResponseSection(geminiState)
                            }
                            items(listings) { listing ->
                                ListingCard(
                                    listing = listing,
                                    onClick = { /* Navigate to detail */ },
                                    onViewClick = { selectedListing = listing }
                                )
                            }
                        }
                    }
                    is UiState.Error -> {
                        Text(
                            text = (uiState as UiState.Error).message,
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                    else -> {}
                }
            }
        }
    }
}

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val geminiState by viewModel.geminiState.collectAsState()

    HomeScreenContent(
        uiState = uiState,
        geminiState = geminiState,
        onSearch = { query -> viewModel.searchWithGemini(query) },
        onBellClick = { /* TODO: navigate to notifications */ }
    )
}

// Preview functions
@Preview(showBackground = true)
@Composable
fun TopBarPreview() {
    BookingAppTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            TopBar()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    val sampleListings = listOf(
        Listing(
            id = "1",
            name = "Preview Villa",
            location = "Bali",
            pricePerNight = 120.0,
            rating = 4.9,
            reviewCount = 42,
            imageUrls = emptyList(),
            category = "Beachfront",
            hostName = "Host",
            description = "A lovely place"
        )
    )

    BookingAppTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            HomeScreenContent(
                uiState = UiState.Success(sampleListings),
                geminiState = UiState.Idle,
                onSearch = {}
            )
        }
    }
}
