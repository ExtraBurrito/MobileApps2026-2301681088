package com.example.mobileapps2025.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mobileapps2025.data.UserPreferencesRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class AppViewModel (private val repository: UserPreferencesRepository) : ViewModel(){

    //Reading language from DataStore and turning it into StateFlow
    val currentLanguage: StateFlow<String> = repository.currentLanguageFlow.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = "Русский"
    )

    //TODO
    //Temporary stopper
    val isUserLoggedIn: Boolean
        get() {
            // For now, return false to open "WelcomeScreen"
            return false
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