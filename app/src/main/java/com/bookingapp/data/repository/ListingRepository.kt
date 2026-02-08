package com.bookingapp.data.repository

import com.bookingapp.data.model.Listing
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ListingRepository @Inject constructor() {

    fun getListings(): Flow<List<Listing>> = flow {
        // Simulate network delay
        delay(1500)
        emit(getMockListings())
    }

    fun searchListings(query: String): Flow<List<Listing>> = flow {
        delay(1000)
        val q = query.trim().lowercase()

        // Extract numeric tokens (supports commas, decimals) and analyze whether they form a range
        val numberRegex = Regex("\\d+[.,]?\\d*")
        val numberMatches = numberRegex.findAll(q).map { it.value.replace(",", ".") }.toList()

        var priceMin: Double? = null
        var priceMax: Double? = null
        var priceSingle: Double? = null

        val hasRangeKeyword = q.contains("-") || q.contains(" to ") || q.contains(" to") || q.contains("to ") || q.contains("between")

        if (numberMatches.size >= 2 && hasRangeKeyword) {
            // Treat as explicit range with first two numbers
            val n1 = numberMatches[0].toDoubleOrNull()
            val n2 = numberMatches[1].toDoubleOrNull()
            if (n1 != null && n2 != null) {
                priceMin = minOf(n1, n2)
                priceMax = maxOf(n1, n2)
            }
        } else if (numberMatches.size >= 1) {
            // Single numeric token: determine mode from context
            priceSingle = numberMatches[0].toDoubleOrNull()
            // If there are multiple numeric tokens but no explicit range keyword, prefer the first as single value
        }

        // Also detect explicit comparators like "under 200", "<200", ">=150"
        val lessKeywords = listOf("under", "below", "<=", "< ")
        val greaterKeywords = listOf("over", "above", ">=", "> ")

        val requestedCategoriesKeywords = listOf("hotel", "guesthouse", "villa", "apartment", "cabin", "beachfront", "pool", "house")
        val requestedCategories = requestedCategoriesKeywords.filter { q.contains(it) }

        val filtered = getMockListings().filter { listing ->
            val textMatch = listing.name.contains(q, ignoreCase = true) ||
                    listing.location.contains(q, ignoreCase = true) ||
                    listing.category.contains(q, ignoreCase = true)

            val priceMatch = when {
                priceMin != null && priceMax != null -> listing.pricePerNight in priceMin..priceMax
                priceSingle != null -> {
                    val v = priceSingle
                    when {
                        lessKeywords.any { q.contains(it) } -> listing.pricePerNight <= v
                        greaterKeywords.any { q.contains(it) } -> listing.pricePerNight >= v
                        else -> {
                            // equal_or_close: within +/-20%
                            val lower = v * 0.8
                            val upper = v * 1.2
                            listing.pricePerNight in lower..upper || listing.pricePerNight == v
                        }
                    }
                }
                else -> true
            }

            val categoryMatch = if (requestedCategories.isNotEmpty()) {
                requestedCategories.any { rc ->
                    listing.category.contains(rc, ignoreCase = true) || listing.name.contains(rc, ignoreCase = true)
                }
            } else {
                true
            }

            val hasStructuredFilters = (priceMin != null && priceMax != null) || (priceSingle != null) || requestedCategories.isNotEmpty()
            if (hasStructuredFilters) {
                priceMatch && categoryMatch
            } else {
                textMatch
            }
        }

        emit(filtered)
    }

    private fun getMockListings() = listOf(
        Listing(
            id = "1",
            name = "Luxury Beachfront Villa",
            location = "Bali, Indonesia",
            pricePerNight = 150.0,
            rating = 4.95,
            reviewCount = 127,
            imageUrls = listOf("https://images.unsplash.com/photo-1518780664697-55e3ad937233"),
            category = "Beachfront",
            hostName = "John",
            description = "Experience luxury beachfront living in this stunning villa."
        ),
        Listing(
            id = "2",
            name = "Cozy Mountain Cabin",
            location = "Aspen, USA",
            pricePerNight = 200.0,
            rating = 4.88,
            reviewCount = 85,
            imageUrls = listOf("https://images.unsplash.com/photo-1542718610-a1d656d11844"),
            category = "Cabins",
            hostName = "Sarah",
            description = "A cozy cabin perfect for winter getaways."
        ),
        Listing(
            id = "3",
            name = "Modern City Apartment",
            location = "Tokyo, Japan",
            pricePerNight = 120.0,
            rating = 4.92,
            reviewCount = 56,
            imageUrls = listOf("https://images.unsplash.com/photo-1502672260266-1c1ef2d93688"),
            category = "Amazing Pools",
            hostName = "Yuki",
            description = "Stay in the heart of Tokyo with all modern amenities."
        )
    )
}
