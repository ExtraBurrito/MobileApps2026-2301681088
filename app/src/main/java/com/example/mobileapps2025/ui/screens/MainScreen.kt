package com.example.mobileapps2025.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mobileapps2025.data.local.ArtistEntity
import com.example.mobileapps2025.ui.screens.tabs.*
import com.example.mobileapps2025.ui.viewmodel.ArtistViewModel

@Composable
fun MainScreen(
    currentLanguage: String,
    currentTheme: String,
    viewModel: ArtistViewModel,
    onThemeChange: (String) -> Unit,
    onLanguageChange: (String) -> Unit,
    onLogout: () -> Unit,
    onArtistClick: (String) -> Unit
) {
    val artists by viewModel.artists.collectAsStateWithLifecycle()
    val favoriteIds by viewModel.favoriteIds.collectAsStateWithLifecycle()
    val searchQuery = viewModel.searchQuery

    MainScreenContent(
        currentLanguage = currentLanguage,
        currentTheme = currentTheme,
        artists = artists,
        favoriteIds = favoriteIds,
        searchQuery = searchQuery,
        onSearchQueryChanged = { viewModel.onSearchQueryChanged(it) },
        onToggleFavorite = { viewModel.toggleFavorite(it) },
        onThemeChange = onThemeChange,
        onLanguageChange = onLanguageChange,
        onLogout = onLogout,
        onArtistClick = onArtistClick
    )
}

@Composable
fun MainScreenContent(
    currentLanguage: String,
    currentTheme: String,
    artists: List<ArtistEntity>,
    favoriteIds: List<String>,
    searchQuery: String,
    onSearchQueryChanged: (String) -> Unit,
    onToggleFavorite: (String) -> Unit,
    onThemeChange: (String) -> Unit,
    onLanguageChange: (String) -> Unit,
    onLogout: () -> Unit,
    onArtistClick: (String) -> Unit
) {
    var selectedTab by remember { mutableIntStateOf(0) }

    val tabHome = when (currentLanguage) {
        "English" -> "Home"
        "Български" -> "Начало"
        else -> "Главная"
    }
    val tabFavs = when (currentLanguage) {
        "English" -> "Favorites"
        "Български" -> "Избрани"
        else -> "Избранное"
    }
    val tabProfile = when (currentLanguage) {
        "English" -> "Profile"
        "Български" -> "Профил"
        else -> "Профиль"
    }
    val tabSettings = when (currentLanguage) {
        "English" -> "Settings"
        "Български" -> "Настройки"
        else -> "Настройки"
    }

    val tabs = listOf(
        tabHome,
        tabFavs,
        tabProfile,
        tabSettings
    )
    val icons = listOf(
        Icons.Filled.Home,
        Icons.Filled.Favorite,
        Icons.Filled.Person,
        Icons.Filled.Settings
    )

    Scaffold(
        bottomBar = {
            NavigationBar(containerColor = MaterialTheme.colorScheme.surfaceVariant) {
                tabs.forEachIndexed { index, title ->
                    NavigationBarItem(
                        icon = { Icon(icons[index], contentDescription = title) },
                        label = { Text(title) },
                        selected = selectedTab == index,
                        onClick = { selectedTab = index }
                    )
                }
            }
        }
    ) { innerPadding ->
        // innerPadding protects our content from going under the navigation bar
        Box(modifier = Modifier.padding(innerPadding).fillMaxSize()) {
            when (selectedTab) {
                0 -> HomeTab(
                    currentLanguage = currentLanguage,
                    searchQuery = searchQuery,
                    onSearchQueryChanged = onSearchQueryChanged,
                    artists = artists,
                    favoriteIds = favoriteIds,
                    onToggleFavorite = onToggleFavorite,
                    onArtistClick = onArtistClick
                )
                1 -> FavoritesTab(
                    currentLanguage = currentLanguage,
                    artists = artists,
                    favoriteIds = favoriteIds,
                    onToggleFavorite = onToggleFavorite,
                    onArtistClick = onArtistClick
                )
                2 -> {
                    val currentUser =
                        com.google.firebase.auth.FirebaseAuth.getInstance().currentUser
                    val actualUsername = currentUser?.displayName ?: "Guest"
                    val actualEmail = currentUser?.email ?: "No email"
                    ProfileTab(

                        currentLanguage = currentLanguage,
                        favoriteCount = favoriteIds.size,
                        onLogout = onLogout,
                        username = actualUsername,
                        email = actualEmail,
                    )
                }
                3 -> SettingsTab(
                    currentLanguage = currentLanguage,
                    currentTheme = currentTheme,
                    onThemeChange = onThemeChange,
                    onLanguageChange = onLanguageChange
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MaterialTheme {
        MainScreenContent(
            currentLanguage = "English",
            currentTheme = "Light",
            artists = emptyList(),
            favoriteIds = emptyList(),
            searchQuery = "",
            onSearchQueryChanged = {},
            onToggleFavorite = {},
            onThemeChange = {},
            onLanguageChange = {},
            onLogout = {},
            onArtistClick = {}
        )
    }
}

