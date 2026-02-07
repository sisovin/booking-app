package com.bookingapp.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Listing(
    val id: String,
    val name: String,
    val location: String,
    val pricePerNight: Double,
    val rating: Double,
    val reviewCount: Int,
    val imageUrls: List<String>,
    val category: String,
    val hostName: String,
    val description: String,
    val coordinates: Coordinates? = null
)

@Serializable
data class Coordinates(
    val latitude: Double,
    val longitude: Double
)
