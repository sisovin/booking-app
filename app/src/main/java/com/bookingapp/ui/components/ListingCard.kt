package com.bookingapp.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.animation.core.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Scale
import com.bookingapp.data.model.Listing
import com.bookingapp.ui.theme.TextPrimary
import com.bookingapp.ui.theme.TextSecondary
import com.bookingapp.ui.theme.ShimmerBase
import com.bookingapp.ui.theme.ShimmerHighlight
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import com.bookingapp.R

// compose-shimmer imports
import com.valentinilk.shimmer.shimmer
import androidx.compose.foundation.background

@Composable
fun ListingCard(
    listing: Listing,
    onClick: () -> Unit,
    onViewClick: () -> Unit = onClick,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Box(modifier = Modifier.fillMaxWidth()) {
                val imageUrl = listing.imageUrls.firstOrNull().orEmpty()
                val painter = if (imageUrl.isBlank()) {
                    painterResource(id = R.drawable.ic_image_placeholder)
                } else {
                    rememberAsyncImagePainter(
                        model = ImageRequest.Builder(context)
                            .data(imageUrl)
                            .crossfade(true)
                            .scale(Scale.FILL)
                            .build()
                    )
                }

                val asyncState = (painter as? AsyncImagePainter)?.state

                if (asyncState is AsyncImagePainter.State.Loading) {
                    // show shimmer placeholder while loading, using compose-shimmer library
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1.4f)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Brush.linearGradient(listOf(ShimmerBase, ShimmerHighlight)))
                            .shimmer()
                    )
                } else {
                    androidx.compose.foundation.Image(
                        painter = painter,
                        contentDescription = "Image of ${listing.name}",
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1.4f)
                            .clip(RoundedCornerShape(12.dp)),
                        contentScale = ContentScale.Crop
                    )
                }

                IconButton(
                    onClick = { /* Toggle favorite */ },
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = "Favorite",
                        tint = Color.White.copy(alpha = 0.8f)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = listing.name,
                        style = MaterialTheme.typography.titleMedium,
                        color = TextPrimary,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1
                    )

                    Spacer(modifier = Modifier.height(2.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp),
                            tint = TextPrimary
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = listing.rating.toString(),
                            style = MaterialTheme.typography.bodyMedium,
                            color = TextPrimary
                        )
                    }

                    Text(
                        text = listing.location,
                        style = MaterialTheme.typography.bodyLarge,
                        color = TextSecondary,
                        modifier = Modifier.padding(top = 2.dp)
                    )

                    Text(
                        text = "$${listing.pricePerNight.toInt()} / night",
                        style = MaterialTheme.typography.bodyLarge,
                        color = TextPrimary,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = 4.dp, bottom = 4.dp)
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                // Green "View" button
                Button(
                    onClick = onViewClick,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D32), contentColor = Color.White),
                    modifier = Modifier
                        .height(40.dp)
                        .padding(start = 8.dp)
                ) {
                    Text(text = "View")
                }
            }
        }
    }
}

@androidx.compose.ui.tooling.preview.Preview(showBackground = true)
@Composable
fun ListingCardPreview() {
    com.bookingapp.ui.theme.BookingAppTheme {
        ListingCard(
            listing = Listing(
                id = "1",
                name = "Modern Beach Villa",
                location = "Seminyak, Bali",
                pricePerNight = 250.0,
                rating = 4.98,
                reviewCount = 45,
                imageUrls = listOf("https://images.unsplash.com/photo-1518780664697-55e3ad937233"),
                category = "Beachfront",
                hostName = "Wayan",
                description = "Luxury villa with private pool and ocean view."
            ),
            onClick = {},
            onViewClick = {}
        )
    }
}
