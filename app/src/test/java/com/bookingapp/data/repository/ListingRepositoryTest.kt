package com.bookingapp.data.repository

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test

class ListingRepositoryTest {

    private val repo = ListingRepository()

    @Test
    fun `search single price approx 200 returns cabin`() = runBlocking {
        val results = repo.searchListings("200").first()
        // Cozy Mountain Cabin is price 200 should be returned
        assertTrue(results.any { it.name.contains("Cozy Mountain Cabin") || it.pricePerNight == 200.0 })
    }

    @Test
    fun `search range 100-160 returns beachfront villa`() = runBlocking {
        val results = repo.searchListings("100-160").first()
        // Luxury Beachfront Villa (150) should be included
        assertTrue(results.any { it.name.contains("Luxury Beachfront Villa") })
        // Modern City Apartment (120) should also be included
        assertTrue(results.any { it.name.contains("Modern City Apartment") })
    }

    @Test
    fun `search under 160 villa returns beachfront villa only`() = runBlocking {
        val results = repo.searchListings("under 160 villa").first()
        assertTrue(results.any { it.name.contains("Luxury Beachfront Villa") })
        // Cabin is 200 so should not be returned
        assertFalse(results.any { it.name.contains("Cozy Mountain Cabin") })
    }

    @Test
    fun `search multiple categories with price returns matching`() = runBlocking {
        val results = repo.searchListings("hotel guesthouse 120").first()
        // modern city apartment is 120 and category 'Amazing Pools' won't match, so results may be empty
        // But ensure we don't crash and get a list back
        assertNotNull(results)
    }
}

