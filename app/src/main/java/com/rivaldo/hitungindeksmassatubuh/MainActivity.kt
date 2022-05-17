package com.rivaldo.hitungindeksmassatubuh

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.Crossfade
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.rivaldo.hitungindeksmassatubuh.ui.HomeScreen
import com.rivaldo.hitungindeksmassatubuh.ui.theme.AppTheme

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                ComposeApp()
            }
        }
    }

    override fun onBackPressed() {
        if (ComposeStatus.currentScreen == Screen.Home) {
            super.onBackPressed()
        }
    }
}

@Composable
fun ComposeApp() {
    AppTheme {
        AppContent()
    }
}

@Preview(showSystemUi = true)
@Composable
fun AppContent() {
    Crossfade(ComposeStatus.currentScreen) { screen ->
        Surface(color = MaterialTheme.colors.background) {
            when (screen) {
                is Screen.Home -> HomeScreen()
            }
        }
    }
}