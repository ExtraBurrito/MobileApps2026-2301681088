package com.example.mobileapps2025.data.network

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FirestoreDataSource {
    private val db = FirebaseFirestore.getInstance()

    // 1. Download all artists from firestore collection "artists"
    suspend fun getAllArtists(): List<ArtistNetworkModel> {
        return try {
            // Wait database (.await())
            val snapshot = db.collection("artists").get().await()

            snapshot.documents.mapNotNull { doc ->
                val artist = doc.toObject(ArtistNetworkModel::class.java)
                // Give ID to all
                artist?.copy(id = doc.id)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList() // If there is no internet or an error occurs, we return an empty list.
        }
    }

    // === Saved Block ===

    // 2. Add to saved
    // Create package: users - user ID  - favorites - artist ID
    suspend fun addFavorite(userId: String, artistId: String): Result<Unit> {
        return try {
            val data = mapOf("artistId" to artistId, "timestamp" to System.currentTimeMillis())
            db.collection("users").document(userId)
                .collection("favorites").document(artistId)
                .set(data).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // 3. Delete from cloud saved
    suspend fun removeFavorite(userId: String, artistId: String): Result<Unit> {
        return try {
            db.collection("users").document(userId)
                .collection("favorites").document(artistId)
                .delete().await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // 4. Download saved
    suspend fun getUserFavorites(userId: String): List<String> {
        return try {
            val snapshot = db.collection("users").document(userId)
                .collection("favorites").get().await()
            snapshot.documents.map { it.id }
        } catch (e: Exception) {
            emptyList()
        }
    }
}