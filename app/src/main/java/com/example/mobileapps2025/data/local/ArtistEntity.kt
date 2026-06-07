package com.example.mobileapps2025.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "artists")
data class ArtistEntity(
    @PrimaryKey val id: String,
    val name: String,
    val genre: String,
    val imageUrl: String,
    val descriptionEn: String,
    val descriptionRu: String,
    val descriptionBg: String,
    val concertCities: String
)