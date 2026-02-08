@file:OptIn(ExperimentalMaterial3Api::class)

package com.bookingapp.ui.screens.listing

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.ui.Alignment
import com.bookingapp.data.model.Listing
import androidx.compose.foundation.text.BasicText
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.bookingapp.ui.theme.BookingAppTheme

@Composable
fun ListingDetailScreen(
    listing: Listing,
    onBack: () -> Unit,
    onBookNow: () -> Unit
) {
    var selectedTab by remember { mutableStateOf(0) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(listing.name) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        bottomBar = {
            // Book Now button fixed at bottom
            Surface(shadowElevation = 8.dp) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        onClick = onBookNow,
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFB00020), contentColor = Color.White),
                        modifier = Modifier.fillMaxWidth(0.9f).height(52.dp)
                    ) {
                        Text(text = "Book Now")
                    }
                }
            }
        }
    ) { padding ->
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(padding)
        ) {
            TabRow(selectedTabIndex = selectedTab) {
                Tab(selected = selectedTab == 0, onClick = { selectedTab = 0 }) {
                    Text(text = "Map", modifier = Modifier.padding(12.dp))
                }
                Tab(selected = selectedTab == 1, onClick = { selectedTab = 1 }) {
                    Text(text = "Details", modifier = Modifier.padding(12.dp))
                }
            }

            when (selectedTab) {
                0 -> {
                    // Placeholder for Google Map - avoids adding Maps dependency here
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .height(280.dp), contentAlignment = Alignment.Center) {
                        Text(text = "Map placeholder for ${listing.location}")
                    }
                }
                1 -> {
                    Column(modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)) {
                        Text(text = listing.name, style = MaterialTheme.typography.titleLarge)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = "Hosted by ${listing.hostName}", style = MaterialTheme.typography.bodyMedium)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = listing.description, style = MaterialTheme.typography.bodyMedium)
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(text = "Category: ${listing.category}", style = MaterialTheme.typography.bodySmall)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = "Reviews: ${listing.reviewCount}", style = MaterialTheme.typography.bodySmall)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = "Price: $${listing.pricePerNight.toInt()} / night", style = MaterialTheme.typography.titleMedium)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ListingDetailPreview() {
    val sample = Listing(
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

    BookingAppTheme {
        ListingDetailScreen(listing = sample, onBack = {}, onBookNow = {})
    }
}
