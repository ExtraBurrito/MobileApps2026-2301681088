package com.example.mobileapps2025.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobileapps2025.data.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {
    private val repository = AuthRepository()

    private val _authState = MutableStateFlow<Boolean?>(null)
    val authState: StateFlow<Boolean?> = _authState.asStateFlow()

    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    private val authListener = FirebaseAuth.AuthStateListener { auth ->
        _authState.value = auth.currentUser != null
    }

    init {
        FirebaseAuth.getInstance().addAuthStateListener(authListener)
    }

    override fun onCleared() {
        super.onCleared()
        FirebaseAuth.getInstance().removeAuthStateListener(authListener)
    }

    fun signUp(email: String, password: String, username: String, language: String, onSuccess: () -> Unit) {
        if (email.isBlank() || password.isBlank() || username.isBlank()) {
            errorMessage = when(language) {
                "English" -> "Fill in all fields"
                "Български" -> "Попълнете всички полета"
                else -> "Заполните все поля"
            }
            return
        }

        viewModelScope.launch {
            isLoading = true
            errorMessage = null

            try {
                val result = repository.signUp(email, password, username)
                if (result.isSuccess) {
                    onSuccess()
                } else {
                    errorMessage = getLocalizedErrorMessage(result.exceptionOrNull(), language)
                }
            } finally {
                isLoading = false
            }
        }
    }

    fun signIn(email: String, password: String, language: String, onSuccess: () -> Unit) {
        if (email.isBlank() || password.isBlank()) {
            errorMessage = when(language) {
                "English" -> "Fill in all fields"
                "Български" -> "Попълнете всички полета"
                else -> "Заполните все поля"
            }
            return
        }
        viewModelScope.launch {
            isLoading = true
            errorMessage = null

            try {
                val result = repository.signIn(email, password)
                if (result.isSuccess) {
                    onSuccess()
                } else {
                    errorMessage = getLocalizedErrorMessage(result.exceptionOrNull(), language)
                }
            } finally {
                isLoading = false
            }
        }
    }

    fun resetPassword(email: String, language: String, onSuccess: () -> Unit) {
        if (email.isBlank()) {
            errorMessage = when(language) {
                "English" -> "Enter your email"
                "Български" -> "Въведете вашия имейл"
                else -> "Введите email"
            }
            return
        }

        viewModelScope.launch {
            isLoading = true
            errorMessage = null

            try {
                val result = repository.resetPassword(email)
                if (result.isSuccess) {
                    onSuccess()
                } else {
                    errorMessage = getLocalizedErrorMessage(result.exceptionOrNull(), language)
                }
            } finally {
                isLoading = false
            }
        }
    }
    fun signOut() {
        repository.signOut()
    }
}
// Translator for Firebase errors
private fun getLocalizedErrorMessage(exception: Throwable?, language: String): String {

    exception?.printStackTrace()

    val errorCode = (exception as? FirebaseAuthException)?.errorCode ?: ""
    val errorMsg = exception?.message?.lowercase() ?: ""

    return when {
        errorCode == "ERROR_INVALID_EMAIL" || errorMsg.contains("badly formatted") -> when (language) {
            "English" -> "Invalid email format (e.g. user@mail.com)"
            "Български" -> "Невалиден формат на имейл"
            else -> "Неверный формат почты (пример: user@mail.com)"
        }
        errorCode == "ERROR_USER_NOT_FOUND" || errorCode == "ERROR_INVALID_CREDENTIAL" ||
                errorCode == "INVALID_LOGIN_CREDENTIALS" || errorMsg.contains("invalid credential") -> when (language) {
            "English" -> "Invalid email or password"
            "Български" -> "Грешен имейл или парола"
            else -> "Неверный email или пароль"
        }
        errorCode == "ERROR_EMAIL_ALREADY_IN_USE" || errorMsg.contains("already in use") -> when (language) {
            "English" -> "Email is already registered"
            "Български" -> "Имейлът вече е регистриран"
            else -> "Этот Email уже зарегистрирован"
        }
        errorCode == "ERROR_WEAK_PASSWORD" || errorMsg.contains("weak password") -> when (language) {
            "English" -> "Password is too weak (min 6 characters)"
            "Български" -> "Паролата е твърде слаба (минимум 6 знака)"
            else -> "Слишком простой пароль (минимум 6 символов)"
        }
        errorMsg.contains("network") || errorMsg.contains("timeout") -> when (language) {
            "English" -> "Network error. Please check connection."
            "Български" -> "Мрежова грешка. Проверете връзката."
            else -> "Ошибка сети. Проверьте подключение."
        }
        else -> when (language) {
            "English" -> "Error: ${exception?.localizedMessage ?: "Try again"}"
            "Български" -> "Възникна грешка. Опитайте отново."
            else -> "Ошибка: ${exception?.localizedMessage ?: "Попробуйте снова"}"
        }
    }
}