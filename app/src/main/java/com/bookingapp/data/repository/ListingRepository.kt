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
        val filtered = getMockListings().filter { 
            it.name.contains(query, ignoreCase = true) || 
            it.location.contains(query, ignoreCase = true) ||
            it.category.contains(query, ignoreCase = true)
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
