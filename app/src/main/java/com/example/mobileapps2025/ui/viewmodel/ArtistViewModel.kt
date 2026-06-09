package com.example.mobileapps2025.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import com.example.mobileapps2025.data.ArtistRepository
import com.example.mobileapps2025.data.local.ArtistEntity

class ArtistViewModel(private val repository: ArtistRepository) : ViewModel() {

    // Auth State Flow
    private val userIdFlow: Flow<String> = callbackFlow {
        val listener = FirebaseAuth.AuthStateListener { auth ->
            trySend(auth.currentUser?.uid ?: "")
        }
        FirebaseAuth.getInstance().addAuthStateListener(listener)
        awaitClose { FirebaseAuth.getInstance().removeAuthStateListener(listener) }
    }

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
    @OptIn(ExperimentalCoroutinesApi::class)
    val favoriteIds: StateFlow<List<String>> =
        userIdFlow.flatMapLatest { userId ->
            repository.getUserFavorites(userId)
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

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