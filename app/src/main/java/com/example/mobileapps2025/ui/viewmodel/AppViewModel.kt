package com.example.mobileapps2025.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mobileapps2025.data.UserPreferencesRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow


class AppViewModel (private val repository: UserPreferencesRepository) : ViewModel(){

    //Reading language from DataStore and turning it into StateFlow
    val currentLanguage: StateFlow<String> = repository.currentLanguageFlow.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = "English"
    )
    private val _authState = MutableStateFlow<Boolean?>(null)
    val authState: StateFlow<Boolean?> = _authState.asStateFlow()

    init {
        // Connect Firebase Listener
        FirebaseAuth.getInstance().addAuthStateListener { auth ->
            // Once Firebase wakes up and checks the cache, it changes this value.
            _authState.value = auth.currentUser != null
        }
    }
    //Language changing function
    fun changeLanguage(newLanguage: String) {
        viewModelScope.launch {
            repository.saveLanguage(newLanguage)
        }
    }
    companion object {
        fun provideFactory(repository: UserPreferencesRepository): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return AppViewModel(repository) as T
            }
        }
    }
}