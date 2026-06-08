package com.example.mobileapps2025.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import com.example.mobileapps2025.data.ArtistRepository
import com.example.mobileapps2025.data.local.ArtistEntity

class ArtistViewModel(private val repository: ArtistRepository) : ViewModel() {

    private val currentUserId: String?
        get() = FirebaseAuth.getInstance().currentUser?.uid

    // Search query state
    var searchQuery by mutableStateOf("")
        private set

    // Search query update
    fun onSearchQueryChanged(query: String) {
        searchQuery = query
    }

    // If searchQuery is empty -> get all artists.
    // If not empty -> search the database.
    @OptIn(ExperimentalCoroutinesApi::class)
    val artists: StateFlow<List<ArtistEntity>> =
        androidx.compose.runtime.snapshotFlow { searchQuery }
            .flatMapLatest { query ->
                if (query.isBlank()) repository.getAllArtists()
                else repository.searchArtists(query)
            }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // List of artist IDs added to favorites by the current user
    val favoriteIds: StateFlow<List<String>> =
        repository.getUserFavorites(currentUserId ?: "")
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    init {
        // Sync with network on initialization
        viewModelScope.launch {
            repository.refreshArtists()
            currentUserId?.let { userId ->
                repository.syncUserFavorites(userId)
            }
        }
    }

    fun toggleFavorite(artistId: String) {
        val userId = currentUserId ?: return
        val isFavorite = favoriteIds.value.contains(artistId)

        viewModelScope.launch {
            repository.toggleFavorite(userId, artistId, isFavorite)
        }
    }

    fun getArtistById(artistId: String): Flow<ArtistEntity?> {
        return repository.getArtistById(artistId)
    }

    // Factory to create ViewModel with repository
    companion object {
        fun provideFactory(repository: ArtistRepository): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return ArtistViewModel(repository) as T
                }
            }
    }
}