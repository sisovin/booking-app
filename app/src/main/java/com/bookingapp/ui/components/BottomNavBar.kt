package com.bookingapp.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bookingapp.R
import com.bookingapp.ui.theme.BookingAppTheme

private data class BottomNavItem(val id: String, val titleRes: Int, val icon: androidx.compose.ui.graphics.vector.ImageVector)

@Composable
fun BottomNavBar(
    modifier: Modifier = Modifier,
    selectedItemId: String? = null,
    onItemSelected: (String) -> Unit = {}
) {
    val items = listOf(
        BottomNavItem("home", R.string.nav_home, Icons.Filled.Home),
        BottomNavItem("search", R.string.nav_search, Icons.Filled.Search),
        BottomNavItem("bookings", R.string.nav_bookings, Icons.Filled.CalendarToday),
        BottomNavItem("messages", R.string.nav_messages, Icons.Filled.Chat),
        BottomNavItem("profile", R.string.nav_profile, Icons.Filled.Person)
    )

    var current by remember { mutableStateOf(selectedItemId ?: "home") }

    NavigationBar(modifier = modifier.padding(0.dp)) {
        items.forEach { item ->
            NavigationBarItem(
                selected = (item.id == current),
                onClick = {
                    current = item.id
                    onItemSelected(item.id)
                },
                icon = {
                    Icon(item.icon, contentDescription = stringResource(id = item.titleRes))
                },
                label = { Text(text = stringResource(id = item.titleRes)) }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BottomNavBarPreview() {
    BookingAppTheme {
        BottomNavBar()
    }
}
