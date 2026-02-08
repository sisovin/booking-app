package com.bookingapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.bookingapp.ui.theme.Background
import com.bookingapp.ui.theme.Border
import com.bookingapp.ui.theme.TextSecondary

@Composable
fun ConversationalSearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onSearch: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .shadow(4.dp, CircleShape),
        shape = CircleShape,
        color = Background
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = "Search",
                tint = Color.Black
            )
            
            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 12.dp)
            ) {
                if (query.isEmpty()) {
                    Text(
                        text = "Where to? (e.g., 'Beach villa in Bali')",
                        style = MaterialTheme.typography.bodyLarge,
                        color = TextSecondary
                    )
                }
                BasicTextField(
                    value = query,
                    onValueChange = onQueryChange,
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = MaterialTheme.typography.bodyLarge,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                    keyboardActions = KeyboardActions(onSearch = { onSearch(query) }),
                    singleLine = true
                )
            }

            // Filter icon or Gemini sparkle could go here
            IconButton(onClick = { /* Open filters */ }) {
                Icon(
                    imageVector = Icons.Filled.Search, // Replace with sparkle/filter icon
                    contentDescription = "Filters",
                    tint = TextSecondary
                )
            }
        }
    }
}
