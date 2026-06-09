package com.example.mobileapps2025.ui.screens.tabs

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobileapps2025.data.local.ArtistEntity
import com.example.mobileapps2025.ui.elements.ArtistCard

@Composable
fun FavoritesTab(
    currentLanguage: String,
    artists: List<ArtistEntity>,
    favoriteIds: List<String>,
    onToggleFavorite: (String) -> Unit,
    onArtistClick: (String) -> Unit
) {
    // Keep only artists that are in the favorite IDs list
    val favoriteArtists = artists.filter { favoriteIds.contains(it.id) }

    val title = when (currentLanguage) {
        "English" -> "Favorites"
        "Български" -> "Избрани"
        else -> "Избранное"
    }
    val emptyText = when (currentLanguage) {
        "English" -> "No favorites yet"
        "Български" -> "Още няма добавени"
        else -> "Вы ещё ничего не сохраняли"
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 18.dp)
            .padding(top = 36.dp)
    ) {
        Text(
            text = title,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 16.dp, bottom = 36.dp)
        )

        if (favoriteArtists.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = emptyText, fontSize = 18.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(18.dp)) {
                items(favoriteArtists, key = { it.id }) { artist ->
                    ArtistCard(
                        artist = artist,
                        currentLanguage = currentLanguage,
                        isFavorite = true,
                        onFavoriteClick = { onToggleFavorite(artist.id) },
                        onClick = { onArtistClick(artist.id) }
                    )
                }
                item { Spacer(modifier = Modifier.height(18.dp)) }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FavoritesTabPreview() {
    val dummyArtists = listOf(
        ArtistEntity(
            id = "1",
            name = "Linkin Park",
            genre = "Rock",
            imageUrl = "",
            descriptionEn = "Legendary rock band.",
            descriptionRu = "Легендарная рок-группа.",
            descriptionBg = "Легендарна рок група.",
            concertCities = "London, Sofia"
        ),
                ArtistEntity(
                id = "2",
        name = "John doe",
        genre = "Rock",
        imageUrl = "",
        descriptionEn = "Legendary rock band.",
        descriptionRu = "Легендарная рок-группа.",
        descriptionBg = "Легендарна рок група.",
        concertCities = "London, Sofia"
        )
    )

    MaterialTheme {
        FavoritesTab(
            currentLanguage = "English",
            artists = dummyArtists,
            favoriteIds = listOf("1" , "2"),
            onToggleFavorite = {},
            onArtistClick = {}
        )
    }
}
