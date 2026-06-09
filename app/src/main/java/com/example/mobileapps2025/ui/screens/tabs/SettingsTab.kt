package com.example.mobileapps2025.ui.screens.tabs

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SettingsTab(
    currentLanguage: String,
    currentTheme: String,
    onThemeChange: (String) -> Unit,
    onLanguageChange: (String) -> Unit
) {
    val title = when (currentLanguage) {
        "English" -> "Settings"
        "Български" -> "Настройки"
        else -> "Настройки"
    }
    val themeLabel = when (currentLanguage) {
        "English" -> "Theme"
        "Български" -> "Тема"
        else -> "Тема"
    }
    val languageLabel = when (currentLanguage) {
        "English" -> "Language"
        "Български" -> "Език"
        else -> "Язык"
    }

    val themeAuto = when(currentLanguage) {
        "English" -> "Auto"
        "Български" -> "Авто"
        else -> "Авто"
    }
    val themeLight = when(currentLanguage) {
        "English" -> "Light"
        "Български" -> "Светла"
        else -> "Светлая"
    }
    val themeDark = when(currentLanguage) {
        "English" -> "Dark"
        "Български" -> "Тъмна"
        else -> "Темная"
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(text = title, fontSize = 32.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 16.dp, bottom = 24.dp))


        Text(text = themeLabel, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold, fontSize = 16.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            listOf("auto" to themeAuto, "light" to themeLight, "dark" to themeDark).forEach { (mode, label) ->
                FilterChip(
                    selected = currentTheme == mode,
                    onClick = { onThemeChange(mode) },
                    label = { Text(label) },
                    modifier = Modifier.weight(1f)
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))


        Text(text = languageLabel, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold, fontSize = 16.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            listOf("Русский", "English", "Български").forEach { lang ->
                FilterChip(
                    selected = currentLanguage == lang,
                    onClick = { onLanguageChange(lang) },
                    label = { Text(lang) },
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}
