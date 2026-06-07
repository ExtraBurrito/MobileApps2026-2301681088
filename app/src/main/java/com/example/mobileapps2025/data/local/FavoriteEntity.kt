package com.example.mobileapps2025.data.local

import androidx.room.Entity

@Entity(tableName = "favorites", primaryKeys = ["userId", "artistId"])
data class FavoriteEntity(
    val userId: String,
    val artistId: String
)