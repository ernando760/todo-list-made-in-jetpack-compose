package com.ernando.jetpack_compose_todo_list.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = PrimaryDarkColor,
    onPrimary = OnPrimaryDarkColor,
    secondary = SecondaryDarkColor,
    onSecondary = OnSecondaryDarkColor,
    tertiary = TertiaryDarkColor,
    onTertiary = OnTertiaryDarkColor,
    primaryContainer = PrimaryContainerDarkColor,
    onPrimaryContainer = OnPrimaryContainerDarkColor,
    secondaryContainer = SecondaryContainerDarkColor,

    onSecondaryContainer = OnSecondaryContainerDarkColor
)

private val LightColorScheme = lightColorScheme(
    primary = PrimaryLightColor,
    onPrimary = OnPrimaryLightColor,
    secondary = SecondaryLightColor,
    onSecondary = OnSecondaryLightColor,
    tertiary = TertiaryLightColor,
    onTertiary = OnTertiaryLightColor,
    primaryContainer = PrimaryContainerLightColor,
    onPrimaryContainer = OnPrimaryContainerLightColor,
    secondaryContainer = SecondaryContainerLightColor,
    onSecondaryContainer = OnSecondaryContainerLightColor,

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

@Composable
fun JetpackComposeTodoListTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}