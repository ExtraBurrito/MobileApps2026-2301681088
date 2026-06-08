package com.example.mobileapps2025.data

import com.example.mobileapps2025.data.local.ArtistDao
import com.example.mobileapps2025.data.local.FavoriteEntity
import com.example.mobileapps2025.data.network.FirestoreDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class ArtistRepository(
    private val artistDao: ArtistDao,
    private val firestoreDataSource: FirestoreDataSource,
    private val networkMonitor: NetworkMonitor
) {
    // Read from room
    fun getAllArtists(): Flow<List<com.example.mobileapps2025.data.local.ArtistEntity>> = artistDao.getAllArtists()
    fun searchArtists(query: String) = artistDao.searchArtists(query)
    fun getUserFavorites(userId: String) = artistDao.getUserFavorites(userId)

    // Sync
    suspend fun refreshArtists() {
        // Check internet connection
        if (networkMonitor.isConnected.first()) {
            val networkArtists = firestoreDataSource.getAllArtists()
            if (networkArtists.isNotEmpty()) {
                // Transform
                val entities = networkArtists.map { it.toEntity() }
                artistDao.insertArtists(entities)
            }
        }
    }

    // Sync Saved
    suspend fun syncUserFavorites(userId: String) {
        if (networkMonitor.isConnected.first()) {
            val favoriteIds = firestoreDataSource.getUserFavorites(userId)
            val favoriteEntities = favoriteIds.map { FavoriteEntity(userId, it) }

            // Clear old cache
            artistDao.clearUserFavorites(userId)
            if (favoriteEntities.isNotEmpty()) {
                artistDao.insertFavoritesList(favoriteEntities)
            }
        }
    }
    // Actions
    suspend fun toggleFavorite(userId: String, artistId: String, isCurrentlyFavorite: Boolean) {
        val favorite = FavoriteEntity(userId, artistId)

        // Update Room immediately for responsive UI
        if (isCurrentlyFavorite) {
            artistDao.removeFavorite(favorite)
            // Sync deletion to cloud
            firestoreDataSource.removeFavorite(userId, artistId)
        } else {
            artistDao.insertFavorite(favorite)
            // Sync addition to cloud
            firestoreDataSource.addFavorite(userId, artistId)
        }
    }
}