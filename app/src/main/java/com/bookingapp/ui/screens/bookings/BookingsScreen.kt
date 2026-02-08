package com.bookingapp.ui.screens.bookings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bookingapp.ui.theme.BookingAppTheme
import com.bookingapp.ui.theme.Success

// A comprehensive, modern Bookings screen with TopAppBar back navigation
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookingsScreen(
    onBack: () -> Unit = {},
    onViewDetails: (com.bookingapp.data.model.Listing) -> Unit = {}
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Your Bookings") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            Icons.Filled.ArrowBack,
                            contentDescription = "Back to Home",
                        )
                    }
                },
                actions = {
                    // Placeholder for filter/sort/menu
                    IconButton(onClick = { /* TODO: open filters */ }) {
                        Icon(Icons.Filled.FilterList, contentDescription = "Filter")
                    }
                }
            )
        }
    ) { padding ->
        BookingsContent(modifier = Modifier.padding(padding), onViewDetails = onViewDetails)
    }
}

@Composable
fun BookingsContent(modifier: Modifier = Modifier, onViewDetails: (com.bookingapp.data.model.Listing) -> Unit = {}) {
    var query by remember { mutableStateOf("") }
    var selectedFilter by remember { mutableStateOf("All") }

    val sampleBookings = remember {
        listOf(
            Booking("1", "Cozy Mountain Cabin", "June 20 - June 24, 2026", 320.0, "Confirmed"),
            Booking("2", "Seaside Bungalow", "July 10 - July 15, 2026", 560.0, "Pending"),
            Booking("3", "City Apartment", "August 1 - August 5, 2026", 280.0, "Cancelled")
        )
    }

    Column(modifier = modifier.fillMaxSize().padding(12.dp)) {
        // Search + filter row
        Row(verticalAlignment = Alignment.CenterVertically) {
            OutlinedTextField(
                value = query,
                onValueChange = { query = it },
                modifier = Modifier.weight(1f),
                placeholder = { Text("Search bookings") }
            )
            Spacer(modifier = Modifier.width(8.dp))
            FilterChip(selected = selectedFilter == "All", onClick = { selectedFilter = "All" }, label = { Text("All") })
            Spacer(modifier = Modifier.width(6.dp))
            FilterChip(selected = selectedFilter == "Upcoming", onClick = { selectedFilter = "Upcoming" }, label = { Text("Upcoming") })
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Bookings list
        LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            items(sampleBookings) { booking ->
                // convert Booking to Listing-like data to show detail; here we'll create a minimal Listing
                val listing = com.bookingapp.data.model.Listing(
                    id = booking.id,
                    name = booking.title,
                    location = "",
                    pricePerNight = booking.total,
                    rating = 4.5,
                    reviewCount = 0,
                    imageUrls = emptyList(),
                    category = "",
                    hostName = "",
                    description = ""
                )
                BookingCard(booking = booking, onDetails = { onViewDetails(listing) })
            }
        }
    }
}

@Composable
private fun BookingCard(booking: Booking, onDetails: () -> Unit = {}) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            // Image placeholder
            Box(modifier = Modifier
                .size(96.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color.LightGray), contentAlignment = Alignment.Center) {
                // could use CoilImage here for real images
                Text(text = "IMG", fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(booking.title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(4.dp))
                Text(booking.dates, style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.height(6.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("$${booking.total}", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.width(8.dp))
                    StatusBadge(booking.status)
                }
            }

            Spacer(modifier = Modifier.width(8.dp))

            Column(horizontalAlignment = Alignment.End) {
                Button(onClick = onDetails, shape = RoundedCornerShape(8.dp)) {
                    Text("Details")
                }
                Spacer(modifier = Modifier.height(8.dp))
                TextButton(onClick = { /* TODO: cancel/reschedule */ }) {
                    Text("Manage")
                }
            }
        }
    }
}

@Composable
fun StatusBadge(status: String) {
    val color = when (status) {
        "Confirmed" -> Success
        "Pending" -> Color(0xFFFFB400)
        "Cancelled" -> Color(0xFFE00B41)
        else -> Color.Gray
    }
    Surface(shape = RoundedCornerShape(8.dp), color = color.copy(alpha = 0.12f)) {
        Text(
            text = status,
            color = color,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            style = LocalTextStyle.current.copy(fontSize = 12.sp)
        )
    }
}

// Simple Booking data class for the screen
private data class Booking(
    val id: String,
    val title: String,
    val dates: String,
    val total: Double,
    val status: String
)

@Preview(showBackground = true)
@Composable
fun BookingsPreview() {
    BookingAppTheme {
        BookingsScreen(onBack = {})
    }
}
