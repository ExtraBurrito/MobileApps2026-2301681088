package com.example.mobileapps2025.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobileapps2025.ui.viewmodel.AuthViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun SignupScreen(
    currentLanguage: String,
    authViewModel: AuthViewModel = viewModel(),
    onNavigateToLogin: () -> Unit,
    onSignupSuccess: () -> Unit
){
    SignupScreenContent(
        currentLanguage = currentLanguage,
        errorMessage = authViewModel.errorMessage,
        isLoading = authViewModel.isLoading,
        onSignupClick = { email, password, username ->
            authViewModel.signUp(email, password, username, onSignupSuccess)
        },
        onNavigateToLogin = onNavigateToLogin
    )
}

@Composable
fun SignupScreenContent(
    currentLanguage: String,
    errorMessage: String?,
    isLoading: Boolean,
    onSignupClick: (String, String, String) -> Unit,
    onNavigateToLogin: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val usernamelabel = when(currentLanguage) {
        "English" -> "Username"
        "Български" -> "Потребителско име"
        else -> "Имя пользователя"
    }
    val emaillabel = when(currentLanguage) {
        "English" -> "Email"
        "Български" -> "Имейл"
        else -> "Адрес электронной почты"
    }
    val passwordLabel = when(currentLanguage) {
        "English" -> "Password"
        "Български" -> "Парола"
        else -> "Пароль"
    }
    val registrationButtonText = when(currentLanguage) {
        "English" -> "Register"
        "Български" -> "Регистрация"
        else -> "Зарегистрироваться"
    }
    val backToLoginText = when(currentLanguage) {
        "English" -> "Already have an account? Log in"
        "Български" -> "Вече имате акаунт? Вход"
        else -> "Уже есть аккаунт? Войти"
    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(30.dp)
        ) {
            Text(
                text = "Musify",
                fontSize = 56.sp,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(bottom = 32.dp),
                fontWeight = FontWeight.Bold
            )

            Card(
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                ),
                modifier = Modifier.width(400.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Email
                    Column(horizontalAlignment = Alignment.Start, modifier = Modifier.fillMaxWidth()) {
                        Text(text = emaillabel, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = email,
                            onValueChange = { email = it },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp),
                            singleLine = true,
                            isError = errorMessage != null
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Username
                    Column(horizontalAlignment = Alignment.Start, modifier = Modifier.fillMaxWidth()) {
                        Text(text = usernamelabel, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = username,
                            onValueChange = { username = it },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp),
                            singleLine = true,
                            isError = errorMessage != null
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Password
                    Column(horizontalAlignment = Alignment.Start, modifier = Modifier.fillMaxWidth()) {
                        Text(text = passwordLabel, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = password,
                            onValueChange = { password = it },
                            visualTransformation = PasswordVisualTransformation(),
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp),
                            singleLine = true,
                            isError = errorMessage != null
                        )
                    }

                    // Server error
                    if (errorMessage != null) {
                        Text(
                            text = errorMessage,
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(36.dp))

                    // reg button
                    Button(
                        onClick = {
                            onSignupClick(
                                email.trim(),
                                password.trim(),
                                username.trim())
                        },
                        modifier = Modifier.fillMaxWidth().height(50.dp),
                        shape = RoundedCornerShape(25.dp),
                        enabled = !isLoading // block button when loading
                    ) {
                        if (isLoading) {
                            CircularProgressIndicator(color = MaterialTheme.colorScheme.onPrimary, modifier = Modifier.size(24.dp))
                        } else {
                            Text(text = registrationButtonText, fontSize = 18.sp)
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // return to login
                    TextButton(onClick = onNavigateToLogin) {
                        Text(text = backToLoginText, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, device = "id:pixel_6_pro", showSystemUi = true)
@Composable
fun SignupScreenPreview() {
    MaterialTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            SignupScreenContent(
                currentLanguage = "English",
                errorMessage = null,
                isLoading = false,
                onSignupClick = { _, _, _ -> },
                onNavigateToLogin = {}
            )
        }
    }
}