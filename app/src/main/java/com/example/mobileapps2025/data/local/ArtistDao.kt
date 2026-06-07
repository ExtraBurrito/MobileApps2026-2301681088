package com.example.mobileapps2025.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ArtistDao {

    // === ARTISTS OPERATION ===

    // All artists
    @Query("SELECT * FROM artists")
    fun getAllArtists(): Flow<List<ArtistEntity>>

    // Search by NAME
    @Query("SELECT * FROM artists WHERE name LIKE '%' || :query || '%'")
    fun searchArtists(query: String): Flow<List<ArtistEntity>>

    // Genre filter
    @Query("SELECT * FROM artists WHERE genre = :genre")
    fun getArtistsByGenre(genre: String): Flow<List<ArtistEntity>>

    //Save artists list from NET
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArtists(artists: List<ArtistEntity>)

    // Clear cache
    @Query("DELETE FROM artists")
    suspend fun clearAllArtists()


    // === FAVORITES OPERATIONS ===

    // Add to fav
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFavorite(favorite: FavoriteEntity)

    // Delete from fav
    @Delete
    suspend fun removeFavorite(favorite: FavoriteEntity)

    // Check if fav exists
    @Query("SELECT artistId FROM favorites WHERE userId = :userId")
    fun getUserFavorites(userId: String): Flow<List<String>>

    // Clear fav
    @Query("DELETE FROM favorites WHERE userId = :userId")
    suspend fun clearUserFavorites(userId: String)

    // Sync with cloud
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoritesList(favorites: List<FavoriteEntity>)
}