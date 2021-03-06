package com.example.stattrack.presentation.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.ProvideTextStyle
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle


private val defaultColors = lightColors(
    primary = PrimaryBlue,
    background = PrimaryWhite
    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun StattrackTheme(
    content: @Composable() () -> Unit)
{
    MaterialTheme(
        colors = defaultColors,
        typography = Typography,
        shapes = Shapes,
        content = {
            ProvideTextStyle(value = TextStyle(color= PrimaryBlue),
            content = content
            )
        }
    )
}