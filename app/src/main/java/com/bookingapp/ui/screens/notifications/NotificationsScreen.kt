package com.bookingapp.ui.screens.notifications

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ClearAll
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bookingapp.ui.theme.BookingAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationsScreen(
    onBack: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Notifications") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back to Home")
                    }
                },
                actions = {
                    IconButton(onClick = { /* TODO: clear all */ }) {
                        Icon(Icons.Filled.ClearAll, contentDescription = "Clear all")
                    }
                }
            )
        }
    ) { padding ->
        NotificationsContent(Modifier.padding(padding))
    }
}

@Composable
fun NotificationsContent(modifier: Modifier = Modifier) {
    // Sample notifications
    val sample = remember {
        mutableStateListOf(
            NotificationItem("Booking confirmed", "Your booking at Cozy Mountain Cabin is confirmed.", false),
            NotificationItem("Price drop", "Seaside Bungalow dropped 20% for your dates.", false),
            NotificationItem("New message", "Host Sarah sent you a message.", true)
        )
    }

    Column(modifier = modifier.fillMaxSize().padding(12.dp)) {
        if (sample.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("No notifications", color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            return@Column
        }

        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(sample) { item ->
                NotificationRow(item = item)
            }
        }
    }
}

@Composable
fun NotificationRow(item: NotificationItem) {
    Card(shape = RoundedCornerShape(10.dp), modifier = Modifier.fillMaxWidth()) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .clickable { /* open related screen */ }
            .padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = Icons.Filled.Notifications, contentDescription = null, tint = if (item.unread) MaterialTheme.colorScheme.primary else Color.Gray)
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(item.title, fontWeight = if (item.unread) FontWeight.Bold else FontWeight.Normal)
                Spacer(modifier = Modifier.height(4.dp))
                Text(item.text, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            if (item.unread) {
                TextButton(onClick = { /* mark read */ }) { Text("Mark read") }
            }
        }
    }
}

data class NotificationItem(val title: String, val text: String, val unread: Boolean)

@Preview(showBackground = true)
@Composable
fun NotificationsPreview() {
    BookingAppTheme {
        NotificationsScreen(onBack = {})
    }
}
