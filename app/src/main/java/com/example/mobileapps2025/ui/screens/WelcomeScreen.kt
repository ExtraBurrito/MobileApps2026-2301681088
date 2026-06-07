package com.example.mobileapps2025.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun WelcomeScreen (
    currentLanguage: String,
    onLanguageChange: (String) -> Unit,
    onNavigateToLogin: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val languages = listOf("English", "Русский", "Български")

    val titleText = "Musify"
    val subtitleText = when (currentLanguage) {
        "English" -> "Track your favorite bands and artists"
        "Български" -> "Проследявай любимите си групи и изпълнители"
        else -> "Отслеживай любимые группы и артистов"
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = titleText,
            fontSize = 48.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = subtitleText,
            fontSize = 18.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 32.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))

        Box{
            OutlinedButton(onClick = {expanded = true}) {
                Text(currentLanguage)
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = {expanded = false}

            ) {
                languages.forEach { lang ->
                    DropdownMenuItem(
                        text = {Text(lang)},
                        onClick = {
                            onLanguageChange(lang) // Save chosen language
                            expanded = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(64.dp))

        FilledIconButton(
            onClick = onNavigateToLogin,
            modifier = Modifier.size(64.dp)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = "Go to login",
                modifier = Modifier.size(32.dp)
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, device = "id:pixel_8_pro")
@Composable
fun WelcomeScreenPreview() {
    MaterialTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            WelcomeScreen(
                currentLanguage = "English",
                onLanguageChange = {},
                onNavigateToLogin = {}
            )
        }
    }
}

