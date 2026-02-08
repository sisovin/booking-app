@file:OptIn(ExperimentalMaterial3Api::class)

package com.bookingapp.ui.screens.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Payment
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Help
import androidx.compose.material.icons.filled.Book
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bookingapp.R
import com.bookingapp.ui.theme.BookingAppTheme
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ProfileScreen(
    userName: String = "John Doe",
    email: String = "john@example.com",
    onEditProfile: () -> Unit = {},
    onBack: () -> Unit = {},
    onManagePayment: () -> Unit = {},
    onViewBookings: () -> Unit = {},
    onSupport: () -> Unit = {},
    onLogout: () -> Unit = {}
) {
    // Local UI states
    var notificationsEnabled by remember { mutableStateOf(true) }
    var darkModeEnabled by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Profile") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back to Home")
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                // Header
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_profile_placeholder),
                        contentDescription = stringResource(id = R.string.profile_image_description),
                        modifier = Modifier
                            .size(88.dp)
                            .clip(CircleShape)
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(text = userName, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.SemiBold)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = email, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)

                        Spacer(modifier = Modifier.height(8.dp))

                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            OutlinedButton(onClick = onEditProfile) {
                                Icon(Icons.Default.Edit, contentDescription = "Edit profile")
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(text = "Edit Profile")
                            }
                            TextButton(onClick = onViewBookings) {
                                Icon(Icons.Default.Book, contentDescription = "My bookings")
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(text = "My Bookings")
                            }
                        }
                    }
                }
            }

            item {
                // Quick stats
                Surface(
                    tonalElevation = 2.dp,
                    shape = MaterialTheme.shapes.medium,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(modifier = Modifier.padding(12.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(text = "24", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                            Text(text = "Bookings", style = MaterialTheme.typography.bodySmall)
                        }
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(text = "4.9", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                            Text(text = "Rating", style = MaterialTheme.typography.bodySmall)
                        }
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(text = "12", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                            Text(text = "Reviews", style = MaterialTheme.typography.bodySmall)
                        }
                    }
                }
            }

            item {
                // Sections header
                Text(text = "Account", style = MaterialTheme.typography.titleMedium)
            }

            item {
                // Manage account
                ListItem(
                    headlineContent = { Text(text = "Manage Account") },
                    supportingContent = { Text(text = "Name, email, phone and preferences") },
                    leadingContent = { Icon(Icons.Default.Person, contentDescription = null) },
                    trailingContent = { TextButton(onClick = onEditProfile) { Text("Manage") } },
                    modifier = Modifier.clickable { onEditProfile() }
                )
            }

            item {
                ListItem(
                    headlineContent = { Text(text = "Payment Methods") },
                    supportingContent = { Text(text = "Add or remove cards, view payment history") },
                    leadingContent = { Icon(Icons.Default.Payment, contentDescription = null) },
                    trailingContent = { TextButton(onClick = onManagePayment) { Text("Manage") } },
                    modifier = Modifier.clickable { onManagePayment() }
                )
            }

            item {
                Text(text = "Preferences", style = MaterialTheme.typography.titleMedium)
            }

            item {
                // Notifications toggle
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(text = "Notifications", style = MaterialTheme.typography.bodyLarge)
                        Text(text = "Receive app & email notifications", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                    Switch(checked = notificationsEnabled, onCheckedChange = { notificationsEnabled = it })
                }
            }

            item {
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(text = "Dark Mode", style = MaterialTheme.typography.bodyLarge)
                        Text(text = "Use dark theme across the app", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                    Switch(checked = darkModeEnabled, onCheckedChange = { darkModeEnabled = it })
                }
            }

            item {
                Text(text = "Support & Legal", style = MaterialTheme.typography.titleMedium)
            }

            item {
                ListItem(
                    headlineContent = { Text(text = "Help & Support") },
                    supportingContent = { Text(text = "FAQ, contact support, report an issue") },
                    leadingContent = { Icon(Icons.Default.Help, contentDescription = null) },
                    modifier = Modifier.clickable { onSupport() }
                )
            }

            item {
                ListItem(
                    headlineContent = { Text(text = "Terms & Privacy") },
                    supportingContent = { Text(text = "View terms of service and privacy policy") },
                    leadingContent = { Icon(Icons.Default.Settings, contentDescription = null) },
                    modifier = Modifier.clickable { /* open terms */ }
                )
            }

            item {
                Spacer(modifier = Modifier.height(12.dp))
                OutlinedButton(
                    onClick = onLogout,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 24.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.error)
                ) {
                    Icon(Icons.Default.Logout, contentDescription = "Logout")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Logout", color = MaterialTheme.colorScheme.error)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfilePreview() {
    BookingAppTheme {
        ProfileScreen()
    }
}
