package com.example.mobileapps2025.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.example.mobileapps2025.data.local.ArtistEntity
import com.example.mobileapps2025.ui.viewmodel.ArtistViewModel

@Composable
fun DetailScreen(
    artistId: String,
    currentLanguage: String,
    viewModel: ArtistViewModel,
    onNavigateBack: () -> Unit
) {
    // Subscribe to a specific artist's data
    val artist by viewModel.getArtistById(artistId).collectAsStateWithLifecycle(initialValue = null)

    DetailScreenContent(
        artist = artist,
        currentLanguage = currentLanguage,
        onNavigateBack = onNavigateBack
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun DetailScreenContent(
    artist: ArtistEntity?,
    currentLanguage: String,
    onNavigateBack: () -> Unit
) {
    // Header translations
    val aboutTitle = when (currentLanguage) {
        "English" -> "About"
        "Български" -> "За изпълнителя"
        else -> "Об артисте"
    }
    val concertsTitle = when (currentLanguage) {
        "English" -> "Upcoming Concerts"
        "Български" -> "Предстоящи концерти"
        else -> "Ближайшие концерты"
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(artist?.name ?: "") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        }
    ) { paddingValues ->
        if (artist != null) {
            // Select the correct description based on the app language
            val localizedDescription = when (currentLanguage) {
                "English" -> artist.descriptionEn
                "Български" -> artist.descriptionBg
                else -> artist.descriptionRu
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .verticalScroll(rememberScrollState())
            ) {
                // Large poster image
                AsyncImage(
                    model = artist.imageUrl,
                    contentDescription = artist.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .padding(horizontal = 16.dp)
                        .clip(RoundedCornerShape(16.dp))
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Text information
                Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                    Text(
                        text = artist.name,
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Text(
                        text = artist.genre,
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Medium
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // Description
                    Text(
                        text = aboutTitle,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = localizedDescription,
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        lineHeight = 24.sp
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // Concert cities
                    Text(
                        text = concertsTitle,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    // Split the "London, Sofia" string into chips
                    val cities = artist.concertCities.split(",").map { it.trim() }

                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        cities.forEach { city ->
                            if (city.isNotEmpty()) {
                                SuggestionChip(
                                    onClick = { },
                                    label = { Text(city) }
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(32.dp))
                }
            }
        } else {
            // Show loading indicator
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
    }
}

@Preview(showBackground = true, device = "id:pixel_6_pro")
@Composable
fun DetailScreenPreview() {
    val dummyArtist = ArtistEntity(
        id = "1",
        name = "Linkin Park",
        genre = "Rock",
        imageUrl = "https://example.com/artist.jpg",
        descriptionEn = "Legendary rock band from California.",
        descriptionRu = "Легендарная рок-группа из Калифорнии.",
        descriptionBg = "Легендарна рок група от Калифорния.",
        concertCities = "London, Los Angeles, Sofia"
    )

    MaterialTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            DetailScreenContent(
                artist = dummyArtist,
                currentLanguage = "English",
                onNavigateBack = {}
            )
        }
    }
}
