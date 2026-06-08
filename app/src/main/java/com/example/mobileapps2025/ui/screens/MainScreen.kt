package com.example.mobileapps2025.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mobileapps2025.data.local.ArtistEntity
import com.example.mobileapps2025.ui.viewmodel.ArtistViewModel
import com.example.mobileapps2025.ui.elements.ArtistCard

@Composable
fun MainScreen(
    currentLanguage: String,
    viewModel: ArtistViewModel,
    onArtistClick: (String) -> Unit
) {
    val artists by viewModel.artists.collectAsStateWithLifecycle()
    val favoriteIds by viewModel.favoriteIds.collectAsStateWithLifecycle()
    val searchQuery = viewModel.searchQuery

    MainScreenContent(
        currentLanguage = currentLanguage,
        searchQuery = searchQuery,
        onSearchQueryChanged = { viewModel.onSearchQueryChanged(it) },
        artists = artists,
        favoriteIds = favoriteIds,
        onToggleFavorite = { viewModel.toggleFavorite(it) },
        onArtistClick = onArtistClick
    )
}

@Composable
fun MainScreenContent(
    currentLanguage: String,
    searchQuery: String,
    onSearchQueryChanged: (String) -> Unit,
    artists: List<ArtistEntity>,
    favoriteIds: List<String>,
    onToggleFavorite: (String) -> Unit,
    onArtistClick: (String) -> Unit
) {
    val searchPlaceholder = when (currentLanguage) {
        "English" -> "Search artists..."
        "Български" -> "Търсене на изпълнители..."
        else -> "Поиск артистов..."
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 18.dp)
            .padding(top = 48.dp)
    ) {
        // Search Bar
        OutlinedTextField(
            value = searchQuery,
            onValueChange = onSearchQueryChanged,
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text(searchPlaceholder) },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
            shape = RoundedCornerShape(24.dp),
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = MaterialTheme.colorScheme.surfaceVariant,
                focusedBorderColor = MaterialTheme.colorScheme.primary
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Cards List
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(artists, key = { it.id }) { artist ->
                val isFavorite = favoriteIds.contains(artist.id)

                ArtistCard(
                    artist = artist,
                    currentLanguage = currentLanguage,
                    isFavorite = isFavorite,
                    onFavoriteClick = { onToggleFavorite(artist.id) },
                    onClick = { onArtistClick(artist.id) }
                )
            }
        }
    }
}

@Preview(showBackground = true, device = "id:pixel_6_pro", showSystemUi = true)
@Composable
fun MainScreenPreview() {
    val dummyArtists = listOf(
        ArtistEntity(
            id = "1",
            name = "Linkin Park",
            genre = "Rock",
            imageUrl = "",
            descriptionEn = "Legendary rock band.",
            descriptionRu = "Легендарная рок-группа.",
            descriptionBg = "Легендарна рок група.",
            concertCities = "London, New York, Sofia"
        ),
        ArtistEntity(
            id = "2",
            name = "Eminem",
            genre = "Hip-Hop",
            imageUrl = "",
            descriptionEn = "Rap God.",
            descriptionRu = "Бог рэпа.",
            descriptionBg = "Бог на рапа.",
            concertCities = "Detroit, Los Angeles"
        )
    )

    MaterialTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            MainScreenContent(
                currentLanguage = "English",
                searchQuery = "",
                onSearchQueryChanged = {},
                artists = dummyArtists,
                favoriteIds = listOf("1"),
                onToggleFavorite = {},
                onArtistClick = {}
            )
        }
    }
}
