package com.example.mobileapps2025.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import com.example.mobileapps2025.data.AuthRepository

class AuthViewModel : ViewModel(){
    private val repository = AuthRepository()

    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    fun signUp(email: String, password: String, username: String, onSuccess: () -> Unit) {
        if (email.isBlank() || password.isBlank() || username.isBlank()) {
            errorMessage = "Fields cannot be empty"
            return
        }
        if (password.length < 6) {
            errorMessage = "Password must be at least 6 characters"
            return
        }

        viewModelScope.launch {
            isLoading = true
            errorMessage = null

            val result = repository.signUp(email, password, username)
            isLoading = false

            if (result.isSuccess) {
                onSuccess()
            } else {
                errorMessage = result.exceptionOrNull()?.message ?: "Sign up failed"
            }
            isLoading = false
        }



    }
}