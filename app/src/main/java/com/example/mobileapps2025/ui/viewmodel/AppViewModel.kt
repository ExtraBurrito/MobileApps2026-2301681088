package com.example.mobileapps2025.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mobileapps2025.data.NetworkMonitor
import com.example.mobileapps2025.data.UserPreferencesRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


class AppViewModel (
    private val repository: UserPreferencesRepository,
    networkMonitor: NetworkMonitor
) : ViewModel(){

    // Ready status
    private val _isAppReady = MutableStateFlow(false)
    val isAppReady = _isAppReady.asStateFlow()

    // Internet Status
    val isOnline: StateFlow<Boolean> = networkMonitor.isConnected
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), true)

    // "Fast cache"
    val cachedAuthState: StateFlow<Boolean> = repository.isLoggedInFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    //Language
    val currentLanguage: StateFlow<String> = repository.currentLanguageFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "Русский")

    init {
        viewModelScope.launch {
            // Wait to loading cache from DataStore then remove the splash
            repository.isLoggedInFlow.first()
            _isAppReady.value = true
        }
        FirebaseAuth.getInstance().addAuthStateListener { auth ->
            val isReallyLoggedIn = auth.currentUser != null
            viewModelScope.launch {
                repository.saveAuthCache(isReallyLoggedIn)
            }
        }
    }
    //Language changing function
    fun changeLanguage(newLanguage: String) {
        viewModelScope.launch { repository.saveLanguage(newLanguage) }
    }
    companion object {
        fun provideFactory(
            repository: UserPreferencesRepository,
            networkMonitor: NetworkMonitor
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return AppViewModel(repository, networkMonitor) as T
            }
        }
    }
}