// In: ui/theme/Theme.kt

package com.rahul.auric.auricsnap.ui.theme

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

// Using our new custom colors to define the Dark Color Scheme
private val DarkColorScheme = darkColorScheme(
    primary = VibrantTeal,
    background = SlateGray,
    surface = SurfaceGray,
    onPrimary = SlateGray,    // Text/icons on a primary-colored button will be dark
    onBackground = TextWhite, // Text/icons on the background will be white
    onSurface = TextWhite     // Text/icons on surfaces (like cards) will be white
)

@Composable
fun AuricSnapTheme(
    darkTheme: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = DarkColorScheme
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            // Make the system status bar match our new background color
            window.statusBarColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}