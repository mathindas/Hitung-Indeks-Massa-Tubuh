package com.rivaldo.hitungindeksmassatubuh

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.rivaldo.hitungindeksmassatubuh.util.BmiCalculator

/**
 * Class defining the screens we have in the app: home, article details and interests
 */
sealed class Screen {
    object Home : Screen()
}

object ComposeStatus {
    var currentScreen by mutableStateOf<Screen>(Screen.Home)
}
