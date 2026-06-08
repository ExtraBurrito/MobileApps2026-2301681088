package com.example.mobileapps2025.data.network

import com.example.mobileapps2025.data.local.ArtistEntity
data class ArtistNetworkModel(
    // Set default values ("") so that Firebase can automatically create this object
    val id: String = "",
    val name: String = "",
    val genre: String = "",
    val imageUrl: String = "",
    val descriptionEn: String = "",
    val descriptionRu: String = "",
    val descriptionBg: String = "",
    val concertCities: String = ""
) {
    // function to convert data from the Internet into the format of our local Room database
    fun toEntity(): ArtistEntity {
        return ArtistEntity(
            id = id,
            name = name,
            genre = genre,
            imageUrl = imageUrl,
            descriptionEn = descriptionEn,
            descriptionRu = descriptionRu,
            descriptionBg = descriptionBg,
            concertCities = concertCities
        )
    }
}